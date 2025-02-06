package ro.platformaStudiu.serviceClass.Exceptii;

public class InvalidParticipantNumberException extends IllegalArgumentException {
        public InvalidParticipantNumberException(String message) {
        super(message);
    }
}