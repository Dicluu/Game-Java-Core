package Main.Maps;

import Main.Objects.Characters.Player;
import Main.Objects.Entity;
import Main.Objects.Tile.Tile;
import Main.Objects.Unique.Entrance;
import Main.Singletones.GameExecutor;
import Main.Utils.Annotations.NeedRevision;
import Main.Utils.Messenger;

import java.io.Serializable;
import java.util.*;
import java.util.stream.IntStream;

public abstract class Map implements Serializable {

    private int x,y,id;
    private Cell[][] map;
    private Tile tile;
    private static int freeId;
    @NeedRevision(comment = "maybe gameExecutor should to store map list")
    private static HashMap<Integer, Map> allMaps = new HashMap();


    public Map(int x, int y, Tile tile) {
        this.x = x;
        this.y = y;
        this.tile = tile;
        this.map = new Cell[y][x];
        this.id = freeId;
        generateBlank();
        freeId++;
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
        for (int i = 0; i < allMaps.size(); i++) {
            if (allMaps.get(i).getId() > freeId) {
                freeId = allMaps.get(i).getId() + 1;
            }
        }
        Map.allMaps = allMaps;
    }

    public static Map generateLocations(String name, int x, int y) {
        switch (name) {
            case "building":
                GameExecutor ge = GameExecutor.getGame();
                Map cm = ge.getCurrentMap();
                Location l = new Location(5,5,Tile.ROCK);
                l.setObject(new Entrance(0, 2, cm.getId(), x, y));
                return l;
        }
        return null;
    }

    public static Map generateDefaultBuildingFromFile(String name, int x, int y, int mapID) {
        Location l = new Location(5,5,Tile.ROCK);
        l.setObject(new Entrance(0, 2, mapID, x, y));
        return l;
    }

    public void setUID(int id) {
        this.id = id;
        allMaps.put(id, this);
    }
}
