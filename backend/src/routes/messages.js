const router = require('express').Router();
const auth   = require('../middleware/auth');
const ctrl   = require('../controllers/chatController');

router.get('/:chatId',  auth, ctrl.getChatMessages);
router.post('/:chatId', auth, ctrl.sendMessage);

module.exports = router;
