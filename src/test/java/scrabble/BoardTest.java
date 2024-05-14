package scrabble;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import scrabble.model.game.Board;

public class BoardTest {
	
	@Test
	public void isEmptyCaseNewBoard() {
		Board board = new Board();
		boolean isEmpty = board.getCase(1,1).isEmpty();
		assertEquals(true, isEmpty);
	}

	@Test
	public void isCaseMultiplierEquals1(){
		Board board = new Board();
		int multiplier = board.getCase(1,1).getMultiplier();
		assertEquals(1, multiplier);
	}


}
