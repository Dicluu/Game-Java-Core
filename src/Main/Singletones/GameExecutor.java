package Main.Singletones;

import Main.Items.Item;
import Main.Maps.Map;
import Main.Objects.Characters.Player;
import Main.Objects.Entity;
import Main.Singletones.Utils.AdminCommandManager;
import Main.Singletones.Utils.CommandManager;
import Main.Utils.Messenger;
import Main.Utils.Timers.TimeCounter;

import java.util.Scanner;

public class GameExecutor {

    private static GameExecutor instanceGame;
    private Player currentPlayer;
    private Map currentMap;
    private TimeCounter timecounter;

    private GameExecutor() {}

    public static GameExecutor getGame() {
        if (instanceGame == null) {
            instanceGame = new GameExecutor();
        }
        return instanceGame;
    }


    public void render() throws InterruptedException, CloneNotSupportedException {
        timecounter = new TimeCounter();
        Thread.sleep(10);
        Item.showLastInstance();
        Entity.showLastInstance();
        currentMap.showMap();
        Scanner num = new Scanner(System.in);
        String answer;
        while(true) {
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
                    CommandManager.showInventory(currentPlayer);
                    break;
                case "show inv id":
                    AdminCommandManager.showInventoryId(currentPlayer);
                    break;
                case "show inv hash":
                    AdminCommandManager.showInventoryHash(currentPlayer);
                    break;
                case "show inv uid":
                    AdminCommandManager.showInventoryUID(currentPlayer);
                    break;
                case "give item":
                    AdminCommandManager.giveItem();
                    break;
                case "get":
                    CommandManager.getAction(timecounter);
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
                case "stop":
                    return;
                default:
                    Messenger.ingameMessage("unknown command");
            }
        }
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
}
