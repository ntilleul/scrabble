package scrabble.model.game;

import scrabble.model.letter.Letter;
import scrabble.model.player.Player;
import scrabble.utilities.Utility;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static scrabble.utilities.Utility.changeASCII;

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
        //TODO modify for multiplayer in future
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
            System.out.println("Vous avez gagné " + wordPoints + " points ce qui vous amène à un total de " + player.getPoint() + " points.");
        } else {
            System.out.println("Vous avez annulé votre mot.");
        }
    }

    public boolean verifWin(Game game) {
        return game.getBag().getLetters().isEmpty() && game.getPlayer().getDeck().isEmpty();
    }

    public void printWord(List<Letter> word) {
        String direction = "";
        char column = ' ';
		boolean tf = true;
        int x = 0;
        int y = 0;
        
        
        do {
            System.out.println("Dans quelle direction voulez-vous placer votre mot ? (" + Direction.HORIZONTAL.getCommand() + "/" + Direction.VERTICAL.getCommand() + ")");
            direction = scanner.next().toUpperCase();
            System.out.println(direction);
            System.out.println(Direction.HORIZONTAL.getCommand());
            System.out.println((direction.equals(Direction.HORIZONTAL.getCommand())));;
            if ( (!direction.equals(Direction.HORIZONTAL.getCommand())) && (!direction.equals(Direction.VERTICAL.getCommand())) )
                System.err.println("Commande inconnu.");
        } while ( (!direction.equals(Direction.HORIZONTAL.getCommand())) && (!direction.equals(Direction.VERTICAL.getCommand())) );
        
        while (tf) {
			if (wordCount == 0) {
            	tf = ((column < 'A') || (column > 'O')) || ((y < 1) || (y > board.getSize())) || (!firstWordIsOnStar(word, x, y, direction));
        	} else {
				tf = ((column < 'A') || (column > 'O')) || ((y < 1) || (y > board.getSize()));
			}
            System.out.println("A quelle position voulez-vous placer votre mot ? (x y)");
            column = scanner.next().charAt(0);
            column = Character.toUpperCase(column);
            x = changeASCII(column);
            y = scanner.nextInt();
            if (column < 'A' || column > 'O') {
                System.out.println("Erreur : x doit être un caractère entre 'a' et 'o'.");
			}
            else if (y < 1 || y > board.getSize()) {
                System.out.println("Erreur : y doit être un entier entre 1 et " + Integer.toString(board.getSize())  + ".");
            } 
            else if (!firstWordIsOnStar(word, x, y, direction) && wordCount == 0) {
            	System.out.println("Erreur : le mot doit passer par la case centrale.");
            }
            else
                tf = false;
        }
        placeWord(word, direction, y, x);
        wordCount++;
        board.print();
    }

    private void placeWord(List<Letter> word, String direction, int x, int y) {
        if (direction.equals(Direction.HORIZONTAL.getCommand())) {
            for (int i = 0; i < word.size(); i++) {
                while(!board.getCase(x - 1, y + i - 1).isEmpty()){
                    y++;
                }
                board.getCase(x - 1, y + i - 1).setLetter(word.get(i));
            }
        } else {
            for (int i = 0; i < word.size(); i++) {
                while(!board.getCase(x + i - 1, y - 1).isEmpty()){
                    x++;
                }
                board.getCase(x + i - 1, y - 1).setLetter(word.get(i));
            }
        }
    }
    
	public boolean firstWordIsOnStar(List<Letter> word, int x, int y, String dir) {
        if (dir.equals(Direction.HORIZONTAL.getCommand())) {
            if (x + word.size() < board.getMiddleSize() || x > board.getMiddleSize()) {
                return false;
            }
            for (int i = 0; i < word.size(); i++) {
                if (x + i == board.getMiddleSize() && y == board.getMiddleSize()) {
                    return true;
                }
            }
        } else {
            if (y + word.size() < board.getMiddleSize() || y > board.getMiddleSize()) {
                return false;
            }
            for (int i = 0; i < word.size(); i++) {
                if (y + i == board.getMiddleSize() && x == board.getMiddleSize()) {
                    return true;
                }
            }
        }
        return false;
	}
	
	public boolean playedWordIsConnectedToTheRest(List<Letter> word, int x, int y, String dir) {
		if (dir.equals(Direction.HORIZONTAL.getCommand())){
			for(int i=x ; i<(word.size()+x) ; i++) {
				if (!(board.getCase(i-1, y).isEmpty() || board.getCase(i+1, y).isEmpty() || board.getCase(i, y-1).isEmpty() || board.getCase(i, y+1).isEmpty())) {
					return true;
				}
			}
		} else {
			for(int i=y ; i<(word.size()+x) ; i++) {
				if (!(board.getCase(x-1, i).isEmpty() || board.getCase(x+1, i).isEmpty() || board.getCase(x, i-1).isEmpty() || board.getCase(x, i+1).isEmpty())) {
					return true;
				}
			}
		}
		return false;
	}
}

