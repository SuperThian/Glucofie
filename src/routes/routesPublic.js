const express = require("express");
const { register, login } = require("../controllers/authController");

const routerPublic = express.Router();

routerPublic.post("/register", register);
routerPublic.post("/login", login);
router.post("/analyze-image", modelController.analyzeImage); //image analysis

module.exports = routerPublic;
