package ro.platformaStudiu.serviceClass.Exceptii;

public class InvalidYearException extends RuntimeException {
    public InvalidYearException(String message) {
        super(message);
    }
}
