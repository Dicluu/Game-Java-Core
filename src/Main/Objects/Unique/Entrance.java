package Main.Objects.Unique;

import Main.Objects.Characters.Player;
import Main.Objects.Entity;
import Main.Utils.Messenger;

public class Entrance extends UniqueEntity {
    static {
        try {
            Entity.addInstance(2, Entrance.class);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private final int ID = 2;
    private final int referMapId;
    public Entrance(int x, int y, int referMapId) {
        super(x, y);
        this.referMapId = referMapId;
    }

    public Entrance () {
        referMapId = -1;
        Messenger.systemMessage("instance initiated", Entrance.class);
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

    @Override
    public String getName() {
        return "Entrance";
    }
}
