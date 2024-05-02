package scrabble.application;

import scrabble.model.game.Game;

import java.util.Scanner;

public class MainMenu {
    private Game game = new Game();
    private Scanner scanner;

    public MainMenu() {
        scanner = new Scanner(System.in);
    }

    public void printStartMenu() {
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
        scanner.close(); // Ferme le scanner après la boucle do-while
    }

    public void printGameMenu() {
        String choix;

        do {
            System.out.println("\nMENU PRINCIPAL:");
            System.out.println("C - Changer les lettres du deck");
            System.out.println("A - Afficher le deck");
            System.out.println("Q - Quitter");

            System.out.print("\nChoisissez une option : ");
            choix = scanner.next().toUpperCase();

            switch (choix) {
                case "C":
                    System.out.println("\nChangement de vos lettres . . . \n");
                    System.out.println("Affichage de votre nouveau deck: ");
                    game.refillPlayerDeck();
                    game.printPlayerdeck();
                    break;
                case "A":
                    System.out.println("Affichage du deck . . .\n");
                    game.printPlayerdeck();
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
