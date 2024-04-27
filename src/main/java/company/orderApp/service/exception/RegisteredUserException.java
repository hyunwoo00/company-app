package company.orderApp.service.exception;

public class RegisteredUserException extends RuntimeException {
    public RegisteredUserException() {
    }

    public RegisteredUserException(String message) {
        super(message);
    }

    public RegisteredUserException(String message, Throwable cause) {
        super(message, cause);
    }
}
