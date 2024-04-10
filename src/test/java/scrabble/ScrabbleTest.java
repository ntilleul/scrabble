package scrabble;

import org.junit.Test;
import scrabble.model.letter.Letter;

import static org.junit.Assert.assertEquals;

public class ScrabbleTest {

    @Test
    public void testValue() {
        assertEquals(Letter.A.getValue(), 'A');
        assertEquals(Letter.B.getValue(), 'B');
    }

    @Test
    public void testPoints(){
        assertEquals(Letter.A.getPoints(), 1);
        assertEquals(Letter.B.getPoints(), 3);
    }

}
