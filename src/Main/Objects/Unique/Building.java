package Main.Objects.Unique;


import Main.Maps.Cell;
import Main.Maps.Map;
import Main.Objects.Characters.Player;
import Main.Objects.Entity;
import Main.Singletones.GameExecutor;

public class Building extends UniqueEntity implements Enterable {

    private final char symbol = "b".charAt(0);
    private final static int ID = 5;
    private String name = "Building";
    private int referId;
    private Cell node = null;

    public Building(int x, int y, String name, int referId) {
        super(x, y);
        this.name = name;
        this.referId = referId;
        this.node = Map.getMapById(referId).getCell(2, 0);
    }

    public Building(int x, int y, String name) {
        super(x, y);
        this.name = name;
        referId = Map.generateLocations("building",x,y).getId();
        this.node = Map.getMapById(referId).getCell(0, 2);
    }

    static {
        try {
            Entity.addInstance(ID, Building.class);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * ONLY FOR INITIALIZATION
     */
    public Building() {

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

    public static Building loadBuildingFromFile(Building b, String name, int mapID) {
        b.setName(name);
        int referId = Map.generateDefaultBuildingFromFile("building", b.getX(), b.getY(), mapID).getId();
        b.setReferMapId(referId);
        Cell node = Map.getMapById(referId).getCell(0, 2);
        b.setNode(node);
        return b;
    }
}
