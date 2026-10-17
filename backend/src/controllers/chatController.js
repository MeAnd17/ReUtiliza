const { v4: uuidv4 } = require('uuid');
const pool = require('../db/pool');

function formatChat(row, currentUserId) {
  return {
    id:                     row.id,
    productId:              row.product_id,
    productTitle:           row.product_title           || '',
    productPrice:           parseFloat(row.product_price) || 0.0,
    productTransactionType: row.product_transaction_type || '',
    productImageUrl:        row.product_image_url       || '',
    participantIds:         row.participant_ids         || [],
    participantNames:       row.participant_names       || {},
    participantPhotos:      row.participant_photos      || {},
    sellerId:               row.seller_id,
    buyerId:                row.buyer_id,
    lastMessage:            row.last_message            || '',
    lastMessageTime:        row.last_message_time       || 0,
    unreadCount:            row.unread_count            || 0,
  };
}

function formatMessage(row) {
  return {
    id:         row.id,
    chatId:     row.chat_id,
    senderId:   row.sender_id,
    senderName: row.sender_name || '',
    content:    row.content,
    type:       row.type        || 'TEXT',
    timestamp:  row.timestamp   || 0,
    isRead:     row.is_read     || false,
  };
}

/** Construye el mensaje automático inicial según el tipo de transacción */
function defaultMessage(transactionType, productTitle) {
  if (transactionType === 'DONATION')
    return `¡Hola! Me interesa tu donación de "${productTitle}". ¿Sigue disponible? 🎁`;
  if (transactionType === 'EXCHANGE')
    return `¡Hola! Me interesa intercambiar por "${productTitle}". ¿Sigue disponible? 🔄`;
  return `¡Hola! Estoy interesado en comprar "${productTitle}". ¿Sigue disponible? 💰`;
}

// ── GET /api/chats ────────────────────────────────────────────────────────────
async function getUserChats(req, res) {
  try {
    const { rows } = await pool.query(
      `SELECT c.*,
              ARRAY[c.buyer_id, c.seller_id] AS participant_ids,
              json_build_object(
                c.buyer_id,  pb.name,
                c.seller_id, ps.name
              ) AS participant_names,
              json_build_object(
                c.buyer_id,  pb.photo_url,
                c.seller_id, ps.photo_url
              ) AS participant_photos
       FROM chats c
       JOIN users pb ON pb.id = c.buyer_id
       JOIN users ps ON ps.id = c.seller_id
       WHERE c.buyer_id = $1 OR c.seller_id = $1
       ORDER BY c.last_message_time DESC`,
      [req.userId]
    );
    res.json(rows.map(r => formatChat(r, req.userId)));
  } catch (err) {
    console.error('getUserChats error:', err.message);
    res.status(500).json({ error: 'Error interno del servidor' });
  }
}

// ── POST /api/chats ───────────────────────────────────────────────────────────
async function getOrCreateChat(req, res) {
  const { productId, buyerId, sellerId } = req.body;
  if (!productId || !buyerId || !sellerId) {
    return res.status(400).json({ error: 'productId, buyerId y sellerId son requeridos' });
  }

  try {
    // ¿ya existe?
    const existing = await pool.query(
      'SELECT id FROM chats WHERE product_id = $1 AND buyer_id = $2 AND seller_id = $3',
      [productId, buyerId, sellerId]
    );
    if (existing.rowCount > 0) {
      return res.json({ chatId: existing.rows[0].id, created: false });
    }

    // Obtener producto y participantes
    const [prodRes, buyerRes, sellerRes] = await Promise.all([
      pool.query('SELECT * FROM products WHERE id = $1', [productId]),
      pool.query('SELECT id, name, photo_url FROM users WHERE id = $1', [buyerId]),
      pool.query('SELECT id, name, photo_url FROM users WHERE id = $1', [sellerId]),
    ]);

    if (!prodRes.rowCount || !buyerRes.rowCount || !sellerRes.rowCount) {
      return res.status(404).json({ error: 'Producto o usuario no encontrado' });
    }

    const product = prodRes.rows[0];
    const buyer   = buyerRes.rows[0];
    const seller  = sellerRes.rows[0];
    const chatId  = uuidv4();
    const now     = Date.now();
    const msgText = defaultMessage(product.transaction_type, product.title);

    await pool.query(
      `INSERT INTO chats
         (id, product_id, product_title, product_price, product_transaction_type,
          product_image_url, seller_id, buyer_id, last_message, last_message_time,
          unread_count, created_at)
       VALUES ($1,$2,$3,$4,$5,$6,$7,$8,$9,$10,0,$11)`,
      [
        chatId, productId, product.title, product.price,
        product.transaction_type,
        (product.image_urls && product.image_urls[0]) || '',
        sellerId, buyerId, msgText, now, now,
      ]
    );

    // Primer mensaje automático
    const msgId = uuidv4();
    await pool.query(
      `INSERT INTO messages (id, chat_id, sender_id, sender_name, content, type, is_read, timestamp)
       VALUES ($1,$2,$3,$4,$5,'TEXT',FALSE,$6)`,
      [msgId, chatId, buyerId, buyer.name, msgText, now]
    );

    res.status(201).json({ chatId, created: true });
  } catch (err) {
    console.error('getOrCreateChat error:', err.message);
    res.status(500).json({ error: 'Error interno del servidor' });
  }
}

// ── GET /api/messages/:chatId ─────────────────────────────────────────────────
async function getChatMessages(req, res) {
  try {
    const { rows } = await pool.query(
      `SELECT * FROM messages WHERE chat_id = $1 ORDER BY timestamp ASC`,
      [req.params.chatId]
    );
    res.json(rows.map(formatMessage));
  } catch (err) {
    console.error('getChatMessages error:', err.message);
    res.status(500).json({ error: 'Error interno del servidor' });
  }
}

// ── POST /api/messages/:chatId ────────────────────────────────────────────────
async function sendMessage(req, res) {
  const { chatId } = req.params;
  const { content, type = 'TEXT' } = req.body;

  if (!content) return res.status(400).json({ error: 'content es requerido' });

  try {
    // Verificar que el usuario es participante del chat
    const chatRes = await pool.query(
      'SELECT * FROM chats WHERE id = $1', [chatId]
    );
    if (!chatRes.rowCount) return res.status(404).json({ error: 'Chat no encontrado' });

    const chat = chatRes.rows[0];
    if (chat.buyer_id !== req.userId && chat.seller_id !== req.userId) {
      return res.status(403).json({ error: 'No sos participante de este chat' });
    }

    const senderRes = await pool.query('SELECT name FROM users WHERE id = $1', [req.userId]);
    const senderName = senderRes.rows[0]?.name || '';

    const msgId = uuidv4();
    const now   = Date.now();

    const { rows } = await pool.query(
      `INSERT INTO messages (id, chat_id, sender_id, sender_name, content, type, is_read, timestamp)
       VALUES ($1,$2,$3,$4,$5,$6::message_type,FALSE,$7)
       RETURNING *`,
      [msgId, chatId, req.userId, senderName, content, type, now]
    );

    // Actualizar último mensaje del chat
    await pool.query(
      `UPDATE chats SET last_message = $1, last_message_time = $2 WHERE id = $3`,
      [content, now, chatId]
    );

    res.status(201).json(formatMessage(rows[0]));
  } catch (err) {
    console.error('sendMessage error:', err.message);
    res.status(500).json({ error: 'Error interno del servidor' });
  }
}

module.exports = { getUserChats, getOrCreateChat, getChatMessages, sendMessage };
