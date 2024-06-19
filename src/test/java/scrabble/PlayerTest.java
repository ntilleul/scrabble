package scrabble;

import org.junit.Test;
import scrabble.model.game.Bag;
import scrabble.model.game.Game;
import scrabble.model.words.Letter;
import scrabble.model.player.Deck;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class PlayerTest {

    @Test
    public void haveSevenLetters() {
        Bag testBag = new Bag();
        List<Letter> sevenLetters = new ArrayList<>();
        sevenLetters = testBag.getSevenLetters();
        assertEquals(0, sevenLetters.size());
    }

    @Test
    public void refillDeck() {
        Game testGame = new Game();
        Deck oldDeck = new Deck(testGame.getBag().getSevenLetters());
        oldDeck.getLetters().clear();
        for (Letter letter : testGame.getPlayer().getDeck().getLetters()) {
            oldDeck.addLetter(letter);
        }
        testGame.refillPlayerDeck();
        assertNotEquals(oldDeck, testGame.getPlayer().getDeck());
    }
}
