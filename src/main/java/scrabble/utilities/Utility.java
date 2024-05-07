package scrabble.utilities;

import scrabble.model.game.Game;
import scrabble.model.letter.Letter;

import java.util.Arrays;
import java.util.List;

public class Utility {
    public boolean verifyNumber(int number, Game game){
        return number > 0 && number <= game.getPlayer().getDeck().size();
    }

    public boolean verifyLetter(char letter){
        List<Character> alphabet = Arrays.asList(
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
                'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '?'
        );
        return alphabet.contains(letter);
    }

    public boolean verifyContainsLetterInDeck(Letter letter, Game game){
        return game.getPlayer().getDeck().getLetters().contains(letter);
    }
}
