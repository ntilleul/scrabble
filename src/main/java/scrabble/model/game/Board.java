package scrabble.model.game;

import java.util.List;

import scrabble.model.letter.Letter;

public class Board {

	public Tile[][] board;
	private final int boardSize = 15;

	public Board() {
		board = new Tile[boardSize][boardSize];
		for (int i = 0; i < boardSize; i++) {
			for (int j = 0; j < boardSize; j++) {
				board[i][j] = new Tile(Multiplier.DEFAULT);
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
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				Multiplier multiplier = board[i][j].getMultiplier();
				if (multiplier != null && multiplier != Multiplier.DEFAULT) {
					board[j][i].setMultiplier(multiplier);
					board[size - 1 - i][j].setMultiplier(multiplier);
					board[j][size - 1 - i].setMultiplier(multiplier);
					board[i][size - 1 - j].setMultiplier(multiplier);
					board[size - 1 - j][i].setMultiplier(multiplier);
					board[size - 1 - i][size - 1 - j].setMultiplier(multiplier);
					board[size - 1 - j][size - 1 - i].setMultiplier(multiplier);
				}
			}
		}
	}

	public Tile getTile(int i, int j) {
		return this.board[i][j];
	}

	public void print() {
		System.out.println("  | A | B | C | D | E | F | G | H | I | J | K | L | M | N | O |");
		for (int i = 0; i < boardSize; i++) {
			if (i <= getMiddleSize()) {
				System.out.println("──┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┤");
				System.out.println("0" + (i + 1) + "|" + this.board[i][0] + "|" + this.board[i][1] + "|"
						+ this.board[i][2] + "|" + this.board[i][3] + "|" + this.board[i][4] + "|" + this.board[i][5]
						+ "|" + this.board[i][6] + "|" + this.board[i][7] + "|" + this.board[i][8] + "|"
						+ this.board[i][9] + "|" + this.board[i][10] + "|" + this.board[i][11] + "|" + this.board[i][12]
						+ "|" + this.board[i][13] + "|" + this.board[i][14] + "|");
			} else {
				System.out.println("──┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┤");
				System.out.println((i + 1) + "|" + this.board[i][0] + "|" + this.board[i][1] + "|" + this.board[i][2]
						+ "|" + this.board[i][3] + "|" + this.board[i][4] + "|" + this.board[i][5] + "|"
						+ this.board[i][6] + "|" + this.board[i][7] + "|" + this.board[i][8] + "|" + this.board[i][9]
						+ "|" + this.board[i][10] + "|" + this.board[i][11] + "|" + this.board[i][12] + "|"
						+ this.board[i][13] + "|" + this.board[i][14] + "|");
			}
		}
		System.out.println("──┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┘");
	}

	public int getSize() {
		return this.boardSize;
	}

	public int getMiddleSize() {
		return boardSize / 2 + 1;
	}

	public void placeLetter(Letter letter, int x, int y) {
		getTile(x, y).setLetter(letter);
	}

	public void placeWord(List<Letter> word, Direction direction, int x, int y) {
		for (int i = 0; i < word.size(); i++) {
			if (direction == Direction.HORIZONTAL)
				placeLetter(word.get(i), x, y + i);
			else
				placeLetter(word.get(i), x + i, y);
		}
	}

	public boolean letterNextToCoord(int x, int y) {
		Boolean letterUp;
		Boolean letterDown;
		Boolean letterRight;
		Boolean letterLeft;

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
}