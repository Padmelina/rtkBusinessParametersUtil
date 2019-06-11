package bp.exceptions;

public class UnknownIdentificatorException extends RuntimeException {
    public UnknownIdentificatorException(Throwable cause) {
        super(cause);
    }

    public UnknownIdentificatorException(String message) {
        super(message);
    }
}
