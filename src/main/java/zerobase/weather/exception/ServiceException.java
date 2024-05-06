package zerobase.weather.exception;

public abstract class ServiceException extends RuntimeException{

    abstract public int getStatusCode();
    abstract public String getMessage();

    public ServiceException() {
        super();
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }
}
