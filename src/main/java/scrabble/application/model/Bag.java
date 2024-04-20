package scrabble.application.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import scrabble.model.letter.Letter;

public class Bag {

    private List<Letter> letters;

    public Bag() {
        this.letters = new ArrayList<>();
        initializeLetters();
    }

    private void initializeLetters() {
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

    public List<Letter> getSevenLetters() {
        List<Letter> sevenLetters = new ArrayList<>();
        int count = 0;
        for (int i = 0; i < letters.size(); i++) {
            Letter letter = letters.get(i);
            if (letter.getNumber() > 0) {
                sevenLetters.add(letter);
                letter.decrementNumber();
                letters.remove(i);
                count++;
                i--;
                if (count >= 7) {
                    break;
                }
            }
        }
        return sevenLetters;
    }

    public List<Letter> getNLetters(int n ){
        List<Letter> nLetters = new ArrayList<>();
        int count = 0;
        for (int i = 0; i < n; i++) {
            Letter letter = letters.get(i);
            if (letter.getNumber() > 0) {
                nLetters.add(letter);
                letter.decrementNumber();
                letters.remove(i);
                count++;
                i--;
                if (count >= 7) {
                    break;
                }
            }
        }
        return nLetters;
    }


}
