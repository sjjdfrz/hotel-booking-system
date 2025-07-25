# Frontend Architecture

This document describes the architecture of the Hotel Booking System frontend application.

## Overview

The Hotel Booking System frontend is built as a Single Page Application (SPA) using Vue.js 3 with the Composition API. It follows a component-based architecture with a clear separation of concerns between UI components, state management, routing, and API integration.

## Architectural Layers

The application is organized into the following layers:

### 1. Presentation Layer (UI Components)

The presentation layer consists of Vue components that handle the user interface and user interactions. Components are organized by feature and reusability:

- **Views**: Page-level components that represent different routes in the application
- **Layout Components**: Components that define the overall structure of the application
- **Feature Components**: Components specific to a particular feature (e.g., hotel search, booking)
- **UI Components**: Reusable UI elements used across the application

### 2. State Management Layer

The state management layer uses Pinia to manage application state. Stores are organized by domain:

- **authStore**: Manages authentication state and user information
- **hotelsStore**: Manages hotel listings and search functionality
- **hotelDetailStore**: Manages detailed information about a specific hotel
- **bookingStore**: Manages booking-related state and operations

### 3. Routing Layer

Vue Router handles navigation between different views in the application. Routes are defined in `src/router/index.js` and organized by feature.

### 4. API Integration Layer

API integration is handled through service modules that encapsulate API calls using Axios. These services abstract the details of API communication from the rest of the application.

## Directory Structure

```
frontend/
├── public/                 # Static assets
├── src/
│   ├── assets/             # Application assets (images, styles)
│   ├── components/         # Vue components
│   │   ├── auth/           # Authentication components
│   │   ├── home/           # Home page components
│   │   ├── hotels/         # Hotel-related components
│   │   ├── layout/         # Layout components
│   │   ├── rooms/          # Room-related components
│   │   └── ui/             # Reusable UI components
│   ├── router/             # Vue Router configuration
│   ├── services/           # API services
│   ├── stores/             # Pinia stores
│   ├── utils/              # Utility functions
│   ├── views/              # Page components
│   ├── App.vue             # Root component
│   └── main.js             # Application entry point
├── .env                    # Environment variables
├── index.html              # HTML template
├── package.json            # Project dependencies
├── tailwind.config.js      # Tailwind CSS configuration
└── vite.config.js          # Vite configuration
```

## Component Architecture

Components follow the Composition API pattern, which provides better type inference, more flexible code organization, and improved reusability compared to the Options API.

### Example Component Structure

```vue
<template>
  <!-- Component template -->
</template>

<script setup>
// Imports
import { ref, computed, onMounted } from 'vue'
import { useStore } from '../stores/exampleStore'

// State
const store = useStore()
const localState = ref(initialValue)

// Computed properties
const derivedValue = computed(() => {
  // Compute value based on state
})

// Methods
const handleEvent = () => {
  // Handle event
}

// Lifecycle hooks
onMounted(() => {
  // Component mounted logic
})
</script>

<style scoped>
/* Component-specific styles */
</style>
```

## Data Flow

1. **User Interaction**: User interacts with a component in the UI
2. **Component Event**: Component emits an event or calls a method
3. **Store Action**: Method calls an action in the appropriate store
4. **API Call**: Store action makes an API call via a service
5. **State Update**: Store updates its state based on the API response
6. **UI Update**: Components reactively update based on the state change

## Routing

The application uses Vue Router for navigation. Routes are defined with the following structure:

```javascript
const routes = [
  {
    path: '/',
    name: 'home',
    component: HomeView
  },
  {
    path: '/hotels',
    name: 'hotels',
    component: HotelsView
  },
  {
    path: '/hotels/:id/rooms',
    name: 'rooms',
    component: RoomsView,
    props: true
  },
  // Protected routes require authentication
  {
    path: '/reserve/:roomId',
    name: 'reserve',
    component: ReserveView,
    meta: { requiresAuth: true },
    props: true
  }
]
```

Navigation guards ensure that protected routes require authentication:

```javascript
router.beforeEach((to, from, next) => {
  const authStore = useAuthStore()
  
  if (to.meta.requiresAuth && !authStore.isAuthenticated) {
    next({ name: 'login', query: { redirect: to.fullPath } })
  } else {
    next()
  }
})
```

## State Management

Pinia stores follow a consistent pattern:

```javascript
export const useExampleStore = defineStore('example', {
  state: () => ({
    // State properties
  }),
  
  getters: {
    // Computed properties derived from state
  },
  
  actions: {
    // Methods that can change state and perform side effects
  }
})
```

## Responsive Design

The application uses Tailwind CSS for responsive design, with breakpoints defined for different device sizes:

- **sm**: 640px and above (small devices)
- **md**: 768px and above (medium devices)
- **lg**: 1024px and above (large devices)
- **xl**: 1280px and above (extra large devices)
- **2xl**: 1536px and above (2x extra large devices)

Components use responsive classes to adapt their layout and appearance based on the screen size.

## Performance Considerations

- **Code Splitting**: Routes are lazy-loaded to reduce initial bundle size
- **Component Reuse**: Common UI elements are abstracted into reusable components
- **Virtualization**: Large lists use virtualization to improve rendering performance
- **Caching**: API responses are cached where appropriate to reduce network requests