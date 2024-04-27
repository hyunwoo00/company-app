package company.orderApp.service.exception;

public class NonExistentCartException extends RuntimeException{
    public NonExistentCartException() {
    }

    public NonExistentCartException(String message) {
        super(message);
    }

    public NonExistentCartException(String message, Throwable cause) {
        super(message, cause);
    }
}
