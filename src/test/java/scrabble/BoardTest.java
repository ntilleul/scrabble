package scrabble;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import scrabble.model.game.Board;
import scrabble.model.game.Multiplier;
import scrabble.model.letter.Letter;

public class BoardTest {

	@Test
	public void isEmptyTileNewBoard() {
		Board board = new Board();
		boolean isEmpty = board.getTile(1, 1).isEmpty();
		assertTrue(isEmpty);
	}

	@Test
	public void isTileMultiplierEquals1() {
		Board board = new Board();
		Multiplier multiplier = board.getTile(1, 1).getMultiplier();
		assertEquals(Multiplier.WORD_2, multiplier);
	}

	@Test
	public void isTileNextToLetter() {
		Board board = new Board();

		board.getTile(1, 1).setLetter(Letter.A);

		board.getTile(5, 5).setLetter(Letter.R);
		board.getTile(5, 4).setLetter(Letter.R);

		board.getTile(8, 3).setLetter(Letter.A);
		board.getTile(7, 3).setLetter(Letter.B);

		board.getTile(10, 10).setLetter(Letter.C);
		board.getTile(11, 11).setLetter(Letter.C);
		board.getTile(10, 12).setLetter(Letter.C);

		assertTrue(!board.letterNextToCoord(1, 1));
		assertTrue(!board.letterNextToCoord(0, 0));
		assertTrue(board.letterNextToCoord(5, 5));
		assertTrue(board.letterNextToCoord(8, 3));
		assertTrue(!board.letterNextToCoord(10, 10));
		assertTrue(!board.letterNextToCoord(11, 11));
		assertTrue(!board.letterNextToCoord(10, 12));
	}

}
