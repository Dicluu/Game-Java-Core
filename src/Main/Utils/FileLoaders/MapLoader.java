package Main.Utils.FileLoaders;

import Main.Items.Item;
import Main.Maps.Instances.Forest;
import Main.Maps.Instances.Interiors;
import Main.Maps.Map;
import Main.Objects.Characters.Character;
import Main.Objects.Characters.NPC.NonPlayerCharacter;
import Main.Objects.Entity;
import Main.Objects.Materials.Material;
import Main.Objects.Tile.Tile;
import Main.Objects.Unique.*;
import Main.Utils.Messenger;

import java.io.*;
import java.util.HashMap;

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
                    if (ID >= 0) {
                        map = new Forest(Integer.parseInt(args[1]), Integer.parseInt(args[2]));
                    } else {
                        map = new Forest(Integer.parseInt(args[1]), Integer.parseInt(args[2]), ID);
                    }
                    break;
                case 1:
                    map = new Interiors(Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3]));
                    break;
            }
            while (br.ready()) {
                String arg = br.readLine();
                String[] es = arg.split(":");
                try {
                    Entity e = Entity.newInstance(Integer.parseInt(es[0]));
                    HashMap<String, String> EntityData = loadEntityData(es[1]);
                    switch (es[0]) {
                        case "1":
                            e.setCID(Integer.parseInt(EntityData.get("CID")));
                            ((Material) e).setItem(Item.getItemByName(EntityData.get("Item")));
                            ((Material) e).setTOOLID(Integer.parseInt(EntityData.get("Tool")));
                            ((Material) e).setRespawnTime(Long.parseLong(EntityData.get("Respawn")));
                            ((Material) e).setComplexity(Float.parseFloat(EntityData.get("Complexity")));
                            ((Material) e).setSymbol(EntityData.get("Symbol").charAt(0));
                            e.setX(Integer.parseInt(es[2]));
                            e.setY(Integer.parseInt(es[3]));
                            map.setObject(e);
                            break;
                        case "3":
                            int CID = Integer.parseInt(EntityData.get("CID"));
                            e.setCID(CID);
                            e.setX(Integer.parseInt(es[2]));
                            e.setY(Integer.parseInt(es[3]));
                            ((NonPlayerCharacter) e).setCID(CID);
                            ((NonPlayerCharacter) e).load();
                            ((NonPlayerCharacter) e).setSymbol(EntityData.get("Symbol").charAt(0));
                            e.setDescription(EntityData.get("Description"));
                            map.setObject(e);
                            break;
                        case "5":
                            try {
                                e = new Building(Integer.parseInt(es[2]), Integer.parseInt(es[3]), Integer.parseInt(es[4]), Integer.parseInt(es[5]), map.getId(), map.getName());
                                if (EntityData.get("Symbol") != null)
                                    ((Building) e).setSymbol(EntityData.get("Symbol").charAt(0));
                                if (EntityData.get("Description") != null) e.setDescription(EntityData.get("Description"));
                            } catch (ArrayIndexOutOfBoundsException ex) {
                                e = new Building(Integer.parseInt(es[1]), Integer.parseInt(es[2]), Integer.parseInt(es[3]), map.getId(), map.getName());
                            }
                            map.setObject(e);
                            break;
                        case "6":
                            try {
                                e.setCID(Integer.parseInt(es[1]));
                                int x = Integer.parseInt(es[2]);
                                int y = Integer.parseInt(es[3]);
                                e.setX(x);
                                e.setY(y);
                                int MapId = Integer.parseInt(es[4]);
                                ((Entrance) e).setReferMapId(MapId);
                                try {
                                    if (es[5].equals("node")) {
                                        int nodeX = Integer.parseInt(es[6]);
                                        int nodeY = Integer.parseInt(es[7]);
                                        ((Entrance) e).initializeMutualMaps(x, y, nodeX, nodeY, MapId, map.getId());
                                    }
                                } catch (ArrayIndexOutOfBoundsException ex) {
                                }
                            } catch (NumberFormatException exc) {
                                ((Entrance) e).setName(EntityData.get("Name"));
                                ((Entrance) e).setSymbol(EntityData.get("Symbol").charAt(0));
                                e.setDescription(EntityData.get("Description"));
                                ((Entrance) e).setReferMapId(Integer.parseInt(es[5]));
                                int x = Integer.parseInt(es[2]);
                                int y = Integer.parseInt(es[3]);
                                int nodeX = Integer.parseInt(es[6]);
                                int nodeY = Integer.parseInt(es[7]);
                                int MapId = Integer.parseInt(es[5]);
                                e.setX(x);
                                e.setY(y);
                                ((Entrance) e).initializeMutualMaps(x, y, nodeX, nodeY, MapId, map.getId());
                            }
                            map.setObject(e);
                            break;
                        case "7":
                            e.setDescription(EntityData.get("Description"));
                            e.setX(Integer.parseInt(es[2]));
                            e.setY(Integer.parseInt(es[3]));
                            ((Gates) e).setLocked(Boolean.parseBoolean(es[4]));
                            ((Gates) e).setLockedDirection(es[5]);
                            if (EntityData.get("CID") != null) {
                                e.setCID(Integer.parseInt(EntityData.get("CID")));
                                e.setObjectID(Integer.parseInt(EntityData.get("CID")));
                            }
                            map.setObject(e);
                            break;
                        default:
                            e.setSymbol(EntityData.get("Symbol").charAt(0));
                            if (EntityData.get("Description") != null) e.setDescription(EntityData.get("Description"));
                            e.setCID(Integer.parseInt(es[2]));
                            e.setX(Integer.parseInt(es[3]));
                            e.setY(Integer.parseInt(es[4]));
                            map.setObject(e);
                    }
                } catch (ArrayIndexOutOfBoundsException | NumberFormatException | CloneNotSupportedException e) {
                    switch (es[0]) {
                        case "Description":
                            map.setDescription(es[1]);
                            break;
                    }
                } catch (IOException e) {
                    Messenger.systemMessage("IOException caught with string '" + arg + "' with mapID = " + ID + " in loadMap()", MapLoader.class);
                }
            }

            return map;
        } catch (IOException e) {
            Messenger.systemMessage("IOException loadMap()", MapLoader.class);
        }
        /*
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
                    break;
                case 3:
                    map = new Main.Maps.Instances.Location(Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3]));
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
                            //e = setBuilding((Building) e, es[3], map.getId(), Integer.parseInt(es[4]));
                            try {
                                e = new Building(Integer.parseInt(es[1]), Integer.parseInt(es[2]), Integer.parseInt(es[4]), Integer.parseInt(es[5]), map.getId(), map.getName());
                            }
                            catch (ArrayIndexOutOfBoundsException ex) {
                                e = new Building(Integer.parseInt(es[1]), Integer.parseInt(es[2]), Integer.parseInt(es[4]), map.getId(), map.getName());
                            }
                            break;
                        case 6:
                            //e = new Main.Objects.Unique.Town(Integer.parseInt(es[1]), Integer.parseInt(es[2]), Integer.parseInt(es[3]), map.getId());
                            break;
                        case 8:
                            //e = new Location(Integer.parseInt(es[1]), Integer.parseInt(es[2]), Integer.parseInt(es[3]), map.getId());
                    }
                    if (e instanceof Character) {
                        e = setCharacter((Character) e, Integer.parseInt(es[3]));
                    }
                    map.setObject(e);
                } catch (NumberFormatException exc) {
                    Entity e;
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
                        case "unique":
                            e = Entity.newInstance(Integer.parseInt(es[1]));
                            e.setCID(Integer.parseInt(es[2]));
                            e.setX(Integer.parseInt(es[3]));
                            e.setY(Integer.parseInt(es[4]));
                            map.setObject(e);
                            break;
                        case "gates":
                            e = Entity.newInstance(Integer.parseInt(es[1]));
                            e.setCID(Integer.parseInt(es[2]));
                            e.setX(Integer.parseInt(es[3]));
                            e.setY(Integer.parseInt(es[4]));
                            ((Gates)e).setLocked(Boolean.parseBoolean(es[5]));
                            ((Gates)e).setLockedDirection(es[6]);
                            map.setObject(e);
                    }
                }
            }
            map.tune(ID, description, name, symbol);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            Messenger.systemMessage("Exception loadMap()", MapLoader.class);
        }


         */
        return null;
    }

    private static HashMap<String, String> loadEntityData(String name) throws IOException {
        try {
            String path = "src/Main/Resource/Entities/Instances/" + name + ".txt";
            HashMap<String, String> data = new HashMap<>();
            File file = new File(path);
            BufferedReader br = new BufferedReader(new FileReader(file));
            while (br.ready()) {
                String str = br.readLine();
                String[] args = str.split(":");
                data.put(args[0], args[1]);
            }
            return data;
        } catch (IOException e) {
            try {
                Integer.parseInt(name);
            } catch (NumberFormatException exc) {
                throw new IOException();
            }
            return new HashMap<>();
        }
    }

    private static Entity setEntrance(Enterable e, int referID) {
        e.setReferMapId(referID);
        return (Entity) e;
    }

    private static Entity setBuilding(Building b, String name, int ID, int interiorID) {
        return Building.loadBuildingFromFile(b, name, ID, interiorID);
    }

    private static Entity setCharacter(Character c, int CID) {
        c.setCID(CID);
        return c;
    }

    private static void setTile(Map map, int xf, int xt, int yf, int yt, Tile tile) {
        try {
            for (int i = xf; i <= xt; i++) {
                for (int j = yf; j <= yt; j++) {
                    map.getCell(i, j).setTile(tile);
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            Messenger.systemMessage("ArrayIndexOutOfBoundsException caught in setTile()", MapLoader.class);
        }
    }
}
