package ro.platformaStudiu.serviceClass.Exceptii;

public class InvalidPercentageException extends IllegalArgumentException {
    public InvalidPercentageException(String message) {
        super(message);
    }
}
