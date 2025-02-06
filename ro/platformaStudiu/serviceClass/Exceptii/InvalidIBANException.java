package ro.platformaStudiu.serviceClass.Exceptii;

public class InvalidIBANException extends RuntimeException {
    public InvalidIBANException(String message) {
        super(message);
    }
}
