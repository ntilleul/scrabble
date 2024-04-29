package scrabble.model.letter;

public enum Letter {

    A('A', 1, 9), B('B', 3,2), C('C', 3,2), D('D', 2,3), E('E', 1,15), F('F', 4,2), G('G', 2,2), H('H', 4,2),
    I('I', 1,8), J('J', 8,1), K('K', 10,1), L('L', 1,5), M('M', 3,3), N('N', 1,6), O('O', 1,6), P('P', 3,2),
    Q('Q', 10,1), R('R', 1,6), S('S', 1,6), T('T', 1,6), U('U', 1,6), V('V', 4,2), W('W', 4,1), X('X', 8,1),
    Y('Y', 4,1), Z('Z', 10,1), JOKER(' ', 0,2);

    private final char value;
    private final int points;
    private int number;

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


}