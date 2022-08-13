package Main.Objects.Characters.NPC;

public class Dealer extends NonPlayerCharacter {
    private static final int ID = 4;

    public Dealer(String name, int x, int y) {
        super(name, x, y, ID);
    }

    @Override
    public char getSymbol() {
        return "$".charAt(0);
    }

    @Override
    public int getId() {
        return ID;
    }
}
