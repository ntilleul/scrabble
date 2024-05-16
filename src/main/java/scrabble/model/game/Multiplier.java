package scrabble.model.game;

public enum Multiplier {
    DEFAULT("   "), LETTER_2("L2 "), LETTER_3("L3 "), WORD_2("M2 "), WORD_3("M3 "), STAR(" * ");

    private final String string;

    Multiplier(String string) {
        this.string = string;
    }

    public String toString() {
        return string;
    }
}
