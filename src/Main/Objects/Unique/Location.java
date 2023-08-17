package Main.Objects.Unique;

import Main.Maps.Cell;
import Main.Maps.Map;
import Main.Objects.Entity;
import Main.Utils.FileLoaders.MapLoader;

public class Location extends UniqueEntity implements Enterable {

    private char symbol = 'l';
    private final static int ID = 8;
    private String name = "Location";
    private int referId;
    private Cell node = null;

    static {
        try {
            Entity.addInstance(ID, Location.class);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public Location(int x, int y, int mapToID, int mapFromID) {
        super(x, y, ID);
        Map map = MapLoader.loadMapById(mapToID);
        this.name = map.getName();
        this.symbol = map.getDelegateSymbol();
        referId = map.getId();
        this.node = Map.getMapById(referId).getCell(0, 2);
        Entrance exit = new Entrance(0,2, mapFromID);
        exit.setNode(x,y);
        node.addObject(exit);
    }

    /**
     * ONLY FOR INITIALIZATION
     */
    public Location() {

    }

    @Override
    public int getReferMapId() {
        return referId;
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
