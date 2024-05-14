package scrabble;

import org.junit.Test;
import scrabble.model.game.Bag;
import scrabble.model.letter.Letter;
import static org.junit.Assert.assertEquals;

public class LetterTest {
	
	@Test
    public void testLetterValue() {
        Letter letterA = Letter.A;
        Letter letterB = Letter.B;

        char valueA = letterA.getValue();
        char valueB = letterB.getValue();

        assertEquals('A', valueA);
        assertEquals('B', valueB);

    }

    @Test
    public void testLetterPoints(){
        Letter letterA = Letter.A;
        Letter letterB = Letter.B;

        int pointsA = letterA.getPoints();
        int pointsB = letterB.getPoints();

        assertEquals(1, pointsA);
        assertEquals(3, pointsB);
    }

    @Test
    public void verificationOfNumberOfLetterInBagInInitialisation(){
        Bag bag = new Bag();
        int numberOfLetterInBag = bag.getLetters().size();
        assertEquals(102, numberOfLetterInBag);
    }
}
