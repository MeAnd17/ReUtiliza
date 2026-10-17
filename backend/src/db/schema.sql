-- ============================================================
--  ReUtiliza — Schema PostgreSQL
-- ============================================================

-- Tipos enumerados
CREATE TYPE user_level AS ENUM ('SEED','SPROUT','BRIGHT','GUARDIAN','ECO_MASTER');
CREATE TYPE product_category AS ENUM ('CLOTHING','BOOKS','FURNITURE','ELECTRONICS','TOYS','HOME','SPORTS','OTHER');
CREATE TYPE transaction_type AS ENUM ('SALE','DONATION','EXCHANGE');
CREATE TYPE product_condition AS ENUM ('NEW','LIKE_NEW','GOOD','ACCEPTABLE');
CREATE TYPE message_type AS ENUM ('TEXT','IMAGE');

-- ── Usuarios ────────────────────────────────────────────────
CREATE TABLE users (
    id              VARCHAR(36)  PRIMARY KEY,          -- UUID generado por la app/servidor
    name            VARCHAR(100) NOT NULL,
    email           VARCHAR(150) NOT NULL UNIQUE,
    password_hash   VARCHAR(255) NOT NULL,
    photo_url       TEXT         DEFAULT '',
    location        VARCHAR(200) DEFAULT '',
    bio             TEXT         DEFAULT '',
    green_points    INT          DEFAULT 0,
    level           user_level   DEFAULT 'SEED',
    published_count INT          DEFAULT 0,
    reutilized_count INT         DEFAULT 0,
    streak_days     INT          DEFAULT 0,
    badges          TEXT[]       DEFAULT '{}',         -- array de IDs de insignias
    is_verified     BOOLEAN      DEFAULT FALSE,
    fcm_token       TEXT         DEFAULT '',
    created_at      BIGINT       DEFAULT EXTRACT(EPOCH FROM NOW()) * 1000
);

-- ── Productos ────────────────────────────────────────────────
CREATE TABLE products (
    id                  VARCHAR(36)       PRIMARY KEY,
    owner_id            VARCHAR(36)       NOT NULL REFERENCES users(id),
    owner_name          VARCHAR(100)      DEFAULT '',
    owner_photo_url     TEXT              DEFAULT '',
    title               VARCHAR(200)      NOT NULL,
    description         TEXT              DEFAULT '',
    category            product_category  DEFAULT 'OTHER',
    transaction_type    transaction_type  DEFAULT 'DONATION',
    condition           product_condition DEFAULT 'GOOD',
    price               DECIMAL(10,2)     DEFAULT 0.0,
    image_urls          TEXT[]            DEFAULT '{}',
    location            VARCHAR(200)      DEFAULT '',
    latitude            DECIMAL(10,7)     DEFAULT 0.0,
    longitude           DECIMAL(10,7)     DEFAULT 0.0,
    green_points_reward INT               DEFAULT 0,
    is_available        BOOLEAN           DEFAULT TRUE,
    favorites_count     INT               DEFAULT 0,
    created_at          BIGINT            DEFAULT EXTRACT(EPOCH FROM NOW()) * 1000
);

-- ── Favoritos ───────────────────────────────────────────────
CREATE TABLE favorites (
    id          VARCHAR(73) PRIMARY KEY,   -- "{userId}_{productId}"
    user_id     VARCHAR(36) NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    product_id  VARCHAR(36) NOT NULL REFERENCES products(id) ON DELETE CASCADE,
    created_at  BIGINT      DEFAULT EXTRACT(EPOCH FROM NOW()) * 1000,
    UNIQUE(user_id, product_id)
);

-- ── Chats ────────────────────────────────────────────────────
CREATE TABLE chats (
    id                      VARCHAR(36)  PRIMARY KEY,
    product_id              VARCHAR(36)  NOT NULL REFERENCES products(id),
    product_title           VARCHAR(200) DEFAULT '',
    product_price           DECIMAL(10,2) DEFAULT 0.0,
    product_transaction_type VARCHAR(20) DEFAULT '',
    product_image_url       TEXT         DEFAULT '',
    seller_id               VARCHAR(36)  NOT NULL REFERENCES users(id),
    buyer_id                VARCHAR(36)  NOT NULL REFERENCES users(id),
    last_message            TEXT         DEFAULT '',
    last_message_time       BIGINT       DEFAULT EXTRACT(EPOCH FROM NOW()) * 1000,
    unread_count            INT          DEFAULT 0,
    created_at              BIGINT       DEFAULT EXTRACT(EPOCH FROM NOW()) * 1000,
    UNIQUE(product_id, buyer_id, seller_id)   -- evita chats duplicados
);

-- ── Participantes del chat ───────────────────────────────────
CREATE TABLE chat_participants (
    chat_id  VARCHAR(36)  NOT NULL REFERENCES chats(id) ON DELETE CASCADE,
    user_id  VARCHAR(36)  NOT NULL REFERENCES users(id),
    name     VARCHAR(100) DEFAULT '',
    photo_url TEXT        DEFAULT '',
    PRIMARY KEY (chat_id, user_id)
);

-- ── Mensajes ─────────────────────────────────────────────────
CREATE TABLE messages (
    id          VARCHAR(36)   PRIMARY KEY,
    chat_id     VARCHAR(36)   NOT NULL REFERENCES chats(id) ON DELETE CASCADE,
    sender_id   VARCHAR(36)   NOT NULL REFERENCES users(id),
    sender_name VARCHAR(100)  DEFAULT '',
    content     TEXT          NOT NULL,
    type        message_type  DEFAULT 'TEXT',
    is_read     BOOLEAN       DEFAULT FALSE,
    timestamp   BIGINT        DEFAULT EXTRACT(EPOCH FROM NOW()) * 1000
);

-- ── Índices ──────────────────────────────────────────────────
CREATE INDEX idx_products_available       ON products(is_available);
CREATE INDEX idx_products_owner           ON products(owner_id);
CREATE INDEX idx_products_category        ON products(category);
CREATE INDEX idx_products_transaction     ON products(transaction_type);
CREATE INDEX idx_products_created         ON products(created_at DESC);
CREATE INDEX idx_favorites_user           ON favorites(user_id);
CREATE INDEX idx_messages_chat            ON messages(chat_id);
CREATE INDEX idx_messages_timestamp       ON messages(timestamp ASC);
CREATE INDEX idx_chats_seller             ON chats(seller_id);
CREATE INDEX idx_chats_buyer              ON chats(buyer_id);
CREATE INDEX idx_chats_last_msg           ON chats(last_message_time DESC);
CREATE INDEX idx_users_green_points       ON users(green_points DESC);
