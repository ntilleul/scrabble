package scrabble.model.game;

public class Board {
	
	public Case[][] board ;
	
	public Board() {
		board = new Case[15][15];
		for (int i=0; i<15; i++) {
			for (int j=0; j<15; j++) {
				board[i][j]=new Case(i, j, 1);
			}
		}
	}
	
	public Case getCase(int i, int j) {
		return this.board[i][j];
	}
	
	public void print() {
		System.out.println("  |A|B|C|D|E|F|G|H|I|J|K|L|M|N|O|");
		for (int i=0; i<15;i++) {
			if (i < 9) {
				if (i != 7) {
					System.out.println("──┼─┼─┼─┼─┼─┼─┼─┼─┼─┼─┼─┼─┼─┼─┼─┤");
					System.out.println("0"+(i+1)+"|"+this.board[i][0]+"|"+this.board[i][1]+"|"+this.board[i][2]+"|"+this.board[i][3]+"|"+this.board[i][4]+"|"+this.board[i][5]+"|"+this.board[i][6]+"|"+this.board[i][7]+"|"+this.board[i][8]+"|"+this.board[i][9]+"|"+this.board[i][10]+"|"+this.board[i][11]+"|"+this.board[i][12]+"|"+this.board[i][13]+"|"+this.board[i][14]+"|");
				}
				else {
					if (this.board[i][7].toString()==" ") {
						System.out.println("──┼─┼─┼─┼─┼─┼─┼─┼─┼─┼─┼─┼─┼─┼─┼─┤");
						System.out.println("0"+(i+1)+"|"+this.board[i][0]+"|"+this.board[i][1]+"|"+this.board[i][2]+"|"+this.board[i][3]+"|"+this.board[i][4]+"|"+this.board[i][5]+"|"+this.board[i][6]+"|*|"+this.board[i][8]+"|"+this.board[i][9]+"|"+this.board[i][10]+"|"+this.board[i][11]+"|"+this.board[i][12]+"|"+this.board[i][13]+"|"+this.board[i][14]+"|");
					}
					else {
						System.out.println("──┼─┼─┼─┼─┼─┼─┼─┼─┼─┼─┼─┼─┼─┼─┼─┤");
						System.out.println("0"+(i+1)+"|"+this.board[i][0]+"|"+this.board[i][1]+"|"+this.board[i][2]+"|"+this.board[i][3]+"|"+this.board[i][4]+"|"+this.board[i][5]+"|"+this.board[i][6]+"|"+this.board[i][7]+"|"+this.board[i][8]+"|"+this.board[i][9]+"|"+this.board[i][10]+"|"+this.board[i][11]+"|"+this.board[i][12]+"|"+this.board[i][13]+"|"+this.board[i][14]+"|");
					}
				}
			}
			else {
				System.out.println("──┼─┼─┼─┼─┼─┼─┼─┼─┼─┼─┼─┼─┼─┼─┼─┤");
				System.out.println((i+1)+"|"+this.board[i][0]+"|"+this.board[i][1]+"|"+this.board[i][2]+"|"+this.board[i][3]+"|"+this.board[i][4]+"|"+this.board[i][5]+"|"+this.board[i][6]+"|"+this.board[i][7]+"|"+this.board[i][8]+"|"+this.board[i][9]+"|"+this.board[i][10]+"|"+this.board[i][11]+"|"+this.board[i][12]+"|"+this.board[i][13]+"|"+this.board[i][14]+"|");
			}
		}
		System.out.println("──┴─┴─┴─┴─┴─┴─┴─┴─┴─┴─┴─┴─┴─┴─┴─┘");
	}
}