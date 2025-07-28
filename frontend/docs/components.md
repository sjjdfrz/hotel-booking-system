# Component Library

This document provides an overview of the components available in the Hotel Booking System frontend application.

## Overview

The component library is organized by feature and reusability. Components are built using Vue.js 3 with the Composition API and styled with Tailwind CSS.

## Component Categories

### Authentication Components

Located in `src/components/auth/`

#### AuthForm.vue

A unified component for handling both login and registration.

**Props:**
- `mode` (String): Either 'login' or 'register' to determine the form's behavior
- `redirectUrl` (String, optional): URL to redirect to after successful authentication

**Events:**
- `success`: Emitted when authentication is successful
- `error`: Emitted when authentication fails

**Usage:**
```vue
<AuthForm 
  mode="login" 
  redirectUrl="/hotels" 
  @success="handleSuccess" 
  @error="handleError" 
/>
```

### Home Page Components

Located in `src/components/home/`

#### HeroSection.vue

The main banner section displayed on the home page.

**Props:**
- `title` (String): Main heading text
- `subtitle` (String): Subheading text
- `backgroundImage` (String, optional): URL for the background image

**Usage:**
```vue
<HeroSection 
  title="Find Your Perfect Stay" 
  subtitle="Search from thousands of hotels worldwide" 
  backgroundImage="/images/hero.jpg" 
/>
```

#### HeroTextBlock.vue

Text content component used within the hero section.

**Props:**
- `title` (String): Main heading text
- `subtitle` (String): Subheading text
- `alignment` (String, optional): Text alignment ('left', 'center', 'right')

**Usage:**
```vue
<HeroTextBlock 
  title="Find Your Perfect Stay" 
  subtitle="Search from thousands of hotels worldwide" 
  alignment="center" 
/>
```

#### HotelSearchForm.vue

Form component for searching hotels.

**Props:**
- `initialValues` (Object, optional): Initial form values

**Events:**
- `search`: Emitted when the search form is submitted with the search parameters

**Usage:**
```vue
<HotelSearchForm 
  :initialValues="{ location: 'Tehran', checkIn: '2023-06-01', checkOut: '2023-06-05', guests: 2 }" 
  @search="handleSearch" 
/>
```

### Hotel Components

Located in `src/components/hotels/`

#### HotelCard.vue

Card component for displaying hotel information in a list.

**Props:**
- `hotel` (Object): Hotel data object
- `showDetails` (Boolean, optional): Whether to show detailed information

**Events:**
- `view`: Emitted when the user clicks to view hotel details
- `select`: Emitted when the user selects the hotel

**Usage:**
```vue
<HotelCard 
  :hotel="hotelData" 
  :showDetails="true" 
  @view="viewHotelDetails" 
  @select="selectHotel" 
/>
```

#### HotelFilters.vue

Component for filtering hotel search results.

**Props:**
- `filters` (Object): Current filter values
- `availableFilters` (Object): Available filter options

**Events:**
- `filter`: Emitted when filters are changed with the new filter values

**Usage:**
```vue
<HotelFilters 
  :filters="currentFilters" 
  :availableFilters="filterOptions" 
  @filter="updateFilters" 
/>
```

#### HotelHeader.vue

Header component for hotel detail pages.

**Props:**
- `hotel` (Object): Hotel data object

**Usage:**
```vue
<HotelHeader :hotel="hotelData" />
```

### Layout Components

Located in `src/components/layout/`

#### AppNavigation.vue

Main navigation component used across the application.

**Props:**
- `user` (Object, optional): User data for authenticated users

**Events:**
- `login`: Emitted when the login button is clicked
- `logout`: Emitted when the logout button is clicked

**Usage:**
```vue
<AppNavigation 
  :user="currentUser" 
  @login="navigateToLogin" 
  @logout="handleLogout" 
/>
```

### Room Components

Located in `src/components/rooms/`

#### RoomCard.vue

Card component for displaying room information.

**Props:**
- `room` (Object): Room data object
- `selected` (Boolean, optional): Whether the room is selected

**Events:**
- `select`: Emitted when the room is selected
- `reserve`: Emitted when the reserve button is clicked

**Usage:**
```vue
<RoomCard 
  :room="roomData" 
  :selected="isSelected" 
  @select="selectRoom" 
  @reserve="reserveRoom" 
/>
```

#### RoomList.vue

Component for displaying a list of rooms.

**Props:**
- `rooms` (Array): Array of room data objects
- `selectedRoomId` (String, optional): ID of the currently selected room

**Events:**
- `select`: Emitted when a room is selected with the room ID
- `reserve`: Emitted when the reserve button is clicked for a room

**Usage:**
```vue
<RoomList 
  :rooms="availableRooms" 
  :selectedRoomId="selectedRoom?.id" 
  @select="handleRoomSelect" 
  @reserve="handleRoomReserve" 
/>
```

### UI Components

Located in `src/components/ui/`

#### EmptyState.vue

Component for displaying when no data is available.

**Props:**
- `message` (String): Message to display
- `icon` (String, optional): Icon name to display
- `actionText` (String, optional): Text for the action button
- `actionRoute` (String or Object, optional): Route to navigate to when action button is clicked

**Events:**
- `action`: Emitted when the action button is clicked

**Usage:**
```vue
<EmptyState 
  message="No hotels found matching your criteria" 
  icon="search" 
  actionText="Clear filters" 
  @action="clearFilters" 
/>
```

#### FormInput.vue

Reusable form input component.

**Props:**
- `modelValue` (Any): Current input value (v-model)
- `label` (String): Input label
- `type` (String, optional): Input type (text, email, password, etc.)
- `placeholder` (String, optional): Input placeholder
- `error` (String, optional): Error message to display
- `required` (Boolean, optional): Whether the input is required

**Events:**
- `update:modelValue`: Emitted when the input value changes

**Usage:**
```vue
<FormInput 
  v-model="formData.email" 
  label="Email Address" 
  type="email" 
  placeholder="Enter your email" 
  :error="errors.email" 
  required 
/>
```

#### StarRating.vue

Component for displaying and setting star ratings.

**Props:**
- `modelValue` (Number): Current rating value (v-model)
- `max` (Number, optional): Maximum rating value (default: 5)
- `readonly` (Boolean, optional): Whether the rating is read-only

**Events:**
- `update:modelValue`: Emitted when the rating value changes

**Usage:**
```vue
<StarRating 
  v-model="hotelRating" 
  :max="5" 
  :readonly="true" 
/>
```

#### SubmitButton.vue

Reusable button component for form submissions.

**Props:**
- `text` (String): Button text
- `loading` (Boolean, optional): Whether to show loading state
- `disabled` (Boolean, optional): Whether the button is disabled
- `variant` (String, optional): Button variant (primary, secondary, etc.)

**Events:**
- `click`: Emitted when the button is clicked

**Usage:**
```vue
<SubmitButton 
  text="Book Now" 
  :loading="isLoading" 
  :disabled="!isFormValid" 
  variant="primary" 
  @click="submitForm" 
/>
```

## Best Practices

### Component Composition

- Use smaller, focused components that do one thing well
- Compose complex UIs from simpler components
- Use slots for flexible content insertion

### Props Validation

Always validate component props:

```javascript
const props = defineProps({
  user: {
    type: Object,
    required: true,
    validator: (value) => {
      return value.id && value.name
    }
  },
  mode: {
    type: String,
    default: 'view',
    validator: (value) => ['view', 'edit'].includes(value)
  }
})
```

### Event Handling

- Use custom events for component communication
- Emit events with meaningful names and payload data
- Document events in component comments

### Styling

- Use Tailwind utility classes for styling
- Use scoped styles for component-specific CSS
- Follow the project's design system for consistent UI

### Accessibility

- Use semantic HTML elements
- Include ARIA attributes where appropriate
- Ensure keyboard navigation works
- Test with screen readers

## Component Development Workflow

1. **Plan**: Define the component's purpose, props, events, and behavior
2. **Implement**: Create the component with the necessary logic and styling
3. **Test**: Test the component in isolation and in context
4. **Document**: Add comments and update this documentation
5. **Review**: Have another developer review the component
6. **Refine**: Refine based on feedback and testing