package company.orderApp.service.exception;

public class NonExistentRefreshTokenException extends RuntimeException {
    public NonExistentRefreshTokenException() {
    }

    public NonExistentRefreshTokenException(String message) {
        super(message);
    }

    public NonExistentRefreshTokenException(String message, Throwable cause) {
        super(message, cause);
    }
}
