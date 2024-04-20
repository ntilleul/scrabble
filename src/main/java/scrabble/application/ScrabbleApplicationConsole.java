package scrabble.application;

import scrabble.application.model.Game;

public class ScrabbleApplicationConsole {

	public static void main(String[] args) {
		System.out.println("-----------------------------------------------------------------");
		System.out.println("----------------Bienvenue dans le jeu du scrabble----------------");
		System.out.println("-----------------------------------------------------------------");
		Game game = new Game();
		game.start();
		game.refillPlayerDeck();
		game.printPlayerdeck();
	}
}
