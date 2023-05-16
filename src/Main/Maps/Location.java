package Main.Maps;

import Main.Objects.Tile.Tile;

public class Location extends Map{

    public Location(int x, int y, Tile tile) {
        super(x, y, tile);
    }

    public Location (int x, int y, int tileID) {
        super(x,y, Tile.findById(tileID));
    }

}
