require('dotenv').config();
const express = require('express');
const bodyParser = require('body-parser');
const routes = require('./routes');

const app = express();
app.use(bodyParser.json());
app.use('/api', routes);

app.get('/', (req, res) => {
  res.send('Hello Dunia aku test!');
});

const port = process.env.PORT || 3000;
app.listen(port, () => {
  console.log(`Server running at http://localhost:${port}/`);
});
