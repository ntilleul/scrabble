package scrabble.application;

import scrabble.model.letter.Letter;
import scrabble.model.letter.Value;

public class ScrabbleApplicationConsole {


	public static void main(String[] args) {

		System.out.println("-----------------------------------------------------------------");
		System.out.println("----------------Bienvenue dans le jeu du scrabble----------------");
		System.out.println("-----------------------------------------------------------------");

		Letter letterA = new Letter('A');
		System.out.println(letterA.value());

		System.out.println(Value.B + " n'est pas la lettre A");
	}
}
