# Investigación 1: Campañas de Marketing Irresponsable en Aplicaciones Móviles y Propuestas Éticas
**Universidad Privada del Norte — Facultad de Ingeniería**
**Curso:** Desarrollo de Aplicaciones Móviles
**Proyecto:** ReUtiliza

---

## Introducción

El diseño de aplicaciones móviles modernas incorpora cada vez más técnicas de marketing y persuasión para aumentar el tiempo de uso, las transacciones y la retención de usuarios. Sin embargo, muchas de estas técnicas cruzan la línea ética al manipular psicológicamente al usuario, explotar sus datos personales o inducirlo a comportamientos de consumo perjudiciales para su bienestar.

Esta investigación analiza tres casos reales y documentados de campañas y prácticas de marketing irresponsable en aplicaciones móviles, examina sus mecanismos de manipulación y propone alternativas éticas aplicables al diseño de ReUtiliza.

---

## Caso 1: Dark Patterns en plataformas de segunda mano — El caso Vinted

### 1.1 Descripción del caso

Vinted es una plataforma europea de compraventa de ropa de segunda mano con más de 65 millones de usuarios registrados. A pesar de posicionarse como una solución sostenible y ecológica, ha sido objeto de críticas y análisis académicos por el uso sistemático de *dark patterns* (patrones de diseño oscuros) en su interfaz.

Los dark patterns son elementos de diseño de interfaz que inducen al usuario a tomar decisiones que no tomaría de forma consciente e informada. Mathur et al. (ACM CHI, 2024) los definen como "desviaciones del comportamiento esperado positivo del diseño, que benefician a la organización en detrimento del usuario". En el caso de Vinted se identificaron los siguientes:

**a) Falsa urgencia y escasez artificial**
Mensajes como *"X personas están viendo este artículo ahora"* o *"Solo queda 1 disponible"* generan la percepción de que el artículo se agotará pronto si el usuario no actúa de inmediato. Investigaciones de la Universidad de Cambridge (2022) demostraron que en muchos casos estos contadores son falsos o están inflados artificialmente.

**b) Precios de referencia inflados**
Se muestra un precio de referencia ("precio original de tienda") significativamente más alto que el precio de venta, creando la ilusión de un ahorro importante aunque el precio de referencia sea arbitrario o no verificable.

**c) Fricción asimétrica**
El proceso de compra tiene mínima fricción (un clic), mientras que el proceso de devolución, cancelación o eliminación de cuenta tiene múltiples pasos y confirmaciones. Esto no es neutral: está diseñado deliberadamente para favorecer la compra impulsiva.

**d) Notificaciones de reactivación agresivas**
Usuarios que no han ingresado a la app en días reciben notificaciones como *"¡Ana acaba de publicar algo que te puede gustar!"* con nombres inventados para simular actividad social cercana.

### 1.2 Impacto negativo

- Estimula el **consumo impulsivo**, contradiciendo el discurso de sostenibilidad de la plataforma.
- Genera **disonancia cognitiva** en el usuario: cree que está siendo sostenible comprando segunda mano, pero en realidad compra más de lo que necesita.
- La Autoridad de Competencia y Mercados del Reino Unido (CMA) emitió en 2022 una guía específica sobre dark patterns en marketplaces, citando prácticas similares a las de Vinted.

### 1.3 Propuesta ética para ReUtiliza

| Mala práctica | Propuesta ética |
|---|---|
| Falsa urgencia ("X personas viendo esto") | **No implementar.** ReUtiliza no mostrará contadores de visualización ni mensajes de urgencia artificial. |
| Precios de referencia inflados | **No aplica.** En ventas ecológicas el vendedor define el precio libremente sin referencia inflada. |
| Friccción asimétrica | El flujo de publicar, editar y eliminar un artículo tiene la misma cantidad de pasos. Eliminar cuenta es accesible en Perfil → Privacidad con un solo paso adicional de confirmación. |
| Notificaciones de reactivación falsas | Las notificaciones solo se envían cuando hay actividad real: un mensaje nuevo o una respuesta a una publicación propia. |

---

## Caso 2: Gamificación manipuladora — El caso de apps de delivery (Rappi, Glovo)

### 2.1 Descripción del caso

Rappi y Glovo son aplicaciones de delivery con sistemas de fidelización gamificados que han sido ampliamente documentados como ejemplos de gamificación manipuladora.

**a) Puntos con fecha de vencimiento corta**
Los puntos acumulados vencen en 30 días si no se realizan nuevas compras. Esto crea una presión artificial que obliga al usuario a comprar no porque lo necesite, sino para no "perder" lo que ya tiene. Este mecanismo explota la aversión a la pérdida (*loss aversion*), sesgo cognitivo ampliamente documentado en el contexto de apps y dark patterns por Luguri & Strahilevitz (2021) en su estudio sobre manipulación en interfaces digitales.

**b) Niveles que se degradan**
En los programas de fidelidad de estas apps, si el usuario no alcanza un mínimo de pedidos al mes, baja de nivel y pierde sus beneficios. Esto genera ansiedad y pedidos innecesarios para mantener el estatus.

**c) Variable Ratio Reinforcement**
Las apps de delivery ofrecen descuentos y recompensas de forma impredecible: a veces sí, a veces no. Este mecanismo, conocido como *variable ratio reinforcement*, genera el mayor nivel de dependencia conductual documentado, y ha sido estudiado en el contexto de apps móviles por Chou et al. (2022) en su investigación sobre diseño adictivo en plataformas digitales. El usuario sigue usando la app porque "quizás esta vez le toque" el descuento.

**d) Misiones artificiales**
"Haz 3 pedidos esta semana y gana 500 puntos." Estas misiones no tienen relación con las necesidades reales del usuario; están diseñadas exclusivamente para incrementar la frecuencia de pedidos.

### 2.2 Impacto negativo

- **Dependencia psicológica:** el usuario siente ansiedad cuando no usa la app por miedo a perder su nivel o sus puntos.
- **Gasto innecesario:** los usuarios hacen pedidos que no necesitaban para cumplir misiones o evitar la degradación de nivel.
- El Centre for Humane Technology (2023) clasificó estas prácticas como *"engagement-maximizing design"* en contraposición al *"wellbeing-maximizing design"*.
- En España, la Organización de Consumidores y Usuarios (OCU) denunció en 2023 estas prácticas en plataformas de delivery como potencialmente lesivas para el consumidor.

### 2.3 Propuesta ética para ReUtiliza

| Mala práctica | Propuesta ética |
|---|---|
| Puntos con fecha de vencimiento | **Los Puntos Verdes nunca caducan.** Reflejan el historial ambiental permanente del usuario. |
| Niveles que se degradan | **Los niveles nunca bajan.** Una vez alcanzado un nivel, el usuario lo mantiene para siempre. El progreso es siempre positivo. |
| Variable ratio reinforcement | **Sin recompensas aleatorias.** Cada acción tiene una recompensa fija, transparente y predecible. El usuario sabe exactamente cuántos puntos ganará antes de publicar. |
| Misiones artificiales | **Sin misiones que incentiven el consumo.** Los retos de ReUtiliza incentivan la *reducción* de residuos (publicar un objeto, donar 3 artículos), nunca el consumo. |
| Insignias que se pierden | **Las insignias son permanentes.** Una vez desbloqueada, ninguna insignia puede perderse. |

---

## Caso 3: Explotación de datos y privacidad — El caso Facebook Marketplace y OLX

### 3.1 Descripción del caso

Facebook Marketplace y OLX son dos de las plataformas de compraventa más usadas en Latinoamérica. Ambas han sido objeto de investigaciones regulatorias y académicas por sus prácticas de recopilación y uso de datos.

**a) Perfilado conductual sin consentimiento informado**
Facebook Marketplace registra no solo lo que el usuario compra o vende, sino también cuánto tiempo observa cada producto, qué palabras usa en los mensajes privados, con quién habla y desde dónde. Estos datos se cruzan con el perfil de Facebook para construir un perfil conductual detallado usado para publicidad microfocalizada.

En 2023, Meta fue multada con 1.200 millones de euros por el regulador irlandés de protección de datos (DPC) por transferencia ilegal de datos de usuarios europeos a Estados Unidos, en el marco del caso *Meta Platforms vs. Schrems II*.

**b) Consentimiento enterrado**
Los términos de uso de OLX tienen más de 8.000 palabras en promedio. La cláusula que autoriza el uso de datos para publicidad se encuentra en la página 12 del documento, después de secciones sobre "Definiciones" y "Modificaciones al servicio". Este diseño no es neutral: está deliberadamente pensado para que el usuario haga clic en "Acepto" sin leer.

**c) Datos vendidos a brokers de información**
Investigaciones del Pew Research Center (2023) revelaron que el 79% de los usuarios de apps de marketplace en Estados Unidos no sabían que sus datos de comportamiento podían ser vendidos a terceros (*data brokers*) para fines ajenos a la plataforma.

**d) Publicidad invasiva basada en conversaciones**
Usuarios de Facebook han reportado consistentemente que después de mencionar un producto en una conversación de Messenger, ese producto aparece en anuncios de Marketplace. Aunque Meta niega técnicamente "escuchar" conversaciones, los algoritmos de inferencia basados en comportamiento logran efectos prácticamente indistinguibles.

### 3.2 Impacto negativo

- **Violación del derecho a la privacidad** reconocido en el artículo 12 de la Declaración Universal de Derechos Humanos.
- **Asimetría de poder:** la empresa tiene información detallada sobre el usuario, pero el usuario no sabe qué información tiene la empresa ni para qué la usa.
- **Discriminación algorítmica:** el perfilado puede llevar a que ciertos usuarios vean precios más altos o menos opciones dependiendo de su perfil económico inferido.
- En Perú, la Autoridad Nacional de Protección de Datos Personales (ANPDP) multó en 2022 a una empresa de marketplace por compartir datos de usuarios con terceros sin consentimiento explícito.

### 3.3 Propuesta ética para ReUtiliza

| Mala práctica | Propuesta ética |
|---|---|
| Perfilado conductual para publicidad | **Sin publicidad en la app.** ReUtiliza no monetiza mediante publicidad. No hay SDKs de terceros que rastreen el comportamiento. |
| Análisis de mensajes privados | **Los chats son privados.** El contenido de los mensajes se almacena en Firestore solo para entrega entre los dos participantes. No se analiza ni indexa. |
| Consentimiento enterrado en términos largos | **Política de privacidad en lenguaje claro**, accesible antes del registro y en el perfil, redactada en menos de 500 palabras con puntos concretos. |
| Venta de datos a terceros | **Principio de no venta de datos.** ReUtiliza no vende, cede ni comparte datos de usuarios con terceros bajo ninguna circunstancia. |
| Recopilación excesiva de datos | **Principio de minimización:** solo nombre, correo, ciudad y contenido de publicaciones. Sin registro de tiempo de visualización, patrones de búsqueda exportables ni geolocalización exacta compartida. |

---

## Síntesis: Decálogo Ético de ReUtiliza

A partir del análisis de los tres casos, el equipo de ReUtiliza adopta formalmente los siguientes compromisos éticos de diseño:

1. **Sin dark patterns.** La interfaz no usa mecanismos de presión, urgencia artificial ni fricción asimétrica.
2. **Gamificación positiva.** Los puntos, niveles e insignias reflejan logros reales y nunca generan ansiedad por pérdida.
3. **Recompensas predecibles y transparentes.** El usuario sabe exactamente qué ganará antes de realizar cualquier acción.
4. **Sin publicidad de terceros.** La app no integra ningún SDK publicitario o de rastreo de comportamiento.
5. **Datos mínimos necesarios.** Solo se recopilan los datos estrictamente necesarios para el funcionamiento de la app.
6. **Mensajes privados son privados.** El contenido de los chats no se analiza, indexa ni usa para ningún otro propósito.
7. **Consentimiento informado real.** La política de privacidad es corta, clara y accesible antes del registro.
8. **Sin notificaciones manipuladoras.** Solo se envían notificaciones cuando hay actividad real y relevante para el usuario.
9. **El usuario controla sus datos.** Puede eliminar su cuenta y todos sus datos en cualquier momento desde la app.
10. **El valor es el impacto ambiental, no el consumo.** Ninguna funcionalidad incentiva comprar más; todas incentivan reutilizar más.

---

## Referencias

- Mathur, A. et al. (2024). *An Ontology of Dark Patterns Knowledge: Foundations, Definitions, and a Pathway for Shared Knowledge-Building.* ACM CHI Conference on Human Factors in Computing Systems. https://dl.acm.org/doi/10.1145/3613904.3642436
- Luguri, J. & Strahilevitz, L. (2021). *Shining a Light on Dark Patterns.* Journal of Legal Analysis, 13(1), 43–109. https://doi.org/10.1093/jla/laaa006
- Chou, H. et al. (2022). *Dark Patterns Conceptualization and Future Research Directions.* Business & Information Systems Engineering. https://link.springer.com/article/10.1007/s12599-022-00783-7
- Centre for Humane Technology. (2023). *The Social Dilemma: Engagement vs. Wellbeing.* https://www.humanetech.com
- Pew Research Center. (2023). *Americans and Privacy: Concerned, Confused and Feeling Lack of Control.* https://www.pewresearch.org/internet/2023/10/18/how-americans-think-about-privacy/
- Data Protection Commission Ireland. (2023). *Meta Platforms Inc. — Decision on cross-border processing.* https://www.dataprotection.ie/en/news-media/press-releases/dpc-announces-decision-in-meta-ireland-inquiry
- Organización de Consumidores y Usuarios — OCU España. (2023). *Gamificación y consumo: ¿incentivo o trampa?* https://www.ocu.org
- Autoridad Nacional de Protección de Datos Personales — ANPDP Perú. (2022). *Resolución Directoral N° 087-2022.* https://www.gob.pe/anpdp
- Halonen, V., Claudelin, A., Kareinen, E. y Levänen, J. (2026). *Exploring the Impacts of Mobile Application Interventions on Sustainable Lifestyle Changes.* Circular Economy and Sustainability, 6(2). https://doi.org/10.1007/s43615-026-00901-7
- Shinnaranantana, N. (2025). *Impacts of gamification of eco-friendly behavior.* Human Behavior, Development and Society. https://so01.tci-thaijo.org/index.php/hbds/article/view/287128

---

*Documento elaborado en el marco del curso Desarrollo de Aplicaciones Móviles — UPN 2026-2*
