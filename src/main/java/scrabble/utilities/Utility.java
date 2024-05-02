package scrabble.utilities;

import scrabble.model.game.Game;

public class Utility {
    public boolean verifyNumber(int number, Game game){
        if (number > 0 && number <= game.getPlayer().getDeck().size()){
            return true;
        } else {
            return false;
        }
    }
}
