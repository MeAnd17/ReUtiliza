const router = require('express').Router();
const auth   = require('../middleware/auth');
const ctrl   = require('../controllers/productController');

router.get('/',                 auth, ctrl.getFavorites);
router.put('/:productId',       auth, ctrl.addFavorite);
router.delete('/:productId',    auth, ctrl.removeFavorite);

module.exports = router;
