package sa.com.tree.account.statment.treecodingchallenge.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import sa.com.tree.account.statment.treecodingchallenge.exception.HashingException;
import sa.com.tree.account.statment.treecodingchallenge.exception.InvalidSearchCriteriaException;
import sa.com.tree.account.statment.treecodingchallenge.utils.ApiResponse;

import java.time.LocalDateTime;

@ControllerAdvice
@Slf4j
public class AppExceptionHandler {


    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    protected ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request) {
        log.error("[MethodArgumentNotValidException] DTO properties validation fails with message : {}", ex.getMessage());
        ApiResponse apiResponse = ApiResponse.builder()
                .status(400)
                .timestamp(LocalDateTime.now())
                .message("Invalid search criteria")
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HashingException.class)
    public ResponseEntity<ApiResponse> handleHashingException(HashingException hashingException) {
        log.error("[HashingException] Hashing fails with message : {}", hashingException.getMessage());
        ApiResponse apiResponse = ApiResponse.builder()
                .status(500)
                .timestamp(LocalDateTime.now())
                .message("Internal Server Error")
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(InvalidSearchCriteriaException.class)
    public ResponseEntity<ApiResponse> handleInvalidSearchCriteriaException(InvalidSearchCriteriaException invalidSearchCriteriaException) {
        log.error("[InvalidSearchCriteriaException] Invalid search criteria with message : {}", invalidSearchCriteriaException.getMessage());
        ApiResponse apiResponse = ApiResponse.builder()
                .status(400)
                .timestamp(LocalDateTime.now())
                .message(invalidSearchCriteriaException.getMessage())
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }
}
