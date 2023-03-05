package Main.Objects.Characters;

import Main.Maps.Cell;
import Main.Singletones.GameExecutor;

public class Player extends Character{


    private String name;
    private static final int ID = 3;
    public Player(String name, int x, int y) {
        super(name, x, y, ID, 0f);
        this.name = name;
    }

    public Cell getCurrentCell() {
        return GameExecutor.getGame().getCurrentMap().getCell(x,y);
    }

    @Override
    public char getSymbol() {
        return name.toUpperCase().charAt(0);
    }
}
