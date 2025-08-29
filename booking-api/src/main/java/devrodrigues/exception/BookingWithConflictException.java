package devrodrigues.exception;

public class BookingWithConflictException extends RuntimeException {
    public BookingWithConflictException(String message) {
        super(message);
    }
}