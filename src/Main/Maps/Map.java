package Main.Maps;

import Main.Objects.Entity;
import Main.Objects.Tile.Tile;
import Main.Utils.Messenger;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public abstract class Map {

    private int x,y,id;
    private Cell[][] map;
    private Tile tile;
    private static int freeId;
    private static List<Map> allMaps = new ArrayList<>();


    public Map(int x, int y, Tile tile) {
        this.x = x;
        this.y = y;
        this.tile = tile;
        this.map = new Cell[y][x];
        this.id = freeId;
        generateBlank();
        freeId++;
        allMaps.add(this);
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
        allMaps.add(this);
    }

    private void generateEntities(Set<Entity> entities) {
        for (Entity entity : entities) {
            setObject(entity);
        }
    }

    public void generateBlank() {
        for (int i = 0; i < y; i++) {
            for (int j = 0; j < x; j++) {
                map[i][j] = new Cell(x,y,tile);
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

    public static Map getMapById(int id) {
        try {
            return allMaps.get(id);
        }
        catch (NullPointerException e) {
            Messenger.systemMessage("InputMismatchException catched in getMapById(int id))", Map.class);
            return null;
        }
    }
}
