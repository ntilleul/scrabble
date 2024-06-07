package scrabble.model.letter;

import java.util.Scanner;

import scrabble.utilities.Utility;

public enum Letter {

    A('A', 1, 9), B('B', 3, 2), C('C', 3, 2), D('D', 2, 3), E('E', 1, 15), F('F', 4, 2), G('G', 2, 2), H('H', 4, 2),
    I('I', 1, 8), J('J', 8, 1), K('K', 10, 1), L('L', 1, 5), M('M', 2, 3), N('N', 1, 6), O('O', 1, 6), P('P', 3, 2),
    Q('Q', 8, 1), R('R', 1, 6), S('S', 1, 6), T('T', 1, 6), U('U', 1, 6), V('V', 4, 2), W('W', 10, 1), X('X', 10, 1),
    Y('Y', 10, 1), Z('Z', 10, 1), JOKER('?', 0, 1);

    private char value;
    private final int points;
    private int number;
    private static boolean joker1Used = false;

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

    public void setValue(char v) {
        this.value = v;
    }

    public static void resetJokerValue() {
        Letter.JOKER.setValue('?');
    }

    public String toString() {
        return Character.toString(value);
    }

    public static boolean isJoker1Used() {
        return joker1Used;
    }

    public static void useJoker1() {
        joker1Used = true;
    }
}