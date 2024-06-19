package scrabble.model.board;

public enum Direction {
    HORIZONTAL("H"), VERTICAL("V");

    private String command;

    Direction(String command) {
        this.command = command;
    }

    public String getCommand() {
        return this.command;
    }
}