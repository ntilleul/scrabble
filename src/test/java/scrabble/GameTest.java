package scrabble;

import org.junit.Test;
import scrabble.model.game.Direction;
import scrabble.model.game.Game;
import scrabble.model.letter.Letter;
import scrabble.model.letter.Word;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class GameTest {
    @Test
    public void playedWordIsWorth7() {
        Game game = new Game();
        List<Letter> letters = Arrays.asList(Letter.A, Letter.B, Letter.C);
        Word word = new Word(letters);
        game.getBoard().placeWord(word, Direction.HORIZONTAL, 7, 7);
        assertEquals(7, game.countPoints(letters, game.getPlayer(), 7, 7, Direction.HORIZONTAL));
    }
}
