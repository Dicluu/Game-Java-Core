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
import Main.Utils.FileLoaders.MapLoader;
import Main.Utils.Messenger;
import Main.Utils.Timers.TimeCounter;

import java.util.HashSet;
import java.util.Set;

public class Main {

    private static void initiateEntities() {
        new Material(Materials.Tree);
        new Entrance();
        new Player();
        new Dealer();
        new Building();
        new Town();
        new Peasant();
    }

    public static void main(String[] args) throws InterruptedException, CloneNotSupportedException {
        Messenger.setSystem(true);
        Messenger.setHelp(true);
        initiateEntities();
        Map map1 = MapLoader.loadMap(0);
        Map map2 = MapLoader.loadMap(-1);
        Entity.showInstancesSystem();
        Item.showInstancesSystem();
        GameExecutor.getGame().setCurrentMap(map1);
        GameExecutor.getGame().setCurrentPlayer(new Player("Dicluu",0,1));
        GameExecutor.getGame().render();
        TimeCounter.setActive(false);
    }

}
