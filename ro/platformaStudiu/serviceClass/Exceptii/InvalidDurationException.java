package ro.platformaStudiu.serviceClass.Exceptii;

public class InvalidDurationException extends RuntimeException {
    public InvalidDurationException(String message) {
        super(message);
    }
}
