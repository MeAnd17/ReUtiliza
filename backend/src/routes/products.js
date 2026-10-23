const router = require('express').Router();
const multer = require('multer');
const path   = require('path');
const auth   = require('../middleware/auth');
const ctrl   = require('../controllers/productController');

// Configuración de multer para guardar imágenes localmente
const storage = multer.diskStorage({
  destination: (req, file, cb) => cb(null, 'uploads/'),
  filename:    (req, file, cb) => {
    const unique = Date.now() + '-' + Math.round(Math.random() * 1e9);
    cb(null, unique + path.extname(file.originalname));
  },
});
const upload = multer({ storage, limits: { files: 5, fileSize: 5 * 1024 * 1024 } });

router.get('/',                    ctrl.getProducts);
router.get('/search',              ctrl.searchProducts);
router.get('/user/:userId', auth,  ctrl.getUserProducts);
router.get('/:productId',          ctrl.getProductById);
router.post('/', auth, upload.array('images', 5), ctrl.createProduct);

module.exports = router;
