package Main.Objects.Unique;

import Main.Maps.Cell;
import Main.Maps.Map;
import Main.Objects.Entity;
import Main.Utils.Messenger;

public class Entrance extends UniqueEntity implements Enterable {
    static {
        try {
            Entity.addInstance(2, Entrance.class);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private static final int ID = 2;
    private int referMapId;
    private Cell node = null;
    public Entrance(int x, int y, int referMapId) {
        super(x, y, ID);
        this.referMapId = referMapId;
    }

    public Entrance(int x, int y, int referMapId, int xn, int yn) {
        super(x, y, ID);
        this.referMapId = referMapId;
        this.node = Map.getMapById(referMapId).getCell(xn, yn);
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
        return "e".charAt(0);
    }

    public int getReferMapId() {
        return referMapId;
    }

    @Override
    public String getName() {
        return "Entrance";
    }

    @Override
    public Cell getNode() {
        if (node != null) {
            return node;
        }
        else {
            node = Map.getMapById(referMapId).getCell(super.getX(),super.getY());
            return node;
        }
    }

    @Override
    public void setReferMapId(int referMapId) {
        this.referMapId = referMapId;
    }
}
