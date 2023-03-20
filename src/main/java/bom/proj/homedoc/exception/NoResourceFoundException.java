package bom.proj.homedoc.exception;

public class NoResourceFoundException  extends RuntimeException {
    public NoResourceFoundException() { super("NO_RESOURCE_FOUND"); }

    public NoResourceFoundException(String message) { super(message); }

    public NoResourceFoundException(String message, Throwable cause) { super(message, cause); }

    public NoResourceFoundException(Throwable cause) { super(cause); }
}
