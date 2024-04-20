package scrabble.model.player;

import java.util.List;

import scrabble.model.letter.Letter;

public class Player {
    private int point;
    private final String name;
    private Deck deck;

    public Player(String name, List<Letter> deal){
        this.name = name;
        this.deck = new Deck(deal);
        point = 0;
    }

    public void setPoint(int p){

        point = p;
    }

    public void addPoint(int amount){

        point += amount;
    }

    public void getRid(int index){
        deck.removeLetter(index);
    }

    public void draw(List<Letter> letters){
        for (Letter letter : letters) {
            deck.addLetter(letter);
        }
    }

    public String getName(){

        return name;
    }

    public int getPoint(){

        return point;
    }

    public Deck getDeck(){
        return deck;
    }
}
