package scrabble.model.game;

import scrabble.model.player.Player;

public class Game {

    private Board board;
    private Bag bag;
    private Player player;

    public Game(){
        board = new Board();
        bag = new Bag();
        player = new Player("Player", bag.getSevenLetters());
    }

    public void start(){
        board.print();
        System.out.println();
    }

    public void refillPlayerDeck() {
        //TODO modify for multiplayer in future
        int numPlayerLetters = player.getDeck().size();

        bag.getLetters().addAll(player.getDeck().getLetters());

        bag.shuffleLetters();

        while (!player.getDeck().getLetters().isEmpty()) {
            player.getRid(0);
        }

        player.draw(bag.getNLetters(numPlayerLetters));
    }

    public void printPlayerdeck(){
        System.out.println("\nAffichage de votre banc:");
        player.getDeck().getLetters().forEach(letter -> System.out.print(letter.getValue() + " "));
        System.out.println();
    }

    public Player getPlayer() {
        return this.player;
    }

    public Bag getBag() {
        return this.bag;
    }
}
