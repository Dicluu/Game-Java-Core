package Main;

import Main.Maps.Instances.Forest;
import Main.Maps.Map;
import Main.Objects.Characters.Player;
import Main.Objects.Entity;
import Main.Objects.Materials.Tree;
import Main.Objects.Unique.Entrance;
import Main.Singletones.GameExecutor;
import Main.Utils.Messenger;
import Main.Utils.Timers.TimeCounter;

import java.util.HashSet;
import java.util.Set;

public class Main {

    private static Set<Entity> forest1 = new HashSet<>();
    private static Set<Entity> forest2 = new HashSet<>();

    static {
        forest1.add(new Tree(4,4));
        forest1.add(new Tree(4,3));
        forest1.add(new Tree(3,3));
        forest1.add(new Tree(3,4));
        forest1.add(new Entrance(1,1,1));

        forest2.add(new Tree(14,4));
        forest2.add(new Tree(14,3));
        forest2.add(new Tree(13,4));
        forest2.add(new Tree(13,3));
        forest2.add(new Tree(12,4));
        forest2.add(new Entrance(1,1,0));
    }

    public static void main(String[] args) {
        Messenger.setSystem(true);
        Messenger.setHelp(true);
        Map map1 = new Forest(15,5, forest1);
        Map map2 = new Forest(20,5, forest2);
        GameExecutor.getGame().setCurrentMap(map1);
        GameExecutor.getGame().setCurrentPlayer(new Player("Dicluu",0,1));
        GameExecutor.getGame().render();
        TimeCounter.setActive(false);
    }
}
