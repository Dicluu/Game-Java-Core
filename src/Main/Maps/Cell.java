package Main.Maps;

import Main.Objects.Entity;
import Main.Objects.Tile.Tile;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Cell implements Serializable {

    int x,y;
    Set<Entity> objects = new HashSet<>();
    Tile tile;
    char symbol;

    public Cell(int x, int y, Tile tile) {
        this.x = x;
        this.y = y;
        this.tile = tile;
    }

    private void updateSymbol() {
        if (objects.size() > 0) {
            int currentPriority = -1;
            for (Entity object : objects) {
                if (object.getPriority() > currentPriority) {
                    this.symbol = object.getSymbol();
                    currentPriority = object.getPriority();
                }
            }
        }
        else {
            this.symbol = tile.getSymbol();
        }
    }

    public char getSymbol() {
        updateSymbol();
        return this.symbol;
    }

    public void addObject(Entity object) {
        objects.add(object);
    }

    public Set<Entity> getObjects() {
        return objects;
    }

    public void removeObject(Entity object) {
        objects.remove(object);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}