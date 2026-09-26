# Informe de Proyecto: ReUtiliza
**Universidad Privada del Norte — Facultad de Ingeniería**
**Carrera:** Ingeniería de Sistemas Computacionales
**Curso:** Desarrollo de Aplicaciones Móviles

**Autores:**
- Fabian Nicolas Montoya Bravo
- Andrea Christina Sanchez Chigne
- Analucia Hernandez Mendoza
- Alanis Altamirano Diaz

---

# 1. Contexto de la Empresa / Organización

## 1.1 Descripción del contexto

ReUtiliza no es una empresa constituida formalmente, sino un emprendimiento tecnológico académico concebido en el marco del curso de Desarrollo de Aplicaciones Móviles de la Universidad Privada del Norte (UPN), sede Trujillo, Perú. Su naturaleza es la de una startup social orientada a la economía circular, que opera como plataforma digital de intermediación entre personas que desean ceder objetos en desuso y personas que los necesitan.

El proyecto nace como respuesta a una problemática ambiental concreta identificada en el entorno universitario y en comunidades locales: la generación excesiva de residuos sólidos urbanos provocada por el descarte prematuro de bienes materiales que aún conservan vida útil.

## 1.2 Misión

Facilitar la reutilización de objetos en desuso mediante una plataforma móvil gamificada que incentiva a las personas a donar, intercambiar o vender sus bienes de forma sostenible, contribuyendo activamente a la reducción de residuos y al consumo responsable.

## 1.3 Visión

Convertirse en la plataforma de referencia de economía circular en Latinoamérica para el año 2030, promoviendo comunidades locales más sostenibles a través de la tecnología móvil.

## 1.4 Valores

- **Sostenibilidad:** cada funcionalidad de la app está diseñada para reducir el impacto ambiental.
- **Comunidad:** el valor no es el dinero sino el impacto colectivo.
- **Transparencia:** el usuario ve en todo momento su huella positiva (Puntos Verdes).
- **Accesibilidad:** la app debe ser intuitiva para todo tipo de usuario.

## 1.5 Contexto tecnológico

El equipo de desarrollo trabaja sobre el ecosistema Android, que representa aproximadamente el 72% del mercado de smartphones en Perú y Latinoamérica (Statcounter, 2024). La elección de Android como plataforma objetivo responde directamente a la necesidad de maximizar el alcance de la solución en el contexto socioeconómico local.

---

# 2. Análisis del Problema

## 2.1 Descripción del problema

La generación de residuos sólidos urbanos en el Perú crece a un ritmo superior al 3% anual. Según el Ministerio del Ambiente del Perú (MINAM, 2023), cada peruano genera en promedio 0.85 kg de residuos por día, de los cuales una proporción significativa corresponde a bienes que aún podrían tener un segundo ciclo de vida: ropa, muebles, electrónicos, libros y artículos del hogar.

El problema central no es únicamente ambiental sino también conductual: los usuarios no tienen una motivación constante para donar o regalar objetos frente a la conveniencia de simplemente desecharlos. Las plataformas existentes no han resuelto este problema de retención a largo plazo.

## 2.2 Árbol del problema

```
PROBLEMA CENTRAL
Alta tasa de descarte prematuro de bienes con vida útil remanente
│
├── CAUSAS
│   ├── Falta de canales digitales centralizados para intercambio no monetario
│   ├── Ausencia de incentivos continuos para la donación
│   ├── Desconexión entre oferta (quien tiene el objeto) y demanda (quien lo necesita)
│   └── Plataformas existentes con enfoque puramente comercial (Vinted, OLX)
│
└── EFECTOS
    ├── Incremento del volumen de residuos sólidos urbanos
    ├── Mayor extracción de materias primas para producir nuevos bienes
    ├── Aumento de la huella de carbono por fabricación y transporte
    └── Pérdida de valor económico y ambiental de bienes reutilizables
```

## 2.3 Stakeholders identificados

| Stakeholder | Rol | Interés |
|---|---|---|
| Usuarios donantes | Primario | Ceder objetos, ganar reconocimiento |
| Usuarios receptores | Primario | Obtener bienes sin costo o a bajo precio |
| Comunidades locales | Secundario | Reducir residuos en su entorno |
| Municipalidades | Terciario | Cumplir metas de gestión de residuos |
| Organizaciones ambientales | Terciario | Alianzas estratégicas futuras |

---

# 3. Antecedentes

## 3.1 Soluciones existentes y sus limitaciones

### Vinted
Plataforma europea de compra-venta de ropa de segunda mano. Aunque tiene éxito comercial, su motivador principal es el dinero, lo que excluye a usuarios sin interés en transacciones monetarias y genera dinámicas de consumo que no necesariamente son más sostenibles.

### Olio
Aplicación británica que conecta vecinos para compartir excedentes de alimentos y artículos del hogar. Tiene un enfoque altruista pero carece de un sistema de incentivos sólido a largo plazo, lo que deriva en alta tasa de abandono después del primer uso.

### OLX / Facebook Marketplace
Plataformas de clasificados generales con presencia en Latinoamérica. No tienen ningún enfoque ambiental ni sistema de recompensas. El foco es exclusivamente transaccional.

## 3.2 Vacío identificado

Ninguna solución existente combina los tres elementos clave que ReUtiliza propone:
1. **Intercambio no monetario** (donación e intercambio, no solo venta)
2. **Gamificación con propósito ambiental** (Puntos Verdes, niveles, insignias)
3. **Comunidad local** (geolocalización hiperlocal para minimizar emisiones de transporte)

## 3.3 Sustento académico

- **Shinnaranantana (2025)** — *"Impacts of gamification of eco-friendly behavior"*: demuestra que las mecánicas de juego en apps móviles incrementan significativamente la retención de usuarios al promover comportamientos sostenibles, convirtiendo acciones esporádicas en hábitos continuos.

- **Halonen et al. (2026)** — *"Exploring the Impacts of Mobile Application Interventions on Sustainable Lifestyle Changes"*: los usuarios tienen más éxito completando retos a corto plazo y de bajo impacto (como compartir objetos), y que la continuidad y participación constante son cruciales para el impacto a largo plazo.

## 3.4 Marco normativo — ODS 12

El proyecto se alinea directamente con la **Meta 12.5 de los Objetivos de Desarrollo Sostenible de la ONU**: *"De aquí a 2030, reducir considerablemente la generación de desechos mediante actividades de prevención, reducción, reciclado y reutilización."*

---

# 4. Restricciones Realistas

## 4.1 Restricciones técnicas

| # | Restricción | Justificación |
|---|---|---|
| R1 | La app solo estará disponible para Android (mínimo API 26 / Android 8.0) | El equipo tiene experiencia en Android/Kotlin; iOS requeriría Swift y recursos adicionales |
| R2 | No se implementará búsqueda full-text avanzada | Firestore no soporta búsqueda full-text nativa; implementarla requeriría Algolia o ElasticSearch, fuera del alcance académico |
| R3 | Las imágenes tienen un límite de 5 por publicación | Restricción de almacenamiento en Firebase Storage plan gratuito (5 GB) |
| R4 | No se implementará pasarela de pago real | Las ventas ecológicas muestran precio referencial; el pago se coordina fuera de la app |
| R5 | Sin mapa interactivo en esta versión | La geolocalización se muestra como texto; el mapa requeriría Google Maps API con cuota de uso |

## 4.2 Restricciones de tiempo

| # | Restricción | Detalle |
|---|---|---|
| T1 | Entrega académica en el ciclo 2026-2 | El proyecto debe completarse dentro del calendario universitario |
| T2 | Equipo de 4 integrantes sin dedicación full-time | Los integrantes tienen otras materias simultáneas |

## 4.3 Restricciones económicas

| # | Restricción | Detalle |
|---|---|---|
| E1 | Se usa el plan gratuito de Firebase (Spark Plan) | Límites: 1 GB Firestore, 5 GB Storage, 10k autenticaciones/mes |
| E2 | Sin presupuesto para publicación en Google Play Store | La app se distribuye mediante APK directo o internal testing |
| E3 | Sin presupuesto para servidores propios | Toda la infraestructura backend es Firebase (BaaS) |

## 4.4 Restricciones legales y éticas

| # | Restricción | Detalle |
|---|---|---|
| L1 | Cumplimiento con la Ley N° 29733 de Protección de Datos Personales (Perú) | Los datos de usuarios no pueden compartirse con terceros sin consentimiento |
| L2 | Prohibición de venta de objetos ilegales o peligrosos | La app incluye moderación básica en las categorías disponibles |
| L3 | No se permiten menores de 18 años sin supervisión | Condición en los Términos de Uso |

---

# 5. Alcance

## 5.1 Dentro del alcance (IN SCOPE)

- ✅ Registro e inicio de sesión con correo y contraseña (Firebase Auth)
- ✅ Publicación de objetos con fotos, descripción, categoría y tipo de transacción
- ✅ Exploración y búsqueda de objetos con filtros por categoría y tipo
- ✅ Sistema de Puntos Verdes y niveles de usuario (5 niveles)
- ✅ Insignias desbloqueables (8 insignias)
- ✅ Chat en tiempo real entre usuarios sobre un objeto específico
- ✅ Perfil de usuario con estadísticas de impacto ambiental
- ✅ Notificaciones push básicas (Firebase Cloud Messaging)
- ✅ Pantalla de detalle de producto con botón de contacto
- ✅ Filtros por tipo de transacción (Venta, Donación, Intercambio)

## 5.2 Fuera del alcance (OUT OF SCOPE)

- ❌ Versión iOS
- ❌ Panel web de administración
- ❌ Pasarela de pago integrada
- ❌ Mapa interactivo con geolocalización visual
- ❌ Moderación automática de contenido con IA
- ❌ Sistema de valoraciones y reseñas entre usuarios
- ❌ Búsqueda full-text avanzada
- ❌ Publicación en Google Play Store

---

# 6. Reglas de Negocio

| ID | Regla | Descripción |
|---|---|---|
| RN-01 | Registro obligatorio | El usuario debe registrarse para publicar o contactar a alguien. La exploración es pública. |
| RN-02 | Máximo 5 fotos por publicación | Restricción técnica y de experiencia de usuario. |
| RN-03 | Título obligatorio | Toda publicación debe tener al menos un título. |
| RN-04 | Tipo de transacción obligatorio | El usuario debe declarar si es Venta, Donación o Intercambio antes de publicar. |
| RN-05 | Puntos Verdes por publicación | Donación = +30 pts, Intercambio = +20 pts, Venta ecológica = +15 pts. |
| RN-06 | Puntos Verdes no son canjeables | Los puntos son exclusivamente un indicador de impacto ambiental, no tienen valor monetario. |
| RN-07 | Un usuario no puede chatear consigo mismo | Si el dueño del objeto intenta contactarse, la acción se bloquea. |
| RN-08 | Niveles calculados automáticamente | El nivel se recalcula cada vez que el usuario gana puntos, sin intervención manual. |
| RN-09 | Insignias no se pueden perder | Una vez desbloqueada una insignia, permanece en el perfil permanentemente. |
| RN-10 | Precio en cero para donaciones | Si el tipo es Donación, el precio se muestra como "Gratis" y no se puede ingresar monto. |
| RN-11 | Objetos ilegales prohibidos | No se permiten publicaciones de armas, sustancias controladas o fauna silvestre. |
| RN-12 | Un chat por objeto entre dos usuarios | Si ya existe un chat entre dos usuarios sobre el mismo objeto, se reutiliza el chat existente. |

---

# 7. Product Backlog

## 7.1 Épicas

| ID | Épica | Descripción |
|---|---|---|
| EP-01 | Autenticación | Registro, login y gestión de sesión |
| EP-02 | Publicación | Subir y gestionar objetos |
| EP-03 | Exploración | Buscar y filtrar objetos |
| EP-04 | Gamificación | Puntos, niveles e insignias |
| EP-05 | Comunicación | Chat en tiempo real |
| EP-06 | Perfil | Información personal y estadísticas |
| EP-07 | Notificaciones | Alertas de mensajes y actividad |

---

## 7.2 User Stories

### EP-01 — Autenticación

| ID | Historia de Usuario | Criterios de Aceptación | Prioridad | Estimación |
|---|---|---|---|---|
| US-01 | Como usuario nuevo, quiero registrarme con mi correo y contraseña para acceder a la app. | El sistema valida que el correo tenga formato válido, la contraseña mínimo 6 caracteres y que las contraseñas coincidan. Si el registro es exitoso, redirige al Home. | Alta | 3 pts |
| US-02 | Como usuario registrado, quiero iniciar sesión con mis credenciales para acceder a mi cuenta. | El sistema autentica contra Firebase Auth. Si las credenciales son incorrectas, muestra mensaje de error específico. | Alta | 2 pts |
| US-03 | Como usuario, quiero cerrar sesión para proteger mi cuenta en dispositivos compartidos. | Al confirmar el cierre, limpia la sesión local y redirige a Login. | Media | 1 pt |

---

### EP-02 — Publicación

| ID | Historia de Usuario | Criterios de Aceptación | Prioridad | Estimación |
|---|---|---|---|---|
| US-04 | Como usuario, quiero publicar un objeto con fotos para que otros puedan verlo. | Se pueden subir hasta 5 fotos desde la galería. Las imágenes se suben a Firebase Storage. La publicación aparece en Explorar en menos de 5 segundos. | Alta | 5 pts |
| US-05 | Como usuario, quiero elegir entre Venta, Donación o Intercambio al publicar para que el tipo de transacción quede claro. | El selector de tipo es obligatorio. Según el tipo seleccionado, cambia el campo de precio (visible solo en Venta). | Alta | 3 pts |
| US-06 | Como usuario, quiero categorizar mi objeto (Ropa, Electrónicos, etc.) para que sea más fácil de encontrar. | La categoría es obligatoria. Hay 8 categorías disponibles con ícono visual. | Alta | 2 pts |
| US-07 | Como usuario, quiero indicar la condición del objeto para que el comprador/receptor tenga expectativas claras. | 4 opciones: Nuevo, Como nuevo, Buen estado, Aceptable. Condición obligatoria. | Media | 2 pts |
| US-08 | Como usuario, quiero ganar Puntos Verdes al publicar un objeto para sentir que mi acción tiene un impacto. | Al publicar exitosamente, se suman los puntos correspondientes al tipo de transacción y se actualiza el nivel si corresponde. | Alta | 3 pts |

---

### EP-03 — Exploración

| ID | Historia de Usuario | Criterios de Aceptación | Prioridad | Estimación |
|---|---|---|---|---|
| US-09 | Como usuario, quiero buscar objetos por nombre para encontrar lo que necesito rápidamente. | La búsqueda filtra resultados en tiempo real (debounce de 400ms). Muestra el número de resultados encontrados. | Alta | 3 pts |
| US-10 | Como usuario, quiero filtrar por categoría para ver solo los objetos que me interesan. | Los chips de categoría son seleccionables. Al seleccionar uno, la lista se actualiza. Solo se puede seleccionar una categoría a la vez. | Alta | 2 pts |
| US-11 | Como usuario, quiero filtrar por tipo de transacción (Donación, Venta, Intercambio) para ver solo lo que me conviene. | El filtro de tipo funciona independientemente del filtro de categoría. Se pueden combinar ambos filtros. | Alta | 2 pts |
| US-12 | Como usuario, quiero ver el detalle completo de un objeto al tocarlo para decidir si me interesa. | La pantalla de detalle muestra: fotos, título, precio/tipo, condición, categoría, descripción, ubicación y datos del publicante. | Alta | 4 pts |

---

### EP-04 — Gamificación

| ID | Historia de Usuario | Criterios de Aceptación | Prioridad | Estimación |
|---|---|---|---|---|
| US-13 | Como usuario, quiero ver mis Puntos Verdes en todo momento para saber cuánto impacto he tenido. | Los puntos se muestran en el top bar y en la tarjeta del Home. Se actualizan en tiempo real con Firestore. | Alta | 3 pts |
| US-14 | Como usuario, quiero ver mi nivel actual y el progreso al siguiente para motivarme a seguir. | La barra de progreso muestra el porcentaje hacia el siguiente nivel. El nombre del nivel cambia automáticamente al alcanzar el umbral. | Alta | 3 pts |
| US-15 | Como usuario, quiero desbloquear insignias por acciones específicas para coleccionarlas. | Las insignias se desbloquean automáticamente al cumplir la condición. Aparecen en el perfil con estado "Desbloqueada". Las bloqueadas se muestran con candado. | Media | 4 pts |
| US-16 | Como usuario, quiero ver mis estadísticas (publicados, reutilizados, racha de días) para medir mi actividad. | Las 3 estadísticas se muestran en la tarjeta verde del Home y del Perfil. Se actualizan en tiempo real. | Media | 2 pts |

---

### EP-05 — Comunicación

| ID | Historia de Usuario | Criterios de Aceptación | Prioridad | Estimación |
|---|---|---|---|---|
| US-17 | Como usuario interesado en un objeto, quiero contactar al dueño con un botón para coordinar la entrega. | El botón "Contactar" en el detalle crea o reutiliza un chat entre los dos usuarios sobre ese objeto específico. Redirige al chat automáticamente. | Alta | 5 pts |
| US-18 | Como usuario, quiero enviar y recibir mensajes en tiempo real para coordinar el intercambio. | Los mensajes se sincronizan en tiempo real vía Firestore. Los mensajes propios aparecen a la derecha (verde) y los del otro a la izquierda (blanco). | Alta | 4 pts |
| US-19 | Como usuario, quiero ver todos mis chats activos en una lista para no perder ninguna conversación. | La lista de chats muestra: avatar, nombre, producto relacionado, último mensaje y hora. Se ordena por más reciente. | Alta | 3 pts |

---

### EP-06 — Perfil

| ID | Historia de Usuario | Criterios de Aceptación | Prioridad | Estimación |
|---|---|---|---|---|
| US-20 | Como usuario, quiero ver mi perfil completo con mis estadísticas e insignias. | El perfil muestra: foto, nombre, ubicación, nivel, puntos, estadísticas, insignias desbloqueadas y configuración. | Alta | 4 pts |
| US-21 | Como usuario, quiero cerrar sesión desde mi perfil de forma segura. | Al tocar "Cerrar sesión" aparece un diálogo de confirmación. Al confirmar, limpia la sesión y redirige a Login. | Media | 1 pt |

---

### EP-07 — Notificaciones

| ID | Historia de Usuario | Criterios de Aceptación | Prioridad | Estimación |
|---|---|---|---|---|
| US-22 | Como usuario, quiero recibir una notificación cuando alguien me envía un mensaje para no perderme la conversación. | Firebase Cloud Messaging envía notificación push con nombre del usuario y preview del mensaje. Al tocarla, abre el chat correspondiente. | Media | 3 pts |

---

## 7.3 Resumen del backlog

| Épica | Historias | Puntos totales | Prioridad |
|---|---|---|---|
| EP-01 Autenticación | 3 | 6 pts | Alta |
| EP-02 Publicación | 5 | 15 pts | Alta |
| EP-03 Exploración | 4 | 11 pts | Alta |
| EP-04 Gamificación | 4 | 12 pts | Alta/Media |
| EP-05 Comunicación | 3 | 12 pts | Alta |
| EP-06 Perfil | 2 | 5 pts | Alta/Media |
| EP-07 Notificaciones | 1 | 3 pts | Media |
| **TOTAL** | **22** | **64 pts** | — |

---

## 7.4 Sprint Planning sugerido

| Sprint | Historias incluidas | Objetivo |
|---|---|---|
| Sprint 1 | US-01, US-02, US-03, US-09, US-10, US-12 | App funcional: auth + exploración básica |
| Sprint 2 | US-04, US-05, US-06, US-07, US-08 | Publicación completa con puntos |
| Sprint 3 | US-13, US-14, US-15, US-16 | Gamificación completa |
| Sprint 4 | US-17, US-18, US-19 | Chat en tiempo real |
| Sprint 5 | US-11, US-20, US-21, US-22 | Perfil + filtros avanzados + notificaciones |

---

# Anexo 1: Casos de Marketing Irresponsable en Aplicaciones Móviles — Análisis de Malas Prácticas y Propuestas Éticas

## A1.1 Introducción

El diseño de aplicaciones móviles con componentes de gamificación y economía puede derivar en prácticas de marketing irresponsables que manipulan al usuario, explotan sus datos o generan comportamientos de consumo perjudiciales. Este anexo analiza tres casos reales de malas prácticas y propone cómo ReUtiliza adopta una postura ética frente a cada una.

---

## A1.2 Caso 1: Dark Patterns en aplicaciones de segunda mano — Vinted y la presión de compra

### Descripción de la mala práctica
Vinted y plataformas similares utilizan *dark patterns* (patrones oscuros de diseño) para presionar al usuario a comprar de forma impulsiva:
- **Falsa escasez:** mensajes como "¡Solo queda 1!" o "15 personas están viendo esto" aunque no sean ciertos.
- **Urgencia artificial:** contadores regresivos en ofertas que se renuevan automáticamente.
- **Notificaciones agresivas:** envío de decenas de notificaciones diarias para reactivar usuarios inactivos.
- **Precios psicológicos:** mostrar el "precio original" inflado para que el descuento parezca mayor.

### Impacto negativo
Estos mecanismos estimulan el consumo impulsivo, contradiciendo el objetivo de reducir el consumo que teóricamente justifica el mercado de segunda mano. El usuario termina comprando más de lo que necesita, simplemente porque el precio es "bajo".

### Propuesta ética para ReUtiliza
- **Cero contadores de urgencia artificial.** ReUtiliza no implementará ningún mecanismo de presión temporal falso.
- **Notificaciones con consentimiento explícito.** El usuario activa o desactiva cada tipo de notificación desde Ajustes → Notificaciones.
- **Transparencia en el precio.** El precio mostrado es el único precio. No se inflan precios de referencia.
- **Principio rector:** el valor de la plataforma es el impacto ambiental, no el volumen de transacciones.

---

## A1.3 Caso 2: Gamificación manipuladora — Aplicaciones de delivery y puntos de fidelidad

### Descripción de la mala práctica
Aplicaciones como Rappi, Glovo o PedidosYa han sido criticadas por usar la gamificación de forma manipuladora:
- **Puntos que caducan:** acumulan puntos con fecha de vencimiento corta para forzar compras innecesarias antes de que expiren.
- **Niveles que se pierden:** si el usuario no compra en X días, pierde su nivel y sus beneficios, generando ansiedad y compras compulsivas.
- **Recompensas inalcanzables:** los niveles superiores requieren un gasto mensual tan alto que solo los usuarios con consumo excesivo los alcanzan.
- **Variable ratio reinforcement:** la lógica de "quizás esta compra me dé un descuento especial" imita mecánicas de juego de azar.

### Impacto negativo
Estas prácticas generan dependencia psicológica, ansiedad por no perder el nivel y gastos injustificados. La gamificación deja de ser un incentivo positivo para convertirse en una herramienta de manipulación conductual.

### Propuesta ética para ReUtiliza
- **Los Puntos Verdes nunca caducan.** Los puntos acumulados son permanentes y reflejan el impacto histórico del usuario.
- **Los niveles nunca se pierden.** Una vez alcanzado un nivel, el usuario lo mantiene. El progreso es siempre positivo.
- **Las insignias nunca se pierden.** Una vez desbloqueada, la insignia es permanente.
- **Sin mecánicas de azar.** No hay cajas de sorpresa, sorteos ni recompensas aleatorias que generen adicción.
- **Principio rector:** la gamificación en ReUtiliza es un reflejo de logros reales, no una trampa conductual.

---

## A1.4 Caso 3: Explotación de datos personales — Aplicaciones de marketplace y publicidad dirigida

### Descripción de la mala práctica
Plataformas como Facebook Marketplace y OLX han sido objeto de críticas y multas regulatorias por:
- **Venta de datos de comportamiento** a anunciantes sin consentimiento explícito del usuario.
- **Perfilado excesivo:** registran no solo qué compra el usuario sino con quién habla, cuánto tiempo ve cada producto y qué palabras usa en sus mensajes.
- **Consentimiento enterrado:** los términos de privacidad son documentos de 30+ páginas diseñados para que el usuario no los lea.
- **Publicidad microfocalizada:** uso de datos sensibles (ubicación precisa, hábitos de consumo) para segmentar anuncios sin que el usuario lo sepa.

### Impacto negativo
El usuario se convierte en el producto. Sus datos personales, conversaciones y comportamiento son monetizados. Esto vulnera el derecho a la privacidad y genera desconfianza en las plataformas digitales.

### Propuesta ética para ReUtiliza
- **Sin publicidad de terceros.** ReUtiliza no integra SDKs publicitarios de terceros (Google AdMob, Facebook Audience Network, etc.).
- **Datos mínimos necesarios.** Solo se recopilan los datos estrictamente necesarios: nombre, correo, ubicación general (ciudad) y contenido de publicaciones.
- **Los mensajes son privados.** El contenido de los chats no se analiza ni se usa para ningún propósito más allá de la entrega del mensaje.
- **Cumplimiento con Ley N° 29733 (Perú):** política de privacidad clara y accesible desde la pantalla de Perfil → Privacidad.
- **Principio rector:** el usuario es una persona con derechos, no una fuente de datos.

---

## A1.5 Tabla resumen de compromisos éticos de ReUtiliza

| Práctica irresponsable | Presente en | Postura de ReUtiliza |
|---|---|---|
| Falsa escasez y urgencia artificial | Vinted, Amazon | ❌ No implementado |
| Notificaciones agresivas sin control | Rappi, Glovo | ✅ Notificaciones con consentimiento explícito |
| Puntos y niveles que caducan | Apps de delivery | ❌ Puntos e insignias permanentes |
| Mecánicas de azar (loot boxes) | Juegos móviles, Rappi | ❌ No implementado |
| Venta de datos a terceros | Facebook, OLX | ❌ Sin publicidad de terceros |
| Perfilado de mensajes privados | WhatsApp, Facebook | ❌ Chats no analizados |
| Términos de uso ilegibles | La mayoría | ✅ Política de privacidad clara y accesible |
| Precios inflados artificialmente | Vinted, MercadoLibre | ❌ Precio único y transparente |

---

# Evidencia Técnica

## ET.1 Prototipo Inicial

El prototipo de alta fidelidad fue diseñado previamente al desarrollo en código, con las siguientes pantallas definidas:

| Pantalla | Descripción |
|---|---|
| Login / Registro | Autenticación con correo y contraseña |
| Home | Dashboard con nivel, puntos, acciones rápidas y feed de productos |
| Explorar | Búsqueda con filtros por categoría y tipo de transacción |
| Publicar artículo | Formulario completo con fotos, tipo, categoría y condición |
| Detalle de producto | Vista completa del objeto con botón de contacto |
| Chats | Lista de conversaciones activas |
| Chat detalle | Conversación en tiempo real con burbujas de mensaje |
| Perfil | Información del usuario, nivel, insignias y configuración |

El prototipo fue desarrollado en Figma siguiendo la paleta de colores verde (#2E9E5B como color primario) y tipografía sans-serif para garantizar legibilidad en pantallas móviles.

---

## ET.2 Stack Tecnológico — Android / Kotlin

### Lenguaje de programación
**Kotlin 2.0.0** — Lenguaje oficial de Android desde 2019. Ofrece null safety, coroutines nativas, sintaxis concisa y total interoperabilidad con Java.

### UI Framework
**Jetpack Compose (BOM 2024.08.00)** — Framework declarativo de UI de Google. Reemplaza al sistema de XML tradicional. Permite construir interfaces reactivas donde la UI se actualiza automáticamente cuando el estado cambia.

### Arquitectura
**MVVM (Model — View — ViewModel)** con Repository Pattern:
```
UI (Composables)
    ↓ observa StateFlow
ViewModel
    ↓ llama a
Repository
    ↓ consulta
Firebase (Firestore / Auth / Storage)
```

### Navegación
**Navigation Compose 2.8.0** — Maneja la navegación entre pantallas con soporte para argumentos tipados y back stack.

### Inyección de dependencias
**Hilt 2.51.1** (basado en Dagger) — Automatiza la creación e inyección de dependencias, reduciendo el boilerplate y facilitando las pruebas.

### Backend como Servicio (BaaS)
**Firebase (BOM 33.2.0)**:
- **Firebase Authentication** — Registro y login con email/password
- **Cloud Firestore** — Base de datos NoSQL en tiempo real con sincronización automática
- **Firebase Storage** — Almacenamiento de imágenes de productos
- **Firebase Cloud Messaging (FCM)** — Notificaciones push

### Carga de imágenes
**Coil 2.7.0** — Librería de carga de imágenes optimizada para Kotlin y Compose. Soporta caché en memoria y disco.

### Concurrencia
**Kotlin Coroutines + Flow** — Manejo asíncrono de operaciones de red y base de datos sin bloquear el hilo principal. `StateFlow` y `callbackFlow` para datos reactivos en tiempo real.

### Permisos
**Accompanist Permissions 0.36.0** — Gestión declarativa de permisos de Android (cámara, galería, ubicación) integrada con Compose.

### Persistencia local
**DataStore Preferences** — Almacenamiento ligero de preferencias del usuario (reemplaza SharedPreferences).

---

## ET.3 Estructura del proyecto

```
com.reutiliza.app/
├── MainActivity.kt              ← Entry point con Hilt + Compose
├── ReUtilizaApp.kt              ← Application class con @HiltAndroidApp
├── data/
│   ├── model/                   ← Data classes: User, Product, Chat, Message, Badge
│   ├── repository/              ← AuthRepository, ProductRepository, ChatRepository, UserRepository
│   └── remote/                  ← ReUtilizaMessagingService (FCM)
├── di/
│   └── AppModule.kt             ← Módulo Hilt: provee Firebase instances
├── ui/
│   ├── theme/                   ← Color.kt, Type.kt, Theme.kt
│   ├── components/              ← ProductCard, GreenLevelCard, TopBarReUtiliza
│   ├── navigation/              ← NavGraph.kt, Screen.kt, BottomNavItem.kt
│   └── screens/
│       ├── auth/                ← LoginScreen, RegisterScreen
│       ├── home/                ← HomeScreen
│       ├── explore/             ← ExploreScreen
│       ├── publish/             ← PublishScreen
│       ├── chat/                ← ChatsScreen, ChatDetailScreen
│       ├── profile/             ← ProfileScreen
│       └── detail/              ← ProductDetailScreen
├── viewmodel/                   ← AuthViewModel, HomeViewModel, ExploreViewModel,
│                                   PublishViewModel, ChatViewModel, ProfileViewModel,
│                                   ProductDetailViewModel
└── utils/
    └── TimeUtils.kt             ← Formateo de timestamps
```

## ET.4 Decisiones de diseño relevantes

| Decisión | Alternativa descartada | Razón |
|---|---|---|
| Jetpack Compose | XML + View Binding | Compose es el estándar moderno, código más conciso y reactivo |
| Hilt | Manual DI / Koin | Hilt es el estándar oficial de Google para Android |
| Firestore en tiempo real | REST API propia | Firestore ofrece sync en tiempo real sin servidor propio |
| MVVM + Repository | MVC | MVVM separa lógica de negocio de la UI, facilita mantenimiento |
| Kotlin Coroutines | RxJava | Coroutines son más simples, integradas en Kotlin y con soporte oficial |
| Coil | Glide / Picasso | Coil está construido en Kotlin y tiene integración nativa con Compose |

---

*Documento generado en el marco del curso Desarrollo de Aplicaciones Móviles — UPN 2026-2*
