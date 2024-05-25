package bom.proj.homedoc.exception;

public class NotAuthenticatedException extends RuntimeException {
    public NotAuthenticatedException() { super("AUTHENTICATION_REQUIRED"); }

    public NotAuthenticatedException(String message) { super(message); }

    public NotAuthenticatedException(String message, Throwable cause) { super(message, cause); }

    public NotAuthenticatedException(Throwable cause) { super(cause); }
}
