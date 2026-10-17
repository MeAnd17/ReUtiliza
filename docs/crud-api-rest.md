# CRUD con API REST — ReUtiliza
## Integración Retrofit + Manejo de estados

---

## 1. Operaciones CRUD implementadas

La aplicación consume la API pública **JSONPlaceholder** (`https://jsonplaceholder.typicode.com/`) para gestionar Eco-Tips mediante las cuatro operaciones CRUD.

### Tabla de endpoints

| Operación | Método | Endpoint              | Descripción                        |
|-----------|--------|-----------------------|------------------------------------|
| **Read**  | GET    | `/posts?_limit=5`     | Obtiene los primeros 5 eco-tips    |
| **Read One** | GET | `/posts/{id}`         | Obtiene un eco-tip por ID          |
| **Create**| POST   | `/posts`              | Crea un nuevo eco-tip              |
| **Update**| PUT    | `/posts/{id}`         | Actualiza un eco-tip existente     |
| **Delete**| DELETE | `/posts/{id}`         | Elimina un eco-tip por ID          |

### Interfaz Retrofit — `EcoApiService.kt`

```kotlin
interface EcoApiService {

    @GET("posts?_limit=5")
    suspend fun getEcoTips(): Response<List<EcoTipDto>>

    @GET("posts/{id}")
    suspend fun getEcoTipById(@Path("id") id: Int): Response<EcoTipDto>

    @POST("posts")
    suspend fun createEcoTip(@Body request: EcoTipRequest): Response<EcoTipDto>

    @PUT("posts/{id}")
    suspend fun updateEcoTip(
        @Path("id") id: Int,
        @Body request: EcoTipRequest
    ): Response<EcoTipDto>

    @DELETE("posts/{id}")
    suspend fun deleteEcoTip(@Path("id") id: Int): Response<Unit>

    companion object {
        const val BASE_URL = "https://jsonplaceholder.typicode.com/"
    }
}
```

### DTOs utilizados

```kotlin
// Respuesta de la API
data class EcoTipDto(
    val id: Int,
    val title: String,
    val body: String,
    val userId: Int = 1
)

// Cuerpo del request para POST y PUT
data class EcoTipRequest(
    val title: String,
    val body: String,
    val userId: Int = 1
)
```

---

## 2. Manejo de errores y conectividad

Se implementó un sellado `ApiResult` que clasifica cada posible resultado de una llamada a la API:

```kotlin
sealed class ApiResult<out T> {
    data class Success<T>(val data: T) : ApiResult<T>()
    data class Error(val message: String, val type: ErrorType) : ApiResult<Nothing>()
    object Loading : ApiResult<Nothing>()
}

enum class ErrorType {
    NO_CONNECTION,   // sin internet (UnknownHostException)
    TIMEOUT,         // servidor tardó demasiado (SocketTimeoutException)
    NOT_FOUND,       // respuesta HTTP 404
    SERVER_ERROR,    // respuesta HTTP 5xx
    UNKNOWN          // cualquier otro error
}
```

El repositorio `EcoTipRepository` envuelve todas las llamadas en un bloque `safeCall` que captura excepciones de red y las convierte en `ApiResult.Error` con un mensaje legible para el usuario:

```kotlin
private suspend fun <T> safeCall(block: suspend () -> ApiResult<T>): ApiResult<T> {
    return try {
        block()
    } catch (e: UnknownHostException) {
        ApiResult.Error("Sin conexión a internet. Verificá tu red.", ErrorType.NO_CONNECTION)
    } catch (e: SocketTimeoutException) {
        ApiResult.Error("El servidor tardó demasiado en responder.", ErrorType.TIMEOUT)
    } catch (e: Exception) {
        ApiResult.Error("Error inesperado: ${e.localizedMessage}", ErrorType.UNKNOWN)
    }
}
```

---

## 3. Estados de carga en la UI

El `EcoTipsViewModel` expone un único `StateFlow<EcoTipsUiState>` que la pantalla observa:

```kotlin
data class EcoTipsUiState(
    val tips: List<EcoTipDto>   = emptyList(),
    val isLoading: Boolean      = false,   // muestra CircularProgressIndicator
    val errorMessage: String?   = null,    // muestra Snackbar de error
    val successMessage: String? = null,    // muestra Snackbar de éxito
    val isOffline: Boolean      = false    // muestra pantalla sin conexión
)
```

La `EcoTipsScreen` renderiza un estado distinto según el valor:

| Estado         | Lo que ve el usuario                                          |
|----------------|---------------------------------------------------------------|
| `isLoading`    | `CircularProgressIndicator` + texto "Conectando..."           |
| `isOffline`    | Ícono WiFi desactivado + botón "Reintentar"                   |
| `tips.isEmpty` | Pantalla vacía + texto "Presioná + para crear el primero"     |
| Datos cargados | Lista de tarjetas con opciones Editar y Eliminar              |
| Error          | `Snackbar` con el mensaje del error                           |
| Éxito          | `Snackbar` con confirmación de la operación                   |

---

## 4. Capturas de la aplicación

### 4.1 Estado de carga (GET en progreso)
> [ Insertar captura ]

### 4.2 Lista de Eco-Tips cargados desde la API (GET exitoso)
> [ Insertar captura ]

### 4.3 Diálogo de creación de un nuevo Eco-Tip (POST)
> [ Insertar captura ]

### 4.4 Diálogo de edición de un Eco-Tip existente (PUT)
> [ Insertar captura ]

### 4.5 Confirmación de eliminación (DELETE)
> [ Insertar captura ]

### 4.6 Estado sin conexión (error de red)
> [ Insertar captura — desactivar WiFi del emulador y presionar recargar ]

### 4.7 Snackbar de operación exitosa
> [ Insertar captura ]

---

## 5. Descripción de pruebas realizadas

| # | Prueba | Resultado esperado | Cómo probarla |
|---|--------|--------------------|---------------|
| 1 | GET lista de eco-tips | Se cargan 5 tarjetas desde la API | Abrir la pantalla Eco-Tips |
| 2 | POST nuevo eco-tip | El item aparece al inicio de la lista con ID 101 | Presionar FAB `+`, completar el formulario y guardar |
| 3 | PUT actualizar eco-tip | El título y descripción se actualizan en la tarjeta | Presionar ícono ✏️ en cualquier tarjeta y modificar |
| 4 | DELETE eco-tip | La tarjeta desaparece de la lista | Presionar ícono 🗑️ y confirmar |
| 5 | Sin conexión | Aparece pantalla con ícono WiFi y botón Reintentar | Desactivar WiFi del emulador y recargar la pantalla |
| 6 | Timeout | Snackbar con mensaje "El servidor tardó demasiado" | Simular con timeout reducido o red lenta en el emulador |
| 7 | Validación campos vacíos | No se envía el request, aparece Snackbar de error | Intentar guardar con título o descripción en blanco |

---

## 6. Arquitectura implementada

```
EcoTipsScreen (Compose)
    │  observa StateFlow<EcoTipsUiState>
    ▼
EcoTipsViewModel
    │  llama create / read / update / delete
    ▼
EcoTipRepository   ←── safeCall (manejo de errores y timeouts)
    │
    ▼
EcoApiService (Retrofit interface)
    │  @GET / @POST / @PUT / @DELETE
    ▼
OkHttpClient (timeout 15s + LoggingInterceptor)
    │
    ▼
https://jsonplaceholder.typicode.com/
```

---

## 7. Dependencias utilizadas

```toml
[versions]
retrofit = "2.9.0"
okhttp   = "4.12.0"

[libraries]
retrofit                = { group = "com.squareup.retrofit2", name = "retrofit",              version.ref = "retrofit" }
retrofit-converter-gson = { group = "com.squareup.retrofit2", name = "converter-gson",        version.ref = "retrofit" }
okhttp-logging          = { group = "com.squareup.okhttp3",   name = "logging-interceptor",   version.ref = "okhttp"   }
```

```kotlin
// app/build.gradle.kts
implementation(libs.retrofit)
implementation(libs.retrofit.converter.gson)
implementation(libs.okhttp.logging)
```
