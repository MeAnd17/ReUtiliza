🌱 ReUtiliza

Plataforma móvil de economía circular gamificada para la reducción de residuos sólidos urbanos.

📱 Descripción

ReUtiliza es una aplicación móvil nativa para Android que permite publicar, explorar e intercambiar bienes en desuso mediante tres modalidades: **donación**, **intercambio** y **venta ecológica**. Incorpora un sistema de gamificación basado en **Puntos Verdes**, niveles progresivos e insignias desbloqueables para incentivar hábitos sostenibles.

🏗️ Arquitectura
- Cliente Android: Kotlin 2.0 + Jetpack Compose + MVVM
- Backend: Node.js + Express + PostgreSQL desplegado en Render
- Autenticación: JWT almacenado en DataStore Preferences
- Notificaciones: Firebase Cloud Messaging

🚀 Backend en producción
https://reutiliza.onrender.com/api/health



📋 Funcionalidades

- Registro e inicio de sesión con JWT
- Publicación de artículos con categoría y tipo de transacción
- Catálogo con búsqueda y filtros por categoría
- Sistema de favoritos
- Chat entre usuarios
- Perfil con Puntos Verdes, niveles e insignias
- Notificaciones push

🛠️ Tecnologías

| Capa | Tecnología |
|------|-----------|
| UI | Jetpack Compose |
| Arquitectura | MVVM + Hilt |
| Red | Retrofit 2.11 + OkHttp |
| Backend | Node.js + Express 4.19 |
| Base de datos | PostgreSQL 18 (Render) |
| Autenticación | JWT + bcryptjs |

👥 Equipo

- Altamirano Diaz, Alanis
- Hernandez Mendoza, Analucia
- Montoya Bravo, Fabian Nicolas
- Sanchez Chigne, Andrea Christina

🎓 Universidad Privada del Norte — 2026-2
