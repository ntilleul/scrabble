package scrabble.model.game;

public enum Multiplier {
    DEFAULT("   ", 1), LETTER_2("L2 ", 2), LETTER_3("L3 ", 3), WORD_2("M2 ", 2), WORD_3("M3 ", 3), STAR(" * ", 2);

    private final String string;
    private final int value;

    Multiplier(String string, int value) {
        this.string = string;
        this.value = value;
    }

    public String toString() {
        return string;
    }

    public int getValue() {
        return value;
    }
}
