package Main.Objects.Unique;


import Main.Maps.Cell;
import Main.Maps.Map;
import Main.Objects.Characters.Player;
import Main.Objects.Entity;
import Main.Singletones.GameExecutor;
import Main.Utils.Messenger;

public class Building extends UniqueEntity implements Enterable {

    private final char symbol = "b".charAt(0);
    private final static int ID = 5;
    private String name = "Building";
    private int referId;
    private Cell node = null;

    /**
     * Constructors for unique interiors
     * @param x
     * @param y
     * @param name
     * @param referId must to referring to unique maps (must be less than zero)
     */
    public Building(int x, int y, String name, int referId) {
        super(x, y);
        this.name = name;
        if (referId <= 0) {
            this.referId = referId;
            this.node = Map.getMapById(referId).getCell(2, 0);
        } else {
            Messenger.ingameMessage("This Building referring to not unique map");
        }
    }

    /**
     * constructor for default unique interior
     * @param x
     * @param y
     * @param name
     */
    public Building(int x, int y, String name) {
        super(x, y);
        this.name = name;
        referId = Map.generateLocations("building",x,y).getId();
        this.node = Map.getMapById(referId).getCell(0, 2);
    }

    /**
     * static pseudo-constructor for load building from file
     * @param b
     * @param name
     * @param mapID
     * @return
     */
    public static Building loadBuildingFromFile(Building b, String name, int mapID) {
        b.setName(name);
        int referId = Map.generateDefaultBuildingFromFile("building", b.getX(), b.getY(), mapID).getId();
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
