


https://github.com/user-attachments/assets/612b446a-060f-4f3b-879b-a47e83da3826


![Hotel Booking](https://qloapps.com/wp-content/uploads/2023/11/QLO-BLOG-IMAGE2.png)

# Hotel Booking System

## Overview
This is a hotel booking system with a Vue.js frontend and a Spring Boot backend. The application allows users to browse hotels, view rooms, make bookings, and process payments.

## Prerequisites
- [Docker](https://docs.docker.com/get-docker/)
- [Docker Compose](https://docs.docker.com/compose/install/)

## Running the Application with Docker Compose

### 1. Clone the Repository
```bash
git clone https://github.com/yourusername/hotel-booking.git
cd hotel-booking
```

### 2. Start the Application
Run the following command to start all services (frontend, backend, and database):
```bash
docker compose up --build -d
```

This command will:
- Build the frontend container
- Build the backend container
- Start a PostgreSQL database container
- Initialize the database with sample data from init.sql
- Connect all services through a Docker network

### 3. Access the Application
Once all containers are up and running:
- Frontend: http://localhost:80
- Backend API: http://localhost:8080

### 4. Stop the Application
To stop all running containers:
```bash
docker compose down
```

To stop and remove all containers, networks, and volumes:
```bash
docker compose down -v
```

## Project Structure
- **Frontend**: Vue.js application (port 80)
- **Backend**: Spring Boot application (port 8080)
- **Database**: PostgreSQL (port 5432)

## Configuration
The Docker Compose setup uses the following configuration:

### Database
- Database Name: hotel_booking
- Username: postgres
- Password: postgres
- Port: 5432

### Backend
- The backend service connects to the database using the environment variables defined in docker-compose.yml

### Frontend
- The frontend service is configured to communicate with the backend API

## Troubleshooting
- If you encounter issues with the database connection, ensure the database container is running: `docker ps`
- Check container logs for errors: `docker compose logs`
- For specific service logs: `docker compose logs [service_name]` (e.g., `docker compose logs backend`)

## Development
For local development without Docker, refer to the setup instructions in the backend/docs/setup.md file.
