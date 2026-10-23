const router = require('express').Router();
const auth   = require('../middleware/auth');
const ctrl   = require('../controllers/chatController');

router.get('/',         auth, ctrl.getUserChats);
router.post('/',        auth, ctrl.getOrCreateChat);

module.exports = router;
