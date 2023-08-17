package Main.Utils.FileLoaders;

import Main.Maps.Instances.Forest;
import Main.Maps.Instances.Interiors;
import Main.Maps.Instances.Town;
import Main.Maps.Map;
import Main.Objects.Characters.Character;
import Main.Objects.Entity;
import Main.Objects.Tile.Tile;
import Main.Objects.Unique.Building;
import Main.Objects.Unique.Enterable;
import Main.Objects.Unique.Location;
import Main.Utils.Messenger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class MapLoader {

    public static Map loadMapById(int ID) {
        return loadMap("src/Main/Resource/Maps/" + ID, ID);
    }

    private static Map loadMap(String path, int ID) {
        try {
            Map map = null;
            File file = new File(path);
            BufferedReader br = new BufferedReader(new FileReader(file));
            String length = br.readLine();
            String description = " ", name = " ", symbol = " ";
            String[] args = length.split(":");
            switch (Integer.parseInt(args[0])) {
                case 0:
                    map = new Forest(Integer.parseInt(args[1]), Integer.parseInt(args[2]));
                    break;
                case 1:
                    map = new Interiors(Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3]));
                    break;
                case 2:
                    map = new Town(Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3]));
            }

            while (br.ready()) {
                String arg = br.readLine();
                String[] es = arg.split(":");
                try {
                    Entity e = Entity.newInstance(Integer.parseInt(es[0]));
                    e.setX(Integer.parseInt(es[1]));
                    e.setY(Integer.parseInt(es[2]));

                    switch (e.getId()) {
                        case 2:
                            e = setEntrance((Enterable) e, Integer.parseInt(es[3]));
                            break;
                        case 5:
                            e = setBuilding((Building) e, es[3], map.getId(), Integer.parseInt(es[4]));
                            break;
                        case 6:
                            e = new Main.Objects.Unique.Town(Integer.parseInt(es[1]), Integer.parseInt(es[2]), Integer.parseInt(es[3]), map.getId());
                            break;
                        case 8:
                            e = new Location(Integer.parseInt(es[1]), Integer.parseInt(es[2]), Integer.parseInt(es[3]), map.getId());
                    }
                    if (e instanceof Character) {
                        e = setCharacter((Character) e, es[3]);
                    }
                    map.setObject(e);
                } catch (NumberFormatException exc) {
                    switch (es[0]) {
                        case "description":
                            description = es[1];
                            break;
                        case "tile":
                            int xf = Integer.parseInt(es[1]);
                            int xt = Integer.parseInt(es[2]);
                            int yf = Integer.parseInt(es[3]);
                            int yt = Integer.parseInt(es[4]);
                            Tile tile = Tile.findById(Integer.parseInt(es[5]));
                            setTile(map, xf, xt, yf, yt, tile);
                            break;
                        case "name":
                            name = es[1];
                            break;
                        case "symbol":
                            symbol = es[1];
                            break;
                    }
                }
            }
            map.tune(ID, description, name, symbol);
            return map;
        } catch (Exception e) {
            Messenger.systemMessage("Exception loadMap()", MapLoader.class);
        }
        return null;
    }

    private static Entity setEntrance(Enterable e, int referID) {
        e.setReferMapId(referID);
        return (Entity) e;
    }

    private static Entity setBuilding(Building b, String name, int ID, int interiorID) {
        return Building.loadBuildingFromFile(b, name, ID, interiorID);
    }

    private static Entity setCharacter(Character c, String name) {
        c.setName(name);
        return c;
    }

    private static void setTile(Map map, int xf, int xt, int yf, int yt, Tile tile) {
        for (int i = xf; i <= xt; i++) {
            for (int j = yf; j <= yt; j++) {
                map.getCell(i, j).setTile(tile);
            }
        }
    }
}
