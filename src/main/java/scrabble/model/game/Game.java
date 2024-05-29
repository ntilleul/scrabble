package scrabble.model.game;

import scrabble.model.letter.Letter;
import scrabble.model.player.Player;
import scrabble.utilities.Utility;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game {

    private Board board;
    private Bag bag;
    private Player player;
    Scanner scanner = new Scanner(System.in);
    private int wordCount = 0;

    public Game() {
        board = new Board();
        bag = new Bag();
        player = new Player("Player", bag.getSevenLetters());
    }

    public void start() {
        board.print();
        System.out.println();
    }

    public void refillPlayerDeck() {
        // TODO modify for multiplayer in future
        int numPlayerLetters = player.getDeck().size();
        for (Letter letter : player.getDeck().getLetters()) {
            bag.addLetters(letter);
            letter.incrementNumber();
        }
        bag.shuffleLetters();

        while (!player.getDeck().getLetters().isEmpty()) {
            player.getRid(0);
        }

        player.draw(bag.getNLetters(numPlayerLetters));
    }

    public void printPlayerdeck() {
        System.out.println("\nAffichage de votre banc:");
        player.getDeck().getLetters().forEach(letter -> System.out.print(letter.getValue() + " "));
        System.out.println();
    }

    public Player getPlayer() {
        return this.player;
    }

    public Bag getBag() {
        return this.bag;
    }

    public boolean verifWord(String word) {
        List<Letter> deck = new ArrayList<>(player.getDeck().getLetters());

        List<Letter> availableLetters = new ArrayList<>(deck);

        for (int i = 0; i < word.length(); i++) {
            char letterChar = word.charAt(i);
            if (!Utility.verifyLetter(letterChar)) {
                System.out.println("Votre mot contient des caractères invalides.");
                return true;
            } else {
                Letter letter;
                if (letterChar == Letter.JOKER.getValue()) {
                    letter = Letter.JOKER;
                } else {
                    letter = Letter.valueOf(Character.toString(letterChar));
                }
                if (!availableLetters.contains(letter)) {
                    System.out.println("Vous ne possédez pas toutes ces lettres dans votre deck.");
                    return true;
                } else {
                    availableLetters.remove(letter);
                }
            }
        }
        return false;
    }

    public List<Letter> createWord(String word) {
        List<Letter> letterList = new ArrayList<>();
        for (int i = 0; i < word.length(); i++) {
            char letterChar = word.charAt(i);
            Letter letter;
            if (letterChar == Letter.JOKER.getValue()) {
                letter = Letter.JOKER;
            } else {
                letter = Letter.valueOf(Character.toString(letterChar));
            }
            letterList.add(letter);
        }
        return letterList;
    }

    public void playWord(String stringInput) {
        System.out.println("Voulez vous jouer ce mot ? (O/N)");
        String choice = scanner.next().toUpperCase();
        int wordSize = stringInput.length();
        int wordPoints = 0;
        if (choice.equals("O")) {
            List<Letter> createdWord = createWord(stringInput);
            for (Letter letter : createdWord) {
                wordPoints = wordPoints + letter.getPoints();
                player.addPoint(letter.getPoints());
                player.getDeck().getLetters().remove(letter);
            }
            char tempC;
            String tempS = "";
            for (int i = 0; i < stringInput.length(); i++) {
                if (stringInput.charAt(i) == Letter.JOKER.getValue()) {
                    tempC = Letter.changeJokerValue();
                    tempS = tempS + tempC;
                } else {
                    tempS = tempS + stringInput.charAt(i);
                }
            }
            stringInput = tempS;
            player.draw(bag.getNLetters(wordSize));
            System.out.println("Vous avez jouer ce mot: " + stringInput);
            printWord(createdWord);
            System.out.println("Vous avez gagné " + wordPoints + " points ce qui vous amène à un total de "
                    + player.getPoint() + " points.");
        } else {
            System.out.println("Vous avez annulé votre mot.");
        }
    }

    public boolean verifWin(Game game) {
        return game.getBag().getLetters().isEmpty() && game.getPlayer().getDeck().isEmpty();
    }

    public void printWord(List<Letter> word) {
        String directionString;
        Direction direction;
        boolean tf = true;
        char frontx;
        int fronty;
        int x = 0;
        int y = 0;

        do {
            System.out.println("Dans quelle direction voulez-vous placer votre mot ? ("
                    + Direction.HORIZONTAL.getCommand() + "/" + Direction.VERTICAL.getCommand() + ")");
            directionString = scanner.next().toUpperCase();
            if ((!directionString.equals(Direction.HORIZONTAL.getCommand()))
                    && (!directionString.equals(Direction.VERTICAL.getCommand())))
                System.err.println("Commande inconnu.");
        } while ((!directionString.equals(Direction.HORIZONTAL.getCommand()))
                && (!directionString.equals(Direction.VERTICAL.getCommand())));

        if (directionString.equals(Direction.HORIZONTAL.getCommand()))
            direction = Direction.HORIZONTAL;
        else
            direction = Direction.VERTICAL;

        while (tf) {
            System.out.println("A quelle position voulez-vous placer votre mot ? (x y)");
            frontx = scanner.next().charAt(0);
            frontx = Character.toUpperCase(frontx);
            fronty = scanner.nextInt();
            x = Utility.frontToBackCoord(frontx, fronty)[0];
            y = Utility.frontToBackCoord(frontx, fronty)[1];

            if (wordCount == 0) {
                tf = ((frontx < 'A') || (frontx > 'O')) || ((fronty < 1) || (fronty > board.getSize()))
                        || (!firstWordIsOnStar(word, x, y, direction));
            } else {
                tf = ((frontx < 'A') || (frontx > 'O')) || ((fronty < 1) || (fronty > board.getSize())
                        || !(playedWordIsConnectedToTheRest(word, x, y, direction)));
            }

            if (frontx < 'A' || frontx > 'O') {
                System.out.println("Erreur : x doit être un caractère entre 'a' et 'o'.");
            } else if (fronty < 1 || fronty > board.getSize()) {
                System.out.println(
                        "Erreur : y doit être un entier entre 1 et " + Integer.toString(board.getSize()) + ".");
            } else if (!firstWordIsOnStar(word, x, y, direction) && wordCount == 0) {
                System.out.println("Erreur : le mot doit passer par la case centrale.");
            } else if (!playedWordIsConnectedToTheRest(word, x, y, direction) && wordCount != 0) {
                System.out.println("Erreur : le mot doit être connecté aux autres.");
            } else
                tf = false;
        }
        board.placeWord(word, direction, y, x);
        wordCount++;
        board.print();
    }

    // private void placeWord(List<Letter> word, Direction direction, int x, int y)
    // {
    // if (direction.equals(Direction.HORIZONTAL)) {
    // for (int i = 0; i < word.size(); i++) {
    // while (!board.getTile(x, y + i).isEmpty()) {
    // y++;
    // }
    // board.getTile(x, y + i).setLetter(word.get(i));
    // }
    // } else {
    // for (int i = 0; i < word.size(); i++) {
    // while (!board.getTile(x + i, y).isEmpty()) {
    // x++;
    // }
    // board.getTile(x + i, y).setLetter(word.get(i));
    // }
    // }
    // }

    public boolean firstWordIsOnStar(List<Letter> word, int x, int y, Direction dir) {
        if (dir.equals(Direction.HORIZONTAL)) {
            if (x + word.size() < board.getMiddleSize() - 1 || x > board.getMiddleSize() - 1) {
                return false;
            }
            for (int i = 0; i < word.size(); i++) {
                if (x + i == board.getMiddleSize() - 1 && y == board.getMiddleSize() - 1) {
                    return true;
                }
            }
        } else {
            if (y + word.size() < board.getMiddleSize() - 1 || y > board.getMiddleSize() - 1) {
                return false;
            }
            for (int i = 0; i < word.size(); i++) {
                if (y + i == board.getMiddleSize() - 1 && x == board.getMiddleSize() - 1) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean playedWordIsConnectedToTheRest(List<Letter> word, int x, int y, Direction dir) {
        if (dir.equals(Direction.HORIZONTAL)) {
            for (int i = x; i < (word.size() - 1 + x); i++) {
                if (board.letterNextToCoord(i, y))
                    return true;
            }
        } else {
            for (int i = y; i < (word.size() + x); i++) {
                if (board.letterNextToCoord(x, i))
                    return true;
            }
        }
        return false;
    }
}
