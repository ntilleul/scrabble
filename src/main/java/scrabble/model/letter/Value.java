package scrabble.model.letter;

public enum Value {

    A('A'), B('B'), C('C'), D('D'), E('E'), F('F'),G('G'),H('H'),I('I'),J('J'),K('K'),L('L'),M('M'),N('N'),O('O'),P('P'),Q('Q'),R('R'),S('S'),T('T'),U('U'),V('V'),W('W'),X('X'),Y('Y'),Z('Z'), JOKER(' ');

    private final char code;

    private Value(char code) {
        this.code= code;
    }
    public char getCode() {
        return this.code;
    }

}
