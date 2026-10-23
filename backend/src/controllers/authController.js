const bcrypt   = require('bcryptjs');
const jwt      = require('jsonwebtoken');
const { v4: uuidv4 } = require('uuid');
const pool     = require('../db/pool');

/** Calcula el nivel a partir de los puntos verdes (refleja UserLevel.fromPoints de la app) */
function levelFromPoints(points) {
  if (points >= 1000) return 'ECO_MASTER';
  if (points >= 600)  return 'GUARDIAN';
  if (points >= 300)  return 'BRIGHT';
  if (points >= 100)  return 'SPROUT';
  return 'SEED';
}

// ── POST /api/auth/register ──────────────────────────────────────────────────
async function register(req, res) {
  const { name, email, password } = req.body;
  if (!name || !email || !password) {
    return res.status(400).json({ error: 'name, email y password son requeridos' });
  }

  try {
    const existing = await pool.query('SELECT id FROM users WHERE email = $1', [email]);
    if (existing.rowCount > 0) {
      return res.status(409).json({ error: 'El email ya está registrado' });
    }

    const passwordHash = await bcrypt.hash(password, 10);
    const userId = uuidv4();
    const now    = Date.now();

    const { rows } = await pool.query(
      `INSERT INTO users (id, name, email, password_hash, green_points, level, created_at)
       VALUES ($1, $2, $3, $4, 0, 'SEED', $5)
       RETURNING id, name, email, photo_url, location, bio,
                 green_points, level, published_count, reutilized_count,
                 streak_days, badges, is_verified, created_at`,
      [userId, name, email, passwordHash, now]
    );

    const user  = rows[0];
    const token = jwt.sign({ userId: user.id }, process.env.JWT_SECRET, {
      expiresIn: process.env.JWT_EXPIRES_IN || '7d',
    });

    res.status(201).json({ token, user: formatUser(user) });
  } catch (err) {
    console.error('register error:', err.message);
    res.status(500).json({ error: 'Error interno del servidor' });
  }
}

// ── POST /api/auth/login ─────────────────────────────────────────────────────
async function login(req, res) {
  const { email, password } = req.body;
  if (!email || !password) {
    return res.status(400).json({ error: 'email y password son requeridos' });
  }

  try {
    const { rows } = await pool.query(
      `SELECT id, name, email, password_hash, photo_url, location, bio,
              green_points, level, published_count, reutilized_count,
              streak_days, badges, is_verified, created_at
       FROM users WHERE email = $1`,
      [email]
    );

    if (rows.length === 0) {
      return res.status(401).json({ error: 'Credenciales incorrectas' });
    }

    const user = rows[0];
    const valid = await bcrypt.compare(password, user.password_hash);
    if (!valid) {
      return res.status(401).json({ error: 'Credenciales incorrectas' });
    }

    const token = jwt.sign({ userId: user.id }, process.env.JWT_SECRET, {
      expiresIn: process.env.JWT_EXPIRES_IN || '7d',
    });

    res.json({ token, user: formatUser(user) });
  } catch (err) {
    console.error('login error:', err.message);
    res.status(500).json({ error: 'Error interno del servidor' });
  }
}

// ── POST /api/auth/logout ────────────────────────────────────────────────────
function logout(req, res) {
  // Con JWT stateless el cliente simplemente descarta el token.
  // Aquí se puede implementar una blacklist si se desea.
  res.json({ message: 'Sesión cerrada correctamente' });
}

// ── GET /api/auth/me ─────────────────────────────────────────────────────────
async function me(req, res) {
  try {
    const { rows } = await pool.query(
      `SELECT id, name, email, photo_url, location, bio,
              green_points, level, published_count, reutilized_count,
              streak_days, badges, is_verified, created_at
       FROM users WHERE id = $1`,
      [req.userId]
    );
    if (rows.length === 0) {
      return res.status(404).json({ error: 'Usuario no encontrado' });
    }
    res.json(formatUser(rows[0]));
  } catch (err) {
    console.error('me error:', err.message);
    res.status(500).json({ error: 'Error interno del servidor' });
  }
}

// ── helper ───────────────────────────────────────────────────────────────────
function formatUser(row) {
  return {
    id:             row.id,
    name:           row.name,
    email:          row.email,
    photoUrl:       row.photo_url       || '',
    location:       row.location        || '',
    bio:            row.bio             || '',
    greenPoints:    row.green_points    || 0,
    level:          row.level           || 'SEED',
    publishedCount: row.published_count || 0,
    reutilizedCount:row.reutilized_count|| 0,
    streakDays:     row.streak_days     || 0,
    badges:         row.badges          || [],
    isVerified:     row.is_verified     || false,
    createdAt:      row.created_at      || 0,
  };
}

module.exports = { register, login, logout, me, formatUser, levelFromPoints };
