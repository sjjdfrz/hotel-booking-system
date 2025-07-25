# Hotel Booking System - Backend Documentation

A comprehensive hotel booking system built with Spring Boot that allows users to search for hotels, view room availability, make bookings, and process payments.

## Features

- User authentication with two-factor authentication
- Hotel search and filtering
- Room availability checking
- Booking management
- Payment processing via Zarinpal
- User booking history

## Technology Stack

- **Backend**: Spring Boot
- **Database**: PostgreSQL
- **Security**: Spring Security with JWT
- **Payment Gateway**: Zarinpal

## Documentation

Comprehensive documentation is available in the `docs` directory:

- [Setup Guide](docs/setup.md)
- [API Documentation](docs/api/README.md)
- [Database Schema](docs/database/README.md)
- [Security Implementation](docs/security/README.md)
- [Payment Integration](docs/payment/README.md)
- [System Architecture](docs/architecture.md)

## Quick Start

### Prerequisites
- Java 17 or higher
- Maven 3.6 or higher
- PostgreSQL 12 or higher

### Running the Application

1. Clone the repository
```bash
git clone https://github.com/yourusername/hotel-booking.git
cd hotel-booking/backend
```

2. Build the application
```bash
mvn clean install
```

3. Run the application
```bash
mvn spring-boot:run
```

The application will be available at `http://localhost:8080`.

## Docker Deployment

You can also run the application using Docker:

```bash
docker-compose up
```

See the [Setup Guide](docs/setup.md) for detailed instructions.

## API Endpoints

The application exposes the following main API endpoints:

- `/auth/*` - Authentication endpoints
- `/api/hotels` - Hotel management
- `/api/rooms` - Room management
- `/api/bookings` - Booking management
- `/api/payments/*` - Payment processing
- `/api/users/me/*` - User-specific operations

For detailed API documentation, see the [API Documentation](docs/api/README.md).

## Contributing

1. Fork the repository
2. Create a feature branch: `git checkout -b feature-name`
3. Commit your changes: `git commit -m 'Add some feature'`
4. Push to the branch: `git push origin feature-name`
5. Submit a pull request
