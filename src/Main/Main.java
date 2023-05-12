package Main;

import Main.Items.Item;
import Main.Maps.Instances.Forest;
import Main.Maps.Map;
import Main.Objects.Characters.NPC.Dealer;
import Main.Objects.Characters.NPC.Peasant;
import Main.Objects.Characters.Player;
import Main.Objects.Entity;
import Main.Objects.Materials.Material;
import Main.Objects.Materials.Materials;
import Main.Objects.Unique.Building;
import Main.Objects.Unique.Entrance;
import Main.Objects.Unique.Town;
import Main.Singletones.GameExecutor;
import Main.Utils.Messenger;
import Main.Utils.Timers.TimeCounter;

import java.util.HashSet;
import java.util.Set;

public class Main {

    private static boolean stop;
    private static Set<Entity> forest1 = new HashSet<>();
    private static Set<Entity> forest2 = new HashSet<>();

    static {
        forest1.add(new Material(4, 4, Materials.Tree));
        forest1.add(new Material(4,3,Materials.Tree));
        forest1.add(new Material(3,3, Materials.Tree));
        forest1.add(new Material(3,4, Materials.Tree));
        forest1.add(new Entrance(1,1,1));
        forest1.add(new Dealer("test",2, 1));

        forest2.add(new Material(14,4, Materials.Tree));
        forest2.add(new Material(14,3, Materials.Tree));
        forest2.add(new Material(13,4, Materials.Tree));
        forest2.add(new Material(13,3, Materials.Tree));
        forest2.add(new Material(12,4, Materials.Tree));
        forest2.add(new Entrance(1,1,0));
    }

    public static void main(String[] args) throws InterruptedException, CloneNotSupportedException {
        Messenger.setSystem(true);
        Messenger.setHelp(true);
        Map map1 = new Forest(15,5, forest1);
        Map map2 = new Forest(20,5, forest2);
        initiateEntities();
        Entity.showInstancesSystem();
        Item.showInstancesSystem();
        GameExecutor.getGame().setCurrentMap(map1);
        GameExecutor.getGame().setCurrentPlayer(new Player("Dicluu",0,1));
        while (!stop) {
            GameExecutor.getGame().render();
        }
        TimeCounter.setActive(false);
    }

    private static void initiateEntities() {
        new Material(Materials.Tree);
        new Entrance();
        new Player();
        new Dealer();
        new Building();
        new Town();
        new Peasant();
    }

    public static void stop() {
        stop = true;
    }
}
