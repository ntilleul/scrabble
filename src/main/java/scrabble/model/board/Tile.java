package scrabble.model.game;

import scrabble.model.letter.Letter;

public class Tile {
    private Multiplier multiplier;
    private Letter letter;
    private char jokerValue;

    public Tile(Multiplier multiplier) {
        this.multiplier = multiplier;
    }

    public boolean letterIsJoker() {
        return letter == Letter.JOKER;
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

    public char getJokerValue() {
        return jokerValue;
    }

    public void setJokerValue(char c) {
        this.jokerValue = c;
    }

    public boolean isEmpty() {
        return letter == null;
    }

    public String toString() {
        if (isEmpty())
            return this.multiplier.toString();
        else if (letterIsJoker())
            return Character.toString(this.jokerValue);
        else
            return this.letter.toString();
    }
}
