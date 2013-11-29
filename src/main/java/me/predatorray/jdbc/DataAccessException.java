package me.predatorray.jdbc;

public class DataAccessException extends RuntimeException {

    public DataAccessException() {
        super("data access exception occurred");
    }

    public DataAccessException(Throwable cause) {
        super(cause);
    }

    public DataAccessException(String message) {
        super(message);
    }

    public DataAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}
