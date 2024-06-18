const express = require('express');
const bodyParser = require('body-parser');
const loadModel = require('../services/loadModel');
const routes = require('../routes/routes');
const routesPublic = require('../routes/routesPublic');

const app = express();
app.use(bodyParser.json());

// Middleware untuk memuat model dan menambahkannya ke req.app.locals
const initializeModel = async () => {
  try {
    const model = await loadModel();
    app.locals.model = model;
    console.log('Model loaded successfully');
  } catch (error) {
    console.error('Error loading model:', error);
    process.exit(1);
  }
};

// Panggil initializeModel saat server dimulai
initializeModel()
  .then(() => {
    // Setelah model dimuat, pasang rute publik dan rute lainnya
    app.use('/api', routesPublic);
    app.use('/api', routes);

    const port = process.env.PORT || 3000;
    const host = '0.0.0.0';
    app.listen(port, host, () => {
      console.log(`Server running at http://${host}:${port}/`);
    });
  })
  .catch((error) => {
    console.error('Failed to initialize server:', error);
  });

module.exports = app;
