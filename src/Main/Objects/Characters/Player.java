package Main.Objects.Characters;

import Main.Maps.Cell;
import Main.Objects.Entity;
import Main.Singletones.GameExecutor;
import Main.Utils.Messenger;

public class Player extends Character{


    static {
        try {
            Entity.addInstance(3, Player.class);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private String name;
    private static final int ID = 3;
    public Player(String name, int x, int y) {
        super(name, x, y, ID, 0f);
        this.name = name;
    }
    public Player() {
        super("Player");
        Messenger.systemMessage("instance initiated", Player.class);
    }

    @Override
    public int getId() {
        return ID;
    }

    public Cell getCurrentCell() {
        return GameExecutor.getGame().getCurrentMap().getCell(x,y);
    }


}
