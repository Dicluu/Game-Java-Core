package Main.Objects.Unique;

import Main.Maps.Cell;
import Main.Maps.Map;
import Main.Objects.Entity;
import Main.Utils.FileLoaders.MapLoader;

public class Town extends UniqueEntity implements Enterable {

    private char symbol = 'T';
    private final static int ID = 6;
    private String name = "Town";
    private int referId;
    private Cell node = null;

    static {
        try {
            Entity.addInstance(ID, Town.class);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public Town(int x, int y, int mapToID, int mapFromID) {
        super(x, y, ID);
        Map map = MapLoader.loadMapById(mapToID);
        this.name = map.getName();
        this.symbol = map.getDelegateSymbol();
        referId = map.getId();
        this.node = Map.getMapById(referId).getCell(0, 3);
        Entrance exit = new Entrance(0,3, mapFromID);
        exit.setNode(x,y);
        node.addObject(exit);
    }


    @Override
    public char getSymbol() {
        return symbol;
    }

    @Override
    public int getId() {
        return ID;
    }

    @Override
    public String getName() {
        return name;
    }

    /**
     * ONLY FOR INITIALIZING
     */
    public Town() {

    }

    @Override
    public int getReferMapId() {
        return referId;
    }

    @Override
    public Cell getNode() {
        return node;
    }

    @Override
    public void setReferMapId(int referId) {
        this.referId = referId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNode(Cell node) {
        this.node = node;
    }
}
