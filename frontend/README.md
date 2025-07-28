# Hotel Booking System - Frontend Documentation

A modern, responsive frontend for the Hotel Booking System built with Vue.js 3 and Vite. This application provides a user-friendly interface for searching hotels, viewing room availability, making bookings, and processing payments.

## Features

- User authentication with login and registration
- Hotel search and filtering
- Room availability checking
- Booking management
- Payment processing via Zarinpal
- User booking history
- Responsive design for mobile and desktop

## Technology Stack

- **Frontend Framework**: Vue.js 3
- **Build Tool**: Vite
- **State Management**: Pinia
- **Routing**: Vue Router
- **Styling**: Tailwind CSS
- **HTTP Client**: Axios
- **Date Handling**: jalali-moment and vue3-persian-datetime-picker

## Documentation

Comprehensive documentation is available in the `docs` directory:

- [Detailed Documentation](docs/README.md)
- [Setup Guide](docs/setup.md)
- [Architecture](docs/architecture.md)
- [Component Library](docs/components.md)
- [State Management](docs/state-management.md)
- [API Integration](docs/api-integration.md)
- [Security](docs/security.md)

## Quick Start

### Prerequisites
- Node.js 16 or higher
- npm 7 or higher

### Running the Application

1. Clone the repository
```bash
git clone https://github.com/yourusername/hotel-booking.git
cd hotel-booking/frontend
```

2. Install dependencies
```bash
npm install
```

3. Run the development server
```bash
npm run dev
```

The application will be available at `http://localhost:5173`.

## Build for Production

```bash
npm run build
```

The built files will be in the `dist` directory.

## Docker Deployment

You can also run the application using Docker:

```bash
docker build -t hotel-booking-frontend .
docker run -p 80:80 hotel-booking-frontend
```

See the [Setup Guide](docs/setup.md) for detailed instructions.

## Development Scripts

- `npm run dev` - Start development server
- `npm run build` - Build for production
- `npm run preview` - Preview production build
- `npm run lint` - Lint code with ESLint
- `npm run format` - Format code with Prettier

## Contributing

1. Fork the repository
2. Create a feature branch: `git checkout -b feature-name`
3. Commit your changes: `git commit -m 'Add some feature'`
4. Push to the branch: `git push origin feature-name`
5. Submit a pull request
