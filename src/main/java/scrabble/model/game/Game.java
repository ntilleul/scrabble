package scrabble.model.game;

import scrabble.model.letter.Letter;
import scrabble.model.player.Player;
import scrabble.utilities.Utility;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private Board board;
    private Bag bag;
    private Player player;
    Utility utility = new Utility();

    public Game() {
        board = new Board();
        bag = new Bag();
        player = new Player("Player", bag.getSevenLetters());
    }

    public void start() {
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

    public void printPlayerdeck() {
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

    public boolean verifWord(String word, Game game) {

        int i = 0;
        boolean invalidWord = false;

        while (i < word.length() && !invalidWord) {
            if (!utility.verifyNumber(word.length(), game)) {
                System.out.println("Votre mot contient trop de lettres.");
                invalidWord = true;
            }
            char letterChar = word.charAt(i);
            if (!utility.verifyLetter(letterChar)) {
                System.out.println("Votre mot contient des caractères invalides.");
                invalidWord = true;
            } else {
                Letter letter;
                if (letterChar == '?') {
                    letter = Letter.JOKER;
                } else {
                    letter = Letter.valueOf(Character.toString(letterChar));
                }
                if (!utility.verifyContainsLetterInDeck(letter, game)) {
                    System.out.println("Vous ne possédez pas toutes ces lettres dans votre deck.");
                    invalidWord = true;
                }
            }
            i++;
        }
        return invalidWord;
    }

    public List<Letter> createWord(String word) {
        List<Letter> letterList = new ArrayList<>();
        for (int i = 0; i < word.length(); i++) {
            char letterChar = word.charAt(i);
            Letter letter;
            if (letterChar == '?') {
                letter = Letter.JOKER;
            } else {
                letter = Letter.valueOf(Character.toString(letterChar));
            }
            letterList.add(letter);
        }
        return letterList;
    }
}

