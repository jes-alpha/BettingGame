# Betting Game

## Overview

This Betting Game is an application where players can place bets to guess a randomly generated number.

## Installation

To set up and run the project locally, follow these steps:

1. Clone the repository:
    ```sh
    git clone https://github.com/jes-alpha/BettingGame.git
    ```

2. Build the project:
    ```sh
    mvn clean install
    ```

3. Run the application:
    ```sh
    mvn spring-boot:run
    ```

## Usage

Once the project is running, you can interact with the different endpoints using a tool like Postman, curl or from the
swagger. Alternatively, you can also use the enclosed `BettingGameAPI.http` file to run requests directly through
IntelliJ.
For example, to retrieve the leaderboard, you can make a GET request to:

```sh
GET http://localhost:8080/leaderboard
```

## Swagger API Documentation

To explore the API endpoints and their definitions, you can access the Swagger UI available with the running
application.

1. After starting the application, open your web browser.
2. Navigate to:
    ```sh
    [http://localhost:8080/swagger-ui/](http://localhost:8080/swagger-ui/)
    ```
3. You will find a list of all available API endpoints and can interact with them directly through the Swagger UI.

## Features

- Register new players
- Place bets
- Retrieve leaderboard

## Contact

If you have any questions, please reach out to our team at: `bianco.jess@gmail.com`