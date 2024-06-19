package scrabble.model.game;

import scrabble.model.words.*;
import scrabble.model.player.Player;
import scrabble.model.board.Board;
import scrabble.model.board.Direction;
import scrabble.utilities.exceptions.InsufficientLettersException;
import scrabble.utilities.exceptions.InvalidCharacterInWordException;
import scrabble.utilities.exceptions.InvalidPositionException;
import scrabble.utilities.Utility;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private Board board;
    private Bag bag;
    private Player player;
    private static int wordCount = 0;
    private static int defaultDeckSize = 7;

    public Game(String playerName) {
        board = new Board();
        bag = new Bag();
        player = new Player(playerName, bag.getNLetters(defaultDeckSize));
    }
    public Game() {
        this("player");
    }

    public void refillPlayerDeck() {
        int numPlayerLetters = player.getDeckSize();
        for (Letter letter : player.getLetters()) {
            bag.addLetters(letter);
            letter.incrementNumber();
        }
        bag.shuffleLetters();

        player.clearDeck();

        player.draw(bag.getNLetters(numPlayerLetters));
    }
    public void makePlayerDraw(Player player, int nLetter) {
        getPlayer().draw(bag.getNLetters(nLetter));
    }

    public Player getPlayer() {
        return this.player;
    }
    public Bag getBag() {
        return this.bag;
    }
    public Board getBoard() {
        return this.board;
    }

    public boolean verifyWord(String wordString) throws Exception {
        List<Letter> availableLetters = new ArrayList<>(player.getLetters());

        if (wordString.isEmpty())
            throw new InvalidCharacterInWordException("Le mot est vide");
        for (int i = 0; i < wordString.length(); i++) {
            char letterChar = wordString.charAt(i);
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
        return true;
    }
    public boolean verifyWin(Game game) {
        return game.getBag().getLetters().isEmpty() && game.getPlayer().isDeckEmpty();
    }
    public boolean canPlay(Word word, int x, int y, Direction direction) throws InvalidPositionException {
        if (!board.firstWordIsOnStar(word, x, y, direction) && wordCount == 0)
            throw new InvalidPositionException("Le premier mot doit passer par le centre.");
        else if (!board.playedWordIsConnectedToTheRest(word, x, y, direction) && wordCount != 0)
            throw new InvalidPositionException("Le mot doit être connecté au autres");
        else if (board.verifyLetterIsOutOfBoard(word, direction, x, y))
            throw new InvalidPositionException("Le mot dépasse du plateau");
        return true;
    }
    public int nJokerInWord(String wordString) {
        int count = 0;
        for (int i = 0; i < wordString.length(); i++) {
            char letterChar = wordString.charAt(i);
            if (letterChar == '?') {
                count++;
            }
        }
        return count;
    }

    public void incrementWordCount() {
        wordCount++;
    }

    public int countPoints(Player player, int coordinatesX, int coordinatesY, Direction direction) {
        int wordPoints = 0;
        if (direction == Direction.HORIZONTAL) {
            while (coordinatesX > 0 && !board.getTile(coordinatesX - 1, coordinatesY).isEmpty()) {
                coordinatesX--;
            }
            int i = coordinatesX;
            while (coordinatesX < Board.getSize() && !board.getTile(i, coordinatesY).isEmpty()) {
                wordPoints += board.getTile(i, coordinatesY).getLetter().getPoints();
                i++;
            }
        } else {
            while (coordinatesY > 0 && !board.getTile(coordinatesX, coordinatesY - 1).isEmpty()) {
                coordinatesY--;
            }
            int i = coordinatesY;
            while (coordinatesY < Board.getSize() && !board.getTile(coordinatesX, i).isEmpty()) {
                wordPoints += board.getTile(coordinatesX, i).getLetter().getPoints();
                i++;
            }
        }

        if (player.getDeckSize() == 0) {
            wordPoints += 50;
        }
        return wordPoints;
    }
}
