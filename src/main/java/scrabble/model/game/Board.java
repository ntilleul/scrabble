package scrabble.model.game;

public class Board {
	public Board() {
	}
	
	public void print() {
		System.out.println("┌─┬─┬─┬─┬─┬─┬─┬─┬─┬─┬─┬─┬─┬─┬─┐");
		for (int i=0;i<14;i++) {
			if (i == 7) {
				System.out.println("| | | | | | | |*| | | | | | | |");
				System.out.println("├─┼─┼─┼─┼─┼─┼─┼─┼─┼─┼─┼─┼─┼─┼─┤");
			}
			else {
				System.out.println("| | | | | | | | | | | | | | | |");
				System.out.println("├─┼─┼─┼─┼─┼─┼─┼─┼─┼─┼─┼─┼─┼─┼─┤");
			}
		}
		System.out.println("| | | | | | | | | | | | | | | |");
		System.out.println("└─┴─┴─┴─┴─┴─┴─┴─┴─┴─┴─┴─┴─┴─┴─┘");
	}
}
