# BankTransactionBackend

This is a backend application that is taking care of managing a bank with customers, accounts and transactions.

To run the application:

# Run the application locally:
After making sure that you are on the right directory of the project, run the two following commands:
    1- `./gradlew build`
    2- `./gradlew bootRun`

# Run the application using the docker-compose file:

`docker-compose up -d`

This command is going to take care of creating all the containers we need:

- The database container running on port 5342
- The database portal (pgAdmin) container running on port 9090
- The backend application running on port 8080

For more information on the documentation of the APIs, you can visit
http://localhost:8080/swagger-ui/index.html after running the application.