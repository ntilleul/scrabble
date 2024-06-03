package scrabble;

import org.junit.Test;
import scrabble.model.game.Bag;
import scrabble.model.letter.Letter;

import java.util.List;

import static org.junit.Assert.*;

public class BagTest {

    @Test
    public void verificationOfBagAfterShuffle() {
        Bag bag = new Bag();
        Bag oldBag = new Bag();
        oldBag.getLetters().addAll(bag.getLetters());
        bag.shuffleLetters();
        assertNotEquals(oldBag, bag);
    }

    @Test
    public void verificationOfNLetters() {
        Bag bag = new Bag();
        int n = 5;
        bag.getNLetters(n);
        int numberOfLetterInBagAfterNLetters = bag.getLetters().size();
        assertEquals(97, numberOfLetterInBagAfterNLetters);
    }

    @Test
    public void bagShouldReturnNullWhenDrawingFromEmptyBag() {
        Bag bag6 = new Bag();
        bag6.getNLetters(102);
        List<Letter> drawnLetters = bag6.getNLetters(1);
        assertTrue(drawnLetters.isEmpty());
    }

    @Test
    public void verificationNumberOfLettersInBag() {
        Bag bag = new Bag();
        int numberOfLettersInBag = bag.getLetters().size();
        assertEquals(102, numberOfLettersInBag);
    }
}
