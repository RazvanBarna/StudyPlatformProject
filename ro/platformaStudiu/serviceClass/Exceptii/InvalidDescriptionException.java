package ro.platformaStudiu.serviceClass.Exceptii;

public class InvalidDescriptionException extends RuntimeException {
    public InvalidDescriptionException(String message) {
        super(message);
    }
}
