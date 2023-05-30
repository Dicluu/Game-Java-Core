package Main.Singletones;

import Main.Items.Item;
import Main.Maps.Map;
import Main.Objects.Characters.Character;
import Main.Objects.Characters.NPC.Speech;
import Main.Objects.Characters.Player.Player;
import Main.Objects.Characters.Player.Quest;
import Main.Objects.Entity;
import Main.Singletones.Utils.*;
import Main.Utils.Annotations.NeedImprovement;
import Main.Utils.Annotations.NeedRevision;
import Main.Utils.FileLoaders.ScriptLoader;
import Main.Utils.Messenger;
import Main.Utils.Timers.TimeCounter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Scanner;

public class GameExecutor implements Serializable {

    private static GameExecutor instanceGame;
    private Player currentPlayer;
    private Map currentMap;
    private TimeCounter timecounter;
    private HashMap<Integer, Map> maps;
    private HashMap<Integer, Entity> entities;
    private HashMap<Integer, Character> characters;
    private QuestLineManager qm;

    private GameExecutor() {
        maps = Map.getAllMaps();
        entities = Entity.getAllEntities();
        characters = Character.getAllCharacters();
        qm = new QuestLineManager();
        initiateQuestLine(QuestLineManager.getQuestById(0));
    }

    public static GameExecutor getGame() {
        if (instanceGame == null) {
            instanceGame = new GameExecutor();

        }
        return instanceGame;
    }


    @NeedRevision(comment = "review case \"load\" ")
    @NeedRevision(comment = "review initiateQuest() because it's initiating already passed quests")
    @NeedRevision(comment = "make speeches unavailable after initiating quest")
    @NeedImprovement(comment = "make a command 'inspect' that will provide description of a entity")
    public void render() throws InterruptedException, CloneNotSupportedException {
        timecounter = new TimeCounter();
        Thread.sleep(10);
        Item.showLastInstance();
        Entity.showLastInstance();
        currentMap.showMap();
        Scanner num = new Scanner(System.in);
        String answer;
        while(true) {
            qm.touchQuests(GameExecutor.getGame().getCurrentPlayer().getJournal());
            answer = num.nextLine();
            switch(answer) {
                case "w":
                    CommandManager.move("up");
                    break;
                case "s":
                    CommandManager.move("down");
                    break;
                case "d":
                    CommandManager.move("right");
                    break;
                case "a":
                    CommandManager.move("left");
                    break;
                case "show inv":
                    CommandManager.showInventory(GameExecutor.getGame().getCurrentPlayer());
                    break;
                case "show inv id":
                    AdminCommandManager.showInventoryId(GameExecutor.getGame().getCurrentPlayer());
                    break;
                case "show inv hash":
                    AdminCommandManager.showInventoryHash(GameExecutor.getGame().getCurrentPlayer());
                    break;
                case "show inv uid":
                    AdminCommandManager.showInventoryUID(GameExecutor.getGame().getCurrentPlayer());
                    break;
                case "give item":
                    AdminCommandManager.giveItem();
                    break;
                case "get":
                    CommandManager.getAction(GameExecutor.getGame().getTimecounter());
                    break;
                case "come":
                    CommandManager.come();
                    break;
                case "show":
                    CommandManager.showMap();
                    break;
                case "set object":
                    AdminCommandManager.setObject();
                    break;
                case "get info":
                    AdminCommandManager.getInfo();
                    break;
                case "entities":
                    AdminCommandManager.showAllEntities();
                    break;
                case "characters":
                    AdminCommandManager.showCharacters();
                    break;
                case "items":
                    AdminCommandManager.showAllItems();
                    break;
                case "switch":
                    CommandManager.switchSettings();
                    break;
                case "show wallet":
                    CommandManager.showWallet();
                    break;
                case "change wallet":
                    AdminCommandManager.changeWallet();
                    break;
                case "talk":
                    CommandManager.talk();
                    break;
                case "trade":
                    AdminCommandManager.trade(); // only for testing
                    break;
                case "animation":
                    AdminCommandManager.playAnimation(); // only for testing
                    break;
                case "show speeches" :
                    AdminCommandManager.showSpeeches();
                    break;
                case "j":
                    CommandManager.showJournal();
                    break;
                case "quest":
                    ScriptLoader.loadQuest(0);
                    break;
                case "start quest":

                    break;
                case "load":
                    TimeCounter.setActive(false);
                    instanceGame = SaveManager.load();
                    Map.setAllMaps(instanceGame.maps);
                    Entity.setAllEntities(instanceGame.entities);
                    Character.setAllCharacters(instanceGame.characters);
                    qm = instanceGame.qm;
                    Messenger.ingameMessage("last auto-save loaded");
                    CommandManager.showMap();
                    instanceGame.timecounter.restart();
                    break;
                case "stop":
                    entities = Entity.getAllEntities();
                    maps = Map.getAllMaps();
                    characters = Character.getAllCharacters();
                    SaveManager.save();
                    Messenger.ingameMessage("Auto saved confirmed");
                    return;
                default:
                    Messenger.ingameMessage("unknown command");
            }
        }
    }

    public static void initiateDialogue(Character companion, Speech speech) {
        DialogueExecutor.start(GameExecutor.getGame().getCurrentPlayer(), companion, speech);
    }

    public static void initiateTrade(Character companion) {
        TradeExecutor.start(GameExecutor.getGame().getCurrentPlayer(), companion);
    }

    public void initiateQuestLine(Quest quest) {
        qm.startQuestLine(quest);
    }

    public void setCurrentPlayer (Player player) {
        currentMap.setObject(player);
        this.currentPlayer = player;
    }

    public void setCurrentMap(Map currentMap) {
        this.currentMap = currentMap;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Map getCurrentMap() {
        return currentMap;
    }

    public TimeCounter getTimecounter() {
        return timecounter;
    }

    public QuestLineManager getQuestLineManager() {
        return qm;
    }

    public static void setGame(GameExecutor instanceGame) {
        GameExecutor.instanceGame = instanceGame;
    }
}
