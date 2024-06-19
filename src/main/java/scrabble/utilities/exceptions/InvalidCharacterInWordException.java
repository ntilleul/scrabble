package scrabble.utilities.exceptions;

public class InvalidCharacterInWordException extends Exception {
    public InvalidCharacterInWordException() {
        super();
    }

    public InvalidCharacterInWordException(String message) {
        super(message);
    }
}
