package scrabble.model.game;

import scrabble.model.letter.Letter;

public class Tile {
    private Multiplier multiplier;
    private Letter letter;

    public Tile(Multiplier multiplier) {
        this.multiplier = multiplier;
    }

    public Multiplier getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(Multiplier multiplier) {
        this.multiplier = multiplier;
    }

    public Letter getLetter() {
        return letter;
    }

    public void setLetter(Letter letter) {
        this.letter = letter;
    }

    public boolean isEmpty() {
        return letter == null;
    }

    public String toString() {
        if (isEmpty())
            return this.multiplier.toString();
        else
            return " " + this.letter.toString() + " ";
    }

    public int getPoint() {
        int initPoint = 0;
        if (!isEmpty())
            initPoint = getLetter().getPoints();
        if (multiplier.equals(Multiplier.LETTER_2) || multiplier.equals(Multiplier.LETTER_3))
            initPoint *= multiplier.getValue();
        return initPoint;
    }

    public int getMultiplierValue() {
        return multiplier.getValue();
    }
}
