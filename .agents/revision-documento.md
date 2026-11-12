# Revisión de Consistencia: Documento de Contexto vs. Código Real

**Proyecto:** ReUtiliza Android  
**Fecha de revisión:** 2025  
**Archivos revisados:** 7 archivos indicados + `ProductRepository.kt`, `AuthRepository.kt`, `UserRepository.kt`, `ImageUtils.kt`

---

## Resumen ejecutivo

De los 8 puntos verificados, **4 son INCORRECTOS** (no existen en el código), **2 son PARCIALES** (existen pero de forma diferente a lo descrito), y **2 son CORRECTOS**. El documento sobreestima la sofisticación técnica de la capa de red y de los ViewModels. Los patrones descritos (`AuthInterceptor`, `safeCall`, `ApiResult`, `isOffline`, `successMessage`, paginación y compresión de imágenes) son aspiracionales — no están implementados en el código actual.

---

## Análisis por punto

### Punto 1 — AuthInterceptor con OkHttp que inyecta JWT automáticamente
**Veredicto: INCORRECTO**

**Lo que dice el documento:** Existe un `AuthInterceptor` que intercepta todas las llamadas HTTP y agrega el JWT en el header `Authorization` automáticamente.

**Lo que dice el código:**
- `AppModule.kt` solo configura un `HttpLoggingInterceptor`. No hay ningún `AuthInterceptor` registrado ni en el módulo ni en ningún otro archivo `.kt` del proyecto.
- El token se inyecta **manualmente** en cada método de la API con `@Header("Authorization")`. Ejemplos en `ReUtilizaApi.kt`:
  ```kotlin
  @POST("api/products")
  suspend fun createProduct(@Header("Authorization") token: String, ...)
  
  @GET("api/users/{userId}")
  suspend fun getUser(@Header("Authorization") token: String, ...)
  ```
- `SessionManager.kt` tiene el método helper `bearerToken(token)` que los repositorios llaman explícitamente en cada petición.

**Impacto:** Cada endpoint protegido tiene que manejar el token por su cuenta. Si se agrega un nuevo endpoint, hay que recordar agregar el `@Header` manualmente. El `AuthInterceptor` sería la forma correcta pero no está implementado.

---

### Punto 2 — Funciones `safeCall` que retornan `ApiResult`
**Veredicto: INCORRECTO**

**Lo que dice el documento:** Existe una abstracción `safeCall` y un tipo `ApiResult` para el manejo uniforme de errores de red.

**Lo que dice el código:**
- Búsqueda en todo el proyecto: cero ocurrencias de `safeCall` o `ApiResult`.
- Los repositorios usan directamente `runCatching { }` de la stdlib de Kotlin, que retorna `Result<T>` (no `ApiResult`). Ejemplo en `AuthRepository.kt`:
  ```kotlin
  suspend fun login(email: String, password: String): Result<User> = runCatching {
      val response = api.login(LoginRequest(email, password))
      ...
  }
  ```
- Los ViewModels consumen con `.fold(onSuccess = ..., onFailure = ...)` sobre `Result<T>` estándar.

**Impacto:** `Result<T>` funciona igual para el propósito, pero el documento describe una API personalizada que no existe. No es un problema funcional, solo una inconsistencia en la descripción.

---

### Punto 3 — `isOffline` como estado reactivo
**Veredicto: INCORRECTO**

**Lo que dice el documento:** Existe un `StateFlow<Boolean>` llamado `isOffline` que detecta la conectividad de red de forma reactiva.

**Lo que dice el código:**
- Búsqueda en todo el proyecto: cero ocurrencias de `isOffline`.
- Ningún ViewModel tiene detección de conectividad. `HomeViewModel` tiene `isLoading: StateFlow<Boolean>` pero no `isOffline`.
- `ExploreViewModel` tampoco tiene manejo offline; cuando la llamada falla, el `onFailure` hace un comentario vacío `/* manejo de error */`.
- No hay uso de `ConnectivityManager` ni `NetworkCallback` en ningún archivo.

**Impacto:** El documento implica que la app maneja el estado offline graciosamente. En la realidad, las fallas de red resultan en listas vacías o errores silenciosos.

---

### Punto 4 — `successMessage` como estado
**Veredicto: INCORRECTO**

**Lo que dice el documento:** Existe un `StateFlow<String?>` llamado `successMessage` en los ViewModels para comunicar mensajes de éxito a la UI.

**Lo que dice el código:**
- Búsqueda en todo el proyecto: cero ocurrencias de `successMessage`.
- Los ViewModels modelan el éxito como un estado dentro de un `sealed class`. Ejemplo en `PublishViewModel.kt`:
  ```kotlin
  sealed class PublishUiState {
      object Idle    : PublishUiState()
      object Loading : PublishUiState()
      object Success : PublishUiState()
      data class Error(val message: String) : PublishUiState()
  }
  ```
- `AuthViewModel` usa `AuthUiState.Success(val user: User? = null)`.

**Impacto:** La UI puede mostrar feedback de éxito verificando el estado del `sealed class`. No es una limitación funcional, pero el mecanismo es diferente al descrito.

---

### Punto 5 — 4 objetivos específicos (OE1 a OE4)
**Veredicto: PARCIAL**

**Lo que dice el documento:** El proyecto tiene exactamente 4 objetivos específicos (OE1 a OE4).

**Lo que dice el código/documento real:**
- `INFORME_FINAL.md` (líneas 591–598) lista **5 objetivos específicos**: OE1 (publicación), OE2 (gamificación), OE3 (chat), OE4 (perfil de impacto), y **OE5 (búsqueda y filtrado)**.
- El documento de contexto omite OE5.

**Impacto:** Menor, pero el documento de contexto está incompleto al describir el alcance del proyecto.

---

### Punto 6 — Firebase Auth, Firestore y Storage como arquitectura complementaria
**Veredicto: PARCIAL**

**Lo que dice el documento:** Firebase Auth, Firestore y Storage están configurados como arquitectura complementaria al backend REST.

**Lo que dice el código:**
- `AppModule.kt` provisiona los tres: `FirebaseAuth.getInstance()`, `FirebaseFirestore.getInstance()`, `FirebaseStorage.getInstance()`. Están declarados como `@Singleton`.
- **Firestore** se usa activamente para el chat en tiempo real (`ChatRepository.kt` — confirmado por `INFORME_FINAL.md` línea 595).
- **FirebaseAuth** se usa **solo en la UI** (no en la lógica de negocio): `TopBarReUtiliza.kt`, `ProfileScreen.kt` y `ProductDetailScreen.kt` llaman a `FirebaseAuth.getInstance().currentUser` directamente, sin pasar por el `AuthRepository` o un ViewModel.
- **FirebaseStorage** está provisionado en `AppModule.kt` pero no se usa en ningún repository ni ViewModel. Las imágenes se envían al backend como `ByteArray` vía REST (`PublishViewModel.kt` línea ~67: `context.contentResolver.openInputStream(uri)?.use { it.readBytes() }`).
- La autenticación principal va por el backend REST (`AuthRepository` → `api.login()`), no por Firebase Auth.

**Impacto real:** Firebase Storage está declarado pero sin uso real. Firebase Auth se usa de forma directa en la UI (sin inyección de dependencias), lo que es un anti-patrón en la arquitectura MVVM declarada.

---

### Punto 7 — Paginación implementada en las consultas
**Veredicto: INCORRECTO**

**Lo que dice el documento:** Las consultas tienen paginación implementada.

**Lo que dice el código:**
- `ReUtilizaApi.kt`: el endpoint `getProducts` acepta `@Query("limit") limit: Int = 20` — solo un límite fijo, no paginación.
- `searchProducts` no tiene parámetros de página ni offset.
- `ProductRepository.kt` llama a `api.getProducts(limit.toInt())` con el límite como único control.
- Búsqueda en todo el proyecto: cero ocurrencias de `@Query("page")`, `@Query("offset")`, `@Query("cursor")` o parámetros equivalentes.

**Impacto:** La app carga un máximo de 20 productos sin capacidad de cargar más. No hay scroll infinito ni "load more".

---

### Punto 8 — Compresión de imágenes al subir
**Veredicto: INCORRECTO**

**Lo que dice el documento:** Las imágenes se comprimen antes de ser subidas.

**Lo que dice el código:**
- `PublishViewModel.kt` (líneas ~64–68): lee los bytes crudos de la imagen directamente:
  ```kotlin
  val imageBytes = selectedImages.value.mapNotNull { uri ->
      runCatching {
          context.contentResolver.openInputStream(uri)?.use { it.readBytes() }
      }.getOrNull()
  }
  ```
  No hay redimensionamiento, compresión ni procesamiento de ningún tipo.
- `ImageUtils.kt`: solo contiene `rememberImageModel()`, un helper para decodificar imágenes base64 al **mostrarlas**, no al subirlas.
- `ProductRepository.kt` recibe el `List<ByteArray>` pero tampoco comprime: crea el `CreateProductRequest` con metadatos del producto, pero **los bytes de imagen no se envían en la request actual** (no están en `CreateProductRequest`).

**Impacto doble:** (a) No hay compresión, lo que puede causar uploads lentos o fallos con imágenes grandes. (b) Los bytes de imagen se leen en el ViewModel pero no se incluyen en la llamada REST, por lo que las imágenes efectivamente **no se suben**.

---

## Tabla resumen

| # | Afirmación del documento | Veredicto | Nombre real en el código |
|---|--------------------------|-----------|--------------------------|
| 1 | `AuthInterceptor` que inyecta JWT | **INCORRECTO** | No existe; token vía `@Header` manual en cada método |
| 2 | `safeCall` / `ApiResult` | **INCORRECTO** | No existen; se usa `runCatching` → `Result<T>` de Kotlin stdlib |
| 3 | `isOffline` como estado reactivo | **INCORRECTO** | No existe; no hay manejo offline |
| 4 | `successMessage` como estado | **INCORRECTO** | No existe; se usa `sealed class PublishUiState / AuthUiState` |
| 5 | 4 objetivos específicos (OE1–OE4) | **PARCIAL** | En realidad son 5 (OE1–OE5); falta OE5 en el documento |
| 6 | Firebase Auth/Firestore/Storage complementario | **PARCIAL** | Firestore: sí (chat). Storage: provisionado pero sin uso. Auth: solo en UI, no en arquitectura |
| 7 | Paginación en consultas | **INCORRECTO** | Solo hay `limit=20`; sin `page`, `offset` ni cursor |
| 8 | Compresión de imágenes al subir | **INCORRECTO** | No hay compresión; además los bytes leídos no se envían en la request REST |

---

## Conclusiones

El documento de contexto describe una arquitectura más completa y robusta que la implementada. Los puntos incorrectos no son errores de implementación graves — el proyecto funciona — pero generan una brecha entre la documentación y la realidad que puede confundir a quien lea el documento esperando encontrar esas abstracciones.

Los dos problemas más relevantes son:

1. **Las imágenes no se suben realmente** (punto 8): `PublishViewModel` lee los bytes pero `CreateProductRequest` no los incluye. Si el backend no recibe las imágenes por otro canal, los productos se crean sin fotos.

2. **Firebase Auth en la UI sin pasar por la arquitectura** (punto 6): `FirebaseAuth.getInstance().currentUser` se llama directamente en Composables, saltando el `AuthRepository` y el `AuthViewModel`. Esto rompe la separación de capas.

---

## Recomendaciones (sin implementar)

1. **AuthInterceptor (prioridad media):** Crear `AuthInterceptor : Interceptor` que lea el token de `SessionManager` vía corrutina bloqueante y lo agregue en `addInterceptor()` en `AppModule`. Esto elimina los `@Header` manuales en toda la API.

2. **Imágenes en publish (prioridad alta):** Decidir si las imágenes van al backend REST (agregar campo `images: List<String>` en base64 en `CreateProductRequest`) o a Firebase Storage (usar el `FirebaseStorage` ya provisionado desde `ProductRepository`). Actualmente no llegan por ninguna vía.

3. **Compresión de imágenes (prioridad media):** Si se elige enviar por REST, agregar compresión antes de encodear a base64:
   ```kotlin
   BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
       .compress(Bitmap.CompressFormat.JPEG, 70, outputStream)
   ```

4. **Firebase Auth en la arquitectura (prioridad baja):** Mover las llamadas a `FirebaseAuth.getInstance().currentUser` a `AuthRepository` o un `AuthViewModel`, no llamarlas desde Composables.

5. **Actualizar el documento:** Corregir los 6 puntos incorrectos/parciales. En particular: cambiar `safeCall/ApiResult` → `runCatching/Result<T>`, eliminar `isOffline` y `successMessage`, agregar OE5, y describir el estado real de Firebase Storage.
