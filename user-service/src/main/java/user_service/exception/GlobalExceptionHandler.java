package user_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotOkResponseException.class)
    public ResponseEntity<?> handleNotOkResponseException(NotOkResponseException ex) {
        return ResponseEntity
                .status(ex.getHttpStatus())
                .body(new ErrorResponse(
                        ex.getErrorCode(),
                        ex.getHttpStatus().name(),
                        ex.getMessage()
                ));
    }

    public static class ErrorResponse {
        private final int statusCode;
        private final String status;
        private final String message;

        public ErrorResponse(int statusCode, String status, String message) {
            this.statusCode = statusCode;
            this.status = status;
            this.message = message;
        }

        public int getStatusCode() { return statusCode; }
        public String getStatus() { return status; }
        public String getMessage() { return message; }
    }

    public ResponseEntity<?> handleNonUniqueResult(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(400, "BAD_REQUEST", "Duplicate email found. Please contact support."));
    }
}