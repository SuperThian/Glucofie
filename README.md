# Glucofie Web Service API


Update Progress Week 3 (June 3 - June 9, 2024)
- Completed API endpoints for login, register, and profile.
- Hosted API on Vercel for testing to reduce costs.
- Connected API to Firestore.
- Conducted API testing.

Update Progress Week 4 (June 10 - June 20, 2024)

- Developed API service for machine learning.
- Hosted API on cloud using Compute Engine.
- Fixed bugs in login and register functionalities.
- Conducted API testing.
- All API endpoints are now completed.

---



Welcome to the Glucofie Web Service API documentation. This API provides endpoints for user registration, login, user data management, and scanning image to text.

### Development Team

- **C006D4KY1154 – Ekmaldzaki Royhan Mahar Beta Adi Sucipto Universitas Brawijaya - Cloud Computing**
- **C547D4NY1355 – Moh Hasbi Rizqulloh – UIN Sunan Gunung Djati Bandung - Cloud Computing**
## Public API Endpoints

### 1. Register a New User

**Endpoint:** `POST http://34.101.56.125:3000/api/login`

**Request Body:**

```json
{
  "username": "string",
  "email": "string",
  "password": "string",
  "confirm_password": "string"
}

```

**Response:**
```json
{
  "id": "string",
  "message": "User registered successfully"
}

```

**Screenshot Testing**

![Screenshot](https://drive.google.com/uc?export=view&id=1OvQ3MXxFe9Io-CrtAKZExCyB8epj4btC)

### 2. Login

**Endpoint:** `POST http://34.101.56.125:3000/api/register`

**Request Body:**

```json
{
  "email": "string",
  "password": "string"
}


```

**Response:**
```json
{
  "id": "string",
  "token": "string"
}

```
**Screenshot Testing**

![Screenshot](https://drive.google.com/uc?export=view&id=1cp8zrlxrChBR0kH4ys0mRlJzYALyU8ej)

## Non-Public API Endpoints

> **Note:** All non-public endpoints require an `Authorization` header with a valid JWT token.

### 1. Get User by ID

**Endpoint:** `GET http://34.101.56.125:3000/api/users`

**Headers:**
Authorization: Bearer <token>

**Response:**

```json
{
  "id": "string",
  "username": "string",
  "email": "string",
  "type_diabetes": "string",
  "gender": "string"
}

````

**Screenshot Testing**

![Screenshot](https://drive.google.com/uc?export=view&id=1Uhh9EWLY71D1VkBTyREvlU5f6cy6vzx9)

### 2. Get All Users

**Endpoint:** `GET http://34.101.56.125:3000/api/users:id`

**Headers:**
Authorization: Bearer <token>

**Response:**

```json
[
  {
    "id": "string",
    "username": "string",
    "email": "string",
    "type_diabetes": "string",
    "gender": "string"
  },
  ...
]


````

**Screenshot Testing**

![Screenshot](https://drive.google.com/uc?export=view&id=1K3yDxUyArpNvFFR515eJ7J-gpiQTpm3z)

### 3. Update User by ID

**Endpoint:** `PATCH http://34.101.56.125:3000/api/users:id`

**Headers:**
Authorization: Bearer <token>

**Request Body**
```json 

{
  "username": "string",
  "email": "string",
  "type_diabetes": "string",
  "gender": "string"
}

```

**Response:**

```json
[
  {
    "id": "string",
    "username": "string",
    "email": "string",
    "type_diabetes": "string",
    "gender": "string"
  },
  ...
]


````

**Screenshot Testing**

![Screenshot](https://drive.google.com/uc?export=view&id=1C-888dxuQSjLQMsQkxPZ4bKTr0Yj-0QM)

## Testing API For machine Learning (Progress Weeks 4)
### 4. Scan Nutrition
**Endpoint:** `POST http://34.101.56.125:3000/api/detect-nutrition`

**Headers:**
Authorization: Bearer <token>

**Request Body:**

```json
{
  "image": "image.png",
}

```

**Response:**
```json
{
    "error": false,
    "message": "Scan Success"
}

```

**Screenshot Testing**

![Screenshot](https://drive.google.com/uc?export=view&id=1imyE65pdjY0hAvXIjMN-mAftWLPSbCf3)

**Screenshot Coding**
![Screenshot](https://drive.google.com/uc?export=view&id=19sEPcVwF-yYLvBTxFrDRozv8pjWIknYa)

5. Result
**Endpoint:** `GET http://34.101.56.125:3000/api/result`

**Headers:**
Authorization: Bearer <token>

**Response:**

```json
[
  {
        "id": "string",
        "result": {
            "Gula": int,
            "Karbohidrat Total": int,
            "Protein": int,
            "Lemak Total": int
        },
        "suggestion": "string",
        "userId": {
            "type_diabetes": "string",
            "id": "string",
            "email": "string",
            "username": "string"
        }
    }
]
```

## How to Use

1. **Clone the Repository:**

   ```sh
   git clone https://github.com/your-username/your-repo.git
   cd your-repo
   ```

2. **Install Dependencies:**

   ```sh
   npm install
   ```

3. **Create a .env File:**

   ```sh
   touch .env
   ```

   Add the following environment variables to the .env file:
   ```sh
   JWT_SECRET=your_jwt_secret_key
   ```

4. **Create a .env File:**

   ```sh
   npm start
   ```
5. Use Postman or any API client to interact with the endpoints.


## Deployment 
**Screenshot Bucket**
Machine Learning Model 
![Screenshot](https://drive.google.com/uc?export=view&id=17YPTS3ZIyTr_zLf-Jwya3b8tRkVfM7cS)

**Screenshot Compute Engine**
Machine Learning Model 
![Screenshot](https://drive.google.com/uc?export=view&id=1pyaGMJ6x-_tChypu9ZBpbr4Trrfd8Fr6)


## Contributing

Feel free to fork this repository and submit pull requests. For major changes, please open an issue first to discuss what you would like to change.




