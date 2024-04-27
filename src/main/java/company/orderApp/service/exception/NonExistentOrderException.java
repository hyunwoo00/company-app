package company.orderApp.service.exception;

public class NonExistentOrderException extends RuntimeException{
    public NonExistentOrderException() {
    }

    public NonExistentOrderException(String message) {
        super(message);
    }

    public NonExistentOrderException(String message, Throwable cause) {
        super(message, cause);
    }
}
