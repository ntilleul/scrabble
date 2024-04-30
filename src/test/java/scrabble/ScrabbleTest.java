package scrabble;

import org.junit.Test;

import scrabble.model.game.Bag;
import scrabble.model.game.Game;
import scrabble.model.letter.Letter;
import scrabble.model.player.Deck;
import scrabble.model.player.Player;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.ArrayList;
import java.util.List;

public class ScrabbleTest {

    @Test
    public void testValue() {
        assertEquals(Letter.A.getValue(), 'A');
        assertEquals(Letter.B.getValue(), 'B');
    }

    @Test
    public void testPoints(){
        assertEquals(Letter.A.getPoints(), 1);
        assertEquals(Letter.B.getPoints(), 3);
    }
    
    @Test
    public void haveSevenLetters() {
    	Bag testBag = new Bag();
    	List<Letter> sevenLetters = new ArrayList<>();
    	sevenLetters = testBag.getSevenLetters();
    	assertEquals(sevenLetters.size(), 7);
    }
    
    @Test
    public void refillDeck() {
    	Game testGame = new Game();
    	Deck oldDeck = new Deck(testGame.getBag().getSevenLetters());
    	oldDeck.getLetters().clear();
    	for(Letter letter : testGame.getPlayer().getDeck().getLetters()) {
    		oldDeck.addLetter(letter);
    	}
    	testGame.refillPlayerDeck();
    	assertNotEquals(oldDeck, testGame.getPlayer().getDeck());
    	
    	
    }
}
