package pl.earnings.calculator.control.exception;

public class InvalidCountryCodeException extends RuntimeException {
    public InvalidCountryCodeException(String message) {
        super(message);
    }
}
