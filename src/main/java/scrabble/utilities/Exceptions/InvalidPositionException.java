package scrabble.utilities.Exceptions;

public class InvalidPositionException extends Exception {
    public InvalidPositionException() {
        super();
    }

    public InvalidPositionException(String message) {
        super(message);
    }
}
