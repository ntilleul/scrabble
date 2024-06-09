package scrabble;

import org.junit.Test;
import scrabble.model.game.Game;
import scrabble.model.game.Direction;
import scrabble.model.letter.Letter;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class GameTest {



    @Test
    public void playedWordIsWorth7() {
        Game game = new Game();
        List<Letter> word = Arrays.asList(Letter.A, Letter.B, Letter.C);
        game.getBoard().placeWord(word, Direction.HORIZONTAL, 7, 7);
        game.countPointsWord(7, 7, word, Direction.HORIZONTAL);
    }
}
