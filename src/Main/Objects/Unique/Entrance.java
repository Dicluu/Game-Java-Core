package Main.Objects.Unique;

import Main.Maps.Cell;
import Main.Maps.Map;
import Main.Objects.Entity;
import Main.Utils.FileLoaders.MapLoader;
import Main.Utils.Messenger;

public class Entrance extends UniqueEntity implements Enterable {
    static {
        try {
            Entity.addInstance(6, Entrance.class);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private static final int ID = 6;
    private String name = "Entrance";
    private int referMapId;
    private Cell node = null;
    private char symbol = 'e';

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

    public Entrance(int x, int y, int mapToID, int mapFromID) {
        super(x, y, ID);
        Map map = MapLoader.loadMapById(mapToID);
        this.name = map.getName();
        this.symbol = map.getDelegateSymbol();
        referMapId = map.getId();
        this.node = Map.getMapById(referMapId).getCell(0, 3);
        Entrance exit = new Entrance(0,3, mapFromID);
        exit.setNode(x,y);
        node.addObject(exit);
    }

    @Override
    public int getId() {
        return ID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }

    @Override
    public char getSymbol() {
        return symbol;
    }

    public int getReferMapId() {
        return referMapId;
    }

    public void initializeMutualMaps(int x, int y, int nodeX, int nodeY, int mapToID, int mapFromID) {
        super.setX(x);
        super.setY(y);
        Map map = MapLoader.loadMapById(mapToID);
        referMapId = map.getId();
        this.node = Map.getMapById(referMapId).getCell(0, 3);
        Entrance exit = new Entrance(0,3, mapFromID);
        exit.setNode(x,y);
        node.addObject(exit);
    }

    @Override
    public String getName() {
        return name;
    }

    public void setNode(int x, int y) {
        this.node = Map.getMapById(referMapId).getCell(x,y);
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
