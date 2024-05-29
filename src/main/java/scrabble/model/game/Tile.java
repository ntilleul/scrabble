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
}
