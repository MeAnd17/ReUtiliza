# Backend ReUtiliza — Mapa de Endpoints e Integración

## Descripción general

El backend es una API REST construida con **Node.js + Express + PostgreSQL**.  
Reemplaza Firebase/Firestore como capa de datos y autenticación del proyecto Android.

| Campo | Detalle |
|---|---|
| Lenguaje | Node.js 18+ |
| Framework | Express 4.19 |
| Base de datos | PostgreSQL 15+ |
| Autenticación | JWT (Bearer token) |
| Subida de imágenes | Multer (almacenamiento local / adaptable a S3) |
| Puerto por defecto | 3000 |
| Base URL local | `http://localhost:3000/api` |

---

## Mapa completo de endpoints

### AUTH — `/api/auth`

| Método | Endpoint | Auth | Descripción |
|--------|----------|------|-------------|
| POST | `/api/auth/register` | ❌ | Registra un nuevo usuario. Crea el documento en `users`. |
| POST | `/api/auth/login` | ❌ | Inicia sesión. Devuelve JWT + datos del usuario. |
| POST | `/api/auth/logout` | ✅ | Cierra sesión (el cliente descarta el token). |
| GET | `/api/auth/me` | ✅ | Devuelve el usuario autenticado actualmente. |

**Body de `/register`:**
```json
{
  "name": "Ana García",
  "email": "ana@mail.com",
  "password": "miPassword123"
}
```

**Respuesta de `/login` y `/register`:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIs...",
  "user": {
    "id": "uuid",
    "name": "Ana García",
    "email": "ana@mail.com",
    "greenPoints": 0,
    "level": "SEED",
    "badges": []
  }
}
```

---

### USUARIOS — `/api/users`

| Método | Endpoint | Auth | Descripción |
|--------|----------|------|-------------|
| GET | `/api/users/leaderboard` | ✅ | Top 20 usuarios por greenPoints. Query param: `?limit=20` |
| GET | `/api/users/:userId` | ✅ | Perfil completo de un usuario. |
| PUT | `/api/users/:userId/profile` | ✅ | Actualiza nombre y bio (solo el propio usuario). |
| PATCH | `/api/users/:userId/green-points` | ✅ | Suma puntos verdes y recalcula el nivel. |
| PATCH | `/api/users/:userId/badges` | ✅ | Desbloquea una insignia si no fue desbloqueada antes. |
| POST | `/api/users/:userId/fcm-token` | ✅ | Guarda el token FCM para notificaciones push. |

**Body de `/profile`:**
```json
{ "name": "Ana García", "bio": "Amo reciclar" }
```

**Body de `/green-points`:**
```json
{ "points": 30 }
```

**Body de `/badges`:**
```json
{ "badgeId": "eco_pioneer" }
```

**IDs de badges disponibles** (catálogo fijo de la app):
`eco_pioneer`, `reutilizer`, `green_bright`, `donor_hero`, `week_streak`, `chat_starter`, `community_top`, `eco_master`

---

### PRODUCTOS — `/api/products`

| Método | Endpoint | Auth | Descripción |
|--------|----------|------|-------------|
| GET | `/api/products` | ❌ | Lista productos disponibles. Query: `?limit=20` |
| GET | `/api/products/search` | ❌ | Busca productos con filtros. |
| GET | `/api/products/:productId` | ❌ | Detalle de un producto. |
| POST | `/api/products` | ✅ | Publica un nuevo producto con imágenes. |
| GET | `/api/products/user/:userId` | ✅ | Productos publicados por un usuario. |

**Query params de `/search`:**
```
?query=silla&category=FURNITURE&transactionType=DONATION
```

**Categorías válidas:** `CLOTHING`, `BOOKS`, `FURNITURE`, `ELECTRONICS`, `TOYS`, `HOME`, `SPORTS`, `OTHER`

**Tipos de transacción:** `SALE`, `DONATION`, `EXCHANGE`

**Condición del producto:** `NEW`, `LIKE_NEW`, `GOOD`, `ACCEPTABLE`

**Body de `POST /products`** — `multipart/form-data`:
```
title:           "Silla de madera"
description:     "Muy buen estado"
category:        FURNITURE
transactionType: DONATION
condition:       GOOD
price:           0
location:        "Buenos Aires"
latitude:        -34.6037
longitude:       -58.3816
images:          [archivo1.jpg, archivo2.jpg]   (máx 5)
```

**Respuesta:**
```json
{
  "productId": "uuid",
  "product": {
    "id": "uuid",
    "ownerId": "uuid",
    "title": "Silla de madera",
    "category": "FURNITURE",
    "transactionType": "DONATION",
    "greenPointsReward": 30,
    "imageUrls": ["/uploads/archivo1.jpg"],
    "isAvailable": true
  }
}
```

> **Side-effect esperado en la integración:** al publicar exitosamente, la app debe llamar a  
> `PATCH /api/users/{ownerId}/green-points` y `PATCH /api/users/{ownerId}/badges` con `eco_pioneer`.

---

### FAVORITOS — `/api/favorites`

| Método | Endpoint | Auth | Descripción |
|--------|----------|------|-------------|
| GET | `/api/favorites` | ✅ | Lista de IDs de productos favoritos del usuario. |
| PUT | `/api/favorites/:productId` | ✅ | Agrega un producto a favoritos. |
| DELETE | `/api/favorites/:productId` | ✅ | Elimina un producto de favoritos. |

**Respuesta de `GET /favorites`:**
```json
["uuid-producto-1", "uuid-producto-2"]
```

---

### CHATS — `/api/chats`

| Método | Endpoint | Auth | Descripción |
|--------|----------|------|-------------|
| GET | `/api/chats` | ✅ | Lista de chats del usuario autenticado, ordenados por último mensaje. |
| POST | `/api/chats` | ✅ | Crea un chat (o devuelve el existente). Idempotente. Crea el primer mensaje automático. |

**Body de `POST /chats`:**
```json
{
  "productId": "uuid-del-producto",
  "buyerId":   "uuid-del-comprador",
  "sellerId":  "uuid-del-vendedor"
}
```

**Respuesta:**
```json
{ "chatId": "uuid", "created": true }
```

---

### MENSAJES — `/api/messages`

| Método | Endpoint | Auth | Descripción |
|--------|----------|------|-------------|
| GET | `/api/messages/:chatId` | ✅ | Mensajes de un chat, ordenados por timestamp ASC. |
| POST | `/api/messages/:chatId` | ✅ | Envía un mensaje. Actualiza `lastMessage` del chat. |

**Body de `POST /messages/:chatId`:**
```json
{
  "content": "Hola, ¿sigue disponible?",
  "type":    "TEXT"
}
```

---

## Modelo de base de datos

```
users
  id, name, email, password_hash, photo_url, location, bio,
  green_points, level, published_count, reutilized_count,
  streak_days, badges[], is_verified, fcm_token, created_at

products
  id, owner_id → users, owner_name, owner_photo_url,
  title, description, category, transaction_type, condition,
  price, image_urls[], location, latitude, longitude,
  green_points_reward, is_available, favorites_count, created_at

favorites
  id (userId_productId), user_id → users, product_id → products

chats
  id, product_id → products, product_title, product_price,
  product_transaction_type, product_image_url,
  seller_id → users, buyer_id → users,
  last_message, last_message_time, unread_count, created_at

messages
  id, chat_id → chats, sender_id → users, sender_name,
  content, type (TEXT|IMAGE), is_read, timestamp
```

---

## Cómo integrar con la app Android

### 1. Paso previo — qué reemplaza cada repositorio

| Repositorio Firebase actual | Cambia por |
|---|---|
| `AuthRepository` (Firebase Auth) | `POST /api/auth/register` y `/login` |
| `UserRepository` (Firestore `users`) | `GET /api/users/:id`, `PATCH /green-points`, etc. |
| `ProductRepository` (Firestore `products`) | `GET /api/products`, `POST /api/products`, etc. |
| `ChatRepository` (Firestore `chats` + `messages`) | `GET /api/chats`, `POST /api/chats`, `GET/POST /api/messages/:chatId` |

### 2. Cambios en el proyecto Android

1. **Agregar base URL del backend** en `EcoApiService` (o crear un `ReUtilizaApiService` separado):
   ```kotlin
   const val BASE_URL = "http://10.0.2.2:3000/api/" // emulador Android → localhost
   ```

2. **Agregar interceptor de autenticación** en `AppModule`:
   ```kotlin
   OkHttpClient.Builder()
     .addInterceptor { chain ->
       val token = tokenStore.getToken() // DataStore o SharedPreferences
       val request = chain.request().newBuilder()
         .addHeader("Authorization", "Bearer $token")
         .build()
       chain.proceed(request)
     }
   ```

3. **Guardar el token** recibido en login/register en `DataStore`:
   ```kotlin
   dataStore.edit { it[TOKEN_KEY] = response.token }
   ```

4. **Polling para tiempo real** — los endpoints `GET /api/products`, `GET /api/chats` y `GET /api/messages/:chatId` son request/response (no WebSocket). Para simular tiempo real se puede usar polling cada 5–10 segundos en el ViewModel con `repeatOnLifecycle`.

### 3. Cómo levantar el backend

```bash
cd backend
cp .env.example .env          # configurar DB_PASSWORD y JWT_SECRET
npm install
# crear la base de datos en PostgreSQL:
# psql -U postgres -c "CREATE DATABASE reutiliza;"
# psql -U postgres -d reutiliza -f src/db/schema.sql
npm run dev                   # inicia en http://localhost:3000
```

### 4. Verificar que funciona

```bash
curl http://localhost:3000/api/health
# respuesta: { "status": "ok", "timestamp": 1729123456789 }
```

---

## Estructura de archivos del backend

```
backend/
├── package.json
├── .env.example
├── .gitignore
└── src/
    ├── index.js               ← entrada principal, configura Express
    ├── db/
    │   ├── pool.js            ← conexión PostgreSQL
    │   └── schema.sql         ← definición completa de tablas e índices
    ├── middleware/
    │   └── auth.js            ← verificación JWT
    ├── controllers/
    │   ├── authController.js  ← register, login, logout, me
    │   ├── userController.js  ← perfil, leaderboard, puntos, badges
    │   ├── productController.js ← CRUD productos + favoritos
    │   └── chatController.js  ← chats y mensajes
    └── routes/
        ├── auth.js
        ├── users.js
        ├── products.js
        ├── favorites.js
        ├── chats.js
        └── messages.js
```

---

## Códigos de respuesta HTTP

| Código | Cuándo se usa |
|--------|---------------|
| `200 OK` | Lectura o actualización exitosa |
| `201 Created` | Recurso creado exitosamente (register, POST product, POST message) |
| `400 Bad Request` | Faltan campos requeridos o el formato del body es incorrecto |
| `401 Unauthorized` | Token ausente, inválido o expirado |
| `403 Forbidden` | El token es válido pero el usuario no tiene permiso (ej: editar perfil ajeno) |
| `404 Not Found` | El recurso solicitado no existe en la base de datos |
| `409 Conflict` | El email ya está registrado |
| `500 Internal Server Error` | Error inesperado en el servidor |

**Formato de error estándar:**
```json
{ "error": "Descripción del error en español" }
```

---

## Niveles de usuario (UserLevel)

La lógica de niveles es idéntica a la app Android. El servidor recalcula el nivel cada vez que se suman puntos verdes.

| Nivel | Nombre | Puntos mínimos |
|-------|--------|---------------|
| `SEED` | Semilla Verde | 0 |
| `SPROUT` | Brote Verde | 100 |
| `BRIGHT` | Verde Brillante | 300 |
| `GUARDIAN` | Guardián Verde | 600 |
| `ECO_MASTER` | Eco Maestro | 1000 |

**Puntos otorgados al publicar un producto:**

| Tipo de transacción | Puntos |
|--------------------|--------|
| `DONATION` | 30 |
| `EXCHANGE` | 20 |
| `SALE` | 15 |

---

## Flujo de autenticación

```
App Android                        Backend
    │                                  │
    │── POST /api/auth/login ─────────►│
    │   { email, password }            │  verifica hash bcrypt
    │                                  │
    │◄── { token, user } ─────────────│
    │                                  │
    │  guarda token en DataStore       │
    │                                  │
    │── GET /api/products ────────────►│
    │   Authorization: Bearer <token>  │  verifica JWT
    │                                  │
    │◄── [ ...products ] ─────────────│
```

El token JWT expira en **7 días** (configurable en `.env`). Cuando expira, la app debe redirigir al login. El token no se invalida en el servidor al hacer logout — el cliente simplemente lo descarta.

---

## Flujo de publicación de producto

```
App Android                              Backend
    │                                        │
    │── POST /api/products ─────────────────►│
    │   multipart/form-data                  │  sube imágenes a /uploads/
    │   + imágenes                           │  inserta en tabla products
    │                                        │
    │◄── { productId, product } ────────────│
    │                                        │
    │── PATCH /api/users/{id}/green-points ─►│
    │   { "points": 30 }                     │  suma puntos + recalcula nivel
    │                                        │
    │── PATCH /api/users/{id}/badges ───────►│
    │   { "badgeId": "eco_pioneer" }         │  desbloquea insignia
    │                                        │
    │◄── { badges: [...] } ─────────────────│
```

---

## Flujo de inicio de chat

```
App Android                              Backend
    │                                        │
    │── POST /api/chats ─────────────────────►│
    │   { productId, buyerId, sellerId }      │  busca chat existente
    │                                         │  si no existe: crea chat
    │                                         │  + primer mensaje automático
    │◄── { chatId, created: true/false } ────│
    │                                        │
    │── GET /api/messages/{chatId} ─────────►│
    │                                        │
    │◄── [ ...messages ] ───────────────────│
```

---

## Consideraciones de seguridad

- **Contraseñas** — se almacenan hasheadas con `bcrypt` (salt rounds: 10). Nunca se devuelven en ninguna respuesta.
- **JWT** — firmado con `HS256`. El secreto debe ser una cadena aleatoria larga (mínimo 32 caracteres). Configurar en `.env`.
- **Autorización por recurso** — el backend verifica que el `userId` del token coincida con el recurso al editar perfil, enviar mensajes y guardar el token FCM.
- **Imágenes** — Multer limita a 5 archivos de máximo 5 MB cada uno. En producción se recomienda reemplazar el almacenamiento local por un servicio como AWS S3 o Cloudinary.
- **CORS** — habilitado para todos los orígenes en desarrollo. En producción configurar la lista blanca en `index.js`.
- **Variables sensibles** — nunca commitear el archivo `.env`. Está incluido en `.gitignore`.

---

## Despliegue en producción

El backend puede desplegarse en cualquier servicio que soporte Node.js y PostgreSQL. Algunas opciones gratuitas para proyectos académicos:

| Servicio | Node.js | PostgreSQL | Notas |
|----------|---------|------------|-------|
| **Railway** | ✅ | ✅ | Muy fácil, conecta directo desde GitHub |
| **Render** | ✅ | ✅ | Plan gratuito disponible |
| **Fly.io** | ✅ | ✅ | Requiere CLI |
| **Supabase** | ❌ | ✅ | Solo BD, el backend se despliega por separado |

**Variables de entorno en producción:**
```
DB_HOST=host-del-servicio
DB_PORT=5432
DB_NAME=reutiliza
DB_USER=usuario
DB_PASSWORD=password-seguro
JWT_SECRET=cadena-aleatoria-muy-larga
JWT_EXPIRES_IN=7d
PORT=3000
```

**URL desde el emulador Android:**
```kotlin
// emulador → localhost de la PC
const val BASE_URL = "http://10.0.2.2:3000/api/"

// dispositivo físico → IP local de la PC (ej: misma red WiFi)
const val BASE_URL = "http://192.168.1.X:3000/api/"

// producción
const val BASE_URL = "https://tu-backend.railway.app/api/"
```

---

## Resumen de endpoints (referencia rápida)

| Módulo | Método | Ruta | Auth |
|--------|--------|------|------|
| Auth | POST | `/api/auth/register` | ❌ |
| Auth | POST | `/api/auth/login` | ❌ |
| Auth | POST | `/api/auth/logout` | ✅ |
| Auth | GET | `/api/auth/me` | ✅ |
| Usuarios | GET | `/api/users/leaderboard` | ✅ |
| Usuarios | GET | `/api/users/:userId` | ✅ |
| Usuarios | PUT | `/api/users/:userId/profile` | ✅ |
| Usuarios | PATCH | `/api/users/:userId/green-points` | ✅ |
| Usuarios | PATCH | `/api/users/:userId/badges` | ✅ |
| Usuarios | POST | `/api/users/:userId/fcm-token` | ✅ |
| Productos | GET | `/api/products` | ❌ |
| Productos | GET | `/api/products/search` | ❌ |
| Productos | GET | `/api/products/:productId` | ❌ |
| Productos | POST | `/api/products` | ✅ |
| Productos | GET | `/api/products/user/:userId` | ✅ |
| Favoritos | GET | `/api/favorites` | ✅ |
| Favoritos | PUT | `/api/favorites/:productId` | ✅ |
| Favoritos | DELETE | `/api/favorites/:productId` | ✅ |
| Chats | GET | `/api/chats` | ✅ |
| Chats | POST | `/api/chats` | ✅ |
| Mensajes | GET | `/api/messages/:chatId` | ✅ |
| Mensajes | POST | `/api/messages/:chatId` | ✅ |
| Health | GET | `/api/health` | ❌ |

**Total: 23 endpoints**
