package scrabble;

import org.junit.Test;
import scrabble.model.letter.Letter;

import static org.junit.Assert.assertEquals;

public class ScrabbleTest {

    @Test
    public void testValue() {
        Letter letterA = new Letter('A', 1);
        assertEquals('A', letterA.value());
        assertEquals('A', letterA.value());
    }

    @Test
    public void testSetValue() {
        Letter letterB = new Letter('B', 3);
        assertEquals('B', letterB.value());
        assertEquals(3, letterB.points());
    }

    @Test
    public void testPoints(){
        Letter letterA = new Letter('A', 1);
        Letter letterB = new Letter('B', 3);

        assertEquals(1, letterA.points());
        assertEquals(3, letterB.points());
    }
}
