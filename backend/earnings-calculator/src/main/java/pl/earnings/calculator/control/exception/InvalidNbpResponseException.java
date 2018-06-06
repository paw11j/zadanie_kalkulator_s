package pl.earnings.calculator.control.exception;

public class InvalidNbpResponseException extends RuntimeException {
    public InvalidNbpResponseException(String message) {
        super(message);
    }

    public InvalidNbpResponseException(String message, Throwable cause) {
        super(message, cause);
    }
}
