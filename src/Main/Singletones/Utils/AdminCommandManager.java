package Main.Singletones.Utils;

import Main.Items.Item;
import Main.Maps.Cell;
import Main.Maps.Map;
import Main.Objects.Characters.Character;
import Main.Objects.Characters.NPC.NonPlayerCharacter;
import Main.Objects.Characters.Player.Player;
import Main.Objects.Entity;
import Main.Objects.Unique.Building;
import Main.Objects.Unique.Enterable;
import Main.Objects.Unique.Entrance;
import Main.Objects.Unique.UniqueEntity;
import Main.Singletones.GameExecutor;
import Main.Utils.Annotations.NeedRevision;
import Main.Utils.Messenger;
import Main.Utils.Timers.Timer;

import java.util.*;

/**
 * handler of admin commands which written as an answer of GameExecutor's render
 */
public class AdminCommandManager {

    @NeedRevision(comment = "Make setObject() only for objects and divide Characters and Unique to other methods")
    public static void setObject() {
        try {
            Scanner num = new Scanner(System.in);
            Messenger.ingameMessage("write id of object you want to set");
            Entity.showInstances();
            int id = num.nextInt();
            if (Entity.getObjectById(id) == null) {
                Messenger.ingameMessage("object with this id not found");
                throw new NullPointerException();
            }
            Messenger.ingameMessage("write x coordinate");
            int x = num.nextInt();
            if (GameExecutor.getGame().getCurrentMap().getX() < x || x < 0) {
                Messenger.ingameMessage("x coordinate is wrong");
                throw new ArrayIndexOutOfBoundsException();
            }
            Messenger.ingameMessage("write y coordinate");
            int y = num.nextInt();
            if (GameExecutor.getGame().getCurrentMap().getY() < y || y < 0) {
                Messenger.ingameMessage("y coordinate is wrong");
                throw new ArrayIndexOutOfBoundsException();
            }
            Entity newObject = Entity.getObjectById(id);
            if (newObject instanceof Enterable) {
                setEntrance(x, y, newObject);
            } else {
                newObject.setX(x);
                newObject.setY(y);
                GameExecutor.getGame().getCurrentMap().setObject(Entity.newInstance(newObject.getId()));
                Messenger.ingameMessage("object " + newObject + " has been set on coordinates " + "x = " + x + " y = " + y);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            Messenger.systemMessage("ArrayIndexOutOfBoundsException in setObject() caught ", AdminCommandManager.class);
        } catch (NullPointerException e) {
            Messenger.systemMessage("NullPointerException in setObject() caught ", AdminCommandManager.class);
        } catch (InputMismatchException e) {
            Messenger.ingameMessage("You wrote wrong value");
            Messenger.systemMessage("InputMismatchException int setObject() caught", AdminCommandManager.class);
        } catch (CloneNotSupportedException e) {
            Messenger.systemMessage("CloneNotSupportedException int setObject() caught", AdminCommandManager.class);
        }
    }

    public static void getInfo() {
        Player cp = GameExecutor.getGame().getCurrentPlayer();
        Map cm = GameExecutor.getGame().getCurrentMap();
        Messenger.systemMessage("Cell objects: ");
        for (Entity object : cp.getCurrentCell().getObjects()) {
            System.out.println(object);
            System.out.println(object.getId() + " " + object);
        }
        Messenger.systemMessage("Tile of cell: " + cp.getCurrentCell().getTile());
    }

    public static void showCharacters() {
        Set<Character> characters = getCharacters();
        for (Character character : characters) {
            System.out.println(character.getUID() + " " + character.getId() + " " + character);
        }
    }

    @NeedRevision(comment = "review try-catch structure")
    protected static Set<Character> getCharacters() {
        Collection<Entity> entities = Entity.getAllEntities().values();
        Set<Character> characters = new HashSet<>();
        for (Entity entity : entities) {
            try {
                Character ce = (Character) entity;
                ce.getWallet();
                characters.add(ce);
            } catch (Exception e) {

            }
        }
        return characters;
    }

    public static void setEntrance(int x, int y, Entity e) {
        boolean isUniqueExist = false;
        Cell cc = GameExecutor.getGame().getCurrentMap().getCell(x,y);
        for (Entity entity : cc.getObjects()) {
            if (entity instanceof UniqueEntity) {
                isUniqueExist = true;
                break;
            }
        }
        if (!isUniqueExist) {
            switch (e.getId()) {
                case 2:
                    setPortal(x, y);
                    break;
                case 5:
                    setBuilding(x,y);
                    break;
            }
        }
        else {
            Messenger.ingameMessage("Unique entity already exist on this cell");
        }
    }

    private static void setPortal(int x, int y) {
            Scanner num = new Scanner(System.in);
            Messenger.ingameMessage("Write id map what entrance have to refer");
            int id = num.nextInt();
            if (Map.getMapById(id) != null) {
                GameExecutor.getGame().getCurrentMap().setObject(new Entrance(x, y, id));
                Messenger.ingameMessage("You set portal on x = " + x + " and y = " + y);
            } else {
                Messenger.ingameMessage("You wrote wrong id of map");
            }
    }

    private static void setBuilding(int x, int y) {
        Scanner str = new Scanner(System.in);
        Scanner num = new Scanner(System.in);
        Cell cc = GameExecutor.getGame().getCurrentMap().getCell(x,y);
        Messenger.ingameMessage("Name a building");
        String name = str.nextLine();
        Messenger.ingameMessage("Write id map what entrance have to refer or 1 to set default map");
        try {
            int id = num.nextInt();
            if (Map.getMapById(id) != null) {
                cc.addObject(new Building(x, y, id));
                Messenger.ingameMessage("You set building on x = " + x + " and y = " + y);
            } else {
                Messenger.ingameMessage("You wrote wrong id of map");
                return;
            }
        }
        catch (NumberFormatException e) {
            Messenger.ingameMessage("You wrote incorrect number");
            Messenger.systemMessage("NumberFormatException in setBuilding()", AdminCommandManager.class);
        }
        catch (ClassCastException e) {
            Messenger.ingameMessage("You wrote ID of Unique map, not a location, try different");
            Messenger.systemMessage("ClassCastException in setBuilding()", AdminCommandManager.class);
        }
    }

    public static void showAllEntities() {
        Collection<Entity> allEntities = Entity.getAllEntities().values();
        for (Entity entity : allEntities) {
            Messenger.systemMessage(entity.getObjectID() + ") "
                    + entity.getId() + " "
                    + entity.getSymbol() + " "
                    + entity);
        }
    }

    public static void showAllItems() {
        Item.showAllItems();
    }

    public static void showInventoryId(Character person) {
        person.showInventoryId();
    }

    public static void showInventoryHash(Character person) {
        person.showInventoryHash();
    }

    public static void showInventoryUID(Character person) {
        person.showInventoryUID();
    }

    public static void giveItem() {
        Scanner num = new Scanner(System.in);
        Character cc = GameExecutor.getGame().getCurrentPlayer();
        Messenger.ingameMessage("Write id of item you want to get");
        try {
            Item.showInstances();
            int id = num.nextInt();
            if (Item.getItemById(id) != null) {
                cc.putItem(Item.newInstance(id));
                Messenger.ingameMessage("Done!");
            } else {
                Messenger.ingameMessage("Item with this id not found");
            }
        } catch (InputMismatchException e) {
            Messenger.ingameMessage("You wrote wrong id of item");
        }
    }

    /**
     * [TESTING] Playing instance of animation with duration in 3 secs
     */
    public static void playAnimation() {
        Timer timer = new Timer(3000L);
        System.out.println("");
        System.out.print("[");
        while (!timer.touch()) {
            Timer microtimer = new Timer(3000L / 15);
            while (!microtimer.touch()) {
            }
            System.out.print("|");
        }
        System.out.println("]");
        System.out.println("done");
    }

    public static void changeWallet() {
        Scanner num = new Scanner(System.in);
        Messenger.ingameMessage("How much money do you want to add");
        try {
            float value = num.nextInt();
            Character cp = GameExecutor.getGame().getCurrentPlayer();
            cp.changeBalance(value);
            Messenger.ingameMessage("Done!");
        } catch (InputMismatchException e) {
            Messenger.systemMessage("changeWallet try-catch exception", AdminCommandManager.class);
            Messenger.ingameMessage("You wrote wrong value");
        }
    }

    public static void showSpeeches() {
        Scanner num = new Scanner(System.in);
        try {
            Messenger.ingameMessage("write uid of person you want to get list of speeches");
            int uid = num.nextInt();
            try {
                Character c = (Character) Entity.getEntityById(uid);
                if (c != null) {
                    c.showAllSpeeches();
                } else {
                    Messenger.ingameMessage("You wrote wrong id");
                }
            } catch (Exception e) {
                e.printStackTrace();
                Messenger.ingameMessage("You wrote id of other object");
                Messenger.systemMessage("Exception in showSpeeches()", AdminCommandManager.class);
            }
        } catch (InputMismatchException e) {
            System.out.println("UID is a number");
            Messenger.systemMessage("InputMismatchException in showSpeeches()", AdminCommandManager.class);
        }
    }

    /**
     * [TESTING] Initiate trade around dialogue
     */
    public static void trade() {
        Player cp = GameExecutor.getGame().getCurrentPlayer();
        Cell cc = cp.getCurrentCell();
        Character c = null;
        for (Entity e : cc.getObjects()) {
            if (e.getId() == 4) {
                c = (Character) e;
            }
        }
        if (c != null) {
            GameExecutor.initiateTrade(c);
        } else {
            Messenger.ingameMessage("Here is nothing to trade");
        }
    }
    /**
     * [TESTING] Initiate quest around dialogue
     */
    public static void initiateQuestLine() {
        Player cp = GameExecutor.getGame().getCurrentPlayer();
        Cell cc = cp.getCurrentCell();
        NonPlayerCharacter c = null;
        for (Entity e : cc.getObjects()) {
            if (e instanceof NonPlayerCharacter) {
                c = (NonPlayerCharacter) e;
            }
        }
        if (c != null) {
            GameExecutor.getGame().initiateQuestLine(c.getQuest());
        }
    }
}
