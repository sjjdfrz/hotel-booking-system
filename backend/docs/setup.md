# Setup Guide

## Prerequisites
- Java 17 or higher
- Maven 3.6 or higher
- PostgreSQL 12 or higher
- Docker (optional, for containerized deployment)

## Local Development Setup

### 1. Clone the Repository
```bash
git clone https://github.com/yourusername/hotel-booking.git
cd hotel-booking/backend
```

### 2. Database Setup
1. Create a PostgreSQL database:
```sql
CREATE DATABASE hotel_booking;
```

2. Update the database configuration in `src/main/resources/application.yaml` if needed:
```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/hotel_booking
    username: your_username
    password: your_password
```

### 3. Build the Application
```bash
mvn clean install
```

### 4. Run the Application
```bash
mvn spring-boot:run
```

The application will be available at `http://localhost:8080`.

## Docker Deployment

### 1. Build the Docker Image
```bash
docker build -t hotel-booking .
```

### 2. Run with Docker Compose
Create a `docker-compose.yml` file:

```yaml
version: '3.8'
services:
  app:
    image: hotel-booking
    ports:
      - "8080:8080"
    depends_on:
      - db
    environment:
      - SPRING_DATASOURCE_URL=jdbc:postgresql://db:5432/hotel_booking
      - SPRING_DATASOURCE_USERNAME=postgres
      - SPRING_DATASOURCE_PASSWORD=postgres
    networks:
      - backend'
  
  db:
    image: postgres:14
    ports:
      - "5432:5432"
    environment:
      - POSTGRES_DB=hotel_booking
      - POSTGRES_USER=postgres
      - POSTGRES_PASSWORD=postgres
    volumes:
      - ./init.sql:/docker-entrypoint-initdb.d/init.sql
    networks:
      - backend'

networks:
  backend:
    driver: bridge
```

Run with Docker Compose:
```bash
docker-compose up
```

## Configuration

### JWT Configuration
JWT settings can be configured in `application.yaml`:

```yaml
jwt:
  secret-key: "your-secret-key"
  expiration: 86400000 # a day
  refresh-token-expiration: 604800000 # 7 days
```

### Zarinpal Configuration
Zarinpal payment gateway settings can be configured in `application.yaml`:

```yaml
zarinpal:
  merchant-id: "your-merchant-id"
  request-url: "https://sandbox.zarinpal.com/pg/v4/payment/request.json"
  verification-url: "https://sandbox.zarinpal.com/pg/v4/payment/verify.json"
  gateway-url: "https://sandbox.zarinpal.com/pg/StartPay/"
  callback-url: "your-callback-url"
```

## Troubleshooting

### Database Connection Issues
- Ensure PostgreSQL is running
- Verify database credentials in `application.yaml`
- Check that the database exists

### Application Won't Start
- Check Java version: `java -version`
- Ensure Maven is installed correctly: `mvn -version`
- Check application logs for specific errors