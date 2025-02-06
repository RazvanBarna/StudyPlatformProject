package ro.platformaStudiu.serviceClass.Exceptii;

public class InvalidExpirationTimeException extends RuntimeException {
    public InvalidExpirationTimeException(String message) {
        super(message);
    }
}
