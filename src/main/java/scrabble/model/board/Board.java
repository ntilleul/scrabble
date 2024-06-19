package scrabble.model.board;

import java.util.List;
import scrabble.model.words.Letter;
import scrabble.model.words.Word;
import scrabble.utilities.exceptions.InvalidPositionException;

public class Board {

	public Tile[][] board;
	private final static int boardSize = 15;

	public Board() {
		board = new Tile[boardSize][boardSize];
		for (int y = 0; y < boardSize; y++) {
			for (int x = 0; x < boardSize; x++) {
				board[x][y] = new Tile(Multiplier.DEFAULT);
			}
		}

		board[7][7].setMultiplier(Multiplier.STAR);
		initializeMultipliers();
		completeSymmetry(board);

	}

	private void initializeMultipliers() {
		board[0][0].setMultiplier(Multiplier.WORD_3);
		board[0][3].setMultiplier(Multiplier.LETTER_2);
		board[0][7].setMultiplier(Multiplier.WORD_3);
		board[1][1].setMultiplier(Multiplier.WORD_2);
		board[2][2].setMultiplier(Multiplier.WORD_2);
		board[3][3].setMultiplier(Multiplier.WORD_2);
		board[4][4].setMultiplier(Multiplier.WORD_2);
		board[5][5].setMultiplier(Multiplier.LETTER_3);
		board[6][6].setMultiplier(Multiplier.LETTER_2);
		board[1][5].setMultiplier(Multiplier.LETTER_3);
		board[2][6].setMultiplier(Multiplier.LETTER_2);
		board[2][8].setMultiplier(Multiplier.LETTER_2);
		board[3][0].setMultiplier(Multiplier.LETTER_2);
		board[5][1].setMultiplier(Multiplier.LETTER_3);
		board[7][3].setMultiplier(Multiplier.LETTER_2);
		board[6][2].setMultiplier(Multiplier.LETTER_2);
		board[7][0].setMultiplier(Multiplier.WORD_3);
		board[3][7].setMultiplier(Multiplier.LETTER_2);
	}
	private static void completeSymmetry(Tile[][] board) {
		int size = board.length;
		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {
				Multiplier multiplier = board[x][y].getMultiplier();
				if (multiplier != null && multiplier != Multiplier.DEFAULT) {
					board[y][x].setMultiplier(multiplier);
					board[size - 1 - x][y].setMultiplier(multiplier);
					board[y][size - 1 - x].setMultiplier(multiplier);
					board[x][size - 1 - y].setMultiplier(multiplier);
					board[size - 1 - y][x].setMultiplier(multiplier);
					board[size - 1 - x][size - 1 - y].setMultiplier(multiplier);
					board[size - 1 - y][size - 1 - x].setMultiplier(multiplier);
				}
			}
		}
	}

	public Tile getTile(int x, int y) {
		return this.board[x][y];
	}
	public static int getSize() {
		return boardSize;
	}
	public int getMiddleSize() {
		return boardSize / 2 + 1;
	}

	public void placeLetter(Letter letter, int x, int y, char jokerValue) {
		getTile(x, y).setLetter(letter);
		getTile(x, y).setJokerValue(jokerValue);
	}
	public void placeLetter(Letter letter, int x, int y) {
		getTile(x, y).setLetter(letter);
	}

	public boolean letterNextToCoord(int x, int y) {
		boolean letterUp;
		boolean letterDown;
		boolean letterRight;
		boolean letterLeft;

		try {
			letterUp = !getTile(x, y - 1).isEmpty();
		} catch (ArrayIndexOutOfBoundsException e) {
			letterUp = false;
		}

		try {
			letterDown = !getTile(x, y + 1).isEmpty();
		} catch (ArrayIndexOutOfBoundsException e) {
			letterDown = false;
		}

		try {
			letterRight = !getTile(x + 1, y).isEmpty();
		} catch (ArrayIndexOutOfBoundsException e) {
			letterRight = false;
		}

		try {
			letterLeft = !getTile(x - 1, y).isEmpty();
		} catch (ArrayIndexOutOfBoundsException e) {
			letterLeft = false;
		}

		return (letterDown || letterUp || letterLeft || letterRight);
	}
	public boolean isLetterOnStar(int coordinates1, int coordinates2, int position) {
		return (coordinates1 + position == getMiddleSize()) && (coordinates2 == getMiddleSize());
	}

	public boolean doesWordShouldNotCoverMiddleStar(Word word, int coordinates) {
		return (coordinates + word.getSize() < getMiddleSize() -1) || (coordinates > getMiddleSize() -1);
	}

	public boolean firstWordIsOnStar(Word word, int x, int y, Direction direction) {
		if (direction.equals(Direction.HORIZONTAL)) {
			if (doesWordShouldNotCoverMiddleStar(word, x)) {
				return false;
			}
			for (int i = 0; i < word.getSize(); i++) {
				if (isLetterOnStar(x, y, i)) {
					return true;
				}
			}
		} else {
			if (doesWordShouldNotCoverMiddleStar(word, y)) {
				return false;
			}
			for (int i = 0; i < word.getSize(); i++) {
				if (isLetterOnStar(y, x, i)) {
					return true;
				}
			}
		}
		return false;
	}
	public boolean playedWordIsConnectedToTheRest(Word word, int coordinatesX, int coordinatesY, Direction direction) {
		int i;
		if (direction.equals(Direction.HORIZONTAL)) {
			i = coordinatesX;
			do {
				if (letterNextToCoord(i, coordinatesY))
					return true;
				i++;
			} while (i < (word.getSize() + coordinatesX));
		} else {
			i = coordinatesY;
			do {
				if (letterNextToCoord(coordinatesX, i))
					return true;
				i++;
			} while (i < (word.getSize() + coordinatesY));
		}
		return false;
	}
	public boolean verifyLetterIsOutOfBoard(Word word, Direction direction, int x, int y) throws InvalidPositionException {
		for (int i = 0; i < word.getSize(); i++) {
			if (direction.equals(Direction.HORIZONTAL)) {
				try {
					while (!getTile(x + i, y).isEmpty()) {
						x++;
					}
				} catch (ArrayIndexOutOfBoundsException e) {
					return true;
				}
			} else {
				try {
					while (!getTile(x, y + i).isEmpty()) {
						y++;
					}
				} catch (ArrayIndexOutOfBoundsException e) {
					return true;
				}
			}
		}
		return false;
	}
	public void placeWord(Word word, Direction direction, int x, int y) {
		System.out.println(word.getJokerValues());
		for (int i = 0; i < word.getSize(); i++) {
			if (direction.equals(Direction.HORIZONTAL)) {
				while (!getTile(x + i , y).isEmpty()) {
					x++;
				}
				Letter letter = word.getLetterAt(i);
				if (letter == Letter.JOKER)
					placeLetter(word.getLetterAt(i), x + i, y, word.getJokerValueAt(i));
				else
					placeLetter(word.getLetterAt(i), x + i, y);
			} else {
				while (!getTile(x, y + i).isEmpty()) {
					y++;
				}
				Letter letter = word.getLetterAt(i);
				if (letter == Letter.JOKER)
					placeLetter(word.getLetterAt(i), x, y + i, word.getJokerValueAt(i));
				else
					placeLetter(word.getLetterAt(i), x, y + i);
			}
		}
	}
}