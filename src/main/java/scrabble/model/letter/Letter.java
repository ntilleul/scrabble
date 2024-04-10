package scrabble.model.letter;

public class Letter {
    private final char value;
    private final int points;


    public Letter(char value, int points) {
        this.value = value;
        this.points = points;
    }

    public char value(){
        return this.value;
    }

    public int points() {
        return this.points;
    }
}
