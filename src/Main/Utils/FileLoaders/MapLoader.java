package Main.Utils.FileLoaders;

import Main.Maps.Instances.Forest;
import Main.Maps.Location;
import Main.Maps.Map;
import Main.Objects.Characters.Character;
import Main.Objects.Entity;
import Main.Objects.Unique.Building;
import Main.Objects.Unique.Enterable;
import Main.Utils.Messenger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class MapLoader {

    public static Map loadMap(int ID) {
        try {
            Map map = null;
            File file = new File("src/Main/Resource/Maps/" + ID);
            BufferedReader br = new BufferedReader(new FileReader(file));
            String length = br.readLine();
            String[] args = length.split(":");
            switch (Integer.parseInt(args[0])) {
                case 0:
                    map = new Forest(Integer.parseInt(args[1]), Integer.parseInt(args[2]));
                    break;
                case 1:
                    map = new Location(Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3]));
                    break;
            }
            map.setUID(ID);
            while (br.ready()) {
                String arg = br.readLine();
                String[] es = arg.split(":");
                Entity e = Entity.newInstance(Integer.parseInt(es[0]));
                e.setX(Integer.parseInt(es[1]));
                e.setY(Integer.parseInt(es[2]));
                switch (e.getId()) {
                    case 2:
                        e = setEntrance((Enterable)e, Integer.parseInt(es[3]));
                        break;
                    case 5:
                        e = setBuilding((Building) e, es[3], map.getId());
                }
                if (e instanceof Character) {
                    e = setCharacter((Character) e, es[3]);
                }
                map.setObject(e);
            }
            return map;
        }
        catch (Exception e) {
            Messenger.systemMessage("Exception loadMap()", MapLoader.class);
        }
        return null;
    }

    private static Entity setEntrance(Enterable e, int referID) {
        e.setReferMapId(referID);
        return (Entity) e;
    }

    private static Entity setBuilding(Building b, String name, int ID) {
        return Building.loadBuildingFromFile(b, name, ID);
    }

    private static Entity setCharacter(Character c, String name) {
        c.setName(name);
        return c;
    }
}
