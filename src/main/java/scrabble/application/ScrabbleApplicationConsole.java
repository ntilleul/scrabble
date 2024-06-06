package scrabble.application;

public class ScrabbleApplicationConsole {

	public static void main(String[] args) throws Exception {
		System.out.println("-----------------------------------------------------------------");
		System.out.println("----------------Bienvenue dans le jeu du scrabble----------------");
		System.out.println("-----------------------------------------------------------------");
		MainMenu mainMenu = new MainMenu();
		mainMenu.printStartMenu();

	}
}