package company.orderApp.service.exception;

public class NonExistentItemException extends RuntimeException{
    public NonExistentItemException() {
    }

    public NonExistentItemException(String message) {
        super(message);
    }

    public NonExistentItemException(String message, Throwable cause) {
        super(message, cause);
    }
}
