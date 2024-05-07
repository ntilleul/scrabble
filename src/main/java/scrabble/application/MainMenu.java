package scrabble.application;

import scrabble.model.game.Game;
import scrabble.model.letter.Letter;
import scrabble.utilities.Utility;
import scrabble.utilities.Exceptions.InsufficientLettersException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainMenu {
    private Game game = new Game();
    private Scanner scanner;

    public MainMenu() {
        scanner = new Scanner(System.in);
    }

    public void printStartMenu() throws InsufficientLettersException {
        String choix;

        do {
            System.out.println("\nMENU PRINCIPAL:");
            System.out.println("S - Lancer une nouvelle partie");
            System.out.println("Q - Quitter");

            System.out.print("\nChoisissez une option : ");
            choix = scanner.next().toUpperCase();

            switch (choix) {
                case "S":
                    System.out.println("\nLancement de la partie . . .\n");
                    game.start();
                    printGameMenu();
                    break;
                case "Q":
                    System.out.println("\nFermeture du jeu . . .\n");
                    break;
                default:
                    System.out.println("\nChoix invalide. Veuillez choisir une option valide.\n");
            }
        } while (!choix.equals("Q"));
        scanner.close();
    }

    public void printGameMenu() throws InsufficientLettersException {
        String choix;
        game.printPlayerdeck();
        Utility utility = new Utility();

        do {
            System.out.println("\nMENU PRINCIPAL:");
            System.out.println("C - Changer les lettres du deck");
            System.out.println("A - Afficher le deck");
            System.out.println("P - Jouer un mot");
            System.out.println("Q - Quitter");

            System.out.print("\nChoisissez une option : ");
            choix = scanner.next().toUpperCase();

            switch (choix) {
                case "C":
                    System.out.println("\nChangement de vos lettres . . . \n");
                    game.refillPlayerDeck();
                    game.printPlayerdeck();
                    break;
                case "A":
                    game.printPlayerdeck();
                    break;
                case "P":
                    game.printPlayerdeck();
                    Scanner scanner = new Scanner(System.in);
                    boolean isWord = false;
                    List<Letter> mot = new ArrayList<>();

                    while (!isWord) {
                        System.out.println("\nEntrez le mot que vous souhaitez écrire: ");
                        String stringInput = scanner.nextLine().toUpperCase();

                        if (utility.verifyNumber(stringInput.length(), game)) {
                            for (int i = 0; i < stringInput.length(); i++) {
                                char letterChar = stringInput.charAt(i);
                                if (Character.isLetter(letterChar)) {
                                    Letter letter = Letter.valueOf(Character.toString(letterChar));
                                    if (game.getPlayer().getDeck().getLetters().contains(letter)) {
                                        mot.add(letter);
                                        isWord = true;
                                    } else {
                                        System.out.println("Vous ne possédez pas toutes ces lettres dans votre banc.");
                                        mot.clear();
                                        break;
                                    }
                                } else {
                                    System.out.println("Vous n'avez pas sélectionné uniquement des lettres.");
                                    mot.clear();
                                    break;
                                }
                            }
                        } else {
                            throw new InsufficientLettersException("Vous n'avez pas suffisamment de lettres pour jouer le mot : " + mot);
                        }
                    }
                    System.out.print("Vous allez jouer ce mot: ");
                    for (Letter letter : mot) {
                        System.out.print(letter);
                    }
                    System.out.println();
                    break;
                case "Q":
                    System.out.println("\nFermeture du jeu . . .\n");
                    break;
                default:
                    System.out.println("\nChoix invalide. Veuillez choisir une option valide.\n");
            }
        } while (!choix.equals("Q"));
    }

}
