package sa.com.tree.account.statment.treecodingchallenge.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import sa.com.tree.account.statment.treecodingchallenge.exception.*;
import sa.com.tree.account.statment.treecodingchallenge.utils.ApiResponse;

import java.time.LocalDateTime;

@ControllerAdvice
@Slf4j
public class AppExceptionHandler {


    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    protected ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request) {
        log.error("[MethodArgumentNotValidException] DTO properties validation failed with message : {}", ex.getMessage());
        ApiResponse apiResponse = ApiResponse.builder()
                .status(400)
                .timestamp(LocalDateTime.now())
                .message("Invalid search criteria")
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HashingException.class)
    public ResponseEntity<ApiResponse> handleHashingException(HashingException hashingException) {
        log.error("[HashingException] Hashing failed with message : {}", hashingException.getMessage());
        ApiResponse apiResponse = ApiResponse.builder()
                .status(500)
                .timestamp(LocalDateTime.now())
                .message("Internal Server Error")
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(InvalidSearchCriteriaException.class)
    public ResponseEntity<ApiResponse> handleInvalidSearchCriteriaException(InvalidSearchCriteriaException invalidSearchCriteriaException) {
        log.error(("[InvalidSearchCriteriaException] Search criteria validation failed with message : {}"), invalidSearchCriteriaException.getMessage());
        ApiResponse apiResponse = ApiResponse.builder()
                .status(400)
                .timestamp(LocalDateTime.now())
                .message(invalidSearchCriteriaException.getMessage())
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiResponse> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException methodArgumentTypeMismatchException) {
        log.error("[MethodArgumentTypeMismatchException] Search criteria validation failed with message : {}", methodArgumentTypeMismatchException.getMessage());
        ApiResponse apiResponse = ApiResponse.builder()
                .status(400)
                .timestamp(LocalDateTime.now())
                .message("Invalid search criteria")
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserAlreadyLoggedInException.class)
    public ResponseEntity<ApiResponse> handleUserAlreadyLoggedInException(UserAlreadyLoggedInException userAlreadyLoggedInException) {
        log.error("[UserAlreadyLoggedInException] User logging in failed with message : {}", userAlreadyLoggedInException.getMessage());
        ApiResponse apiResponse = ApiResponse.builder()
                .status(400)
                .timestamp(LocalDateTime.now())
                .message(userAlreadyLoggedInException.getMessage())
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidJWTException.class)
    public ResponseEntity<ApiResponse> handleInvalidJWTException(InvalidJWTException invalidJWTException) {
        log.error("[InvalidJWTException] JWT validation failed with message : {}", invalidJWTException.getMessage());
        ApiResponse apiResponse = ApiResponse.builder()
                .status(403)
                .timestamp(LocalDateTime.now())
                .message(invalidJWTException.getMessage())
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(LogoutException.class)
    public ResponseEntity<ApiResponse> handleLogoutException(LogoutException logoutException) {
        log.error("[LogoutException] Logout failed with message : {}", logoutException.getMessage());
        ApiResponse apiResponse = ApiResponse.builder()
                .status(500)
                .timestamp(LocalDateTime.now())
                .message(logoutException.getMessage())
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(LoginException.class)
    public ResponseEntity<ApiResponse> handleLoginException(LoginException loginException) {
        log.error("[LoginException] Login failed with message : {}", loginException.getMessage());
        ApiResponse apiResponse = ApiResponse.builder()
                .status(403)
                .timestamp(LocalDateTime.now())
                .message(loginException.getMessage())
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(StatementSearchException.class)
    public ResponseEntity<ApiResponse> handleStatementSearchException(StatementSearchException statementSearchException) {
        log.error("[StatementSearchException] Statement search failed with message : {}", statementSearchException.getMessage());
        ApiResponse apiResponse = ApiResponse.builder()
                .status(500)
                .timestamp(LocalDateTime.now())
                .message("Error while getting statements. Please try again.")
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //SessionExpiredException
    @ExceptionHandler(SessionExpiredException.class)
    public ResponseEntity<ApiResponse> handleSessionExpiredException(SessionExpiredException sessionExpiredException) {
        log.error("[SessionExpiredException] Session expired with message : {}", sessionExpiredException.getMessage());
        ApiResponse apiResponse = ApiResponse.builder()
                .status(403)
                .timestamp(LocalDateTime.now())
                .message("Session expired. Please login again.")
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.FORBIDDEN);
    }

    //UsernameNotFoundException
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ApiResponse> handleUsernameNotFoundException(UsernameNotFoundException usernameNotFoundException) {
        log.error("[UsernameNotFoundException] Username not found with message : {}", usernameNotFoundException.getMessage());
        ApiResponse apiResponse = ApiResponse.builder()
                .status(404)
                .timestamp(LocalDateTime.now())
                .message("Incorrect username or password. Please try again.")
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
    }

}
