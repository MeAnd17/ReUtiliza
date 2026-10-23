const { v4: uuidv4 } = require('uuid');
const pool = require('../db/pool');

/** Mapea una fila de BD al formato que espera la app Android */
function formatProduct(row) {
  return {
    id:                row.id,
    ownerId:           row.owner_id,
    ownerName:         row.owner_name        || '',
    ownerPhotoUrl:     row.owner_photo_url   || '',
    title:             row.title,
    description:       row.description       || '',
    category:          row.category          || 'OTHER',
    transactionType:   row.transaction_type  || 'DONATION',
    condition:         row.condition         || 'GOOD',
    price:             parseFloat(row.price) || 0.0,
    imageUrls:         row.image_urls        || [],
    location:          row.location          || '',
    latitude:          parseFloat(row.latitude)  || 0.0,
    longitude:         parseFloat(row.longitude) || 0.0,
    greenPointsReward: row.green_points_reward   || 0,
    isAvailable:       row.is_available,
    isFavorite:        row.is_favorite || false,
    favoritesCount:    row.favorites_count   || 0,
    createdAt:         row.created_at        || 0,
  };
}

/** Calcula los green points según el tipo de transacción */
function rewardFor(transactionType) {
  if (transactionType === 'DONATION') return 30;
  if (transactionType === 'EXCHANGE') return 20;
  return 15; // SALE
}

// ── GET /api/products ─────────────────────────────────────────────────────────
async function getProducts(req, res) {
  const limit = Math.min(parseInt(req.query.limit || '20'), 100);
  try {
    const { rows } = await pool.query(
      `SELECT * FROM products
       WHERE is_available = TRUE
       ORDER BY created_at DESC
       LIMIT $1`,
      [limit]
    );
    res.json(rows.map(formatProduct));
  } catch (err) {
    console.error('getProducts error:', err.message);
    res.status(500).json({ error: 'Error interno del servidor' });
  }
}

// ── GET /api/products/search ──────────────────────────────────────────────────
async function searchProducts(req, res) {
  const { query = '', category, transactionType } = req.query;
  const params = [true];
  const conditions = ['is_available = $1'];
  let idx = 2;

  if (category) {
    conditions.push(`category = $${idx}::product_category`);
    params.push(category);
    idx++;
  }
  if (transactionType) {
    conditions.push(`transaction_type = $${idx}::transaction_type`);
    params.push(transactionType);
    idx++;
  }
  if (query) {
    conditions.push(`LOWER(title) LIKE $${idx}`);
    params.push(`%${query.toLowerCase()}%`);
    idx++;
  }

  try {
    const { rows } = await pool.query(
      `SELECT * FROM products
       WHERE ${conditions.join(' AND ')}
       ORDER BY created_at DESC`,
      params
    );
    res.json(rows.map(formatProduct));
  } catch (err) {
    console.error('searchProducts error:', err.message);
    res.status(500).json({ error: 'Error interno del servidor' });
  }
}

// ── GET /api/products/:productId ─────────────────────────────────────────────
async function getProductById(req, res) {
  try {
    const { rows } = await pool.query(
      'SELECT * FROM products WHERE id = $1',
      [req.params.productId]
    );
    if (rows.length === 0) return res.status(404).json({ error: 'Producto no encontrado' });
    res.json(formatProduct(rows[0]));
  } catch (err) {
    console.error('getProductById error:', err.message);
    res.status(500).json({ error: 'Error interno del servidor' });
  }
}

// ── POST /api/products ────────────────────────────────────────────────────────
async function createProduct(req, res) {
  const {
    title, description, category, transactionType,
    condition, price, location, latitude, longitude,
  } = req.body;

  if (!title || !category || !transactionType || !condition) {
    return res.status(400).json({ error: 'title, category, transactionType y condition son requeridos' });
  }

  try {
    // Obtener datos del owner
    const ownerRes = await pool.query(
      'SELECT name, photo_url FROM users WHERE id = $1',
      [req.userId]
    );
    if (ownerRes.rows.length === 0) return res.status(404).json({ error: 'Usuario no encontrado' });
    const owner = ownerRes.rows[0];

    // Imágenes subidas via multer (si las hay)
    const imageUrls = req.files
      ? req.files.map(f => `/uploads/${f.filename}`)
      : [];

    const productId = uuidv4();
    const reward    = rewardFor(transactionType);
    const now       = Date.now();

    const { rows } = await pool.query(
      `INSERT INTO products
         (id, owner_id, owner_name, owner_photo_url, title, description,
          category, transaction_type, condition, price, image_urls,
          location, latitude, longitude, green_points_reward, is_available,
          favorites_count, created_at)
       VALUES ($1,$2,$3,$4,$5,$6,$7::product_category,$8::transaction_type,
               $9::product_condition,$10,$11,$12,$13,$14,$15,TRUE,0,$16)
       RETURNING *`,
      [
        productId, req.userId, owner.name, owner.photo_url || '',
        title, description || '',
        category, transactionType, condition,
        parseFloat(price) || 0.0,
        imageUrls,
        location || '', parseFloat(latitude) || 0.0, parseFloat(longitude) || 0.0,
        reward, now,
      ]
    );

    res.status(201).json({ productId, product: formatProduct(rows[0]) });
  } catch (err) {
    console.error('createProduct error:', err.message);
    res.status(500).json({ error: 'Error interno del servidor' });
  }
}

// ── GET /api/products/user/:userId ────────────────────────────────────────────
async function getUserProducts(req, res) {
  try {
    const { rows } = await pool.query(
      `SELECT * FROM products
       WHERE owner_id = $1
       ORDER BY created_at DESC`,
      [req.params.userId]
    );
    res.json(rows.map(formatProduct));
  } catch (err) {
    console.error('getUserProducts error:', err.message);
    res.status(500).json({ error: 'Error interno del servidor' });
  }
}

// ── PUT /api/favorites/:productId ─────────────────────────────────────────────
async function addFavorite(req, res) {
  const { productId } = req.params;
  const favId = `${req.userId}_${productId}`;
  try {
    await pool.query(
      `INSERT INTO favorites (id, user_id, product_id, created_at)
       VALUES ($1, $2, $3, $4) ON CONFLICT DO NOTHING`,
      [favId, req.userId, productId, Date.now()]
    );
    await pool.query(
      `UPDATE products SET favorites_count = favorites_count + 1 WHERE id = $1`,
      [productId]
    );
    res.json({ message: 'Favorito agregado' });
  } catch (err) {
    console.error('addFavorite error:', err.message);
    res.status(500).json({ error: 'Error interno del servidor' });
  }
}

// ── DELETE /api/favorites/:productId ──────────────────────────────────────────
async function removeFavorite(req, res) {
  const { productId } = req.params;
  const favId = `${req.userId}_${productId}`;
  try {
    await pool.query('DELETE FROM favorites WHERE id = $1', [favId]);
    await pool.query(
      `UPDATE products SET favorites_count = GREATEST(favorites_count - 1, 0) WHERE id = $1`,
      [productId]
    );
    res.json({ message: 'Favorito eliminado' });
  } catch (err) {
    console.error('removeFavorite error:', err.message);
    res.status(500).json({ error: 'Error interno del servidor' });
  }
}

// ── GET /api/favorites ────────────────────────────────────────────────────────
async function getFavorites(req, res) {
  try {
    const { rows } = await pool.query(
      'SELECT product_id FROM favorites WHERE user_id = $1',
      [req.userId]
    );
    res.json(rows.map(r => r.product_id));
  } catch (err) {
    console.error('getFavorites error:', err.message);
    res.status(500).json({ error: 'Error interno del servidor' });
  }
}

module.exports = {
  getProducts, searchProducts, getProductById,
  createProduct, getUserProducts,
  addFavorite, removeFavorite, getFavorites,
};
