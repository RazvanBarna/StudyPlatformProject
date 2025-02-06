package ro.platformaStudiu.serviceClass.Exceptii;

public class InvalidDayException extends IllegalArgumentException {
  public InvalidDayException(String message) {
    super(message);
  }
}
