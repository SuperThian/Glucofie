# Glucofie Web Service API

Welcome to the Glucofie Web Service API documentation. This API provides endpoints for user registration, login, and user data management.

## Public API Endpoints

### 1. Register a New User

**Endpoint:** `POST https://webservice-glucofie.vercel.app/api/register`

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

![Screenshot](screenshot.png)

### 2. Login

**Endpoint:** `POST https://webservice-glucofie.vercel.app/api/login`

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

![Screenshot](screenshot.png)

## Non-Public API Endpoints

> **Note:** All non-public endpoints require an `Authorization` header with a valid JWT token.

### 1. Get User by ID

**Endpoint:** `GET https://webservice-glucofie.vercel.app/api/users/:id`

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

![Screenshot](screenshot.png)

### 2. Get All Users

**Endpoint:** `GET https://webservice-glucofie.vercel.app/api/users`

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

![Screenshot](screenshot.png)

### 3. Update User by ID

**Endpoint:** `PATCH https://webservice-glucofie.vercel.app/api/users/:id`

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

![Screenshot](screenshot.png)


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


## Contributing

Feel free to fork this repository and submit pull requests. For major changes, please open an issue first to discuss what you would like to change.




