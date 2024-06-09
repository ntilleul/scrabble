package scrabble;

import org.junit.Test;

import scrabble.model.game.Board;
import scrabble.model.game.Direction;
import scrabble.model.game.Game;
import scrabble.model.game.Multiplier;
import scrabble.model.letter.Letter;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

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

        assertFalse(board.letterNextToCoord(1, 1));
        assertFalse(board.letterNextToCoord(0, 0));
		assertTrue(board.letterNextToCoord(5, 5));
		assertTrue(board.letterNextToCoord(8, 3));
        assertFalse(board.letterNextToCoord(10, 10));
        assertFalse(board.letterNextToCoord(11, 11));
        assertFalse(board.letterNextToCoord(10, 12));
	}

	@Test
	public void boardShouldPlaceLetterCorrectly() {
		Board board = new Board();
		board.placeLetter(Letter.A, 1, 1);
		assertEquals(Letter.A, board.getTile(1, 1).getLetter());
	}

	@Test
	public void boardShouldPlaceWordCorrectlyInHorizontalDirection() {
		Board board = new Board();
		List<Letter> word = Arrays.asList(Letter.H, Letter.E, Letter.L, Letter.L, Letter.O);
		board.placeWord(word, Direction.HORIZONTAL, 7, 7);
		for (int i = 0; i < word.size(); i++) {
			assertEquals(word.get(i), board.getTile(7, 7 + i).getLetter());
		}
	}

	@Test
	public void boardShouldPlaceWordCorrectlyInVerticalDirection() {
		Board board = new Board();
		List<Letter> word = Arrays.asList(Letter.H, Letter.E, Letter.L, Letter.L, Letter.O);
		board.placeWord(word, Direction.VERTICAL, 7, 7);
		for (int i = 0; i < word.size(); i++) {
			assertEquals(word.get(i), board.getTile(7 + i, 7).getLetter());
		}
	}

	@Test
	public void boardShouldInitializeWithCorrectSize() {
		Board board = new Board();
		assertEquals(15, board.getSize());
	}

	@Test
	public void boardShouldInitializeWithCorrectMiddleSize() {
		Board board = new Board();
		assertEquals(8, board.getMiddleSize());
	}

	@Test
	public void firstWordIsOnStarShouldReturnTrueWhenWordIsOnStar() {
		Board board = new Board();
		List<Letter> word = Arrays.asList(Letter.A, Letter.B, Letter.C);

		boolean result = board.firstWordIsOnStar(word, 7, 7, Direction.HORIZONTAL);

		assertTrue(result);
	}

	@Test
	public void firstWordIsOnStarShouldReturnFalseWhenWordIsNotOnStar() {
		Board board = new Board();
		List<Letter> word = Arrays.asList(Letter.A, Letter.B, Letter.C);

		boolean result = board.firstWordIsOnStar(word, 6, 6, Direction.HORIZONTAL);

		assertFalse(result);
	}

	@Test
	public void playedWordIsConnectedToTheRestShouldReturnTrueWhenWordIsConnected() {
		Board board = new Board();
		List<Letter> word = Arrays.asList(Letter.A, Letter.B, Letter.C);
		board.placeWord(word, Direction.HORIZONTAL, 7, 7);

		boolean result = board.playedWordIsConnectedToTheRest(word, 7, 8, Direction.HORIZONTAL);

		assertTrue(result);
	}

	@Test
	public void playedWordIsConnectedToTheRestShouldReturnFalseWhenWordIsNotConnected() {
		Board board = new Board();
		List<Letter> word = Arrays.asList(Letter.A, Letter.B, Letter.C);

		boolean result = board.playedWordIsConnectedToTheRest(word, 7, 7, Direction.HORIZONTAL);

		assertFalse(result);
	}
}
