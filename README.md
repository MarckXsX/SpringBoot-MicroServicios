# SpringBoot-MicroServices
The following project consists of a technical test based on a microservices solution. Simulating an Ecommerce. The project was done in Java implementing Springboot.

## Project diagram:
<img src="https://github.com/user-attachments/assets/04f980e8-8891-44c7-8ee3-98716216b463" width="600"/>

## Diagram of the database:
<img src="https://github.com/user-attachments/assets/1be9a8b9-b4b3-430b-bf5d-e8ebc9b336fa" width="600"/>

# Microservice Endpoints

## Order-Service: OrderController (http://localhost:8086/api/v1/order-service)
* **Create Order: (http://localhost:8086/api/v1/order-service)**
* **Process Payment: (http://localhost:8086/api/v1/order-service/{id}/pay)**

## Order-Service: ProductController (http://localhost:8086/api/v1/order-service/products)
* **Get Products: (http://localhost:8086/api/v1/order-service/products/{id})**

## Product-Service: ProductController (http://localhost:8081/api/v1/products)
* **Get Products: (http://localhost:8081/api/v1/products/{id})**

## BackEnd-Service: OrderController (http://localhost:8082/api/v1/orders)
* **Create Order: (http://localhost:8082/api/v1/orders)**
* **Process Payment: (http://localhost:8082/api/v1/orders/{id}/pay)**
