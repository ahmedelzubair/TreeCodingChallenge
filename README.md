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

The project follows a structured layout, including packages for authentication, controllers, database, exceptions, and
more.

## Running the Application with IntelliJ IDEA

To run the application using IntelliJ IDEA, follow these steps:

1. **Clone my repository**: First, clone my repository to your local machine using IntelliJ IDEA.

2. In the project directory, locate
   the `src/main/java/sa/com/tree/account/statment/treecodingchallenge/StatementsApplication.java` file, which contains
   the main application class.

3. **Right-click on the `StatementsApplication` class** and select "Run 'StatementsApplication.main()'." This will start
   the application.

4. The application should now be running on `http://localhost:8080`.

## API Endpoints
The following API endpoints are available under the root path `/api/v1/`:

- `/api/v1/user/login`: Obtain a JWT token for authentication.
- `/api/v1/account/{account_id}/statements`: Retrieve account statements for the specified account (
  replace `{account_id}` with the actual account ID).
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

I have attached the SonarQube Report in the `sonar_qube` directory.

If you have any questions or need further assistance, please feel free to reach out. Thank you for checking out my
completed Java assignment!
