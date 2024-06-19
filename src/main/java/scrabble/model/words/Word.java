package scrabble.model.words;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.HashMap;

public class Word {
    private List<Letter> letters;
    private HashMap<Integer, Character> jokerValues;

    public Word(List<Letter> letters, HashMap<Integer, Character> jokerValues) {
        this.letters = letters;
        this.jokerValues = jokerValues;
    }
    public Word(String word, List<Character> jokerValues) {
        this.letters = new ArrayList<Letter>();
        this.jokerValues = new HashMap<>();
        int iJoker = 0;
        for (int i = 0; i < word.length(); i++) {
            char letterChar = word.charAt(i);
            Letter letter;
            if (letterChar == '?') {
                letter = Letter.JOKER;
                this.jokerValues.put(i, Character.toUpperCase(jokerValues.get(iJoker)));
                iJoker++;
            } else {
                letter = Letter.valueOf(Character.toString(letterChar).toUpperCase());
            }
            letters.add(letter);

        }
    }
    public Word(List<Letter> letters) {
        this(letters, new HashMap<Integer, Character>());
    }
    public Word(String word) {
        this(word, new ArrayList<Character>());
    }

    public List<Letter> getLetters() {
        return letters;
    }
    public HashMap<Integer, Character> getJokerValues() {
        return jokerValues;
    }
    public int getSize() {
        return letters.size();
    }
    public Letter getLetterAt(int i) {
        return letters.get(i);
    }
    public Character getJokerValueAt(int i) {
        return jokerValues.get(i);
    }
    public Character getCharAt(int i) {
        char c;
        if (letters.get(i) == Letter.JOKER)
            c = jokerValues.get(i);
        else
            c = letters.get(i).getValue();
        return c;
    }
}