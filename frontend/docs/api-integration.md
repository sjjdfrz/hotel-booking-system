# API Integration

This document describes how the Hotel Booking System frontend integrates with the backend API.

## Overview

The frontend communicates with the backend through RESTful API endpoints. API calls are made directly within the store actions using Axios, without any intermediate service layer.

## API Base URL

The API base URL is configured in the environment variables:

```
VITE_API_BASE_URL=http://localhost:8080/api
VITE_AUTH_URL=http://localhost:8080/auth
```

These variables are accessed in the code using `import.meta.env.VITE_API_BASE_URL` and `import.meta.env.VITE_AUTH_URL`.

## API Integration in Stores

API calls are made directly within the store actions. Here are examples of how different stores integrate with the API:

### Auth Store

```javascript
// src/stores/authStore.js
import axios from 'axios';
import { defineStore } from 'pinia';

export const useAuth = defineStore('auth', {
  state: () => ({
    error: null,
    loading: false,
    token: localStorage.getItem('token') || null,
    // other state properties
  }),

  actions: {
    async login(credentials) {
      try {
        this.error = null;
        this.loading = true;

        await axios.post('http://localhost:8080/auth/login', credentials);
        this.requiresOtp = true;
        this.pendingEmail = credentials.email;

        return { requiresOtp: true };
      } catch (error) {
        // Error handling
        this.error = 'Login failed';
        return { error: true };
      } finally {
        this.loading = false;
      }
    },

    async verifyOtp({ code }) {
      try {
        this.loading = true;
        this.error = null;
        const email = this.pendingEmail;

        const response = await axios.post('http://localhost:8080/auth/verify-otp', {
          email,
          code,
        });

        const data = response.data.data;
        this.completeAuthentication(data);
        return true;
      } catch (error) {
        // Error handling
        return false;
      } finally {
        this.loading = false;
      }
    }
    // Other actions
  }
});
```

### Hotels Store

```javascript
// src/stores/hotelsStore.js
import { defineStore } from 'pinia';
import axios from 'axios';

export const useHotels = defineStore('hotel', {
  state: () => ({
    hotels: [],
    hotelsFound: false,
    filters: {
      city: '',
      // other filter properties
    },
  }),

  actions: {
    async fetchHotels({
      city,
      dateFrom,
      dateTo,
      // other parameters
    }) {
      try {
        this.hotelsFound = false;
        const params = {};

        if (city) params.city = city;
        if (dateFrom) params.dateFrom = dateFrom;
        if (dateTo) params.dateTo = dateTo;
        // Set other params

        const res = await axios.get('http://localhost:8080/api/hotels', { params });

        this.hotels = res.data.map((hotel) => {
          return {
            ...hotel,
            image: hotel.imagePath,
          };
        });

        this.hotelsFound = true;
      } catch (error) {
        this.hotels = [];
        this.hotelsFound = false;
        console.error('Failed to fetch hotels:', error.message);
        throw new Error('Failed to fetch hotels');
      }
    }
    // Other actions
  }
});
```

## API Endpoints

The frontend interacts with the following API endpoints:

### Authentication Endpoints

| Endpoint | Method | Description | Request Body | Response |
|----------|--------|-------------|-------------|----------|
| `/auth/login` | POST | User login | `{ email, password }` | `{ token, refreshToken }` |
| `/auth/register` | POST | User registration | `{ email, password, firstName, lastName, ... }` | `{ message, userId }` |
| `/auth/verify-otp` | POST | Verify OTP for 2FA | `{ email, otp }` | `{ token, refreshToken }` |
| `/auth/me` | GET | Get user profile | - | User object |
| `/auth/me` | PUT | Update user profile | User data | Updated user object |

### Hotel Endpoints

| Endpoint | Method | Description | Request Params | Response |
|----------|--------|-------------|---------------|----------|
| `/api/hotels` | GET | Get hotel listings | `{ location, checkIn, checkOut, guests, page, size }` | Array of hotel objects with pagination |
| `/api/hotels/{id}` | GET | Get hotel details | - | Hotel object |
| `/api/hotels/{id}/rooms` | GET | Get rooms for a hotel | `{ checkIn, checkOut, guests }` | Array of room objects |

### Booking Endpoints

| Endpoint | Method | Description | Request Body/Params | Response |
|----------|--------|-------------|---------------------|----------|
| `/api/bookings` | POST | Create a booking | `{ hotelId, roomId, checkIn, checkOut, guests, ... }` | Booking object |
| `/api/bookings/{id}` | GET | Get booking details | - | Booking object |
| `/api/bookings/{id}/cancel` | POST | Cancel a booking | - | `{ message, bookingId }` |
| `/api/users/me/bookings` | GET | Get user's booking history | - | Array of booking objects |

### Payment Endpoints

| Endpoint | Method | Description | Request Body/Params | Response |
|----------|--------|-------------|---------------------|----------|
| `/api/payments/initiate/{bookingId}` | POST | Initiate payment | - | `{ paymentUrl, authority }` |
| `/api/payments/verify` | GET | Verify payment | `{ Authority, Status }` | `{ success, message, bookingId }` |

## Error Handling

API errors are handled consistently throughout the application:

1. **Network Errors**: Handled within store actions
2. **Authentication Errors**: 401 responses trigger logout and redirect to login
3. **Validation Errors**: Displayed to the user in the relevant form
4. **Server Errors**: Displayed as notifications to the user

Example of error handling in a store action:

```javascript
// From hotelsStore.js
async fetchHotels({
  city,
  dateFrom,
  dateTo,
  // other parameters
}) {
  try {
    this.hotelsFound = false;
    const params = {};

    if (city) params.city = city;
    if (dateFrom) params.dateFrom = dateFrom;
    if (dateTo) params.dateTo = dateTo;
    // Set other params

    const res = await axios.get('http://localhost:8080/api/hotels', { params });

    this.hotels = res.data.map((hotel) => {
      return {
        ...hotel,
        image: hotel.imagePath,
      };
    });

    this.hotelsFound = true;
  } catch (error) {
    this.hotels = [];
    this.hotelsFound = false;
    console.error('Failed to fetch hotels:', error.message);
    throw new Error('Failed to fetch hotels');
  }
}
```

Example of error handling in a component using the store:

```javascript
import { ref } from 'vue'
import { useHotels } from '@/stores/hotelsStore'
import { storeToRefs } from 'pinia'

const hotelStore = useHotels()
const { hotels, hotelsFound } = storeToRefs(hotelStore)
const error = ref(null)

async function searchHotels(params) {
  try {
    await hotelStore.fetchHotels(params)
  } catch (err) {
    error.value = err.message || 'Failed to fetch hotels'
    console.error('Error fetching hotels:', err)
  }
}
```

## Authentication Flow

1. **Login**:
   - User submits credentials via the login form
   - AuthService.login() sends credentials to the backend
   - On success, the token is stored in localStorage and the user profile is fetched
   - The user is redirected to the requested page or the home page

2. **Token Management**:
   - The authentication token is stored in localStorage
   - The token is included in all API requests via the Axios interceptor
   - If a 401 error is received, the user is logged out and redirected to login

3. **Logout**:
   - The token is removed from localStorage
   - The user state is cleared
   - The user is redirected to the login page

## Data Transformation

In some cases, data received from the API needs to be transformed before being used in the UI. This transformation is typically done in the store actions:

```javascript
// Example from hotelsStore.js
async function fetchHotels(params) {
  loading.value = true
  error.value = null

  try {
    const data = await HotelService.getHotels(params)

    // Transform API data to match UI requirements
    hotels.value = data.content.map(hotel => ({
      id: hotel.id,
      name: hotel.name,
      location: hotel.location,
      price: hotel.minPrice,
      rating: hotel.averageRating,
      imageUrl: hotel.images[0] || '/images/default-hotel.jpg',
      amenities: hotel.amenities.slice(0, 3) // Show only top 3 amenities
    }))

    pagination.value = {
      currentPage: data.number,
      totalPages: data.totalPages,
      totalElements: data.totalElements
    }
  } catch (err) {
    error.value = err.response?.data?.message || 'Failed to fetch hotels'
  } finally {
    loading.value = false
  }
}
```

To use the mock API, simply import it instead of the real service in your stores during development.

## Best Practices

1. **Store Organization**: Keep API calls within store actions for better state management
2. **Error Handling**: Implement consistent error handling across the application
3. **Loading States**: Track loading states for all API calls to improve UX
4. **Data Transformation**: Transform API data to match UI requirements
5. **Authentication**: Handle authentication and token management properly
6. **Environment Variables**: Use environment variables for API URLs
7. **Interceptors**: Use Axios interceptors for common request/response handling
8. **Mock API**: Develop with a mock API when needed
