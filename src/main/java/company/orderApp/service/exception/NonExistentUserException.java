package company.orderApp.service.exception;

public class NonExistentUserException extends RuntimeException{
    public NonExistentUserException() {
    }

    public NonExistentUserException(String message) {
        super(message);
    }

    public NonExistentUserException(String message, Throwable cause) {
        super(message, cause);
    }
}
