package scrabble;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import scrabble.model.game.Board;
import scrabble.model.game.Multiplier;

public class BoardTest {
	
	@Test
	public void isEmptyCaseNewBoard() {
		Board board = new Board();
		boolean isEmpty = board.getCase(1,1).isEmpty();
        assertTrue(isEmpty);
	}

	@Test
	public void isCaseMultiplierEquals1(){
		Board board = new Board();
		Multiplier multiplier = board.getCase(1,1).getMultiplier();
		assertEquals(Multiplier.WORD_2, multiplier);
	}


}
