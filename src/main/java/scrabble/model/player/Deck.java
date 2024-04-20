package scrabble.model.player;

import scrabble.model.letter.*;

import java.util.List;

public class Deck {
    private List<Letter> letters;

    public Deck(List<Letter> letters){

        this.letters = letters;
    }

    public List<Letter> getLetters(){
        return letters;
    }

    public void addLetter(Letter letter){

        letters.add(letter);
    }

    public void removeLetter(int index){
        letters.remove(index);
    }

    public int size() {
        return letters.size();
    }
}
