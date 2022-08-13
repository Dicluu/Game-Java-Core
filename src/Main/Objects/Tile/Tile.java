package Main.Objects.Tile;

public enum Tile {

    GRASS, ROCK;

    private char symbol = "0".charAt(0);

    public char getSymbol() {
        return symbol;
    }
}
