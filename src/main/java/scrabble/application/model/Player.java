package scrabble.application.model;

public class Player {
    private final int point;
    private final String name;

    public Player(String name){
        this.name = name;
        point = 0;
    }

    public int point(){
        return point;
    }

    public String name(){
        return name;
    }
}
