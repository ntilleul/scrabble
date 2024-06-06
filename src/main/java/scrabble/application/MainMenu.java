package scrabble.application;

import scrabble.model.game.Game;

import java.util.Scanner;

public class MainMenu {
    private Game game = new Game();
    private Scanner scanner;

    public MainMenu() {
        scanner = new Scanner(System.in);
    }

    public void printStartMenu() throws Exception {
        String choice;

        do {
            System.out.println("\nMENU PRINCIPAL:");
            System.out.println("S - Lancer une nouvelle partie");
            System.out.println("Q - Quitter");

            System.out.print("\nChoisissez une option : ");
            choice = scanner.next().toUpperCase();

            switch (choice) {
                case "S":
                    System.out.println("\nLancement de la partie . . .\n");
                    game.start();
                    printGameMenu();
                    break;
                case "Q":
                    System.out.println("\nFermeture du jeu . . .\n");
                    break;
                default:
                    System.out.println("\nchoix invalide. Veuillez choisir une option valide.\n");
            }
        } while (!choice.equals("Q"));
        scanner.close();
    }

    public void printGameMenu() throws Exception {
        String choice;
        game.printPlayerdeck();

        do {
            System.out.println("\nMENU PRINCIPAL:");
            System.out.println("C - Changer les lettres du deck");
            System.out.println("A - Afficher le deck");
            System.out.println("P - Jouer un mot");
            System.out.println("Q - Quitter");

            System.out.print("\nChoisissez une option : ");
            choice = scanner.next().toUpperCase();

            switch (choice) {
                case "C":
                    game.refillPlayerDeck();
                    game.printPlayerdeck();
                    break;
                case "A":
                    game.printPlayerdeck();
                    break;
                case "P":
                    game.printPlayerdeck();

                    System.out.println("Saisissez votre mot :");
                    String stringInput = scanner.next().toUpperCase();

                    boolean invalidWord = game.verifWord(stringInput);
                    if (!invalidWord) {
                        game.playWord(stringInput);
                    }
                    System.out.println();
                    break;
                case "Q":
                    System.out.println("\nFermeture du jeu . . .\n");
                    break;
                default:
                    System.out.println("\nchoix invalide. Veuillez choisir une option valide.\n");
            }
        } while ((!choice.equals("Q")) && (!game.verifWin(game)));

        // TODO : afficher le message avec le nom du joueur pour la suite
        if (game.verifWin(game)) {
            System.out.println("Félicitations, vous avez gagné !");
        }
    }
}