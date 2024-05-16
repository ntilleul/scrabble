package scrabble.model.letter;

import java.util.Scanner;

import scrabble.utilities.Utility;

public enum Letter {

    A('A', 1, 9), B('B', 3,2), C('C', 3,2), D('D', 2,3), E('E', 1,15), F('F', 4,2), G('G', 2,2), H('H', 4,2),
    I('I', 1,8), J('J', 8,1), K('K', 10,1), L('L', 1,5), M('M', 2,3), N('N', 1,6), O('O', 1,6), P('P', 3,2),
    Q('Q', 8,1), R('R', 1,6), S('S', 1,6), T('T', 1,6), U('U', 1,6), V('V', 4,2), W('W', 10,1), X('X', 10,1),
    Y('Y', 10,1), Z('Z', 10,1), JOKER('?', 0,2);

    private char value;
    private final int points;
    private int number;
    private Scanner scanner;

    Letter(char value, int points, int number) {
        this.value = value;
        this.points = points;
        this.number = number;
    }

    public char getValue() {
        return this.value;
    }

    public int getPoints() {
        return this.points;
    }

    public int getNumber() {
        return this.number;
    }

    public void decrementNumber() {
        this.number--;
    }

    public void incrementNumber() {
        this.number++;
    }
    
    public void changeJokerValue() {
    	String choice;
    	char newValue;
    	scanner = new Scanner(System.in);
    	System.out.println("Choisissez par quelle lettre vous voulez remplacer le Joker :");
    	choice = scanner.next().toUpperCase();
    	while ((choice.length() != 1) && (!Utility.verifyLetter(choice.charAt(0)))) {
    		System.out.println("Choisissez par quelle lettre vous voulez remplacer le Joker :");
    		choice = scanner.next().toUpperCase();
    	}
    	newValue = choice.charAt(0);
    	this.value = newValue;
    }
}