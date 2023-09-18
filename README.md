# Tree Digital Java Assignment README

Welcome to my Java assignment README. I've completed this assignment for Tree Digital, which involves viewing account
statements. The application is built using Spring Boot 3.1.3 and provides API endpoints for retrieving account
statements. Security is ensured with JWT tokens. Here are the key points:

## Getting Started

1. **Clone my repository**: You can clone my repository to your local machine using IntelliJ IDEA.

2. **Install the dependencies using Maven**: To ensure all required dependencies are installed, navigate to the project
   directory and run the following command:

3. **Configure MS Access database path**: Make sure that the MS Access database path is configured correctly. The
   database is located in the `data` directory in the project root directory. Depending on your OS, the path might
   differ.

## Project Structure

The project follows a structured layout, including packages for config, controllers, service, repository, exceptions,
and
more.

## Running the Application with IntelliJ IDEA

To run the application using IntelliJ IDEA, follow these steps:

1. **Clone my repository**: First, clone my repository to your local machine using IntelliJ IDEA.

2. In the project directory, locate
   the `src/main/java/sa/com/tree/account/statment/treecodingchallenge/TreeCodingChallengeApplication.java` file, which
   contains the main application class.

3. **Right-click on the `TreeCodingChallengeApplication` class** and select "Run '
   TreeCodingChallengeApplication.main()'." This will start the application.

4. The application should now be running on `http://localhost:8080`.

## API Endpoints
The following API endpoints are available under the root path `/api/v1/`:

- `/api/v1/user/login`: Obtain a JWT token for authentication. You can use the following pre-authenticated users to log
  in to the application for testing purposes:

  ### Admin User

    - **Username:** admin
    - **Password:** admin

  ### Regular User

    - **Username:** user
    - **Password:** user


- `/api/v1/account/{account_id}/statements`: Retrieve account statements for the specified account (
  replace `{account_id}` with the actual account ID). Make sure you are already logged in, and the JWT token is passed
  in the Authorization header. Here is an already-made search URL example you can use in Postman to do a quick
  search: http://localhost:8080/api/v1/account/3/statements?fromDate=01.07.2000&toDate=08.08.2023&fromAmount=1&toAmount=900000000000

- `/api/v1/user/logout`: Log out and invalidate the session.

**I recommend using Postman for testing these endpoints**, as it provides a user-friendly interface for making API
requests. You can import the provided collection for easy testing.

## Unit Testing
Run `mvn clean test` to execute unit tests with coverage reports.

## Security
- JWT tokens ensure access control.
- In-memory database manages user login and session information.
- Tokens expire after 5 minutes.

## SonarQube Report

Here is the SonarQube report for the project:
https://sonarcloud.io/project/overview?id=ahmedelzubair_TreeCodingChallenge

## Test Coverage Report

Test coverage report is available in the [test_coverage_report](test_coverage_report) directory.

If you have any questions or need further assistance, please feel free to reach out. Thank you for checking out my work!