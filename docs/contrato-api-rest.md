# Especificación Técnica y Contrato de API REST

**Proyecto:** ReUtiliza  
**Integración:** Servicio Web REST con Retrofit 2.9.0

---

## 1. Documento Breve del Contrato API

Detalle del consumo de la API REST pública para la obtención de consejos ecológicos (*Eco-Tips Service*).

| Componente               | Especificación                                                        |
|--------------------------|-----------------------------------------------------------------------|
| **Servicio consumido**   | API REST pública de Consejos Ecológicos (Eco-Tips Service)            |
| **URL Base (BASE_URL)**  | `https://jsonplaceholder.typicode.com/`                               |
| **Endpoint**             | `/posts`                                                              |
| **Método HTTP**          | `GET`                                                                 |
| **Parámetros de consulta** | `_limit=5` — limita la respuesta a los primeros 5 registros        |
| **Cabeceras (Headers)**  | `Accept: application/json`                                            |
| **Autenticación**        | Ninguna (API pública)                                                 |
| **Respuesta esperada**   | Código HTTP `200 OK` con un arreglo JSON                              |

### Ejemplo de request

```
GET https://jsonplaceholder.typicode.com/posts?_limit=5
```

### Códigos de respuesta HTTP

| Código | Descripción |
|--------|-------------|
| `200 OK` | Solicitud exitosa. Retorna un arreglo JSON con los eco-tips. |
| `404 Not Found` | El recurso solicitado no existe en el servidor. |
| `500 Internal Server Error` | Error interno del servidor remoto. |
| Sin conexión | La app maneja el fallo silenciosamente (lista vacía). |

---

## 2. Modelo de Datos JSON Utilizado

### Ejemplo de respuesta JSON de la API

```json
[
  {
    "id": 1,
    "title": "Reduce el uso de plásticos de un solo uso",
    "body": "Opta por botellas reutilizables y bolsas de tela para tus compras diarias y contribuye a la meta ODS 12."
  },
  {
    "id": 2,
    "title": "Economía circular en tu comunidad",
    "body": "Reutilizar y donar bienes en desuso mitiga la generación de residuos sólidos urbanos en vertederos."
  },
  {
    "id": 3,
    "title": "Ahorra energía en el hogar",
    "body": "Desconecta los aparatos electrónicos cuando no los uses y aprovecha la luz natural durante el día."
  },
  {
    "id": 4,
    "title": "Consume productos locales",
    "body": "Comprar productos locales reduce la huella de carbono del transporte y apoya a los productores de tu región."
  },
  {
    "id": 5,
    "title": "Separa y recicla correctamente",
    "body": "Clasifica tus residuos en orgánicos, reciclables y no reciclables para facilitar su tratamiento."
  }
]
```

### Descripción de campos del objeto JSON

| Campo      | Tipo    | Descripción                              | Usado en la app              |
|------------|---------|------------------------------------------|------------------------------|
| `userId`   | Integer | ID del usuario autor del post            | No (ignorado por Gson)       |
| `id`       | Integer | Identificador único del eco-tip          | Sí                           |
| `title`    | String  | Título del consejo ecológico             | Sí — se muestra en la tarjeta |
| `body`     | String  | Descripción del consejo ecológico        | Sí — se muestra en la tarjeta |

### Modelo DTO en Kotlin — `EcoTipDto.kt`

```kotlin
package com.reutiliza.app.data.remote

data class EcoTipDto(
    val id: Int,
    val title: String,
    val body: String
)
```

> Los campos del JSON que no están declarados en el DTO (como `userId`) son descartados automáticamente por Gson sin generar ningún error.

---

## 3. Evidencias de Consumo Exitoso de la API

### Interfaz del servicio Retrofit — `EcoApiService.kt`

```kotlin
package com.reutiliza.app.data.remote

import retrofit2.http.GET

interface EcoApiService {

    @GET("posts?_limit=5")
    suspend fun getEcoTips(): List<EcoTipDto>

    companion object {
        const val BASE_URL = "https://jsonplaceholder.typicode.com/"
    }
}
```

### Configuración del cliente Retrofit en Hilt — `AppModule.kt`

```kotlin
@Provides @Singleton
fun provideEcoApiService(): EcoApiService {
    return Retrofit.Builder()
        .baseUrl(EcoApiService.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(EcoApiService::class.java)
}
```

### Consumo desde el ViewModel — `HomeViewModel.kt`

```kotlin
fun loadEcoTips() {
    viewModelScope.launch {
        runCatching {
            ecoApiService.getEcoTips()
        }.onSuccess { tips ->
            _ecoTips.value = tips
        }
    }
}
```

### Flujo de datos completo

```
HomeScreen (Compose UI)
    │
    ▼
HomeViewModel  ──── llama loadEcoTips()
    │
    ▼
EcoApiService  ──── @GET("posts?_limit=5")
    │
    ▼
https://jsonplaceholder.typicode.com/posts?_limit=5
    │
    ▼  respuesta JSON 200 OK
GsonConverterFactory  ──── deserializa a List<EcoTipDto>
    │
    ▼
StateFlow<List<EcoTipDto>>  ──── observado por la UI
```

### Captura — Respuesta JSON en tiempo de ejecución

> 📸 *Insertar captura del Logcat o Network Inspector de Android Studio mostrando la respuesta HTTP 200 OK de la API*

---

## 4. Capturas de la Aplicación

### Pantalla principal con Eco-Tips obtenidos desde el servicio web

> 📸 *Insertar captura de la app — sección "🌍 Eco-Tips (API REST)" con el badge "Conectado" y las tarjetas con datos cargados*

### Carrusel de Eco-Tips cargados dinámicamente

> 📸 *Insertar captura del carrusel horizontal mostrando título y descripción de cada item obtenido desde la API*

---

## 5. Dependencias y Permisos

### Dependencias Retrofit — `libs.versions.toml`

```toml
[versions]
retrofit = "2.9.0"

[libraries]
retrofit                = { group = "com.squareup.retrofit2", name = "retrofit",       version.ref = "retrofit" }
retrofit-converter-gson = { group = "com.squareup.retrofit2", name = "converter-gson", version.ref = "retrofit" }
```

### Declaración en `app/build.gradle.kts`

```kotlin
// Retrofit (consumo de API REST)
implementation(libs.retrofit)
implementation(libs.retrofit.converter.gson)
```

### Permisos de red — `AndroidManifest.xml`

```xml
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
```
