package scrabble.model.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import scrabble.model.letter.Letter;
import scrabble.model.player.Player;
import scrabble.utilities.Utility;
import scrabble.utilities.Exceptions.InsufficientLettersException;
import scrabble.utilities.Exceptions.InvalidCharacterInWordException;
import scrabble.utilities.Exceptions.InvalidPositionException;

public class Game {

    private Board board;
    private Bag bag;
    private Player player;
    Scanner scanner = new Scanner(System.in);
    private static int wordCount = 0;

    public Game(String playerName) {
        board = new Board();
        bag = new Bag();
        player = new Player(playerName, bag.getSevenLetters());
    }

    public Game() {
        this("player");
    }

    public void start() {
        board.print();
    }

    public void refillPlayerDeck() {
        // TODO modify for multiplayer in future
        int numPlayerLetters = player.getDeckSize();
        for (Letter letter : player.getLetters()) {
            bag.addLetters(letter);
            letter.incrementNumber();
        }
        bag.shuffleLetters();

        player.clearDeck();

        player.draw(bag.getNLetters(numPlayerLetters));
    }

    public void makerPlayerDraw(Player player, int nLetter) {
        player.draw(bag.getNLetters(nLetter));
    }

    public void printPlayerdeck() {
        System.out.println("\nAffichage de votre banc:");
        player.getLetters().forEach(letter -> System.out.print(letter.getValue() + " "));
        System.out.println();
    }

    public Player getPlayer() {
        return this.player;
    }

    public Bag getBag() {
        return this.bag;
    }

    public boolean verifWord(String word) throws Exception {
        List<Letter> availableLetters = new ArrayList<>(player.getLetters());

        if (word.equals(""))
            throw new InvalidCharacterInWordException("Le mot est vide");
        for (int i = 0; i < word.length(); i++) {
            char letterChar = word.charAt(i);
            if (!Utility.verifyLetter(letterChar)) {
                throw new InvalidCharacterInWordException("Votre mot contient des caractères invalides");
            } else {
                Letter letter;
                if (letterChar == '?') {
                    letter = Letter.JOKER;
                } else {
                    letter = Letter.valueOf(Character.toString(letterChar));
                }
                if (!availableLetters.contains(letter)) {
                    throw new InsufficientLettersException("Vous ne possedez pas les lettres nécéssaire");
                } else {
                    availableLetters.remove(letter);
                }
            }
        }
        return false;
    }

    public int nJokerInWord(String word) {
        int count = 0;
        for (int i = 0; i < word.length(); i++) {
            char letterChar = word.charAt(i);
            if (letterChar == '?') {
                count++;
            }
        }
        return count;
    }

    public List<Letter> createWord(String word, List<Character> jokerChar) {
        List<Letter> letterList = new ArrayList<>();
        int iJoker = 0;
        for (int i = 0; i < word.length(); i++) {
            char letterChar = word.charAt(i);
            Letter letter;
            if (letterChar == '?') {
                letter = Letter.JOKER;
                letter.setValue(jokerChar.get(iJoker));
                iJoker++;
            } else {
                letter = Letter.valueOf(Character.toString(letterChar));
            }
            letterList.add(letter);
        }
        return letterList;
    }

    public List<Letter> createWord(String word) {
        List<Character> chars = new ArrayList<>();
        return createWord(word, chars);
    }

    // public void playWord(String stringInput) throws InvalidPositionException {
    // int wordSize = stringInput.length();
    // int wordPoints = 0;

    // char tempC;
    // String tempS = "";
    // for (int i = 0; i < stringInput.length(); i++) {
    // if (stringInput.charAt(i) == Letter.JOKER.getValue()) {
    // tempC = Letter.changeJokerValue();
    // tempS = tempS + tempC;
    // } else {
    // tempS = tempS + stringInput.charAt(i);
    // }
    // }
    // stringInput = tempS;

    // List<Letter> createdWord = createWord(stringInput);
    // for (Letter letter : createdWord) {
    // wordPoints = wordPoints + letter.getPoints();
    // player.addPoint(letter.getPoints());
    // player.getLetters().remove(letter);
    // }

    // if (player.getDeckSize() == 0) {
    // wordPoints += 50;
    // player.addPoint(50);
    // }

    // player.draw(bag.getNLetters(wordSize));
    // }

    public boolean verifWin(Game game) {
        return game.getBag().getLetters().isEmpty() && game.getPlayer().isDeckEmpty();
    }

    public boolean canPlay(List<Letter> word, int x, int y, Direction direction) throws InvalidPositionException {
        if (!firstWordIsOnStar(word, x, y, direction) && wordCount == 0)
            throw new InvalidPositionException("Le premier mot doit passer par le centre.");
        else if (!playedWordIsConnectedToTheRest(word, x, y, direction) && wordCount != 0)
            throw new InvalidPositionException("Le mot doit être connecté au autres");
        else if (board.verifyLetterIsOutOfBoard(word, direction, y, x))
            throw new InvalidPositionException("Le mot dépasse du plateau");
        return true;
    }

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
        int i;
        if (dir.equals(Direction.HORIZONTAL)) {
            i = x;
            do {
                if (board.letterNextToCoord(y, i))
                    return true;
                i++;
            } while (i < (word.size() + x));
        } else {
            i = y;
            do {
                if (board.letterNextToCoord(i, x))
                    return true;
                i++;
            } while (i < (word.size() + y));
        }
        return false;
    }

    public Board getBoard() {
        return this.board;
    }

    public void incrementWordCount() {
        wordCount++;
    }

    public void countPoints(List<Letter> word, Player player) {
        for (Letter letter : word) {
            player.addPoint(letter.getPoints());
        }

        if (player.getDeckSize() == 0) {
            player.addPoint(50);
        }
    }
}