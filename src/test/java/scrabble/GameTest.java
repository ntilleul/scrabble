package scrabble;

import org.junit.Test;
import scrabble.model.game.Game;
import scrabble.model.game.Direction;
import scrabble.model.letter.Letter;
import scrabble.model.letter.Word;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class GameTest {

    @Test
    public void firstWordIsOnStarShouldReturnTrueWhenWordIsOnStar() {
        Game game = new Game();
        List<Letter> word = Arrays.asList(Letter.A, Letter.B, Letter.C);

        boolean result = game.firstWordIsOnStar(word, 7, 7, Direction.HORIZONTAL);

        assertTrue(result);
    }

    @Test
    public void firstWordIsOnStarShouldReturnFalseWhenWordIsNotOnStar() {
        Game game = new Game();
        List<Letter> word = Arrays.asList(Letter.A, Letter.B, Letter.C);

        boolean result = game.firstWordIsOnStar(word, 6, 6, Direction.HORIZONTAL);

        assertFalse(result);
    }

    @Test
    public void playedWordIsConnectedToTheRestShouldReturnTrueWhenWordIsConnected() {
        Game game = new Game();
        Word word = new Word(Arrays.asList(Letter.A, Letter.B, Letter.C));
        game.getBoard().placeWord(word, Direction.HORIZONTAL, 7, 7);

        boolean result = game.playedWordIsConnectedToTheRest(word.getLetters(), 7, 8, Direction.HORIZONTAL);

        assertTrue(result);
    }

    @Test
    public void playedWordIsConnectedToTheRestShouldReturnFalseWhenWordIsNotConnected() {
        Game game = new Game();
        List<Letter> word = Arrays.asList(Letter.A, Letter.B, Letter.C);

        boolean result = game.playedWordIsConnectedToTheRest(word, 7, 7, Direction.HORIZONTAL);

        assertFalse(result);
    }
}
