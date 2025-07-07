package user_service.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class NotOkResponseException extends RuntimeException {
    private final HttpStatus httpStatus;
    private final int errorCode;

    public NotOkResponseException(String message, HttpStatus httpStatus, int errorCode) {
        super(message);
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
    }

    public NotOkResponseException(String message, Throwable cause, HttpStatus httpStatus, int errorCode) {
        super(message, cause);
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
    }

    public NotOkResponseException(HttpStatus httpStatus, String message, int errorCode, Throwable cause) {
        super(message, cause);
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
    }
}