package scrabble;

import org.junit.Test;
import scrabble.model.letter.Letter;

import static org.junit.Assert.assertEquals;

public class ScrabbleTest {

    @Test
    public void testValue() {
        Letter letter = new Letter('A');
        assertEquals('A', letter.value());
    }

    @Test
    public void testSetValue() {
        Letter letter = new Letter('B');
        assertEquals('B', letter.value());
    }
}
