package scrabble.application;

import scrabble.model.game.Game;
import scrabble.model.letter.Letter;
import scrabble.utilities.Utility;

import java.util.ArrayList;
import java.util.List;
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
        scanner.close();
    }

    public void printGameMenu() {
        String choix;

        System.out.println("Affichage du deck . . .\n");
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
                    System.out.println("Affichage de votre nouveau deck: ");
                    game.refillPlayerDeck();
                    game.printPlayerdeck();
                    break;
                case "A":
                    game.refillPlayerDeck();
                    break;
                case "P":
                    int nbrLetter = 0;
                    game.printPlayerdeck();
                    while (true) {
                        System.out.println("Combien de lettres voulez-vous choisir ? ");
                        if (scanner.hasNextInt()) {
                            nbrLetter = scanner.nextInt();
                            break;
                        } else {
                            System.out.println("Veuillez saisir un nombre valide.");
                            scanner.next();
                        }
                    }
                    if (utility.verifyNumber(nbrLetter, game)){
                        List<Letter> letters = new ArrayList<>();
                        for (int i = 0; i < nbrLetter; i++) {
                            game.printPlayerdeck();
                            int choixLetter;
                            boolean isValid = false;
                            do {
                                System.out.println("\nSaisir une lettre voulez vous choisir: ");
                                choixLetter = scanner.nextInt();
                                if (utility.verifyNumber(choixLetter, game)) {
                                    isValid = true;
                                } else {
                                    System.out.println("Veuillez saisir un nombre valide.");
                                }
                            } while (!isValid);
                            letters.add(game.getPlayer().getDeck().playLetter(choixLetter));
                        }
                        System.out.println("\nVoici votre mot sélectionné: ");
                        for (Letter lettre : letters) {
                            System.out.print(lettre.getValue());
                        }
                    } else {
                        System.out.println("Nombre de lettre invalide.");
                    }
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
