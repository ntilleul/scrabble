package scrabble.model.game;

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

		board[0][0].setMultiplier(Multiplier.WORD_3);
		board[0][7].setMultiplier(Multiplier.WORD_3);
		board[0][14].setMultiplier(Multiplier.WORD_3);

		board[7][0].setMultiplier(Multiplier.WORD_3);
		board[7][7].setMultiplier(Multiplier.WORD_3);
		board[7][14].setMultiplier(Multiplier.WORD_3);

		board[14][0].setMultiplier(Multiplier.WORD_3);
		board[14][7].setMultiplier(Multiplier.WORD_3);
		board[14][14].setMultiplier(Multiplier.WORD_3);

		board[1][1].setMultiplier(Multiplier.WORD_2);
		board[2][2].setMultiplier(Multiplier.WORD_2);
		board[3][3].setMultiplier(Multiplier.WORD_2);
		board[4][4].setMultiplier(Multiplier.WORD_2);

		board[1][13].setMultiplier(Multiplier.WORD_2);
		board[2][12].setMultiplier(Multiplier.WORD_2);
		board[3][11].setMultiplier(Multiplier.WORD_2);
		board[4][10].setMultiplier(Multiplier.WORD_2);

		board[13][1].setMultiplier(Multiplier.WORD_2);
		board[12][2].setMultiplier(Multiplier.WORD_2);
		board[11][3].setMultiplier(Multiplier.WORD_2);
		board[10][4].setMultiplier(Multiplier.WORD_2);

		board[13][13].setMultiplier(Multiplier.WORD_2);
		board[12][12].setMultiplier(Multiplier.WORD_2);
		board[11][11].setMultiplier(Multiplier.WORD_2);
		board[10][10].setMultiplier(Multiplier.WORD_2);

		board[0][3].setMultiplier(Multiplier.LETTER_2);
		board[0][11].setMultiplier(Multiplier.LETTER_2);
		board[14][3].setMultiplier(Multiplier.LETTER_2);
		board[14][11].setMultiplier(Multiplier.LETTER_2);

		board[3][0].setMultiplier(Multiplier.LETTER_2);
		board[11][0].setMultiplier(Multiplier.LETTER_2);
		board[3][14].setMultiplier(Multiplier.LETTER_2);
		board[11][14].setMultiplier(Multiplier.LETTER_2);

		board[7][3].setMultiplier(Multiplier.LETTER_2);
		board[6][2].setMultiplier(Multiplier.LETTER_2);
		board[8][2].setMultiplier(Multiplier.LETTER_2);

		board[7][11].setMultiplier(Multiplier.LETTER_2);
		board[6][12].setMultiplier(Multiplier.LETTER_2);
		board[8][12].setMultiplier(Multiplier.LETTER_2);

		board[3][7].setMultiplier(Multiplier.LETTER_2);
		board[2][6].setMultiplier(Multiplier.LETTER_2);
		board[2][8].setMultiplier(Multiplier.LETTER_2);

		board[11][7].setMultiplier(Multiplier.LETTER_2);
		board[12][6].setMultiplier(Multiplier.LETTER_2);
		board[12][8].setMultiplier(Multiplier.LETTER_2);

		board[6][6].setMultiplier(Multiplier.LETTER_2);
		board[6][8].setMultiplier(Multiplier.LETTER_2);
		board[8][6].setMultiplier(Multiplier.LETTER_2);
		board[8][8].setMultiplier(Multiplier.LETTER_2);

		board[5][5].setMultiplier(Multiplier.LETTER_3);
		board[5][9].setMultiplier(Multiplier.LETTER_3);
		board[9][5].setMultiplier(Multiplier.LETTER_3);
		board[9][9].setMultiplier(Multiplier.LETTER_3);

		board[1][5].setMultiplier(Multiplier.LETTER_3);
		board[1][9].setMultiplier(Multiplier.LETTER_3);
		board[13][5].setMultiplier(Multiplier.LETTER_3);
		board[13][9].setMultiplier(Multiplier.LETTER_3);

		board[5][1].setMultiplier(Multiplier.LETTER_3);
		board[9][1].setMultiplier(Multiplier.LETTER_3);
		board[5][13].setMultiplier(Multiplier.LETTER_3);
		board[9][13].setMultiplier(Multiplier.LETTER_3);

		board[7][7].setMultiplier(Multiplier.STAR);
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

	public boolean letterNextToCoord(int x, int y) {
		return !(getTile(x - 1, y).isEmpty() && getTile(x, y - 1).isEmpty() && getTile(x + 1, y).isEmpty()
				&& getTile(x, y + 1).isEmpty());
	}
}