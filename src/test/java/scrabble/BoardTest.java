package scrabble;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import scrabble.model.game.Board;

public class BoardTest {
	
	@Test
	public void isEmptyCaseNewBoard() {
		Board board = new Board();
		assertEquals(true, board.getCase(1,2).isEmpty());
	}
}
