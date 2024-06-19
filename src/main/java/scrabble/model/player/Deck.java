package scrabble.model.player;

import scrabble.model.words.Letter;
import java.util.List;

public class Deck {
    private List<Letter> letters;

    public Deck(List<Letter> letters) {
        this.letters = letters;
    }
    public List<Letter> getLetters() {
        return letters;
    }

    public Letter getLetter(Letter letter) {
        return letters.get(letters.indexOf(letter));
    }
    public int getSize() {
        return letters.size();
    }

    public void clear() {
        letters.clear();
    }
    public void addLetter(Letter letter) {
        letters.add(letter);
    }
    public Letter removeLetter(Letter letter) {
        Letter l = getLetter(letter);
        letters.remove(letter);
        return l;
    }

    public boolean isEmpty() {
        return letters.isEmpty();
    }
}