# Hotel Booking Frontend Documentation

## Overview
The frontend of the Hotel Booking application is built using Vue.js 3 with Vite as the build tool. It provides a user interface for browsing hotels, viewing rooms, making reservations, processing payments, and managing booking history.

## Technology Stack
- **Vue.js 3**: Frontend framework
- **Vue Router**: For navigation and routing
- **Pinia**: For state management
- **Tailwind CSS**: For styling
- **Axios**: For HTTP requests
- **Persian Date Libraries**: jalali-moment and vue3-persian-datetime-picker for handling Persian dates

## Project Structure

### Root Configuration Files
- **package.json**: Defines project dependencies and scripts
- **vite.config.js**: Configuration for the Vite build tool
- **tailwind.config.js**: Configuration for Tailwind CSS
- **eslint.config.js**: Configuration for ESLint
- **postcss.config.js**: Configuration for PostCSS
- **Dockerfile**: For containerizing the frontend application
- **nginx.conf**: Nginx configuration for serving the application

### Source Code Structure

#### Components
The components are organized by functionality:

##### Authentication Components (`src/components/auth`)
- **AuthForm.vue**: Unified component for handling login and registration

##### Home Page Components (`src/components/home`)
- **HeroSection.vue**: Main banner section on the home page
- **HeroTextBlock.vue**: Text content for the hero section
- **HotelSearchForm.vue**: Form for searching hotels

##### Hotel Components (`src/components/hotels`)
- **HotelCard.vue**: Card component for displaying hotel information
- **HotelFilters.vue**: Filters for refining hotel search results
- **HotelHeader.vue**: Header component for hotel detail pages

##### Layout Components (`src/components/layout`)
- **AppNavigation.vue**: Main navigation component used across the application

##### Room Components (`src/components/rooms`)
- **RoomCard.vue**: Card component for displaying room information
- **RoomList.vue**: Component for displaying a list of rooms

##### UI Components (`src/components/ui`)
- **EmptyState.vue**: Component for displaying when no data is available
- **FormInput.vue**: Reusable form input component
- **StarRating.vue**: Component for displaying and setting star ratings
- **SubmitButton.vue**: Reusable button component for form submissions

#### Views
The views represent the main pages of the application:

- **HomeView.vue**: Landing page with search functionality
- **HotelsView.vue**: Page displaying hotel listings
- **LoginView.vue**: Authentication page
- **PaymentConfirmView.vue**: Page for confirming payment details
- **PaymentFailView.vue**: Page displayed when payment fails
- **PaymentSuccessView.vue**: Page displayed when payment succeeds
- **ReserveView.vue**: Page for making a reservation
- **RoomsView.vue**: Page displaying room listings for a selected hotel
- **UserHistoryView.vue**: Page displaying a user's booking history

#### Router (`src/router`)
- **index.js**: Defines all routes for the application

#### State Management (`src/stores`)
The application uses Pinia for state management with separate stores for different aspects:

- **authStore.js**: Manages authentication state (user login, registration, etc.)
- **bookingStore.js**: Manages booking-related state
- **hotelDetailStore.js**: Manages state for hotel details
- **hotelsStore.js**: Manages state for hotel listings

#### Assets (`src/assets`)
- **tailwind.css**: Main CSS file using Tailwind

#### Public Files (`public`)
- **favicon.ico**: Website favicon
- **hotel.png**: Logo or main image
- **images/**: Directory containing images used throughout the application
  - Numbered images (1.jpg - 11.jpg): Hotel and room images
  - background.svg: Background vector graphic
  - hero.jpg: Main hero image for the home page

## User Flows

### Hotel Search and Booking
1. User lands on the home page (HomeView)
2. User searches for hotels using the HotelSearchForm
3. User browses hotel listings on the HotelsView page
4. User selects a hotel to view its details
5. User browses rooms on the RoomsView page
6. User selects a room and proceeds to reservation (ReserveView)
7. User confirms booking and proceeds to payment
8. User completes payment and sees success or failure page

### User Authentication
1. User navigates to the login page (LoginView)
2. User logs in or registers using the AuthForm
3. Upon successful authentication, user can access protected features

### Booking History
1. Authenticated user navigates to the history page (UserHistoryView)
2. User views their booking history

## Development

### Scripts
- **dev**: Starts the development server
- **build**: Builds the application for production
- **preview**: Previews the production build
- **lint**: Lints and fixes files
- **format**: Formats code using Prettier

### Docker Support
The application includes Docker configuration for containerization:
- **Dockerfile**: Defines how to build the container
- **nginx.conf**: Configures Nginx for serving the application

## Conclusion
The frontend of the Hotel Booking application provides a comprehensive user interface for all aspects of hotel booking, from searching and browsing to reservation and payment. The code is well-organized with a clear separation of concerns, making it maintainable and extensible.