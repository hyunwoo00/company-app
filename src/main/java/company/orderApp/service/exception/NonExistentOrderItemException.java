package company.orderApp.service.exception;

public class NonExistentOrderItemException extends RuntimeException{
    public NonExistentOrderItemException() {
    }

    public NonExistentOrderItemException(String message) {
        super(message);
    }

    public NonExistentOrderItemException(String message, Throwable cause) {
        super(message, cause);
    }
}
