package Main.Objects.Tile;

public enum Tile {

    GRASS(0), ROCK(1);

    Tile(int id) {
        this.id = id;
    }

    private int id;
    private char symbol = "0".charAt(0);

    public char getSymbol() {
        return symbol;
    }

    public int getId() {
        return id;
    }

    public static Tile findById(int id) {
        for (Tile t : Tile.values()) {
            if (t.getId() == id) return t;
        }
        return null;
    }
}
