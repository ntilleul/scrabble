package scrabble.model.board;

public enum Multiplier {

    DEFAULT("", "white"), LETTER_2("L2", "lightblue"), LETTER_3("L3", "blue"),
    WORD_2("M2", "pink"), WORD_3("M3", "red"), STAR("*", "pink");

    private final String string;
    private final String color;

    Multiplier(String string, String color) {
        this.string = string;
        this.color = color;
    }

    public String toString() {
        return string;
    }
    public String getColor() {
        return color;
    }
}