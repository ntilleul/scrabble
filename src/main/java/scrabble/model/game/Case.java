package scrabble.model.game;

import scrabble.model.letter.Letter;

public class Case {
    private int x;
    private int y;
    private Multiplier multiplier;
    private Letter letter;

    public Case(int x, int y, Multiplier multiplier) {
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
