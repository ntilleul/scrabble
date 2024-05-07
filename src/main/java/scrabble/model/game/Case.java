package scrabble.model.game;

import scrabble.model.letter.Letter;

public class Case {
    private int x;
    private int y;
    private boolean empty;
    private int multiplier;
    private Letter letter;

    public Case(int x, int y, int multiplier) {
        this.x = x;
        this.y = y;
        this.multiplier = multiplier;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isEmpty() {
        return empty;
    }

    public int getMultiplier() {
        return multiplier;
    }

    public Letter getLetter() {
        return letter;
    }
}
