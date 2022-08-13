package Main.Objects.Unique;

public class Entrance extends UniqueEntity {

    private final int ID = 2;
    private final int referMapId;
    public Entrance(int x, int y, int referMapId) {
        super(x, y);
        this.referMapId = referMapId;
    }

    @Override
    public int getId() {
        return ID;
    }

    @Override
    public char getSymbol() {
        return "E".charAt(0);
    }

    public int getReferMapId() {
        return referMapId;
    }
}
