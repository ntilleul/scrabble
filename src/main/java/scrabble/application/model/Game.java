//We want that on game start, the board displays, and 7 letters are given into the player's deck. The player is able to reroll his letters


package scrabble.application.model;
import scrabble.model.board.Board;
import scrabble.model.player.Player;
import scrabble.model.player.Deck;
import scrabble.model.letter.Letter;

import java.util.ArrayList;
import java.util.Arrays;

public class Game{
    
    private Board board;
 
    
    public Game(){
        board = new Board();
    }
    
    public void start(){
        board.print();
        Player player = new Player("Player", new ArrayList<>(Arrays.asList(Letter.getRandomLetter(), Letter.getRandomLetter(), Letter.getRandomLetter(), Letter.getRandomLetter(), Letter.getRandomLetter(), Letter.getRandomLetter(), Letter.getRandomLetter())));
        player.getDeck().getLetters().forEach(letter -> System.out.print(letter.getValue() + " "));
    }

    public static void main(String[] args){
        Game game = new Game();
        game.start();
    }

}
