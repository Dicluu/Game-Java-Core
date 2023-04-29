package Main.Singletones.Utils;

import Main.Items.Item;
import Main.Items.Materials.Material;
import Main.Items.Materials.Materials;
import Main.Items.Tools.Tiers;
import Main.Items.Tools.Tool;
import Main.Items.Tools.Tools;
import Main.Maps.Map;
import Main.Objects.Characters.Character;
import Main.Objects.Characters.Player;
import Main.Objects.Entity;
import Main.Objects.Unique.Entrance;
import Main.Singletones.GameExecutor;
import Main.Utils.Annotations.NeedRevision;
import Main.Utils.Messenger;
import Main.Utils.Timers.Timer;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * handler of admin commands which written as an answer of GameExecutor's render
 */
public class AdminCommandManager {

    @NeedRevision(comment = " ")
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
            if (id == 2) {
                setEntrance(x, y);
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
            System.out.println(object.getId() + " " + object);
        }
    }

    public static void showCharacters() {
        List<Character> characters = getCharacters();
        for (Character character : characters) {
            System.out.println(character.getUID() + " " + character.getId() + " " + character);
        }
    }

    protected static List<Character> getCharacters() {
        List<Entity> entities = Entity.getAllEntities();
        List<Character> characters = new ArrayList<>();
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

    public static void setEntrance(int x, int y) {
        boolean isPortalAlreadyExistOnCell = false;
        for (Entity entity : GameExecutor.getGame().getCurrentMap().getCell(x, y).getObjects()) {
            if (entity.getId() == 2) {
                isPortalAlreadyExistOnCell = true;
            }
        }
        if (!isPortalAlreadyExistOnCell) {
            Scanner num = new Scanner(System.in);
            Messenger.ingameMessage("Write id map what entrance have to refer");
            int id = num.nextInt();
            if (Map.getMapById(id) != null) {
                GameExecutor.getGame().getCurrentMap().setObject(new Entrance(x, y, id));
                Messenger.ingameMessage("You set portal on x = " + x + " and y = " + y);
            } else {
                Messenger.ingameMessage("You wrote wrong id");
            }
        } else {
            Messenger.ingameMessage("Portal is already exist on this cell");
        }
    }

    public static void showAllEntities() {
        List<Entity> allEntities = Entity.getAllEntities();
        for (Entity entity : allEntities) {
            Messenger.systemMessage(entity.getObjectID() + ") " + entity.getId() + " " + entity.getSymbol() + " " + entity);
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
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }

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
            }
            catch (Exception e) {
                e.printStackTrace();
                Messenger.ingameMessage("You wrote id of other object");
                Messenger.systemMessage("Exception in showSpeeches()", AdminCommandManager.class);
            }
        } catch (InputMismatchException e) {
            System.out.println("UID is a number");
            Messenger.systemMessage("InputMismatchException in showSpeeches()", AdminCommandManager.class);
        }
    }
}
