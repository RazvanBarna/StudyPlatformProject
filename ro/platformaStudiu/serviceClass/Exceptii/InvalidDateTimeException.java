package ro.platformaStudiu.serviceClass.Exceptii;

public class InvalidDateTimeException extends RuntimeException {
    public InvalidDateTimeException(String message) {
        super(message);
    }
}
