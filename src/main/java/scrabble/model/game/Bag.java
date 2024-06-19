package scrabble.model.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import scrabble.model.words.Letter;

public class Bag {

    private List<Letter> letters;

    public Bag() {
        this.letters = new ArrayList<>();
        initializeLetters();
    }
    public void initializeLetters() {
        for (Letter letter : Letter.values()) {
            int numberOfLetters = letter.getNumber();
            for (int i = 0; i < numberOfLetters; i++) {
                addLetters(letter);
            }
        }
        shuffleLetters();
    }
    public void addLetters(Letter letter) {
        letters.add(letter);
    }
    public void shuffleLetters() {
        Collections.shuffle(letters);
    }

    public List<Letter> getLetters() {
        return letters;
    }
    public List<Letter> getNLetters(int n) {
        List<Letter> nLetters = new ArrayList<>();
        int count = 0;
        int i = 0;
        while ((i < letters.size()) && count < n) {
            Letter letter = letters.get(i);
            if (letter.getNumber() > 0) {
                nLetters.add(letter);
                letter.decrementNumber();
                letters.remove(i);
                count++;
                i--;
            }
            i++;
        }
        return nLetters;
    }
}