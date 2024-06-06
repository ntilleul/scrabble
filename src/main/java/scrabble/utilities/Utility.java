package scrabble.utilities;

import scrabble.model.game.Board;
import scrabble.model.game.Game;
import scrabble.model.letter.Letter;
import scrabble.utilities.Exceptions.InvalidPositionException;

import java.util.Arrays;
import java.util.List;

public class Utility {
    public boolean verifyNumber(int number, Game game) {
        return number > 0 && number <= game.getPlayer().getDeckSize();
    }

    public static boolean verifyLetter(char letter) {
        List<Character> alphabet = Arrays.asList(
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
                'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '?');
        return alphabet.contains(letter);
    }

    public boolean verifyContainsLetterInDeck(Letter letter, List<Letter> deck) {
        return deck.contains(letter);
    }

    public static int changeASCII(char letter) {
        letter = Character.toLowerCase(letter);
        return letter - 'a';
    }

    public static int[] frontToBackCoord(char x, int y) throws InvalidPositionException {

        if (x < 'A' || x > 'O' || y < 1 || y > Board.getSize())
            throw new InvalidPositionException("position invalide (X entre A et O, Y entre 1 et 15)");

        int[] coord = new int[2];
        coord[0] = changeASCII(x);
        coord[1] = y - 1;
        return coord;
    }
}
