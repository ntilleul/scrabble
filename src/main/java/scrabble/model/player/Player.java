package scrabble.model.player;

import java.util.List;

import scrabble.model.letter.Letter;

public class Player {
    private int point;
    private final String name;
    private Deck deck;

    public Player(String name, List<Letter> deal) {
        this.name = name;
        this.deck = new Deck(deal);
        point = 0;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public void addPoint(int amount) {
        point += amount;
    }

    public Letter getRid(int index) {
        return deck.removeLetter(index);
    }

    public void draw(List<Letter> letters) {
        for (Letter letter : letters) {
            deck.addLetter(letter);
        }
    }

    public String getName() {
        return name;
    }

    public Deck getDeck() {
        return deck;
    }

    public int getPoint() {
        return point;
    }

    public boolean isDeckEmpty() {
        return deck.isEmpty();
    }

    public int getDeckSize() {
        return deck.size();
    }

    public List<Letter> getLetters() {
        return deck.getLetters();
    }
}
