package scrabble.application;

import scrabble.model.game.Game;
import scrabble.model.letter.Letter;
import scrabble.utilities.Exceptions.InsufficientLettersException;

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
                    game.refillPlayerDeck();
                    break;
                case "A":
                    game.printPlayerdeck();
                    break;
                case "P":
                    game.printPlayerdeck();

                    System.out.println("Saisissez votre mot :");
                    String stringInput = scanner.next().toUpperCase();

                    boolean motInvalide = game.verifWord(stringInput, game);
                    while (motInvalide) {
                        System.out.println("Saisissez un nouveau mot: ");
                        stringInput = scanner.next().toUpperCase();
                        motInvalide = game.verifWord(stringInput, game);
                    }

                    List<Letter> createdWord = game.createWord(stringInput);
                    System.out.print("Vous avez joué ce mot :");
                    for (Letter letter : createdWord) {
                        System.out.print(letter.getValue());
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