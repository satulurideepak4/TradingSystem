# Event-Driven Trading System (Kafka Matching Engine)

A simplified **trading exchange system** built using **Java, Spring Boot, and Apache Kafka** that demonstrates how buy and sell orders are matched using an **event-driven architecture**.

The project simulates the core functionality of financial exchanges where **orders are placed, matched, and executed in real time**.

---

# Project Goal

The goal of this project is to demonstrate how a **distributed trading system** can process orders asynchronously using Kafka.

The system receives **BUY and SELL orders**, sends them through **Kafka**, and a **matching engine** executes trades whenever compatible orders exist.

---

# How the System Works

1. A user places a **BUY or SELL order** using the Order Service.
2. The order is published to **Kafka (orders-topic)**.
3. The **Matching Engine** consumes the order and checks the order book.
4. If a matching order exists, a **trade is executed**.
5. The executed trade is published to **Kafka (trade-topic)**.
6. The **Trade Service** consumes the event and stores the trade in the database.

---

# Example Scenario

User A places a SELL order

SELL BTC at 50000 for quantity 2

User B places a BUY order

BUY BTC at 50000 for quantity 2

Since the price matches, the **matching engine executes the trade**.

Trade result:

Price: 50000
Quantity: 2

---

# System Architecture

```
Client
  |
  v
Order Service
  |
  v
Kafka (orders-topic)
  |
  v
Matching Engine
  |
  v
Kafka (trade-topic)
  |
  v
Trade Service
  |
  v
Database
```

This design follows an **event-driven microservices architecture** where services communicate through Kafka instead of direct API calls.

---

# Services

## Order Service

Responsible for receiving new orders and publishing them to Kafka.

Responsibilities:

* Accept order requests via REST API
* Validate order data
* Publish order events to Kafka

Endpoint:

POST /orders

Example request:

```
{
 "orderId":"O1",
 "userId":"U1",
 "symbol":"BTC",
 "side":"SELL",
 "price":50000,
 "quantity":2
}
```

Kafka topic produced:

orders-topic

---

## Matching Engine

The **core component** of the system.

Responsibilities:

* Consume orders from Kafka
* Maintain an in-memory **order book**
* Match BUY and SELL orders
* Publish executed trades

Matching rule used:

BUY price >= SELL price

Data structures used:

* **Max Heap (PriorityQueue)** for BUY orders
* **Min Heap (PriorityQueue)** for SELL orders

This ensures the best prices are matched first.

---

## Trade Service

Responsible for storing executed trades.

Responsibilities:

* Consume trade events from Kafka
* Persist trades in the database

Kafka topic consumed:

trade-topic

Stored fields:

* tradeId
* buyOrderId
* sellOrderId
* price
* quantity
* timestamp

---

# Project Structure

```
trading-system
│
├── common
│   ├── models
│   ├── enums
│   └── events
│
├── order-service
│   ├── controller
│   ├── service
│   ├── producer
│   └── config
│
├── matching-engine
│   ├── consumer
│   ├── engine
│   └── producer
│
├── trade-service
│   ├── consumer
│   ├── repository
│   └── entity
│
└── docker-compose.yml
```

---

# Technologies Used

Java 21
Spring Boot
Apache Kafka
PostgreSQL
Docker
Maven

---

# Kafka Topics

orders-topic

Used for publishing new orders.

Produced by:

Order Service

Consumed by:

Matching Engine

trade-topic

Used for publishing executed trades.

Produced by:

Matching Engine

Consumed by:

Trade Service

---

# Running the Project

## Start Kafka

Run Kafka using Docker:

```
docker-compose up -d
```

---

## Start Services

Run each service separately:

```
order-service
matching-engine
trade-service
```

---

## Place Orders

Create a SELL order:

```
POST /orders
```

```
{
 "orderId":"O1",
 "userId":"U1",
 "symbol":"BTC",
 "side":"SELL",
 "price":50000,
 "quantity":2
}
```

Create a BUY order:

```
POST /orders
```

```
{
 "orderId":"O2",
 "userId":"U2",
 "symbol":"BTC",
 "side":"BUY",
 "price":50000,
 "quantity":2
}
```

The matching engine will detect the match and execute a trade.

---

# Key Concepts Demonstrated

Event-driven architecture
Kafka messaging
Microservices communication
Matching engine logic
Order book data structures
Asynchronous processing
Distributed systems design

---

# Future Improvements

Support multiple trading symbols
Add order cancellation
Add market orders
Persist order book using Redis
Add portfolio management service
Scale matching engine using Kafka partitions
Implement high-performance order book with price levels

---

# Why This Project Is Interesting

This project demonstrates the **core mechanism used in real trading platforms**.

Real exchanges like stock and crypto exchanges rely on **matching engines and event streaming systems** to process thousands of orders per second.

This project recreates a simplified version of that architecture.

---
