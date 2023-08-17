package Main.Maps.Instances;

import Main.Maps.Map;
import Main.Objects.Tile.Tile;

public class Town extends Map {

    public Town(int x, int y, Tile tile) {
        super(x, y, tile, 50);
    }

    public Town(int x, int y, int tileID) {
        super(x,y, Tile.findById(tileID), 50);
    }
}
