package scrabble;

import org.junit.Before;
import org.junit.Test;
import scrabble.model.board.Direction;
import scrabble.model.game.Game;
import scrabble.model.words.Letter;
import scrabble.model.words.Word;
import scrabble.model.player.Deck;
import scrabble.model.player.Player;
import scrabble.model.board.Board;
import scrabble.utilities.exceptions.InsufficientLettersException;
import scrabble.utilities.exceptions.InvalidCharacterInWordException;
import scrabble.utilities.exceptions.InvalidPositionException;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class GameTest {

    private Game game;
    private Player player;
    private Board board;

    @Before
    public void setUp() {
        game = new Game();
        player = game.getPlayer();
        board = game.getBoard();
    }

    @Test
    public void playedWordIsWorth66() {
        Game game = new Game();
        List<Letter> letters = Arrays.asList(Letter.A, Letter.B, Letter.C, Letter.D, Letter.E, Letter.F, Letter.G);
        Word word = new Word(letters);
        game.getBoard().placeWord(word, Direction.HORIZONTAL, 7, 7);
        assertEquals(66, game.countPoints(game.getPlayer(), 7, 7, Direction.HORIZONTAL));
    }

    @Test
    public void testCountPointsVerticalWord() {
        board.getTile(7, 7).setLetter(Letter.H);
        board.getTile(8, 7).setLetter(Letter.E);
        board.getTile(9, 7).setLetter(Letter.L);
        board.getTile(10, 7).setLetter(Letter.L);
        board.getTile(11, 7).setLetter(Letter.O);

        int points = game.countPoints(player, 7, 7, Direction.VERTICAL);

        assertEquals(8, points);
    }

    @Test
    public void testPlayedWordIsConnectedToTheRest_Horizontal_Connected() {
        board.getTile(7, 6).setLetter(Letter.H);
        board.getTile(7, 7).setLetter(Letter.E);

        Word word = new Word(Arrays.asList(Letter.L, Letter.L, Letter.O));
        boolean isConnected = game.getBoard().playedWordIsConnectedToTheRest(word, 8, 7, Direction.HORIZONTAL);

        assertTrue(isConnected);
    }

    @Test
    public void testPlayedWordIsConnectedToTheRest_Horizontal_NotConnected() {
        board.getTile(7, 0).setLetter(Letter.H);
        board.getTile(7, 1).setLetter(Letter.E);

        Word word = new Word(Arrays.asList(Letter.L, Letter.L, Letter.O));
        boolean isConnected = game.getBoard().playedWordIsConnectedToTheRest(word, 8, 7, Direction.HORIZONTAL);

        assertFalse(isConnected);
    }

    @Test
    public void testPlayedWordIsConnectedToTheRest_Vertical_Connected() {
        board.getTile(6, 7).setLetter(Letter.H);
        board.getTile(7, 7).setLetter(Letter.E);

        Word word = new Word(Arrays.asList(Letter.L, Letter.L, Letter.O));
        boolean isConnected = game.getBoard().playedWordIsConnectedToTheRest(word, 7, 8, Direction.VERTICAL);

        assertTrue(isConnected);
    }

    @Test
    public void testPlayedWordIsConnectedToTheRest_Vertical_NotConnected() {
        board.getTile(0, 7).setLetter(Letter.H);
        board.getTile(1, 7).setLetter(Letter.E);

        Word word = new Word(Arrays.asList(Letter.L, Letter.L, Letter.O));
        boolean isConnected = game.getBoard().playedWordIsConnectedToTheRest(word, 7, 8, Direction.VERTICAL);

        assertFalse(isConnected);
    }


    // test for verifWord

    @Test
    public void testWordIsEmpty() throws Exception {
        String word = "";
        try {
            game.verifyWord(word);
        } catch (InvalidCharacterInWordException e) {
            assertEquals("Le mot est vide", e.getMessage());
        }
    }

    @Test
    public void testWordContainsInvalidCharacter() throws Exception {
        String word = "!";

        try {
            game.verifyWord(word);
        } catch (InvalidCharacterInWordException e) {
            assertEquals("Votre mot contient des caractères invalides", e.getMessage());
        }
    }

    @Test
    public void testWordContainsJoker() throws Exception {
        Game game = new Game();
        player = game.getPlayer();
        player.getDeck().getLetters().clear();

        player.getDeck().addLetter(Letter.JOKER);
        player.getDeck().addLetter(Letter.A);
        player.getDeck().addLetter(Letter.B);
        player.getDeck().addLetter(Letter.C);
        player.getDeck().addLetter(Letter.D);
        player.getDeck().addLetter(Letter.E);
        player.getDeck().addLetter(Letter.F);

        String word = "A?B";
        assertTrue(game.verifWord(word));

    }

    @Test
    public void testWordWithInsufficientLettersException() throws Exception {
        Game game = new Game();
        player = game.getPlayer();
        player.getDeck().getLetters().clear();

        player.getDeck().addLetter(Letter.A);
        player.getDeck().addLetter(Letter.B);
        player.getDeck().addLetter(Letter.C);
        player.getDeck().addLetter(Letter.D);
        player.getDeck().addLetter(Letter.E);
        player.getDeck().addLetter(Letter.F);

        String word = "ABCDEFZ";
        try {
            game.verifyWord(word);
        } catch (InsufficientLettersException e) {
            assertEquals("Vous ne possedez pas les lettres nécéssaire", e.getMessage());
        }
    }


    @Test
    public void testMakerPlayerDraw() {
        Game game = new Game();
        Player player = game.getPlayer();
        Deck deck = player.getDeck();
        int deckSize = deck.getLetters().size();
        game.makePlayerDraw(player, 1);
        assertEquals(deckSize + 1, deck.getLetters().size());
    }

    @Test
    public void testRefillPlayerDeck() {
        Game game = new Game();
        Player player = game.getPlayer();
        Deck deck = player.getDeck();
        deck.addLetter(Letter.A);
        deck.addLetter(Letter.B);
        deck.addLetter(Letter.C);
        deck.addLetter(Letter.D);
        deck.addLetter(Letter.E);
        deck.addLetter(Letter.F);
        deck.addLetter(Letter.G);
        game.refillPlayerDeck();

        int deckSize = deck.getLetters().size();

        assertEquals(7, deckSize);
    }

    @Test
    public void testFirstWordNotOnStar() {
        // Arrange
        Game game = new Game();
        Word word = new Word(Arrays.asList(Letter.A, Letter.B, Letter.C));
        int x = 3;
        int y = 3;
        Direction direction = Direction.HORIZONTAL;

        // Act & Assert
        try {
            game.canPlay(word, x, y, direction);
            fail("Expected InvalidPositionException to be thrown");
        } catch (InvalidPositionException e) {
            assertEquals("Le premier mot doit passer par le centre.", e.getMessage());
        }
    }

    @Test
    public void testPlayedWordNotConnected() {
        // Arrange
        Game game = new Game();
        Word word = new Word(Arrays.asList(Letter.A, Letter.B, Letter.C));
        int x = 5;
        int y = 5;
        Direction direction = Direction.HORIZONTAL;

        // Act & Assert
        try {
            game.canPlay(word, x, y, direction);
        } catch (InvalidPositionException e) {
            assertEquals("Le premier mot doit passer par le centre.", e.getMessage());
        }
    }

    @Test
    public void testWordOutOfBoard() throws InvalidPositionException {
        // Arrange
        Game game = new Game();
        Word word = new Word(Arrays.asList(Letter.A, Letter.B, Letter.C));
        int x = 7; // Assumant le milieu de votre plateau
        int y = 7;
        Direction direction = Direction.HORIZONTAL;

        game.canPlay(word, x, y, direction);

        word = new Word(Arrays.asList(Letter.A, Letter.B, Letter.C));

        try {
            game.canPlay(word, x, y, direction);
        } catch (InvalidPositionException e) {
            assertEquals("Le mot dépasse du plateau", e.getMessage());
        }
    }
}
