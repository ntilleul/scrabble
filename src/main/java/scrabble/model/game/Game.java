package scrabble.model.game;

import scrabble.model.letter.Letter;
import scrabble.model.player.Player;
import scrabble.utilities.Exceptions.InsufficientLettersException;
import scrabble.utilities.Exceptions.InvalidCharacterInWordException;
import scrabble.utilities.Exceptions.InvalidPositionException;
import scrabble.utilities.Utility;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private Board board;
    private Bag bag;
    private Player player;
    private static int wordCount = 0;

    public Game(String playerName) {
        board = new Board();
        bag = new Bag();
        player = new Player(playerName, bag.getSevenLetters());
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

    public void makerPlayerDraw(Player player, int nLetter) {
        player.draw(bag.getNLetters(nLetter));
    }

    public Player getPlayer() {
        return this.player;
    }

    public Bag getBag() {
        return this.bag;
    }

    public boolean verifWord(String word) throws Exception {
        List<Letter> availableLetters = new ArrayList<>(player.getLetters());

        if (word.isEmpty())
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
        return true;
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

    public boolean verifWin(Game game) {
        return game.getBag().getLetters().isEmpty() && game.getPlayer().isDeckEmpty();
    }

    public boolean canPlay(List<Letter> word, int x, int y, Direction direction) throws InvalidPositionException {
        if (!board.firstWordIsOnStar(word, x, y, direction) && wordCount == 0)
            throw new InvalidPositionException("Le premier mot doit passer par le centre.");
        else if (!board.playedWordIsConnectedToTheRest(word, x, y, direction) && wordCount != 0)
            throw new InvalidPositionException("Le mot doit être connecté au autres");
        else if (board.verifyLetterIsOutOfBoard(word, direction, y, x))
            throw new InvalidPositionException("Le mot dépasse du plateau");
        return true;
    }

    public Board getBoard() {
        return this.board;
    }

    public void incrementWordCount() {
        wordCount++;
    }

    public int countPoints(Player player, int x, int y, Direction direction) {
        int wordPoints = 0;
        if (direction == Direction.HORIZONTAL) {
            while (x > 0 && !board.getTile(y, x - 1).isEmpty()) {
                x--;
            }
            int i = x;
            while (x < Board.getSize() && !board.getTile(y, i).isEmpty()) {
                wordPoints += board.getTile(y, i).getLetter().getPoints();
                i++;
            }
        } else {
            while (y > 0 && !board.getTile(y - 1, x).isEmpty()) {
                y--;
            }
            int i = y;
            while (y < Board.getSize() && !board.getTile(i, x).isEmpty()) {
                wordPoints += board.getTile(i, x).getLetter().getPoints();
                i++;
            }
        }

        if (player.getDeckSize() == 0) {
            wordPoints += 50;
        }
        return wordPoints;
    }
}