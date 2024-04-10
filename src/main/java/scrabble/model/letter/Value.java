package scrabble.model.letter;

public enum Value {

    A('A', 1), B('B', 3), C('C', 3), D('D', 2), E('E', 1), F('F', 4), G('G', 2), H('H', 4),
    I('I', 1), J('J', 8), K('K', 5), L('L', 1), M('M', 3), N('N', 1), O('O', 1), P('P', 3),
    Q('Q', 10), R('R', 1), S('S', 1), T('T', 1), U('U', 1), V('V', 4), W('W', 4), X('X', 8),
    Y('Y', 4), Z('Z', 10), JOKER(' ', 0);

    private final char value;
    private final int points;

    private Value(char value, int points) {

        this.value= value;
        this.points = points;
    }
    public char getValue() {
        return this.value;
    }

    public int getPoints() {
        return this.points;
    }
}