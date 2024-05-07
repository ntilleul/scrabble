package scrabble.application;

import scrabble.model.game.*;
import scrabble.utilities.Exceptions.InsufficientLettersException;

public class ScrabbleApplicationConsole {

	public static void main(String[] args) throws InsufficientLettersException {
		System.out.println("-----------------------------------------------------------------");
		System.out.println("----------------Bienvenue dans le jeu du scrabble----------------");
		System.out.println("-----------------------------------------------------------------");
		MainMenu mainMenu = new MainMenu();
		mainMenu.printStartMenu();

	}
}