package scrabble.application;

import scrabble.model.game.*;

public class ScrabbleApplicationConsole {

	public static void main(String[] args) {
		System.out.println("-----------------------------------------------------------------");
		System.out.println("----------------Bienvenue dans le jeu du scrabble----------------");
		System.out.println("-----------------------------------------------------------------");
		MainMenu mainMenu = new MainMenu();
		mainMenu.printStartMenu();

	}
}
