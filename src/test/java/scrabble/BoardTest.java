package scrabble;

import org.junit.Test;

import scrabble.model.game.Board;
import scrabble.model.game.Direction;
import scrabble.model.game.Game;
import scrabble.model.game.Multiplier;
import scrabble.model.letter.Letter;
import scrabble.model.letter.Word;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertFalse;

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
		Word word = new Word(Arrays.asList(Letter.A, Letter.B, Letter.C));
		board.placeWord(word, Direction.HORIZONTAL, 7, 7);

		boolean result = board.playedWordIsConnectedToTheRest(word.getLetters(), 7, 8, Direction.HORIZONTAL);

		assertTrue(result);
	}

	@Test
	public void playedWordIsConnectedToTheRestShouldReturnFalseWhenWordIsNotConnected() {
		Board board = new Board();
		List<Letter> word = Arrays.asList(Letter.A, Letter.B, Letter.C);

		boolean result = board.firstWordIsOnStar(word, 7, 7, Direction.HORIZONTAL);

		assertFalse(result);
	}
}
