# Glucofie - Mobile Development Path
Bangkit 2024 Batch 1 - Product-based Capstone

Team ID: C241-PS241

| Student ID        | Nama            | Universitas           |
|-------------|-----------------|-----------------------|
| A009D4KY4555  | Kenny Corenthian  | Universitas Gunadarma  |
| A325D4KX4088  | Lutfiana Sinta Lestari | Universitas Tarumanagara  |

## Table Of Contents
- [Table Of Contents](#table-of-contents)
- [Introduction](#introduction)
- [Features](#features)
- [Installation](#installation)
- [Project Structure](#project-structure)
- [Credits](#credits)
- [Screenshots](#screenshots)
  - [Landing Page](#landing-page)
  - [Login and Register Page](#login-and-register-page)
  - [Home Page](#home-page)
  - [Profile Page](#profile-page)
  - [Scan Page](#scan-page)
  - [Result Page](#result-page)
- [Contact](#contact)
  
## Introduction
Glucofie is a revolutionary mobile application designed to help users, especially those with diabetes, monitor their nutritional intake from packaged foods and beverages. This app allows users to easily scan nutritional information from product labels and provides detailed analysis of carbohydrate, protein, sugar, and fat content, along with recommendations on whether the product is safe for consumption by diabetics.

## Features
- **Landing Page**
  - Welcome Page

- **Authentication**
  - Login
  - Register

- **Dashboard Page**
  - Home Page 
  - Scan Tutorial Page
  - Profile Page
  - Logout Menu

- **Scan Nutrition Facts Feature**
  - Image to text scanning or OCR to scan nutrition facts of food products
  - Detection of sugar levels contained in a food product
  - Provide a warning to diabetes sufferers not to consume too much sugar

- **Profile Page**
  - See Personal Data
  - Added Diabetes Type and Gender
  - Edit Diabetes Type and Gender
 
## Installation
1. To install the application, clone the repository using the following command: 
```
git clone -b frontend https://github.com/SuperThian/Glucofie.git
```

## Project Structure
```
app
├── manifests
│   └── AndroidManifest.xml
├── kotlin+java
│   └── com.capstone.glucofie
│       ├── data
│       │   ├── api
│       │   ├── user
│       │   ├── respon
│       │   ├── AuthRepository
│       │   └── Result
│       └── view
│            ├── model
│            │    ├── LoginViewModel
│            │    ├── MainViewModel
│            │    ├── ProfileViewModel
│            │    ├── RegisterViewModel
│            │    ├── ResultViewModel
│            │    ├── ScanViewModel
│            │    └── ViewModelFactory
│            ├── editor
│            │    ├── EmailEditText
│            │    ├── PasswordEditText
│            │    └── ConfirmPasswordEditText
│            ├── BaseActivity
│            ├── HistoryActivity
│            ├── LoginActivity
│            ├── MainActivity
│            ├── ProfileActivity
│            ├── RegisterActivity
│            ├── ResultActivity
│            ├── ScanActivity
│            ├── ScanTutorialActivity
│            └── Splash_Screen
├── com.capstone.glucofie (androidTest)
├── com.capstone.glucofie (test)
├── res
│   ├── drawable
│   ├── layout
│   ├── menu
│   ├── mipmap
│   ├── values
│   └── xml
└── java (generated)
```

## Credits
- **[Android Studio](https://developer.android.com/studio)**
  
- **[Kotlin](https://kotlinlang.org/)**

- **[Retrofit](https://square.github.io/retrofit/)**

- **[Datastore](https://cloud.google.com/datastore)**


## Screenshots

### Landing Page
<div align="left"> 
<img src="https://github.com/SuperThian/Glucofie/assets/144090280/cdc09201-8076-44bf-b30d-3df3433c2973" width="200">
</div>

### Login and Register Page
<div align="left"> 
<img src="https://github.com/SuperThian/Glucofie/assets/144090280/1e47784a-c264-4efa-a1d6-1c5c4afcadb2" width="200">
<img src="https://github.com/SuperThian/Glucofie/assets/144090280/5cfc3367-90d5-459e-b585-3a1695a00e1d" width="200">
</div>

### Home Page
<div align="left"> 
<img src="https://github.com/SuperThian/Glucofie/assets/144090280/1fb2433e-4faa-4c6f-8a45-a037aaf4a4e0" width="200">
</div>

### Profile Page
<div align="left"> 
<img src="https://github.com/SuperThian/Glucofie/assets/144090280/3446c133-074d-4b8f-a324-bd4330fe13f1" width="200">
</div>

### Scan Page
<div align="left"> 
<img src="https://github.com/SuperThian/Glucofie/assets/144090280/6ddbb059-db51-41b5-8907-db05c33ca11a" width="200">
<img src="https://github.com/SuperThian/Glucofie/assets/144090280/ca4d9f0f-4f91-4f4a-8e94-b36a41f044e0" width="200">
</div>

### Result Page
<div align="left"> 
<img src="https://github.com/SuperThian/Glucofie/assets/144090280/017e18b2-5882-4d1c-914d-d2eb17f3a44c" width="200">
</div>

## Contact
- **[Github](https://github.com/SuperThian)**
- **[Linkedin](https://www.linkedin.com/in/kenny-corenthian-29a2822ba/)**
