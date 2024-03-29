package Main.Maps;

import Main.Maps.Instances.Interiors;
import Main.Objects.Entity;
import Main.Objects.Tile.Tile;
import Main.Objects.Unique.Entrance;
import Main.Singletones.GameExecutor;
import Main.Utils.Annotations.NeedRevision;
import Main.Utils.FileLoaders.MapLoader;
import Main.Utils.Messenger;

import java.io.Serializable;
import java.util.*;

public abstract class Map implements Serializable {

    private int x,y,id, cid;
    private Cell[][] map;
    private Tile tile;
    private static int freeId;
    private static HashMap<Integer, Map> allMaps = new HashMap();
    private String description;
    private int scale;
    private String name;
    private char delegateSymbol;


    public Map(int x, int y, Tile tile, int scale) {
        this.x = x;
        this.y = y;
        this.tile = tile;
        this.scale = scale;
        this.map = new Cell[y][x];
        this.id = freeId;
        generateBlank();
        freeId++;
        allMaps.put(id,this);
    }

    public Map(int x, int y, Tile tile, int scale, int CID) {
        this.x = x;
        this.y = y;
        this.tile = tile;
        this.scale = scale;
        this.map = new Cell[y][x];
        this.id = CID;
        generateBlank();
        allMaps.put(id,this);
    }

    public Map(int x, int y, Tile tile, Set<Entity> entities) {
        this.x = x;
        this.y = y;
        this.tile = tile;
        this.map = new Cell[y][x];
        this.id = freeId;
        generateBlank();
        generateEntities(entities);
        freeId++;
        allMaps.put(id,this);
    }

    private void generateEntities(Set<Entity> entities) {
        for (Entity entity : entities) {
            setObject(entity);
        }
    }

    public void generateBlank() {
        for (int i = 0; i < y; i++) {
            for (int j = 0; j < x; j++) {
                map[i][j] = new Cell(j,i,tile);
            }
        }
    }

    public void showMap() {
        for (int i = 0; i < y; i++) {
            for (int j = 0; j < x; j++) {
                System.out.print(map[i][j].getSymbol() + " ");
            }
            System.out.println(" ");
        }
    }

    public void setObject(Entity object) {
        map[object.getY()][object.getX()].addObject(object);
    }

    public Set<Entity> getObjects(int x, int y) {
        return map[y][x].getObjects();
    }

    public void clearCellsFromObject(Entity object) {
        for (int i = 0; i < y; i++) {
            for (int j = 0; j < x; j++) {
                if (map[i][j] != map[object.getY()][object.getX()]) {
                    map[i][j].removeObject(object);
                }
            }
        }
    }

    public Cell getCell(int x, int y) {
        return map[y][x];
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public char getDelegateSymbol() {
        return delegateSymbol;
    }

    public int getScale() {
        return scale;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }

    @NeedRevision(comment = "review auto increment system")
    public static Map getMapById(int id) {
        try {
            return allMaps.get(id);
        }
        catch (NullPointerException e) {
            Messenger.systemMessage("InputMismatchException caught in getMapById(int id))", Map.class);
            return null;
        }
    }

    public static HashMap<Integer, Map> getAllMaps() {
        return allMaps;
    }

    public static void setAllMaps(HashMap<Integer, Map> allMaps) {
        freeId = allMaps.keySet().stream().mapToInt(Integer::intValue).max().getAsInt();
        Map.allMaps = allMaps;
    }

    /**
     * generating building from game
     * @param name
     * @param x
     * @param y
     * @return
     */
    public static Map generateLocation(int ID, int x, int y) {
                GameExecutor ge = GameExecutor.getGame();
                Map cm = ge.getCurrentMap();
                Interiors l = (Interiors) MapLoader.loadMapById(ID);
                l.setObject(new Entrance(0, 2, cm.getId(), x, y));
                return l;
        }

    /**
     * generating default building from file
     * @param name
     * @param x
     * @param y
     * @param mapID
     * @return
     */
    public static Map generateBuildingFromFile(String name, int x, int y, int mapID, int interiorID) {
        Interiors l = (Interiors) MapLoader.loadMapById(interiorID);
        l.setObject(new Entrance(0, 2, mapID, x, y));
        return l;
    }

    public void tune(int id, String... args) {
        this.cid = id;
        if (cid < 0) {
            allMaps.remove(id);
            this.id = cid;
            allMaps.put(id, this);
        }
        try {
            this.description = args[0];
            this.name = args[1];
            this.delegateSymbol = args[2].charAt(0);
        }
        catch (ArrayIndexOutOfBoundsException e) {

        }
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
