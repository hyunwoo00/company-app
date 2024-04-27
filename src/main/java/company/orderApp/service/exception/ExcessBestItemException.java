package company.orderApp.service.exception;

public class ExcessBestItemException extends RuntimeException{
    public ExcessBestItemException() {
    }

    public ExcessBestItemException(String message) {
        super(message);
    }

    public ExcessBestItemException(String message, Throwable cause) {
        super(message, cause);
    }
}
