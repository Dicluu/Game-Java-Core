package Main;

import Main.Items.Item;
import Main.Maps.Map;
import Main.Objects.Characters.NPC.Dealer;
import Main.Objects.Characters.NPC.Peasant;
import Main.Objects.Characters.Player.Player;
import Main.Objects.Entity;
import Main.Objects.Materials.Material;
import Main.Objects.Materials.Materials;
import Main.Objects.Unique.Building;
import Main.Objects.Unique.Entrance;
import Main.Objects.Unique.Town;
import Main.Singletones.GameExecutor;
import Main.Utils.Annotations.NeedImprovement;
import Main.Utils.FileLoaders.MapLoader;
import Main.Utils.Messenger;
import Main.Utils.Timers.TimeCounter;

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

    @NeedImprovement(comment = "make a class that will scan directories and getting all " +
            "@NeedImprovement and @NeedRevision annotations")
    public static void main(String[] args) throws InterruptedException, CloneNotSupportedException {
        Messenger.setSystem(true);
        Messenger.setHelp(true);
        initiateEntities();
        Map map1 = MapLoader.loadMap(0);
        Map map2 = MapLoader.loadMap(-1);
        Entity.showInstancesSystem();
        Item.showInstancesSystem();
        //TEMPORARY ON TESTING
        // pre-initializing GameExecutor
        GameExecutor.getGame();
        // setting NPC with quest
        map1.setObject(new Dealer("QUEST TEST", 2,3,-2, 0));
        GameExecutor.getGame().setCurrentMap(map1);
        GameExecutor.getGame().setCurrentPlayer(new Player("Dicluu",0,1));
        GameExecutor.getGame().render();
        TimeCounter.setActive(false);
    }

}
