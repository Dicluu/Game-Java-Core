package Main.Maps.Instances;

import Main.Maps.Map;
import Main.Objects.Tile.Tile;

public class Location extends Map {

    public Location(int x, int y, Tile tile) {
        super(x, y, tile,20);
    }

    public Location(int x, int y, int tileID) {
        super(x,y, Tile.findById(tileID),20);
    }

}
