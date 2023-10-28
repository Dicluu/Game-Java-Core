package Main.Objects.Unique;


import Main.Maps.Cell;
import Main.Maps.Map;
import Main.Objects.Entity;
import Main.Utils.FileLoaders.MapLoader;
import Main.Utils.Messenger;

public class Building extends UniqueEntity implements Enterable {

    private char symbol = "b".charAt(0);
    private final static int ID = 5;
    private String name = "Building";
    private int referId;
    private Cell node = null;

    /**
     * Constructors for unique interiors
     * @param x
     * @param y
     * @param name
     * @param mapToID must to referring to unique maps (must be less than zero)
     */
    public Building(int x, int y, int mapToID, int CID, int mapFromID, String name) {
        super(x, y, ID);
        Map map = MapLoader.loadMapById(mapToID);
        referId = map.getId();
        this.node = Map.getMapById(referId).getCell(0, 2);
        Entrance exit = new Entrance(0,2, mapFromID);
        exit.setNode(x,y);
        super.setCID(CID);
        node.addObject(exit);
    }

    public Building(int x, int y, int mapToID, int mapFromID, String name) {
        super(x, y, ID);
        Map map = MapLoader.loadMapById(mapToID);
        this.name = map.getName();
        referId = map.getId();
        this.node = Map.getMapById(referId).getCell(0, 2);
        Entrance exit = new Entrance(0,2, mapFromID);
        exit.setNode(x,y);
        node.addObject(exit);
    }

    /**
     * constructor for default unique interior
     * @param x
     * @param y
     * @param name
     */
    public Building(int x, int y, int ID) {
        super(x, y, ID);
        this.name = name;
        referId = Map.generateLocation(ID,x,y).getId();
        this.node = Map.getMapById(referId).getCell(0, 2);
    }

    /**
     * static pseudo-constructor for load building from file
     * @param b
     * @param name
     * @return
     */
    public static Building loadBuildingFromFile(Building b, String name, int mapID, int interiorID) {
        b.setName(name);
        int referId = Map.generateBuildingFromFile("building", b.getX(), b.getY(), mapID, interiorID).getId();
        b.setReferMapId(referId);
        Cell node = Map.getMapById(referId).getCell(0, 2);
        b.setNode(node);
        return b;
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

    public void setSymbol(char symbol) {
        this.symbol = symbol;
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
