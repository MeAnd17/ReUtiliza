const pool = require('../db/pool');
const { formatUser, levelFromPoints } = require('./authController');

// ── GET /api/users/:userId ────────────────────────────────────────────────────
async function getUser(req, res) {
  try {
    const { rows } = await pool.query(
      `SELECT id, name, email, photo_url, location, bio,
              green_points, level, published_count, reutilized_count,
              streak_days, badges, is_verified, created_at
       FROM users WHERE id = $1`,
      [req.params.userId]
    );
    if (rows.length === 0) return res.status(404).json({ error: 'Usuario no encontrado' });
    res.json(formatUser(rows[0]));
  } catch (err) {
    console.error('getUser error:', err.message);
    res.status(500).json({ error: 'Error interno del servidor' });
  }
}

// ── PUT /api/users/:userId/profile ───────────────────────────────────────────
async function updateProfile(req, res) {
  if (req.userId !== req.params.userId) {
    return res.status(403).json({ error: 'No autorizado para modificar este perfil' });
  }
  const { name, bio } = req.body;
  if (!name) return res.status(400).json({ error: 'name es requerido' });

  try {
    const { rows } = await pool.query(
      `UPDATE users SET name = $1, bio = $2
       WHERE id = $3
       RETURNING id, name, email, photo_url, location, bio,
                 green_points, level, published_count, reutilized_count,
                 streak_days, badges, is_verified, created_at`,
      [name, bio || '', req.params.userId]
    );
    if (rows.length === 0) return res.status(404).json({ error: 'Usuario no encontrado' });
    res.json(formatUser(rows[0]));
  } catch (err) {
    console.error('updateProfile error:', err.message);
    res.status(500).json({ error: 'Error interno del servidor' });
  }
}

// ── PATCH /api/users/:userId/green-points ────────────────────────────────────
async function addGreenPoints(req, res) {
  const { points } = req.body;
  if (typeof points !== 'number') {
    return res.status(400).json({ error: 'points debe ser un número' });
  }
  try {
    const { rows } = await pool.query(
      `UPDATE users
       SET green_points = green_points + $1,
           level = $2::user_level
       WHERE id = $3
       RETURNING id, green_points, level`,
      [points, levelFromPoints(0), req.params.userId]   // nivel se recalcula abajo
    );
    if (rows.length === 0) return res.status(404).json({ error: 'Usuario no encontrado' });

    // recalcular nivel con el valor actualizado
    const newLevel = levelFromPoints(rows[0].green_points);
    const final = await pool.query(
      `UPDATE users SET level = $1::user_level WHERE id = $2
       RETURNING id, green_points, level`,
      [newLevel, req.params.userId]
    );
    res.json({ greenPoints: final.rows[0].green_points, level: final.rows[0].level });
  } catch (err) {
    console.error('addGreenPoints error:', err.message);
    res.status(500).json({ error: 'Error interno del servidor' });
  }
}

// ── PATCH /api/users/:userId/badges ──────────────────────────────────────────
async function unlockBadge(req, res) {
  const { badgeId } = req.body;
  if (!badgeId) return res.status(400).json({ error: 'badgeId es requerido' });

  try {
    // Añade el badge al array solo si no existe ya
    const { rows } = await pool.query(
      `UPDATE users
       SET badges = CASE WHEN $1 = ANY(badges) THEN badges ELSE badges || ARRAY[$1] END
       WHERE id = $2
       RETURNING badges`,
      [badgeId, req.params.userId]
    );
    if (rows.length === 0) return res.status(404).json({ error: 'Usuario no encontrado' });
    res.json({ badges: rows[0].badges });
  } catch (err) {
    console.error('unlockBadge error:', err.message);
    res.status(500).json({ error: 'Error interno del servidor' });
  }
}

// ── GET /api/users/leaderboard ────────────────────────────────────────────────
async function getLeaderboard(req, res) {
  const limit = Math.min(parseInt(req.query.limit || '20'), 50);
  try {
    const { rows } = await pool.query(
      `SELECT id, name, photo_url, green_points, level, badges, published_count
       FROM users
       ORDER BY green_points DESC
       LIMIT $1`,
      [limit]
    );
    res.json(rows.map(formatUser));
  } catch (err) {
    console.error('getLeaderboard error:', err.message);
    res.status(500).json({ error: 'Error interno del servidor' });
  }
}

// ── POST /api/users/:userId/fcm-token ────────────────────────────────────────
async function saveFcmToken(req, res) {
  const { token } = req.body;
  if (!token) return res.status(400).json({ error: 'token es requerido' });
  if (req.userId !== req.params.userId) {
    return res.status(403).json({ error: 'No autorizado' });
  }
  try {
    await pool.query('UPDATE users SET fcm_token = $1 WHERE id = $2', [token, req.params.userId]);
    res.json({ message: 'Token FCM guardado' });
  } catch (err) {
    console.error('saveFcmToken error:', err.message);
    res.status(500).json({ error: 'Error interno del servidor' });
  }
}

module.exports = { getUser, updateProfile, addGreenPoints, unlockBadge, getLeaderboard, saveFcmToken };
