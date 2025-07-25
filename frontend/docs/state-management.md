# State Management

This document describes the state management approach used in the Hotel Booking System frontend application.

## Overview

The Hotel Booking System uses [Pinia](https://pinia.vuejs.org/) for state management. Pinia is the official state management library for Vue.js applications, providing a simple and type-safe way to share state between components.

## Store Structure

The application's state is divided into several domain-specific stores:

- **authStore**: Manages authentication state and user information
- **hotelsStore**: Manages hotel listings and search functionality
- **hotelDetailStore**: Manages detailed information about a specific hotel
- **bookingStore**: Manages booking-related state and operations

Each store is defined in a separate file in the `src/stores` directory.

## Store Implementation

### Store Definition

Stores are defined using Pinia's `defineStore` function:

```javascript
// src/stores/authStore.js
import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import axios from 'axios'

export const useAuthStore = defineStore('auth', () => {
  // State
  const user = ref(null)
  const token = ref(localStorage.getItem('token') || null)
  const loading = ref(false)
  const error = ref(null)
  const requiresOtp = ref(false)
  const pendingEmail = ref(null)

  // Getters
  const isAuthenticated = computed(() => !!token.value)
  const userFullName = computed(() => user.value ? `${user.value.firstName} ${user.value.lastName}` : '')

  // Actions
  async function login(credentials) {
    loading.value = true
    error.value = null

    try {
      await axios.post('http://localhost:8080/auth/login', credentials)
      requiresOtp.value = true
      pendingEmail.value = credentials.email
      return { requiresOtp: true }
    } catch (err) {
      error.value = err.message || 'Login failed'
      return { error: true }
    } finally {
      loading.value = false
    }
  }

  async function verifyOtp(data) {
    loading.value = true
    error.value = null

    try {
      const response = await axios.post('http://localhost:8080/auth/verify-otp', {
        email: pendingEmail.value,
        code: data.code
      })

      const responseData = response.data.data
      token.value = responseData.accessToken
      localStorage.setItem('token', responseData.accessToken)
      requiresOtp.value = false
      pendingEmail.value = null
      return true
    } catch (err) {
      error.value = err.message || 'OTP verification failed'
      return false
    } finally {
      loading.value = false
    }
  }

  async function logout() {
    token.value = null
    user.value = null
    requiresOtp.value = false
    pendingEmail.value = null
    localStorage.removeItem('token')
  }

  // Initialize
  if (token.value) {
    // Fetch user profile if needed
  }

  return {
    // State
    user,
    token,
    loading,
    error,
    requiresOtp,
    pendingEmail,

    // Getters
    isAuthenticated,
    userFullName,

    // Actions
    login,
    verifyOtp,
    logout
  }
})
```

### Store Usage in Components

Stores are imported and used in components:

```vue
<script setup>
import { useAuthStore } from '@/stores/authStore'
import { ref } from 'vue'

const authStore = useAuthStore()
const email = ref('')
const password = ref('')

async function handleLogin() {
  const success = await authStore.login({
    email: email.value,
    password: password.value
  })

  if (success) {
    // Navigate to dashboard or other protected page
  }
}
</script>

<template>
  <div>
    <p v-if="authStore.isAuthenticated">
      Welcome, {{ authStore.userFullName }}!
    </p>
    <form v-else @submit.prevent="handleLogin">
      <!-- Login form fields -->
    </form>
  </div>
</template>
```

## Store Descriptions

### Auth Store

**File**: `src/stores/authStore.js`

**Purpose**: Manages user authentication and profile information.

**State**:
- `user`: Current user object
- `token`: JWT authentication token
- `loading`: Loading state for async operations
- `error`: Error message if authentication fails

**Getters**:
- `isAuthenticated`: Whether the user is authenticated
- `userFullName`: Full name of the current user

**Actions**:
- `login(credentials)`: Authenticates the user
- `register(userData)`: Registers a new user
- `logout()`: Logs out the current user
- `fetchUserProfile()`: Fetches the user's profile data
- `updateProfile(userData)`: Updates the user's profile

### Hotels Store

**File**: `src/stores/hotelsStore.js`

**Purpose**: Manages hotel listings and search functionality.

**State**:
- `hotels`: Array of hotel objects
- `filters`: Current search filters
- `loading`: Loading state for async operations
- `error`: Error message if hotel fetching fails
- `pagination`: Pagination information

**Getters**:
- `filteredHotels`: Hotels filtered by current filters
- `totalPages`: Total number of pages based on pagination

**Actions**:
- `fetchHotels(params)`: Fetches hotels based on search parameters
- `setFilters(filters)`: Updates the current filters
- `clearFilters()`: Clears all filters
- `nextPage()`: Loads the next page of results
- `previousPage()`: Loads the previous page of results

### Hotel Detail Store

**File**: `src/stores/hotelDetailStore.js`

**Purpose**: Manages detailed information about a specific hotel.

**State**:
- `hotel`: Current hotel object
- `loading`: Loading state for async operations
- `error`: Error message if hotel fetching fails

**Getters**:
- `hasRooms`: Whether the hotel has available rooms
- `amenities`: List of hotel amenities

**Actions**:
- `fetchHotel(id)`: Fetches detailed information about a hotel
- `fetchHotelRooms(id)`: Fetches rooms available at the hotel

### Booking Store

**File**: `src/stores/bookingStore.js`

**Purpose**: Manages booking-related state and operations.

**State**:
- `currentBooking`: Current booking information
- `bookingHistory`: User's booking history
- `loading`: Loading state for async operations
- `error`: Error message if booking operations fail

**Getters**:
- `totalPrice`: Total price of the current booking
- `bookingDates`: Formatted check-in and check-out dates

**Actions**:
- `createBooking(bookingData)`: Creates a new booking
- `fetchBookingHistory()`: Fetches the user's booking history
- `cancelBooking(bookingId)`: Cancels a booking
- `initiatePayment(bookingId)`: Initiates payment for a booking
- `verifyPayment(params)`: Verifies payment status

## Best Practices

### Store Organization

1. **Domain Separation**: Divide stores by domain or feature
2. **Single Responsibility**: Each store should have a clear, focused purpose
3. **Consistent Structure**: Use a consistent pattern for all stores

### State Management

1. **Minimal State**: Store only necessary state in Pinia
2. **Derived State**: Use getters for derived state
3. **Local State**: Keep component-specific state in components

### Actions

1. **Async Operations**: Use actions for all async operations
2. **Error Handling**: Implement proper error handling in actions
3. **Loading States**: Track loading states for better UX

### Persistence

1. **Token Storage**: Store authentication tokens in localStorage
2. **Sensitive Data**: Never store sensitive data in localStorage
3. **State Rehydration**: Rehydrate state from persistent storage when the app loads

## Debugging

Pinia integrates with Vue DevTools, allowing you to inspect and debug your stores:

1. Install [Vue DevTools](https://devtools.vuejs.org/)
2. Open your application in a browser
3. Open DevTools and navigate to the Vue panel
4. Select the Pinia tab to view your stores

## Performance Considerations

1. **Selective Imports**: Import only the stores you need in each component
2. **Computed Properties**: Use computed properties to derive values from store state
3. **Watchers**: Be cautious with watchers on store state to avoid performance issues
4. **Batch Updates**: Batch multiple state updates when possible
