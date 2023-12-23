
---

# UberWeal SAAS Exercise

## Overview

UberWeal SAAS is a ride-hailing application that allows Riders to request drivers for their journeys. The application calculates the ride price based on the nature of the journey and deducts the corresponding funds from the Rider's account.

## User Stories

### Book a Driver

As a Rider, I want to book a Driver that can take me to my destination, providing an efficient alternative to public transport.

### List all my past races

As a Rider, I would like to list all the history of my races with mention of the respective Drivers, enabling me to track my ride history.

## Pricing Rules

- For a trip from Paris to the outside world, the price is 30 euros.
- For a journey from outside to Paris, the price is 0 euro.
- For a journey within the city walls, the price is 10 euros.
- For a trip outside Paris, the price is 50 euros.
- In the first year of use, a Rider pays half the price of the ride.
- If the price was 0€, then a voucher is considered, valid for the next ride.
- If the number of kilometers is less than 5 km, there is a 5-euro discount.

## Implementation Details

### Project Structure

The project follows the Clean Architecture principles with the following structure:

```
src
├── main
│   ├── java
│   │   └── com.samir.uberweal
│   │       ├── config
│   │       ├── adapters
│   │       │   ├── controllers
│   │       │   ├── dtos
│   │       │   ├── gateways
│   │       │   └── presenters
│   │       ├── application
│   │       │   ├── command
│   │       │   │   ├── commands
│   │       │   │   └── handlers
│   │       │   │
│   │       │   └── query
│   │       │       ├── queries
│   │       │       └── handlers
│   │       ├── domain
│   │       │   ├── entities
│   │       │   ├── exceptions
│   │       │   ├── gateways
│   │       │   └── observers
│   │       │   └── services
│   │       │       └── pricing
│   │       └── infrastructure
│   │           ├── jpa
│   │           │   ├── entities
│   │           │   └── repositories
│   │           └── repositories
│   └── resources
└── test
    └── java
        └── com.samir.uberweal
            └── application
                └── command
                    └── handlers


```

### Classes and Packages

- **core:** application core (business rules).
  - **domain:** Holds the business entities, repositories, and services.
  - **use cases:** Application business rules.
- **infrastructure:** Deals with external concerns, such as databases or external services.
- **interfaces:** Contains the delivery mechanisms like controllers, presenters, and gateways.

### Testing

Test cases are provided to ensure the correctness of the implemented use cases.

## Running the Tests

To run the tests, execute the following command:

```bash
mvn test
```

## Dependencies

The project uses [Maven](https://maven.apache.org/) as the build tool.

---

