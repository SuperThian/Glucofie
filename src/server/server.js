require('dotenv').config();
const express = require('express');
const bodyParser = require('body-parser');
// const routes = require('../routes/routes');

const routesPublic = require('../routes/routesPublic');

const app = express();
app.use(bodyParser.json());
app.use('/api', routesPublic);

const port = process.env.PORT || 3000;
app.listen(port, () => {
  console.log(`Server running at http://localhost:${port}/`);
});
