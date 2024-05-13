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
        this.empty = true;
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
    	if (this.isEmpty()) {
    		return null;
    	}
        return letter;
    }
    
    public String toString() {
    	if (this.getLetter() != null) {
    		return this.getLetter().toString();
    	}
    	return " ";
    }
}
