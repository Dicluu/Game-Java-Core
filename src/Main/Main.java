package Main;

import Main.Items.Item;
import Main.Maps.Instances.Forest;
import Main.Maps.Map;
import Main.Objects.Characters.Character;
import Main.Objects.Characters.NPC.NonPlayerCharacter;
import Main.Objects.Characters.Player.Player;
import Main.Objects.Entity;
import Main.Objects.Materials.Material;
import Main.Objects.Materials.Materials;
import Main.Objects.Unique.*;
import Main.Singletones.GameExecutor;
import Main.Utils.Annotations.NeedImprovement;
import Main.Utils.FileLoaders.ItemLoader;
import Main.Utils.FileLoaders.MapLoader;
import Main.Utils.FileLoaders.ScriptLoader;
import Main.Utils.Messenger;
import Main.Utils.Timers.TimeCounter;

import java.util.Locale;

public class Main {

    private static void initiateEntities() {
        new Material();
        new Character();
        new NonPlayerCharacter();
        new UniqueEntity();
        new Building();
        new Entrance();
        new Gates();
        new IrresistibleEntity();
        new RedirectingEntity();
    }

    @NeedImprovement(comment = "make a class that will scan directories and getting all " +
            "@NeedImprovement and @NeedRevision annotations")
    public static void main(String[] args) throws InterruptedException, CloneNotSupportedException {
        Messenger.setSystem(true);
        Messenger.setHelp(true);
        initiateEntities();
        //Map map1 = MapLoader.loadMapById(0);
        MapLoader.loadMapById(-2);
        Map map2 = MapLoader.loadMapById(-1);
        Map map1 = MapLoader.loadMapById(-7);
        Entity.showInstancesSystem();
        //Item.showInstancesSystem();
        //TEMPORARY ON TESTING
        // pre-initializing GameExecutor
        GameExecutor.getGame();
        GameExecutor.getGame().setCurrentMap(map1);
        GameExecutor.getGame().setCurrentPlayer(new Player("Dicluu",0,1));
        GameExecutor.getGame().render();
        TimeCounter.setActive(false);
    }

}
