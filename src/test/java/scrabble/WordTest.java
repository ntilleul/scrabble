package scrabble;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;

import scrabble.model.words.Letter;
import scrabble.model.words.Word;

public class WordTest {

    @Test
    public void testConstructorFromStringNoJoker() {
        String wordString = "word";
        List<Letter> wordList = Arrays.asList(new Letter[] { Letter.W, Letter.O, Letter.R, Letter.D });
        Word word = new Word(wordString);

        assertEquals(wordList, word.getLetters());
    }

    @Test
    public void testConstructorWithJoker() {
        String wordString = "wo?d?";
        List<Character> jokerList = Arrays.asList(new Character[] { 'r', 's' });
        HashMap<Integer, Character> jokers = new HashMap<>();
        Word word;

        jokers.put(2, 'R');
        jokers.put(4, 'S');
        word = new Word(wordString, jokerList);

        assertEquals(jokers, word.getJokerValues());
    }

    @Test
    public void testGetCharAt() {
        Word word = new Word("e?am?le", Arrays.asList(new Character[] { 'X', 'P' }));
        char charAt3 = word.getCharAt(3);
        char m = 'M';
        char charAt1 = word.getCharAt(1);
        char x = 'X';

        assertEquals(m, charAt3);
        assertEquals(x, charAt1);
    }

    @Test
    public void testGetJokerAt() {
        Word word = new Word("e?am?le", Arrays.asList(new Character[] { 'X', 'P' }));
        char jokerValueAt4 = word.getJokerValueAt(4);
        char p = 'P';

        assertEquals(p, jokerValueAt4);
    }

}
