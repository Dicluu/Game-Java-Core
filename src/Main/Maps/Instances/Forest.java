package Main.Maps.Instances;

import Main.Maps.Map;
import Main.Objects.Entity;
import Main.Objects.Tile.Tile;

import java.util.Set;

public class Forest extends Map {

    public Forest(int x, int y) {
        super(x, y, Tile.GRASS, 100);
    }

    public Forest(int x, int y, int CID) {
        super(x, y, Tile.GRASS, 100, CID);
    }


    public Forest(int x, int y, Set<Entity> entities) {
        super(x, y, Tile.GRASS, entities);
    }
}
