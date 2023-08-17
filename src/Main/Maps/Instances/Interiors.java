package Main.Maps.Instances;

import Main.Maps.Map;
import Main.Objects.Tile.Tile;

public class Interiors extends Map {

    public Interiors(int x, int y, Tile tile) {
        super(x, y, tile, 10);
    }

    public Interiors(int x, int y, int tileID) {
        super(x,y, Tile.findById(tileID),10);
    }

}
