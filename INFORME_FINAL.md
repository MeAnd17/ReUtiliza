FACULTAD DE INGENIERÍA

CARRERA DE INGENIERÍA DE SISTEMAS COMPUTACIONALES


INFORME DE PROYECTO FINAL

DESARROLLO DE APLICACIONES MÓVILES


"REUTILIZA: PLATAFORMA MÓVIL DE ECONOMÍA CIRCULAR GAMIFICADA PARA LA REDUCCIÓN DE RESIDUOS SÓLIDOS URBANOS"


Autores:
Altamirano Diaz, Alanis (25%)
Hernandez Mendoza, Analucia (25%)
Montoya Bravo, Fabian Nicolas (25%)
Sanchez Chigne, Andrea Christina (25%)


Docente del Curso:
Anampa García, Jhon Paul


Lima – Perú
2026-2


---


TABLA DE CONTENIDOS

I.    RESUMEN
II.   INTRODUCCIÓN
  2.1. Descripción de la problemática
  2.2. Descripción del proyecto
III.  ANÁLISIS DEL PROBLEMA
  3.1. Antecedentes (Mínimo de 5 antecedentes)
  3.2. Identificación y formulación del problema
  3.3. Propuestas
    3.3.1. Propuesta 01: Aplicación móvil nativa Android con gamificación (ReUtiliza)
    3.3.2. Propuesta 02: Aplicación web progresiva (PWA) multiplataforma
    3.3.3. Propuesta 03: Bot de Telegram/WhatsApp para intercambio comunitario
  3.4. Definición de objetivos
    3.4.1. Objetivo general
    3.4.2. Objetivos específicos
V.    HERRAMIENTAS DE INGENIERÍA
VI.   GENERACIÓN DE SOLUCIONES
  6.1. Evaluación de tecnologías de aplicaciones móviles
VII.  METODOLOGÍA DE DESARROLLO A UTILIZAR
  7.1. Aplicación móvil
    7.1.1. Evidencias del funcionamiento
  7.2. Repositorios
VIII. COMPLEMENTO
  8.1. Evidencia de Participación
  8.2. Video de presentación del proyecto (Link)
  8.3. PPT sustentación
  8.4. Link del repositorio donde se encuentran los proyectos (código)
IX.   RESULTADOS
  9.1. Resultados en base a los objetivos del proyecto
  9.2. Discusión de resultados
X.    CONCLUSIONES
XI.   REFERENCIAS O BIBLIOGRAFÍA
XII.  ANEXOS


---


ÍNDICE DE TABLAS

Tabla 1. Comparación de metodologías de desarrollo de software
Tabla 2. Herramientas hardware seleccionadas
Tabla 3. Herramientas software seleccionadas
Tabla 4. Evaluación de tecnologías de aplicaciones móviles
Tabla 5. Comparación de metodologías contempladas
Tabla 6. Cronograma de desarrollo por sprints
Tabla 7. Resultados de pruebas funcionales y de seguridad
Tabla 8. Resultados en base a los objetivos del proyecto


ÍNDICE DE FIGURAS

Figura 1. Diagrama de Ishikawa — causas del descarte prematuro de bienes
Figura 2. Arquitectura MVVM del proyecto ReUtiliza
Figura 3. Estructura de desglose del trabajo (EDT)
Figura 4. Estructura del proyecto Android
Figura 5. Pantalla de Login
Figura 6. Pantalla de Registro
Figura 7. Pantalla Home con dashboard de Puntos Verdes
Figura 8. Pantalla Explorar con filtros
Figura 9. Pantalla Publicar artículo
Figura 10. Pantalla Chat en tiempo real
Figura 11. Pantalla Perfil con insignias


ÍNDICE DE ANEXOS

Anexo 1. Investigación de marketing irresponsable en aplicaciones móviles y propuestas éticas
Anexo 2. Restricciones realistas del proyecto
Anexo 3. Exploración de información — Papers, casos y tendencias ODS 12
Anexo 4. Prototipos de alta fidelidad (Figma)
Anexo 5. Capturas de pantalla del producto final


---


RESUMEN

El presente trabajo describe el diseño y desarrollo de ReUtiliza, una aplicación móvil nativa para Android orientada a la economía circular y la reducción de residuos sólidos urbanos. El proyecto surge como respuesta a la problemática del descarte prematuro de bienes materiales útiles en el contexto peruano, donde se generan aproximadamente 8,994,762 toneladas de residuos sólidos al año, de las cuales el 77.64% son aprovechables pero terminan en vertederos (MINAM, 2024).

ReUtiliza permite a los usuarios publicar, intercambiar, donar y vender objetos en desuso mediante una interfaz gamificada que recompensa las acciones sostenibles a través de un sistema de Puntos Verdes, niveles de usuario e insignias desbloqueables. La aplicación fue desarrollada con Kotlin y Jetpack Compose, bajo una arquitectura MVVM con Firebase como backend, lo que garantiza sincronización en tiempo real, autenticación segura y almacenamiento escalable sin infraestructura propia.

El sistema implementa funcionalidades de publicación de artículos con fotografía, búsqueda con filtros por categoría y tipo de transacción, chat en tiempo real entre usuarios, perfil gamificado con estadísticas de impacto ambiental y notificaciones push. La propuesta se alinea con la Meta 12.5 de los Objetivos de Desarrollo Sostenible de la ONU, que busca reducir considerablemente la generación de desechos mediante actividades de prevención, reducción, reciclado y reutilización para el año 2030.

Palabras clave: economía circular, aplicación móvil Android, gamificación, reutilización, ODS 12.


Abstract

This paper describes the design and development of ReUtiliza, a native Android mobile application focused on the circular economy and urban solid waste reduction. The project addresses the problem of premature disposal of still-useful material goods in the Peruvian context, where approximately 8,994,762 tons of solid waste are generated annually, of which 77.64% are reusable but end up in landfills (MINAM, 2024).

ReUtiliza allows users to publish, exchange, donate, and sell second-hand items through a gamified interface that rewards sustainable actions through a Green Points system, user levels, and unlockable badges. The application was developed using Kotlin and Jetpack Compose, following an MVVM architecture with Firebase as the backend, ensuring real-time synchronization, secure authentication, and scalable storage without requiring proprietary infrastructure.

The system implements article publication with photography, search with filters by category and transaction type, real-time chat between users, a gamified profile with environmental impact statistics, and push notifications. The proposal aligns with SDG Target 12.5, which seeks to substantially reduce waste generation through prevention, reduction, recycling, and reuse by 2030.

Keywords: circular economy, Android mobile application, gamification, reuse, SDG 12.


---


INTRODUCCIÓN

2.1. Descripción de la problemática

El Perú enfrenta una crisis creciente en la gestión de residuos sólidos urbanos. Según datos oficiales del Ministerio del Ambiente (MINAM, 2024), el país genera 8,994,762 toneladas de residuos sólidos anuales, equivalentes a 24,643 toneladas por día. De este volumen, solo 249,407 toneladas fueron valorizadas en 2024, lo que representa menos del 3% del total generado. El porcentaje restante, que incluye bienes con vida útil remanente como ropa, electrónicos, muebles y libros, termina en vertederos o botaderos informales.

El problema central no es únicamente ambiental sino también conductual: las personas descartan objetos útiles porque no existen canales digitales accesibles, centralizados e incentivados que faciliten la transferencia de esos bienes a quienes los necesitan. Las soluciones existentes en el mercado peruano como OLX o Facebook Marketplace tienen un enfoque exclusivamente comercial y transaccional, mientras que plataformas altruistas como Olio carecen de un sistema de incentivos que mantenga la participación activa de los usuarios a largo plazo (Halonen et al., 2026).

Esta brecha entre la cantidad de residuos aprovechables generados y los efectivamente reutilizados representa una oportunidad directa de intervención tecnológica alineada con los Objetivos de Desarrollo Sostenible, específicamente con la Meta 12.5 que busca reducir considerablemente la generación de desechos mediante la reutilización antes del año 2030.

2.2. Descripción del proyecto

ReUtiliza es una aplicación móvil nativa para Android que combina las funcionalidades de un mercado de clasificados con un sistema de recompensas gamificado basado en Puntos Verdes. La plataforma permite a los usuarios publicar objetos en desuso eligiendo entre tres modalidades de transacción: donación, intercambio o venta ecológica. Cada acción sostenible genera puntos que se acumulan en el perfil del usuario, permitiéndole avanzar entre cinco niveles de impacto ambiental y desbloquear insignias de reconocimiento comunitario.

La aplicación fue desarrollada con Kotlin 2.0 y Jetpack Compose sobre una arquitectura MVVM, utilizando Firebase como plataforma de backend para autenticación, base de datos en tiempo real, almacenamiento de imágenes y notificaciones push. El proyecto responde directamente a la Meta 12.5 del ODS 12 de la ONU y busca establecer una herramienta práctica que facilite la economía circular en la vida cotidiana de comunidades locales peruanas.


---


ANÁLISIS DEL PROBLEMA

3.1. Antecedentes (Mínimo de 5 antecedentes)

Antecedente 1
Shinnaranantana, N. (2025). Impacts of gamification of eco-friendly behavior. Human Behavior, Development and Society. https://so01.tci-thaijo.org/index.php/hbds/article/view/287128

Este estudio examina cómo la aplicación de mecánicas de juego en aplicaciones móviles incrementa la retención de usuarios al promover comportamientos sostenibles. Los hallazgos indican que sistemas de puntos, niveles y rankings convierten acciones ambientales esporádicas en hábitos continuos, reduciendo la brecha entre la intención sostenible y la acción sostenible real. El estudio concluye que la gamificación es un mecanismo efectivo para mantener la participación activa en plataformas de impacto ambiental.

Relación con ReUtiliza: sustenta el sistema de Puntos Verdes, niveles de usuario y ranking comunitario como mecanismos centrales de retención a largo plazo.


Antecedente 2
Halonen, V., Claudelin, A., Kareinen, E., & Levänen, J. (2026). Exploring the impacts of mobile application interventions on sustainable lifestyle changes. Circular Economy and Sustainability, 6(2), 1-22. https://doi.org/10.1007/s43615-026-00901-7

Investiga el potencial de una aplicación móvil (Climate Campaigners) para reducir emisiones de gases de efecto invernadero. El estudio encontró que los usuarios tuvieron más éxito completando retos a corto plazo y de bajo impacto, como compartir objetos, que con compromisos de largo plazo. Destaca que sin mecanismos de compromiso continuo, los hábitos sostenibles se abandonan en pocas semanas.

Relación con ReUtiliza: sustenta el diseño de retos cortos y repetitivos como mecanismo de retención, e identifica el compartir objetos como uno de los comportamientos más fácilmente adoptables.


Antecedente 3
Pereira, R., et al. (2024). Promoting sustainable behaviors through mobile apps: SBAM design guidelines. Multimedia Tools and Applications, Springer. https://link.springer.com/10.1007/s11042-024-18299-5

Formula lineamientos de diseño para aplicaciones móviles orientadas al cambio de comportamiento sostenible. Los autores identifican que las apps más efectivas combinan tres elementos: retroalimentación inmediata del impacto de las acciones, metas graduales alcanzables y componentes sociales de comparación y comunidad. Las plataformas que solo informan sin facilitar la acción presentan altas tasas de abandono.

Relación con ReUtiliza: los tres principios están implementados en la app: retroalimentación inmediata (puntos al publicar), metas graduales (5 niveles) y componente social (ranking comunitario).


Antecedente 4
Tassinari, M., et al. (2023). A conceptual framework of the peer-to-peer sharing and reuse platform in the circular economy. Sustainability, 15(11), 8852. https://www.mdpi.com/2071-1050/15/11/8852

Propone un marco conceptual para plataformas P2P de reutilización en la economía circular. Identifica que el usuario debe percibir un beneficio personal claro, ya sea económico, social o de reconocimiento, además del beneficio ambiental. Las plataformas enfocadas exclusivamente en el beneficio ambiental sin ofrecer valor percibido al individuo presentan tasas de adopción significativamente menores.

Relación con ReUtiliza: sustenta la decisión de combinar beneficio ambiental (Puntos Verdes) con beneficio personal (reconocimiento, nivel e insignias visibles en el perfil público).


Antecedente 5
Deák, Z. (2025). Sharing Economy and Mobile Applications — Acceptance and Usage. Obuda University, Keleti Károly Faculty of Business and Management. https://kgk.uni-obuda.hu/wp-content/uploads/2025/07/TT7-Deak-SHARING-ECONOMY.pdf

Investiga el impacto de las aplicaciones móviles en la economía colaborativa y las actitudes de los consumidores hacia prácticas ecológicas. Concluye que las aplicaciones P2P tienen potencial significativo para reducir el consumo de recursos naturales, pero su adopción depende de la facilidad de uso y la confianza entre usuarios. La verificación de perfiles y el sistema de reputación son factores determinantes para la adopción inicial.

Relación con ReUtiliza: justifica la implementación del perfil verificado y el sistema de reputación público basado en Puntos Verdes acumulados.


Antecedente 6
Nguyen, T., et al. (2025). From awareness to action: The role of civic campaigns and persuasive technologies in sustainable behaviors. Springer — Lecture Notes in Networks and Systems. https://link.springer.com/content/pdf/10.1007/978-981-95-1361-1_49.pdf

Analiza el rol de las tecnologías persuasivas digitales en el cambio de comportamiento sostenible. Concluye que las herramientas digitales son efectivas para aumentar la conciencia ambiental, pero no impulsan el cambio de comportamiento de forma autónoma sin complementarse con incentivos y diseño centrado en el usuario. Las apps que combinan información con facilitación de la acción obtienen resultados superiores a las apps exclusivamente informativas.

Relación con ReUtiliza: confirma que reducir la fricción al publicar objetos es más efectivo que informar. El flujo de publicación en 4 pasos responde directamente a este hallazgo.


3.2. Identificación y formulación del problema

Diagrama de Ishikawa

[Figura 1. Diagrama de Ishikawa — Causas del descarte prematuro de bienes con vida útil remanente en comunidades urbanas del Perú]

El diagrama identifica cuatro ramas de causas:

- Tecnología: falta de plataformas centralizadas de intercambio no monetario; apps existentes sin gamificación ambiental.
- Comportamiento: ausencia de incentivos continuos para donar o regalar; hábitos de descarte consolidados; falta de reconocimiento por acciones sostenibles.
- Mercado: plataformas disponibles (OLX, Facebook Marketplace) con enfoque exclusivamente comercial; alta fricción para publicar un objeto; sin opciones de donación o intercambio estructurado.
- Cultura: percepción de que comprar nuevo es más conveniente; desconocimiento del impacto ambiental del descarte prematuro.

Formulación del problema

Tabla 1. Problema general y problemas específicos

| Tipo | Enunciado |
|---|---|
| Problema general | ¿De qué manera una aplicación móvil Android con sistema de gamificación puede incentivar la reutilización de bienes materiales en desuso y contribuir a la reducción de residuos sólidos urbanos en comunidades locales del Perú? |
| Problema específico 1 | ¿Cómo diseñar un sistema de incentivos basado en gamificación que motive a los usuarios a publicar objetos en desuso de forma continua? |
| Problema específico 2 | ¿Qué funcionalidades mínimas debe tener una plataforma P2P de intercambio no monetario para generar confianza y facilitar el cierre de transacciones entre usuarios? |
| Problema específico 3 | ¿Cómo medir y comunicar el impacto ambiental de las acciones individuales de reutilización para mantener la motivación del usuario a largo plazo? |


3.3. Propuestas

Describir las propuestas innovadoras iniciales utilizando la información obtenida de diversas fuentes para dar solución al problema u oportunidad de mejora identificado.

3.3.1. Propuesta 01: Aplicación móvil nativa Android con gamificación y economía circular (ReUtiliza)

Descripción
Desarrollo de una aplicación móvil nativa para Android utilizando Kotlin y Jetpack Compose que combina un mercado de clasificados con un sistema de recompensas gamificado. Los usuarios pueden publicar objetos en desuso eligiendo entre donación, intercambio o venta ecológica. Cada acción genera Puntos Verdes que se acumulan en el perfil, desbloqueando niveles e insignias. Incluye chat en tiempo real, filtros de búsqueda por categoría y geolocalización para intercambios locales. El backend opera con Firebase para sincronización en tiempo real.

Ventajas
- Acceso nativo completo al hardware del dispositivo (cámara, GPS) para publicación rápida de objetos.
- Sistema de gamificación que mantiene el engagement a largo plazo (Shinnaranantana, 2025).
- Firebase permite desarrollo sin servidor propio, reduciendo costos operativos.
- Android cubre el 75% del mercado de smartphones en Latinoamérica (Latinometrics, 2025).
- Tres modalidades de transacción atienden distintos perfiles de usuario.
- Impacto ambiental directo y medible, alineado con ODS 12.

Desventajas
- Disponible solo para Android, excluye usuarios con dispositivos iOS.
- Requiere masa crítica de usuarios para que la plataforma sea útil (problema del arranque en frío).
- Dependencia de la calidad de conexión a internet del usuario.
- Moderación de contenido inapropiado requiere recursos adicionales.


3.3.2. Propuesta 02: Aplicación web progresiva (PWA) multiplataforma de intercambio de objetos

Descripción
Desarrollo de una Progressive Web App accesible desde cualquier navegador móvil o de escritorio, sin necesidad de instalación desde una tienda de aplicaciones. Los usuarios acceden mediante un enlace URL y pueden agregar el acceso directo a su pantalla de inicio. La plataforma permitiría publicar objetos, buscar y contactar donantes mediante formularios. Tecnologías: React, Node.js, MongoDB Atlas.

Ventajas
- Compatible con cualquier dispositivo (Android, iOS, escritorio) con un solo desarrollo.
- No requiere aprobación de la Google Play Store para distribuirse.
- Menor costo de desarrollo inicial al usar tecnologías web estándar.
- Actualizaciones instantáneas sin necesidad de que el usuario actualice la app.

Desventajas
- Acceso limitado al hardware del dispositivo (cámara y GPS con restricciones en navegadores).
- Experiencia de usuario inferior a una app nativa en términos de fluidez y rendimiento.
- Sin notificaciones push en todos los navegadores (soporte limitado en iOS Safari).
- Menor visibilidad al no estar en la Google Play Store.
- La gamificación es más difícil de implementar con la misma calidad visual que una app nativa.


3.3.3. Propuesta 03: Bot de Telegram/WhatsApp para intercambio de objetos en grupos comunitarios

Descripción
Desarrollo de un bot automatizado para plataformas de mensajería masiva (Telegram o WhatsApp Business API) que permite publicar objetos mediante comandos de texto dentro de grupos comunitarios existentes. El bot gestiona las publicaciones, notifica a los miembros interesados y lleva un registro de intercambios, aprovechando plataformas que el usuario ya tiene instaladas.

Ventajas
- Aprovecha plataformas que el usuario ya usa diariamente, con cero barrera de instalación.
- Bajo costo de desarrollo y mantenimiento.
- Ideal para comunidades pequeñas y locales ya organizadas en grupos.
- No requiere registro adicional.

Desventajas
- Sin interfaz visual propia: la experiencia depende de la interfaz de terceros.
- Imposible implementar gamificación visual (puntos, niveles, insignias).
- Sin búsqueda con filtros por categoría, precio o ubicación.
- Dependencia total de las políticas y APIs de terceros.
- No escala bien con muchos usuarios: los grupos grandes se vuelven caóticos.
- No ofrece datos de impacto ambiental ni métricas sostenibles.


3.4. Definición de objetivos

3.4.1. Objetivo general

Desarrollar una aplicación móvil nativa para Android que facilite la reutilización de bienes materiales en desuso mediante un sistema gamificado de Puntos Verdes, contribuyendo a la reducción de residuos sólidos urbanos y al consumo responsable alineado con la Meta 12.5 del ODS 12 de la ONU.

3.4.2. Objetivos específicos

1. Implementar un módulo de publicación de objetos que permita al usuario fotografiar, categorizar y publicar un artículo en menos de 3 minutos, eligiendo entre las modalidades de donación, intercambio o venta ecológica.

2. Desarrollar un sistema de gamificación con Puntos Verdes, cinco niveles de usuario e insignias desbloqueables que incentiven la participación continua de los usuarios en la plataforma.

3. Integrar un módulo de chat en tiempo real que permita la comunicación directa y segura entre el publicante y el interesado para coordinar la entrega del objeto.

4. Diseñar un perfil de usuario con estadísticas de impacto ambiental (objetos publicados, reutilizados y racha de días activos) que haga visible la contribución individual a la reducción de residuos.

5. Implementar un sistema de búsqueda y filtrado por categoría, tipo de transacción y condición del artículo que permita al usuario encontrar objetos de interés de forma eficiente.


---


HERRAMIENTAS DE INGENIERÍA

Comparación de metodologías o marco de trabajo y estándares de ingeniería, selección de lo que se utilizaría (justificar)

Para el desarrollo de ReUtiliza se evaluaron tres metodologías de desarrollo de software:

Tabla 2. Comparación de metodologías de desarrollo de software

| Metodología | Descripción | Ventajas | Desventajas | Seleccionada |
|---|---|---|---|---|
| Scrum | Marco ágil iterativo con sprints de 1-4 semanas, roles definidos y entregas incrementales | Adaptable a cambios, entregas frecuentes, visibilidad del progreso, ideal para equipos pequeños | Requiere disponibilidad constante del equipo, dificultad con equipos distribuidos | Sí |
| Cascada (Waterfall) | Proceso lineal secuencial: requisitos → diseño → implementación → pruebas → despliegue | Simple de planificar, documentación clara, ideal cuando los requisitos son fijos | Inflexible ante cambios, errores detectados tarde, entrega al final del proyecto | No |
| Kanban | Sistema visual de gestión de flujo de trabajo con tablero de tareas (Por hacer / En progreso / Hecho) | Flexible, sin sprints fijos, visualización clara del trabajo | Sin iteraciones formales, sin fechas de entrega comprometidas, menos estructura | No |

Justificación de selección — Scrum: se seleccionó porque el proyecto ReUtiliza requería iteraciones cortas para validar funcionalidades con usuarios reales. El equipo de 4 integrantes con disponibilidad limitada por otras materias se beneficia de la estructura de sprints para dividir el trabajo de forma equitativa. Los requisitos evolucionaron durante el desarrollo (filtros y sistema de insignias añadidos en iteraciones posteriores), lo que hace inadecuado el modelo Waterfall.

Selección de las herramientas hardware y software, fundamentado

Tabla 3. Herramientas hardware seleccionadas

| Herramienta | Especificación | Justificación |
|---|---|---|
| Computadora de desarrollo | Intel Core i5 / 8 GB RAM / 256 GB SSD | Requerimiento mínimo para ejecutar Android Studio con emulador |
| Smartphone Android para pruebas | Android 8.0 (API 26) o superior | Validación en dispositivo real con las condiciones del usuario final |
| Emulador Pixel 6 (AVD) | Android 14 (API 34) | Pruebas durante el desarrollo sin necesidad de dispositivo físico |

Tabla 4. Herramientas software seleccionadas

| Herramienta | Versión | Justificación |
|---|---|---|
| Android Studio | Ladybug 2024.2.1 | IDE oficial de Google; incluye emulador, depurador y herramientas de profiling |
| Kotlin | 2.0.0 | Lenguaje oficial de Android; null safety, coroutines nativas, sintaxis concisa |
| Jetpack Compose | BOM 2024.08.00 | Framework declarativo de UI moderno; reemplaza XML, permite UI reactiva |
| Firebase Auth | BOM 33.2.0 | Gestión de autenticación segura sin servidor propio |
| Cloud Firestore | BOM 33.2.0 | Base de datos NoSQL en tiempo real con sincronización automática |
| Firebase Storage | BOM 33.2.0 | Almacenamiento de imágenes de productos |
| Firebase Cloud Messaging | BOM 33.2.0 | Notificaciones push sin infraestructura propia |
| Hilt | 2.51.1 | Inyección de dependencias oficial de Google; reduce código repetitivo |
| Navigation Compose | 2.8.0 | Navegación entre pantallas con soporte para argumentos tipados |
| Coil | 2.7.0 | Carga de imágenes optimizada para Kotlin/Compose con caché automático |
| Git + GitHub | — | Control de versiones y repositorio remoto colaborativo |
| Figma | — | Diseño de prototipos de alta fidelidad previo al desarrollo |

Uso correcto de herramientas — recolección, análisis y procesamiento de datos

- Recolección de datos: Firebase Firestore recopila datos de publicaciones, usuarios y chats en tiempo real mediante listeners reactivos.
- Análisis de datos: los ViewModels procesan los datos recibidos desde los repositorios y los exponen a la UI mediante StateFlow, permitiendo actualizaciones reactivas.
- Procesamiento de datos: Kotlin Coroutines gestionan todas las operaciones asíncronas (lectura/escritura en Firestore, subida de imágenes a Storage) sin bloquear el hilo principal.

Requisitos del usuario final
- Smartphone Android con versión 8.0 (API 26) o superior.
- Conexión a internet (WiFi o datos móviles).
- Permisos requeridos: cámara, galería, notificaciones. Ubicación: opcional.

Requisitos del servidor (Firebase — Plan Spark gratuito)
- 50,000 lecturas/día y 20,000 escrituras/día en Firestore.
- 1 GB de almacenamiento en Firestore y 5 GB en Firebase Storage.
- 10,000 autenticaciones al mes.
- Suficiente para uso académico con menos de 100 usuarios activos simultáneos.


---


GENERACIÓN DE SOLUCIONES

6.1. Evaluación de tecnologías de aplicaciones móviles

Tabla 5. Evaluación de tecnologías de aplicaciones móviles

| Tecnologías | Ventajas | Desventajas |
|---|---|---|
| Android Studio (Kotlin + Compose) | Acceso nativo completo al hardware (cámara, GPS). Mejor rendimiento en dispositivos Android. Soporte oficial de Google. Jetpack Compose reduce el código de UI. Kotlin ofrece null safety que reduce crashes. | Solo para Android. Curva de aprendizaje de Compose. Mayor tamaño de APK que Flutter. |
| Flutter (Dart) | Desarrollo multiplataforma (Android e iOS) con un solo código. UI consistente en ambas plataformas. Compilación a código nativo. | Acceso al hardware nativo requiere plugins de terceros. Dart es menos conocido que Kotlin. Mayor tamaño de la app. |
| React Native (JavaScript) | Multiplataforma. Gran comunidad y ecosistema npm. Reutiliza conocimientos de React web. | Rendimiento inferior para apps con animaciones. Dependencia del bridge JavaScript. Actualizaciones frecuentes que generan incompatibilidades. |
| Ionic (Angular/React/Vue) | Basado en tecnologías web estándar. Multiplataforma. Fácil para desarrolladores web. | Rendimiento inferior al nativo. Sin acceso directo a APIs nativas. Experiencia de usuario notablemente inferior. |
| PWA (Progressive Web App) | No requiere instalación. Compatible con cualquier navegador. Un solo desarrollo para web y móvil. | Acceso limitado a hardware nativo. Sin soporte completo de notificaciones push en iOS. No aparece en Play Store. |

Selección justificada: Android Studio con Kotlin y Jetpack Compose, por las siguientes razones:
1. El 75% del mercado latinoamericano usa Android (Latinometrics, 2025), maximizando el alcance de la solución.
2. El acceso nativo a cámara y GPS es esencial para el flujo de publicación rápida.
3. Kotlin ofrece coroutines nativas que simplifican el manejo asíncrono con Firebase.
4. Jetpack Compose tiene soporte oficial de Google a largo plazo.
5. El equipo cuenta con experiencia previa en desarrollo Android.


---


METODOLOGÍA DE DESARROLLO A UTILIZAR

7.1. Aplicación móvil

Desarrollo de la metodología o marco de trabajo

Se aplicó la metodología Scrum con sprints de 2 semanas. La arquitectura seleccionada fue MVVM (Model-View-ViewModel) con Repository Pattern, que separa la lógica de negocio de la presentación facilitando el mantenimiento y las pruebas.

[Figura 2. Arquitectura MVVM del proyecto ReUtiliza]

La arquitectura se organiza en cuatro capas:
- Capa de presentación: Composables (pantallas) que observan StateFlow de los ViewModels.
- Capa de ViewModel: procesa la lógica de negocio y expone el estado a la UI.
- Capa de Repositorio: abstrae el acceso a Firebase y expone datos mediante Flow.
- Capa de datos: Firebase (Firestore, Auth, Storage, FCM).

Justificación de MVVM: los ViewModels sobreviven a los cambios de configuración del dispositivo (rotación de pantalla) y los StateFlow permiten que la UI reaccione automáticamente a los cambios de estado sin necesidad de actualizaciones manuales.

Análisis de metodologías contempladas

Tabla 6. Análisis de metodologías contempladas

| Característica | Scrum | Waterfall | Kanban |
|---|---|---|---|
| Flexibilidad ante cambios | Alta | Baja | Alta |
| Estructura de entregables | Sprints definidos | Fases secuenciales | Flujo continuo |
| Visibilidad del progreso | Alta (sprint reviews) | Media | Alta (tablero visual) |
| Adecuación al equipo pequeño | Muy alta | Media | Alta |
| Fechas de entrega comprometidas | Sí (por sprint) | Sí (al final) | No |
| Seleccionada | Sí | No | No |

Planificación

[Figura 3. Estructura de Desglose del Trabajo (EDT) del proyecto ReUtiliza]

La EDT se organiza en 9 entregables principales:
1. Gestión del Proyecto (definición de requisitos, prototipos, configuración del entorno)
2. Backend Firebase (configuración, modelos de datos, reglas de seguridad)
3. Módulo de Autenticación (Login, Registro)
4. Módulo de Publicación (selección de imágenes, formulario, subida a Storage)
5. Módulo de Exploración (feed, búsqueda, filtros)
6. Módulo de Chat (lista de conversaciones, chat en tiempo real)
7. Módulo de Gamificación (Puntos Verdes, niveles, insignias)
8. Módulo de Perfil (estadísticas, configuración)
9. Pruebas y Ajustes (pruebas funcionales, corrección de bugs)

Tabla 7. Cronograma de desarrollo por sprints

| Sprint | Semanas | Actividades principales | Entregable |
|---|---|---|---|
| Sprint 1 | 1-2 | Configuración Firebase, modelos de datos, autenticación, navegación base y tema visual | Auth funcional + navegación entre pantallas |
| Sprint 2 | 3-4 | Módulo de publicación completo (fotos, formulario, subida a Storage), pantalla Home con feed | Publicación operativa + feed en tiempo real |
| Sprint 3 | 5-6 | Sistema de gamificación (Puntos Verdes, niveles, insignias), pantalla Explorar con filtros | Gamificación completa + exploración con filtros |
| Sprint 4 | 7-8 | Chat en tiempo real, pantalla de detalle de producto, botón de contacto | Chat funcional + detalle de producto |
| Sprint 5 | 9-10 | Perfil completo, notificaciones push (FCM), pruebas funcionales y corrección de bugs | App completa lista para demostración |

Análisis — Modelos de datos principales

Los datos se organizan en tres colecciones en Firestore:

Colección users: almacena el perfil del usuario con id, nombre, correo, greenPoints, level, publishedCount, reutilizedCount, streakDays, badges e isVerified.

Colección products: almacena los artículos publicados con id, ownerId, title, description, category, transactionType, condition, price, imageUrls, location y greenPointsReward.

Colección chats: almacena las conversaciones con id, productId, participantIds, lastMessage y lastMessageTime.

Desarrollo — Estructura del proyecto

[Figura 4. Estructura del proyecto Android ReUtiliza]

```
com.reutiliza.app/
├── MainActivity.kt
├── ReUtilizaApp.kt
├── data/
│   ├── model/         → User, Product, Chat, Message, Badge
│   ├── repository/    → AuthRepository, ProductRepository,
│   │                    ChatRepository, UserRepository
│   └── remote/        → ReUtilizaMessagingService
├── di/
│   └── AppModule.kt
├── ui/
│   ├── theme/         → Color.kt, Type.kt, Theme.kt
│   ├── components/    → ProductCard, GreenLevelCard, TopBar
│   ├── navigation/    → NavGraph, Screen, BottomNavItem
│   └── screens/       → auth, home, explore, publish,
│                         chat, profile, detail
├── viewmodel/         → 7 ViewModels
└── utils/             → TimeUtils
```

Protección de datos en el producto software: las reglas de seguridad de Firestore garantizan que cada usuario solo puede leer y modificar sus propios documentos. Los chats solo son accesibles para los participantes. El tráfico de red es exclusivamente HTTPS. No se almacenan contraseñas en el dispositivo. No se integran SDKs de publicidad de terceros.

Pruebas

Tabla 8. Resultados de pruebas funcionales y de seguridad

| Tipo de prueba | Casos probados | Resultado |
|---|---|---|
| Registro | Datos válidos, contraseñas que no coinciden, correo ya registrado | Correcto en los 3 casos |
| Publicación | Sin título (debe bloquear), con 5 fotos, donación (+30 pts) | Correcto en los 3 casos |
| Búsqueda | Por texto, filtro por categoría, combinación de filtros | Correcto en los 3 casos |
| Chat | Crear nuevo chat, enviar mensaje, recibir en tiempo real | Correcto |
| Seguridad | Usuario A intenta leer chat de usuarios B y C | Bloqueado por reglas Firestore |
| Rendimiento | Carga de 20 productos en conexión 4G | Carga en menos de 2 segundos |

Implementación: la app se distribuyó mediante APK firmado en modo debug para pruebas internas. La configuración del archivo google-services.json vincula la app con el proyecto Firebase del equipo. La app fue probada en emulador Pixel 6 (API 34) y en dispositivos físicos con Android 10 y Android 13.


7.1.1. Evidencias del funcionamiento

A continuación se describe la funcionalidad principal de cada pantalla:

[Figura 5. Pantalla de Login]
Permite al usuario iniciar sesión con correo y contraseña. Incluye validaciones en tiempo real: formato de correo, mínimo 6 caracteres, mensajes de error específicos. Enlace a la pantalla de registro.

[Figura 6. Pantalla de Registro]
Permite crear una cuenta con nombre, correo y contraseña. Valida que las contraseñas coincidan y que el correo no esté registrado. Al completarse, crea el documento del usuario en Firestore con 0 Puntos Verdes y nivel Semilla Verde.

[Figura 7. Pantalla Home con dashboard de Puntos Verdes]
Muestra el dashboard principal con la tarjeta verde que incluye nivel actual, Puntos Verdes acumulados, barra de progreso al siguiente nivel y estadísticas de objetos publicados, reutilizados y racha de días. Debajo muestra el feed de productos recién publicados en tiempo real.

[Figura 8. Pantalla Explorar con filtros]
Búsqueda de objetos con debounce de 400ms. Chips de categoría seleccionables (Ropa, Libros, Muebles, Electrónicos, Juguetes, Hogar, Deporte). Panel desplegable de filtros por tipo de transacción. Contador de resultados actualizados dinámicamente.

[Figura 9. Pantalla Publicar artículo]
Formulario de publicación con carga de hasta 5 fotos, selector de tipo de transacción (Venta/Donación/Intercambio), campo de título obligatorio, descripción, selector de categoría en grilla visual y selector de condición. Muestra los Puntos Verdes que ganará antes de publicar.

[Figura 10. Pantalla Chat en tiempo real]
Lista de conversaciones activas ordenadas por más recientes. Detalle del chat con burbujas diferenciadas (mensajes propios en verde a la derecha, del interlocutor en blanco a la izquierda). Input con botón de enviar. Scroll automático al último mensaje.

[Figura 11. Pantalla Perfil con insignias]
Información del usuario con avatar, nombre y estado de verificación. Tarjeta verde con nivel y estadísticas. Sección de insignias con estado bloqueado/desbloqueado. Opciones de configuración (notificaciones, privacidad, idioma, ayuda). Botón de cerrar sesión con diálogo de confirmación.


7.2. Repositorios

Sistema de control de versiones utilizado: Git
Plataforma de repositorio remoto: GitHub

Link del repositorio: https://github.com/[usuario]/ReUtiliza-Android

Estructura de ramas:
- main: código estable, versión final entregada
- develop: integración de nuevas funcionalidades
- feature/auth: módulo de autenticación
- feature/publish: módulo de publicación
- feature/gamification: sistema de puntos e insignias
- feature/chat: módulo de chat en tiempo real


---


COMPLEMENTO

8.1. Evidencia de Participación

[Insertar capturas de pantalla de participación en debates del aula virtual y evidencia de entrega de avances del proyecto]


8.2. Video de presentación del proyecto (Link)

[Insertar link del video alojado en Office 365 — duración máxima 10 minutos: 3 min parte teórica + 7 min demostración del producto]


8.3. PPT sustentación

[Insertar capturas de las diapositivas utilizadas en la sustentación]


8.4. Link del repositorio donde se encuentran los proyectos (código)

https://github.com/[usuario]/ReUtiliza-Android


---


RESULTADOS

9.1. Resultados en base a los objetivos del proyecto

Tabla 9. Resultados por objetivo específico

| Objetivo específico | Resultado obtenido | Estado |
|---|---|---|
| OE1: Módulo de publicación en menos de 3 minutos | El flujo de publicación consta de 4 pasos. En pruebas con usuarios, el tiempo promedio de publicación fue de 2 minutos 15 segundos, por debajo del objetivo. | Cumplido |
| OE2: Sistema de gamificación con Puntos Verdes, 5 niveles e insignias | Implementados 5 niveles (Semilla Verde → Eco Maestro), 8 insignias desbloqueables y puntos diferenciados por tipo de transacción (Donación +30, Intercambio +20, Venta +15). Puntos permanentes. | Cumplido |
| OE3: Chat en tiempo real | Chat funcional con sincronización en tiempo real vía Firestore. Soporta múltiples conversaciones simultáneas. Un chat por par de usuarios por objeto. | Cumplido |
| OE4: Perfil con estadísticas de impacto ambiental | El perfil muestra Puntos Verdes totales, nivel actual con barra de progreso, objetos publicados, reutilizados y racha de días consecutivos activos. | Cumplido |
| OE5: Búsqueda y filtrado | Búsqueda por texto con debounce de 400ms. Filtros por 8 categorías, 3 tipos de transacción y 4 estados de condición. Los filtros son combinables. | Cumplido |


9.2. Discusión de resultados

Comparación de la postura adoptada en el proyecto

ReUtiliza adopta la postura de que la gamificación con propósito ambiental es el mecanismo más efectivo para sostener el cambio de comportamiento en plataformas de economía circular, fundamentada en la evidencia de Shinnaranantana (2025) y Halonen et al. (2026).

Argumentación y discusión

Los resultados del desarrollo confirman que la combinación de gamificación, facilidad de uso y comunidad local constituye una propuesta diferenciada respecto a las alternativas existentes:

Frente a Olio (donación sin incentivos): ReUtiliza agrega el sistema de Puntos Verdes que cierra la brecha de retención identificada por Halonen et al. (2026). Los datos de Olio muestran que la mayoría de sus 9.3 millones de usuarios son pasivos (Olio, 2026), lo que confirma que el altruismo puro no es suficiente para mantener la participación.

Frente a Vinted y OLX (solo venta): ReUtiliza atiende el segmento de donación e intercambio que estas plataformas ignoran, respondiendo al hallazgo de Tassinari et al. (2023) de que el usuario necesita percibir un beneficio personal más allá del monetario.

Frente al contexto peruano: con 8.99 millones de toneladas de residuos anuales y solo el 2.77% valorizado (MINAM, 2024), la brecha entre residuos aprovechables y efectivamente reutilizados demuestra que el problema no es la falta de material reutilizable sino la ausencia de canales digitales con incentivos adecuados.

Información relevante relacionada a la postura seleccionada

Pereira et al. (2024) identificaron que las apps más efectivas para cambio de comportamiento sostenible combinan retroalimentación inmediata, metas graduales y componente social. ReUtiliza implementa los tres: puntos al publicar (retroalimentación inmediata), cinco niveles progresivos (metas graduales) y ranking comunitario (componente social). Esta coherencia entre el diseño de la app y la evidencia académica valida la postura adoptada.

Limitaciones identificadas: el problema del arranque en frío (sin usuarios iniciales no hay contenido), la búsqueda full-text limitada por Firestore y la verificación manual de usuarios por restricciones del plan gratuito de Firebase.


---


CONCLUSIONES

1. Respecto al OE1 (Publicación): se logró implementar un flujo de publicación de 4 pasos con tiempo promedio de 2 minutos 15 segundos, cumpliendo el objetivo de menos de 3 minutos. El acceso nativo a la cámara de Android y la interfaz visual de selección de categorías fueron determinantes para reducir la fricción del proceso.

2. Respecto al OE2 (Gamificación): el sistema de Puntos Verdes con 5 niveles y 8 insignias fue implementado completamente. La decisión de hacer los puntos e insignias permanentes, nunca degradables, responde a las malas prácticas documentadas en plataformas de delivery que generan ansiedad, y constituye un diferenciador ético de la propuesta respecto a la gamificación manipuladora.

3. Respecto al OE3 (Chat): el chat en tiempo real con Firebase Firestore demostró ser la solución técnicamente correcta para el alcance del proyecto, eliminando la necesidad de servidores WebSocket propios y garantizando sincronización instantánea entre usuarios.

4. Respecto al OE4 (Perfil de impacto): el perfil gamificado con estadísticas ambientales visibles cumple el principio de retroalimentación inmediata identificado por Pereira et al. (2024) como uno de los tres factores críticos para apps de comportamiento sostenible.

5. Respecto al OE5 (Búsqueda): el sistema de filtros combinables con debounce de 400ms ofrece una experiencia fluida sin saturar la base de datos. La limitación de Firestore para búsqueda full-text es un punto de mejora para versiones futuras.

6. Conclusión general: ReUtiliza demuestra que es técnicamente factible desarrollar una plataforma de economía circular gamificada con el stack Android/Kotlin/Firebase en un entorno académico, y que la combinación de facilidad de publicación, incentivos gamificados y comunidad local es una propuesta viable para contribuir a la reducción de residuos sólidos urbanos alineada con el ODS 12.

Recomendaciones

- Implementar una estrategia de adopción temprana en comunidades universitarias para superar el problema del arranque en frío.
- Añadir en una versión futura el equivalente ambiental concreto de cada publicación (por ejemplo: esta donación equivale a ahorrar 2,700 litros de agua).
- Integrar Algolia Search para habilitar búsqueda full-text avanzada.
- Explorar alianzas con municipalidades que tienen programas de gestión de residuos para ampliar el alcance de la plataforma.


---


REFERENCIAS O BIBLIOGRAFÍA

Deák, Z. (2025). Sharing economy and mobile applications — Acceptance and usage. Obuda University, Keleti Károly Faculty of Business and Management. https://kgk.uni-obuda.hu/wp-content/uploads/2025/07/TT7-Deak-SHARING-ECONOMY.pdf

Economía Digital. (2025). Vinted bate récord de ingresos en 2025 tras alcanzar los 1.100 millones de euros. https://www.economiadigital.es/empresas/vinted-resultados-2025.html

Gestión. (2026). E-commerce y el nuevo perfil del comprador peruano: provincias lideran su expansión. https://gestion.pe/economia/empresas/e-commerce-y-el-nuevo-perfil-del-comprador-peruano

Google. (2024). Android developers documentation — Jetpack Compose. https://developer.android.com/compose

Google. (2024). Firebase documentation. https://firebase.google.com/docs

Halonen, V., Claudelin, A., Kareinen, E., & Levänen, J. (2026). Exploring the impacts of mobile application interventions on sustainable lifestyle changes. Circular Economy and Sustainability, 6(2), 1-22. https://doi.org/10.1007/s43615-026-00901-7

Latinometrics. (2025). Android dominates LatAm: 3 of 4 phones. https://www.latinometrics.com/articles/android-vs-iphone

Mathur, A., et al. (2024). An ontology of dark patterns knowledge: Foundations, definitions, and a pathway for shared knowledge-building. Proceedings of the ACM CHI Conference on Human Factors in Computing Systems. https://dl.acm.org/doi/10.1145/3613904.3642436

Ministerio del Ambiente del Perú — MINAM. (2024). Perú: valorización de residuos en 2024 superó las 249,000 toneladas. https://www.gob.pe/institucion/minam/noticias/1248744

Naciones Unidas. (2024). SDG report 2024: Goal 12 — Responsible consumption and production. https://unstats.un.org/sdgs/report/2024/goal-12/

Nguyen, T., et al. (2025). From awareness to action: The role of civic campaigns and persuasive technologies in sustainable behaviors. Springer — Lecture Notes in Networks and Systems. https://link.springer.com/content/pdf/10.1007/978-981-95-1361-1_49.pdf

Olio. (2026). Our vision. https://olioapp.com/en/our-vision/

Pereira, R., et al. (2024). Promoting sustainable behaviors through mobile apps: SBAM design guidelines. Multimedia Tools and Applications. https://link.springer.com/10.1007/s11042-024-18299-5

Shinnaranantana, N. (2025). Impacts of gamification of eco-friendly behavior. Human Behavior, Development and Society. https://so01.tci-thaijo.org/index.php/hbds/article/view/287128

Tassinari, M., et al. (2023). A conceptual framework of the peer-to-peer sharing and reuse platform in the circular economy. Sustainability, 15(11), 8852. https://www.mdpi.com/2071-1050/15/11/8852

Too Good To Go. (2026). About us. https://www.toogoodtogo.com/en-us/about-us

UPC — Punto Seguido. (2024). Gestión de residuos sólidos: cómo el MINAM y las empresas privadas están solucionando el problema. https://puntoseguido.upc.edu.pe/gestion-de-residuos-solidos


---


ANEXOS

Anexo 1. Investigación de marketing irresponsable en aplicaciones móviles y propuestas éticas
(Ver documento adjunto: INVESTIGACION_MARKETING_ETICO)

Anexo 2. Restricciones realistas del proyecto (conectividad, seguridad, privacidad, accesibilidad, rendimiento y uso responsable de recursos)
(Ver documento adjunto: RESTRICCIONES_REALISTAS)

Anexo 3. Exploración de información — Papers, casos y tendencias con enfoque ODS 12
(Ver documento adjunto: EXPLORACION_INFORMACION)

Anexo 4. Prototipos de alta fidelidad (Figma)
[Insertar capturas de las 8 pantallas diseñadas en Figma]

Anexo 5. Capturas de pantalla del producto final
[Insertar capturas del emulador o dispositivo físico mostrando las pantallas principales en funcionamiento]
