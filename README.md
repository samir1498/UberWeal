
---

<<<<<<< HEAD
# UberWeal SAAS Exercise

## Overview

UberWeal SAAS is a ride-hailing application that allows customers to request drivers for their journeys. The application calculates the ride price based on the nature of the journey and deducts the corresponding funds from the customer's account.
=======
# UberWeal SAAS - Car Ride Booking Application

UberWeal SAAS is a trendy app for car rides that allows Riders to request a Driver at any time to travel to their desired destination. The pricing of the rides varies based on the nature of the journey. This README provides an overview of the application and outlines key features and use cases.

## Pricing Rules

- For a trip from Paris to the outside world, the price is 30 euros.
- For a journey from outside to Paris, the price is 0 euros.
- For a journey within the city walls, the price is 10 euros.
- For a trip outside Paris, the price is 50 euros.

## Booking and Payment

- To book a ride, the Rider must have sufficient funds in their bank account.
- A pre-authorization is made by UberWeal for each Driver booking to ensure the solvency of the account.
- In the first year of use, a Rider will pay half the price of the ride.
- If the price was 0 euros, a voucher is considered, valid for the next ride.
- If the number of kilometers is less than 5 km, the Rider gets a 5-euro discount.
- The Rider's account is charged once the ride is over.
>>>>>>> cbc3cbb (Writing Customer UseCase Tests)

## User Stories

### Book a Driver

<<<<<<< HEAD
As a Customer, I want to book a Driver that can take me to my destination, providing an efficient alternative to public transport.

### List all my past rides

As a Customer, I would like to list all the history of my rides with mention of the respective Drivers, enabling me to track my ride history.

## Pricing Rules

- For a trip from Paris to the outside world, the price is 30 euros.
- For a journey from outside to Paris, the price is 0 euro.
- For a journey within the city walls, the price is 10 euros.
- For a trip outside Paris, the price is 50 euros.
- In the first year of use, a Customer pays half the price of the ride.
- If the price was 0€, then a voucher is considered, valid for the next ride.
- If the number of kilometers is less than 5 km, there is a 5-euro discount.

## Implementation Details

### Project Structure

The project follows the Clean Architecture principles with the following structure:

```
src
├── main
│   ├── java
│   │   ├── com.samir.uberweal
│   │   │   ├── application
│   │   │   ├── domain
│   │   │   ├── infrastructure
│   │   │   └── interfaces
│   │   └── resources
└── test
    ├── java
    │   └── com.samir.uberweal
    │       ├── application
    │       ├── domain
    │       └── interfaces
    └── resources
```

### Classes and Packages

- **application:** Contains use cases or application-specific business rules.
- **domain:** Holds the business entities, repositories, and services.
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
=======
As a Rider, I want to book a Driver that can take me to my destination to ensure an efficient alternative to public transport.

### List all my past rides

As a Rider, I would like to list all the history of my rides with mention of the respective Drivers so that I can keep track of my usage.

## Implementation Details

### Entities

- **Rider:** Represents a user who books a ride.
- **Driver:** Represents a driver available for booking.
- **Ride:** Represents a booked ride with details such as destination, distance, and pricing.
- **BankAccount:** Represents the bank account associated with a Rider.

### Services

- **BookingService:** Manages the process of booking a ride, including checking the Rider's bank account balance and performing a pre-authorization.
- **RideHistoryService:** Provides functionality to retrieve the history of past rides for a Rider.

### Pricing Logic

The pricing logic is implemented in the `calculatePrice` method in the `Ride` entity, considering factors such as journey type, starting point, and distance.

## Getting Started

To get started with the UberWeal SAAS project:

1. Clone the repository: `git clone https://github.com/yourusername/uberweal-saas.git`
2. Navigate to the project directory: `cd uberweal-saas`
3. Open the project in your preferred IDE.
4. Explore and modify the code based on your requirements.

## Dependencies

The project may have dependencies on external libraries or frameworks. Check the `pom.xml` file (for Maven projects) or other dependency management files for details.

## Contributions

Contributions to the UberWeal SAAS project are welcome! Feel free to fork the repository, make changes, and submit a pull request.

## License

This project is licensed under the [MIT License](LICENSE).

---

>>>>>>> cbc3cbb (Writing Customer UseCase Tests)
