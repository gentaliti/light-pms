# Light PMS

## Description

Light version of Property Management System written in Spring Boot & React.

##Table of Contents
- [Backend](#backend)
  - [Domain](#domain)
  - [Distributed Locking](#distributed-locking)
  - [Exception Handling](#exception-handling)
  - [Logging](#logging)
- [UI](#ui)
  - [Code structure](#code-structure)
  - [Additional notes](#additional-notes)
- [Infrastructure](#infrastrucure)
    - [Code structure](#code-structure)
    - [Additional notes](#additional-notes)

<a name="headers"/>

## Features

- [x] Booking Overlaps
- [x] Distributed Locking 
- [x] Crud Operations for Bookings
- [x] Crud Operations for Blocks
- [x] Rebooking option

## Backend

### Domain

In this project, there are two models: Property and Booking. These models are related in a One-to-Many manner, meaning that a single Property can have multiple Bookings.

Bookings can be of type `RESERVATION` and `BLOCK`. Reservation is used by the clients to add a reservation for a property. Block is a special booking type which is used when the property owner or manager selects a range of days during
which no guest can make a booking.

### Distributed locking

In situations where two users attempt to create or update a booking simultaneously, there is a possibility of conflicting booking times. 
To address this issue, I used ShedLock, which provides a distributed locking mechanism. 
By employing ShedLock, all instances of the backend can utilize a shared lock (specifically, a database lock).

The lock is established based on the Property ID, ensuring that only one user can hold the lock at any given time to create or update a booking for a single property.

### Exception handling

Exception handling in this project is implemented using Spring Controller advice. There are two types of exceptions that are handled:

NotFoundException: This exception results in a 404 error being produced in the API.
LightPmsValidationException: This exception leads to a 409 error being generated in the API.

### Logging

Exceptions which produce a 4xx API errors are logged at `WARN` level. Everything else at `ERROR` level. 

## UI

### Recording

![Alt Text](./pms-recording.gif)

### Code structure

Divided it into separate modules or folders, each serving a specific purpose:

- **Components**: This folder contains reusable components that can be used across different pages or sections of the UI.
- **Routes**: Here, the pages of the application are organized.
- **Styles**: This folder is dedicated to managing the styles of the UI components and pages.


### Additional notes
For simplicity, for all the Properties a single local image is used (rather than saving the image path in the database).


## Infrastrucure
