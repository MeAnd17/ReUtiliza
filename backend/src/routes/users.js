const router = require('express').Router();
const auth   = require('../middleware/auth');
const ctrl   = require('../controllers/userController');

router.get('/leaderboard',               auth, ctrl.getLeaderboard);
router.get('/:userId',                   auth, ctrl.getUser);
router.put('/:userId/profile',           auth, ctrl.updateProfile);
router.patch('/:userId/green-points',    auth, ctrl.addGreenPoints);
router.patch('/:userId/badges',          auth, ctrl.unlockBadge);
router.post('/:userId/fcm-token',        auth, ctrl.saveFcmToken);

module.exports = router;
