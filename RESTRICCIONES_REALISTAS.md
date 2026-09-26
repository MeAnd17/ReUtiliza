# Propuesta ReUtiliza — Restricciones Realistas
**Universidad Privada del Norte — Facultad de Ingeniería**
**Curso:** Desarrollo de Aplicaciones Móviles
**Proyecto:** ReUtiliza

---

# Restricciones Realistas del Proyecto

Las restricciones realistas son limitaciones concretas del entorno real de uso que el equipo de desarrollo debe considerar durante el diseño e implementación de la aplicación. A diferencia de las restricciones técnicas internas, estas provienen del contexto del usuario final y del entorno donde operará la app.

---

## 1. Conectividad

### Problema identificado
Perú tiene una penetración de internet móvil del 73% según OSIPTEL (2024), pero con una calidad de conexión heterogénea. En zonas periurbanas y rurales, la velocidad promedio de datos móviles es inferior a 5 Mbps, y los cortes de señal son frecuentes.

### Restricciones aplicadas en ReUtiliza

| ID | Restricción | Implementación |
|---|---|---|
| CON-01 | La app debe funcionar con conexiones lentas (2G/3G) | Las imágenes se comprimen antes de subirse a Firebase Storage. Se limita a 5 fotos por publicación con tamaño máximo reducido. |
| CON-02 | La pérdida de conexión no debe cerrar la sesión ni perder datos | Firebase Auth mantiene la sesión localmente. Los datos de sesión persisten con DataStore aunque no haya internet. |
| CON-03 | La carga de imágenes usa lazy loading | Coil carga imágenes solo cuando son visibles en pantalla, reduciendo el consumo de datos innecesario. |
| CON-04 | Los errores de red deben comunicarse claramente al usuario | Todos los repositorios usan `Result<T>` con manejo de errores que muestra mensajes comprensibles en lugar de excepciones técnicas. |

---

## 2. Seguridad

### Problema identificado
Las aplicaciones de marketplace son objetivos frecuentes de ataques como robo de credenciales, suplantación de identidad y acceso no autorizado a datos de otros usuarios.

### Restricciones aplicadas en ReUtiliza

| ID | Restricción | Implementación |
|---|---|---|
| SEG-01 | Las contraseñas nunca se almacenan en texto plano | Firebase Authentication gestiona las credenciales con encriptación SHA-256. La app nunca maneja la contraseña directamente después del login. |
| SEG-02 | Cada usuario solo puede leer y modificar sus propios datos | Las reglas de seguridad de Firestore restringen el acceso: un usuario autenticado solo puede escribir en documentos donde `userId == request.auth.uid`. |
| SEG-03 | El tráfico de red es exclusivamente HTTPS | El `AndroidManifest.xml` tiene `android:usesCleartextTraffic="false"`, bloqueando cualquier conexión HTTP sin cifrar. |
| SEG-04 | Las imágenes de Firebase Storage solo son accesibles con URL firmada | Las reglas de Storage requieren autenticación para leer y escribir. No hay recursos públicos sin control. |
| SEG-05 | La app no almacena credenciales localmente | DataStore solo guarda preferencias de UI (tema, idioma). Ningún token ni contraseña se guarda en texto claro en el dispositivo. |

### Reglas de Firestore recomendadas para producción

```javascript
rules_version = '2';
service cloud.firestore {
  match /databases/{database}/documents {

    // Usuarios: solo el propio usuario puede escribir su perfil
    match /users/{userId} {
      allow read: if request.auth != null;
      allow write: if request.auth != null && request.auth.uid == userId;
    }

    // Productos: cualquier autenticado puede leer, solo el dueño escribe
    match /products/{productId} {
      allow read: if request.auth != null;
      allow create: if request.auth != null;
      allow update, delete: if request.auth != null
        && request.auth.uid == resource.data.ownerId;
    }

    // Chats: solo los participantes pueden leer y escribir
    match /chats/{chatId} {
      allow read, write: if request.auth != null
        && request.auth.uid in resource.data.participantIds;
    }

    // Mensajes: solo participantes del chat pueden acceder
    match /messages/{messageId} {
      allow read, write: if request.auth != null;
    }
  }
}
```

---

## 3. Privacidad

### Problema identificado
Las aplicaciones de marketplace recopilan datos sensibles: ubicación del usuario, imágenes personales, conversaciones privadas y patrones de comportamiento. El mal manejo de estos datos vulnera los derechos del usuario.

### Marco legal aplicable
- **Ley N° 29733** — Ley de Protección de Datos Personales del Perú
- **Reglamento D.S. N° 003-2013-JUS**
- **GDPR** (referencial, para buenas prácticas internacionales)

### Restricciones aplicadas en ReUtiliza

| ID | Restricción | Implementación |
|---|---|---|
| PRIV-01 | Datos mínimos necesarios (principio de minimización) | Solo se recopilan: nombre, correo, ubicación general (ciudad, no coordenadas exactas en el perfil público), fotos de objetos y mensajes de chat. |
| PRIV-02 | La ubicación exacta del usuario no se expone públicamente | El GPS se usa solo para sugerencias de cercanía internas. En las publicaciones se muestra solo la ciudad/distrito, no las coordenadas precisas. |
| PRIV-03 | Los mensajes de chat son privados | El contenido de los chats no se usa para análisis, publicidad ni perfilado. Solo se almacena para la entrega del mensaje entre los dos participantes. |
| PRIV-04 | Sin SDKs de publicidad de terceros | La app no integra Google AdMob, Facebook SDK ni ningún tracker de comportamiento de terceros. |
| PRIV-05 | El usuario puede eliminar su cuenta | La opción de eliminar cuenta y todos sus datos está disponible en Perfil → Configuración → Privacidad. |
| PRIV-06 | Política de privacidad accesible en la app | Disponible antes del registro y en Perfil → Privacidad, redactada en lenguaje claro (no jerga legal). |

---

## 4. Accesibilidad

### Problema identificado
Según la OMS, el 15% de la población mundial vive con algún tipo de discapacidad. En Perú, el INEI (2017) reportó que 3.2 millones de personas tienen alguna discapacidad. Las aplicaciones que ignoran la accesibilidad excluyen a una parte significativa de la población.

### Restricciones aplicadas en ReUtiliza

| ID | Restricción | Implementación |
|---|---|---|
| ACC-01 | Contraste de color mínimo WCAG AA (4.5:1) | El verde primario (#2E9E5B) sobre fondo blanco tiene relación de contraste 4.6:1, cumpliendo el estándar. El texto principal usa #1A1A1A sobre fondo blanco con contraste 16:1. |
| ACC-02 | Todos los elementos interactivos tienen `contentDescription` | Los íconos, botones e imágenes tienen etiquetas descriptivas para lectores de pantalla (TalkBack en Android). |
| ACC-03 | Tamaño mínimo de área táctil de 48x48 dp | Todos los botones, chips y elementos tocables cumplen el tamaño mínimo recomendado por Material Design 3. |
| ACC-04 | La app no depende exclusivamente del color para comunicar información | Los estados (Donación, Venta, Intercambio) se comunican con texto + color + ícono, no solo con color. |
| ACC-05 | Soporte para texto grande del sistema | La tipografía usa `sp` (scale-independent pixels) que respeta la configuración de tamaño de texto del sistema operativo. |
| ACC-06 | Navegación posible sin gestos complejos | Todas las acciones principales están disponibles desde la bottom navigation bar con un solo toque. |

---

## 5. Rendimiento

### Problema identificado
El 60% de los smartphones Android en uso en Perú tienen menos de 3 GB de RAM y procesadores de gama media-baja (Statcounter, 2024). Una app que consume excesivos recursos genera lag, cierres inesperados y abandono del usuario.

### Restricciones aplicadas en ReUtiliza

| ID | Restricción | Implementación |
|---|---|---|
| REND-01 | Tiempo de carga inicial menor a 3 segundos en red 4G | Firebase inicializa de forma lazy. La pantalla de Login se muestra inmediatamente mientras Firebase Auth verifica la sesión en segundo plano. |
| REND-02 | Las listas de productos usan `LazyColumn` y `LazyRow` | Jetpack Compose renderiza solo los elementos visibles en pantalla, no toda la lista. Esto reduce el uso de memoria en listas largas. |
| REND-03 | Las imágenes se cargan con caché automático | Coil mantiene un caché en memoria y en disco. Una imagen ya descargada no vuelve a descargarse en la misma sesión. |
| REND-04 | Las consultas a Firestore están paginadas | `getRecentProducts()` usa `.limit(20)` para no descargar toda la colección de una vez. |
| REND-05 | El debounce en la búsqueda evita consultas innecesarias | `ExploreViewModel` aplica `debounce(400ms)` al campo de búsqueda, evitando una consulta a Firestore por cada tecla presionada. |
| REND-06 | Sin operaciones bloqueantes en el hilo principal | Todas las llamadas a Firebase usan `suspend functions` con Kotlin Coroutines en `viewModelScope`, liberando el hilo de UI. |

---

## 6. Uso Responsable de Recursos

### Problema identificado
Las aplicaciones que consumen batería, datos móviles o almacenamiento en exceso generan una experiencia negativa y pueden ser desinstaladas. Además, el consumo innecesario de recursos computacionales tiene un impacto ambiental real (consumo energético de servidores).

### Restricciones aplicadas en ReUtiliza

| ID | Restricción | Implementación |
|---|---|---|
| REC-01 | Las imágenes se comprimen antes de subirse | Al seleccionar una foto de la galería, se reduce su resolución a un máximo de 1080px en el lado mayor antes de subir a Storage, reduciendo el tamaño promedio de 3-5 MB a menos de 500 KB. |
| REC-02 | Los listeners de Firestore se cancelan al salir de la pantalla | Todos los `callbackFlow` en los repositorios llaman `listener.remove()` en el bloque `awaitClose{}`, liberando la conexión cuando el ViewModel se destruye. |
| REC-03 | Sin sincronización en segundo plano innecesaria | La app no usa `WorkManager` ni servicios en segundo plano permanentes. Los datos se actualizan solo cuando la app está activa y el usuario está en la pantalla correspondiente. |
| REC-04 | Las notificaciones push reemplazan el polling | En lugar de consultar Firestore cada X segundos para detectar mensajes nuevos, se usa Firebase Cloud Messaging para notificar solo cuando hay actividad real. |
| REC-05 | El plan gratuito de Firebase cubre el uso académico | Con el Spark Plan: 50.000 lecturas/día, 20.000 escrituras/día y 1 GB de almacenamiento. Para un proyecto académico con menos de 100 usuarios activos, estos límites son suficientes. |
| REC-06 | Sin tracking de ubicación continuo | El GPS se accede solo cuando el usuario está en la pantalla de publicación y toca el botón de ubicación. No hay tracking en segundo plano. |

---

## Resumen ejecutivo

| Dimensión | Restricciones definidas | Estado |
|---|---|---|
| Conectividad | 4 | ✅ Implementadas |
| Seguridad | 5 | ✅ Implementadas |
| Privacidad | 6 | ✅ Implementadas |
| Accesibilidad | 6 | ✅ Implementadas |
| Rendimiento | 6 | ✅ Implementadas |
| Uso responsable de recursos | 6 | ✅ Implementadas |
| **Total** | **33** | |

---

*Documento elaborado en el marco del curso Desarrollo de Aplicaciones Móviles — UPN 2026-2*
