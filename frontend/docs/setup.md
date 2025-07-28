# Setup Guide

## Prerequisites
- Node.js 16 or higher
- npm 7 or higher
- Docker (optional, for containerized deployment)

## Local Development Setup

### 1. Clone the Repository
```bash
git clone https://github.com/yourusername/hotel-booking.git
cd hotel-booking/frontend
```

### 2. Install Dependencies
```bash
npm install
```

### 3. Configure Environment Variables
Create a `.env` file in the root directory:

```
VITE_API_BASE_URL=http://localhost:8080/api
VITE_AUTH_URL=http://localhost:8080/auth
```

For production, you might want to create a `.env.production` file with different settings.

### 4. Run the Development Server
```bash
npm run dev
```

The application will be available at `http://localhost:5173`.

## Production Build

### 1. Build the Application
```bash
npm run build
```

The built files will be in the `dist` directory.

### 2. Preview the Production Build
```bash
npm run preview
```

## Docker Deployment

### 1. Build the Docker Image
```bash
docker build -t hotel-booking-frontend .
```

### 2. Run the Docker Container
```bash
docker run -p 80:80 hotel-booking-frontend
```

### 3. Run with Docker Compose
Create or update the `docker-compose.yml` file in the project root:

```yaml
version: '3.8'
services:
  frontend:
    build: ./frontend
    ports:
      - "80:80"
    depends_on:
      - backend
    networks:
      - app-network

  backend:
    build: ./backend
    ports:
      - "8080:8080"
    depends_on:
      - db
    environment:
      - SPRING_DATASOURCE_URL=jdbc:postgresql://db:5432/hotel_booking
      - SPRING_DATASOURCE_USERNAME=postgres
      - SPRING_DATASOURCE_PASSWORD=postgres
    networks:
      - app-network

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
      - app-network

networks:
  app-network:
    driver: bridge
```

Run with Docker Compose:
```bash
docker compose up --build -d 
```

## Configuration Options

### Vite Configuration
The Vite configuration is in `vite.config.js`. You can customize it according to your needs:

```javascript
export default defineConfig({
  plugins: [
    vue(),
  ],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    }
  },
  server: {
    port: 5173,
    proxy: {
      '/api': {
        target: 'http://localhost:8080',
        changeOrigin: true
      }
    }
  }
})
```

### Tailwind Configuration
Tailwind CSS configuration is in `tailwind.config.js`. You can customize colors, fonts, and other design tokens:

```javascript
module.exports = {
  content: [
    "./index.html",
    "./src/**/*.{vue,js,ts,jsx,tsx}",
  ],
  theme: {
    extend: {
      colors: {
        primary: '#3B82F6',
        secondary: '#10B981',
        // Add your custom colors
      },
      fontFamily: {
        sans: ['Vazir', 'sans-serif'],
        // Add your custom fonts
      }
    },
  },
  plugins: [],
}
```

## Troubleshooting

### Node.js Version Issues
- Ensure you're using Node.js 16 or higher: `node -v`
- If you need to manage multiple Node.js versions, consider using [nvm](https://github.com/nvm-sh/nvm)

### Dependency Issues
- Clear npm cache: `npm cache clean --force`
- Delete `node_modules` and reinstall: `rm -rf node_modules && npm install`

### Build Errors
- Check for ESLint errors: `npm run lint`
- Ensure all imports are correct
- Check browser console for errors

### API Connection Issues
- Verify the backend server is running
- Check the API base URL in your environment variables
- Ensure CORS is properly configured on the backend