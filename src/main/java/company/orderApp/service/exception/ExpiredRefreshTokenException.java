package company.orderApp.service.exception;

public class ExpiredRefreshTokenException extends RuntimeException {
    public ExpiredRefreshTokenException() {
    }

    public ExpiredRefreshTokenException(String message) {
        super(message);
    }

    public ExpiredRefreshTokenException(String message, Throwable cause) {
        super(message, cause);
    }
}
