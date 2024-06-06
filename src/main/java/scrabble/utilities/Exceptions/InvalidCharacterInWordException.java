package scrabble.utilities.Exceptions;

public class InvalidCharacterInWordException extends Exception {
    public InvalidCharacterInWordException() {
        super();
    }

    public InvalidCharacterInWordException(String message) {
        super(message);
    }
}
