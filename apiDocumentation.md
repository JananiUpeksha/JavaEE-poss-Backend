## API Documentation

### Base URL

All endpoints are accessible via the base URL:
```
http://localhost:8080
```
## Overview
The POS System API allows clients to manage orders, customers, and items in a point-of-sale system. The API supports operations such as creating, retrieving, updating, and deleting orders, as well as managing items and customers.

---

## Authentication
This API does not require authentication. Ensure that appropriate security measures are implemented in a production environment.

---

### Endpoints

#### 1. Customer Management

##### **POST /customer**

**Description:** Create a new customer.

**Request:**
- **Content-Type:** `application/json`
- **Body:**
  ```json
  {
    "name": "John Doe",
    "address": "123 Elm Street",
    "contact": "555-1234"
  }
  ```

**Response:**
- **Status Code:** `201 Created` if successful
- **Status Code:** `400 Bad Request` if the request is invalid

**Error Response:**
```json
{
  "error": "Save customer failed"
}
```

##### **PUT /customer**

**Description:** Update an existing customer.

**Request:**
- **Content-Type:** `application/json`
- **Query Parameter:** `id` - ID of the customer to update
- **Body:**
  ```json
  {
    "name": "John Smith",
    "address": "456 Oak Avenue",
    "contact": "555-5678"
  }
  ```

**Response:**
- **Status Code:** `200 OK` if successful
- **Status Code:** `400 Bad Request` if the request is invalid
- **Status Code:** `500 Internal Server Error` if the update fails

**Error Response:**
```json
{
  "error": "Update failed"
}
```

##### **DELETE /customer**

**Description:** Delete a customer.

**Request:**
- **Query Parameter:** `id` - ID of the customer to delete

**Response:**
- **Status Code:** `204 No Content` if successful
- **Status Code:** `400 Bad Request` if the ID is missing
- **Status Code:** `500 Internal Server Error` if the deletion fails

**Error Response:**
```json
{
  "error": "Delete Failed"
}
```

##### **GET /customer**

**Description:** Retrieve all customers.

**Response:**
- **Status Code:** `200 OK` with JSON array of customers if successful
- **Status Code:** `204 No Content` if no customers are found

**Example Response:**
```json
[
  {
    "id": 1,
    "name": "John Doe",
    "address": "123 Elm Street",
    "contact": "555-1234"
  },
  {
    "id": 2,
    "name": "Jane Doe",
    "address": "789 Maple Street",
    "contact": "555-9876"
  }
]
```

#### 2. Order Management

##### **POST /order**

**Description:** Create a new order.

**Request:**
- **Content-Type:** `application/json`
- **Body:**
  ```json
  {
    "customerId": 1,
    "total": 100.00
  }
  ```

**Response:**
- **Status Code:** `200 OK` if successful
- **Status Code:** `400 Bad Request` if the request is invalid
- **Status Code:** `500 Internal Server Error` if the order creation fails

**Error Response:**
```json
{
  "error": "Order creation failed"
}
```

##### **GET /order**

**Description:** Retrieve an order by ID or list all orders.

**Request:**
- **Query Parameter:** `orderId` - (Optional) ID of the order to retrieve

**Response:**
- **Status Code:** `200 OK` with JSON representation of the order if successful
- **Status Code:** `404 Not Found` if the order is not found
- **Status Code:** `400 Bad Request` if the ID is invalid

**Example Response (Single Order):**
```json
{
  "orderId": 1,
  "customerId": 1,
  "total": 100.00,
  "date": "2024-08-27T12:34:56Z"
}
```

**Example Response (All Orders):**
```json
[
  {
    "orderId": 1,
    "customerId": 1,
    "total": 100.00,
    "date": "2024-08-27T12:34:56Z"
  }
]
```

#### 3. Item Management

##### **POST /item**

**Description:** Create a new item.

**Request:**
- **Content-Type:** `application/json`
- **Body:**
  ```json
  {
    "name": "Laptop",
    "price": 999.99,
    "qty": 10
  }
  ```

**Response:**
- **Status Code:** `201 Created` if successful
- **Status Code:** `400 Bad Request` if the request is invalid
- **Status Code:** `500 Internal Server Error` if the item creation fails

**Error Response:**
```json
{
  "error": "Save item failed"
}
```

##### **PUT /item**

**Description:** Update an existing item.

**Request:**
- **Content-Type:** `application/json`
- **Query Parameter:** `id` - ID of the item to update
- **Body:**
  ```json
  {
    "name": "Laptop",
    "price": 899.99,
    "qty": 15
  }
  ```

**Response:**
- **Status Code:** `200 OK` if successful
- **Status Code:** `400 Bad Request` if the request is invalid
- **Status Code:** `500 Internal Server Error` if the update fails

**Error Response:**
```json
{
  "error": "Update failed"
}
```

##### **DELETE /item**

**Description:** Delete an item.

**Request:**
- **Query Parameter:** `id` - ID of the item to delete

**Response:**
- **Status Code:** `204 No Content` if successful
- **Status Code:** `400 Bad Request` if the ID is missing
- **Status Code:** `500 Internal Server Error` if the deletion fails

**Error Response:**
```json
{
  "error": "Delete Failed"
}
```

##### **GET /item**

**Description:** Retrieve all items.

**Response:**
- **Status Code:** `200 OK` with JSON array of items if successful
- **Status Code:** `204 No Content` if no items are found

**Example Response:**
```json
[
  {
    "id": 1,
    "name": "Laptop",
    "price": 999.99,
    "qty": 10
  },
  {
    "id": 2,
    "name": "Mouse",
    "price": 25.99,
    "qty": 50
  }
]
```

### Error Codes

- **400 Bad Request:** The request could not be understood or was missing required parameters.
- **404 Not Found:** The requested resource could not be found.
- **500 Internal Server Error:** An error occurred on the server.

---