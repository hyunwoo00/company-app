package company.orderApp.service.exception;

public class NonExistentDeliveryException extends RuntimeException{
    public NonExistentDeliveryException() {
    }

    public NonExistentDeliveryException(String message) {
        super(message);
    }

    public NonExistentDeliveryException(String message, Throwable cause) {
        super(message, cause);
    }
}
