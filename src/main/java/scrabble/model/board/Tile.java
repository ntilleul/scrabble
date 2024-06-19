package scrabble.model.board;

import scrabble.model.words.Letter;

public class Tile {
    private Multiplier multiplier;
    private Letter letter;
    private char jokerValue;

    public Tile(Multiplier multiplier) {
        this.multiplier = multiplier;
    }

    public Multiplier getMultiplier() {
        return multiplier;
    }
    public Letter getLetter() {
        return letter;
    }
    public char getJokerValue() {
        return jokerValue;
    }

    public void setMultiplier(Multiplier multiplier) {
        this.multiplier = multiplier;
    }
    public void setLetter(Letter letter) {
        this.letter = letter;
    }
    public void setJokerValue(char c) {
        this.jokerValue = c;
    }

    public boolean letterIsJoker() {
        return letter == Letter.JOKER;
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