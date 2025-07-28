# Database Schema Documentation

This document describes the database schema for the Hotel Booking System.

## Overview

The Hotel Booking System uses PostgreSQL as its database. The schema consists of four main entities:
- Users
- Hotels
- Rooms
- Bookings

## Tables

### Users Table

The `users` table stores information about registered users.

| Column Name | Data Type | Constraints | Description |
|-------------|-----------|-------------|-------------|
| id | BIGINT | PK, NOT NULL | Unique identifier for the user |
| email | VARCHAR | UNIQUE, NOT NULL | User's email address (used as username) |
| password | VARCHAR | NOT NULL | Encrypted password |
| first_name | VARCHAR | | User's first name |
| last_name | VARCHAR | | User's last name |
| phone_number | VARCHAR | | User's phone number |
| role | VARCHAR | NOT NULL | User's role (USER or ADMIN) |
| secret_key | VARCHAR | | Secret key for two-factor authentication |

### Hotels Table

The `hotels` table stores information about hotels available for booking.

| Column Name | Data Type | Constraints | Description |
|-------------|-----------|-------------|-------------|
| id | BIGINT | PK, NOT NULL | Unique identifier for the hotel |
| name | VARCHAR | NOT NULL | Hotel name |
| description | VARCHAR | | Hotel description |
| city | VARCHAR | | City where the hotel is located |
| address | VARCHAR | | Hotel's address |
| image_path | VARCHAR | | Path to the hotel's image |
| stars | INT | | Hotel's star rating (1-5) |
| rate | INT | | Average user rating |
| votes | INT | | Number of user votes |
| features | VARCHAR[] | | Array of hotel features |

### Rooms Table

The `rooms` table stores information about rooms in hotels.

| Column Name | Data Type | Constraints | Description |
|-------------|-----------|-------------|-------------|
| id | BIGINT | PK, NOT NULL | Unique identifier for the room |
| hotel_id | BIGINT | FK, NOT NULL | Reference to the hotel |
| title | VARCHAR | NOT NULL | Room title/name |
| capacity | INT | NOT NULL | Maximum number of guests |
| price_per_night | DOUBLE | NOT NULL | Price per night |

### Bookings Table

The `bookings` table stores information about room bookings.

| Column Name | Data Type | Constraints | Description |
|-------------|-----------|-------------|-------------|
| id | BIGINT | PK, NOT NULL | Unique identifier for the booking |
| room_id | BIGINT | FK, NOT NULL | Reference to the booked room |
| user_id | BIGINT | FK, NOT NULL | Reference to the user who made the booking |
| payment_code | BIGINT | NOT NULL | Code for payment identification |
| price | DOUBLE | NOT NULL | Total price for the booking |
| check_in_date | TIMESTAMP | NOT NULL | Check-in date and time |
| check_out_date | TIMESTAMP | NOT NULL | Check-out date and time |
| pending_expires_at | TIMESTAMP | | Expiration time for pending bookings |
| status | VARCHAR | NOT NULL | Booking status (AVAILABLE, PENDING, BOOKED) |
| authority | VARCHAR | | Payment authority from Zarinpal |
| ref_id | BIGINT | | Payment reference ID from Zarinpal |

## Relationships

1. **User to Bookings**: One-to-Many
   - A user can have multiple bookings
   - Each booking belongs to one user

2. **Hotel to Rooms**: One-to-Many
   - A hotel can have multiple rooms
   - Each room belongs to one hotel

3. **Room to Bookings**: One-to-Many
   - A room can have multiple bookings (at different times)
   - Each booking is for one room