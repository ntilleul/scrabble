package scrabble.model.player;

import scrabble.model.letter.*;

import java.util.List;

public class Deck {
    private List<Letter> letters;

    public Deck(List<Letter> letters) {

        this.letters = letters;
    }

    public List<Letter> getLetters() {
        return letters;
    }

    public void addLetter(Letter letter) {
        letters.add(letter);
    }

    public void clear() {
        letters.clear();
    }

    public Letter getLetter(Letter letter) {
        return letters.get(letters.indexOf(letter));
    }

    public Letter removeLetter(Letter letter) {
        Letter l = getLetter(letter);
        letters.remove(letter);
        return l;
    }

    public int size() {
        return letters.size();
    }

    public boolean isEmpty() {
        return letters.isEmpty();
    }
}
