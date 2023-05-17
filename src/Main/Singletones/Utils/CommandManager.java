package Main.Singletones.Utils;

import Main.Items.Item;
import Main.Items.Tools.Tool;
import Main.Maps.Map;
import Main.Maps.Cell;
import Main.Objects.Characters.Character;
import Main.Objects.Characters.Player.Journal;
import Main.Objects.Characters.Player.Player;
import Main.Objects.Characters.Player.Quest;
import Main.Objects.Characters.Talkable;
import Main.Objects.Entity;
import Main.Objects.Materials.Material;
import Main.Objects.Unique.Enterable;
import Main.Singletones.GameExecutor;
import Main.Utils.Messenger;
import Main.Utils.Timers.TimeCounter;
import Main.Utils.Timers.Timer;

import java.util.*;

/**
 * handler of in game commands which written as an answer of GameExecutor's render
 */
public class CommandManager {

    private static Scanner num = new Scanner(System.in);

    public static void move(String direction) {
        Player currentPlayer = GameExecutor.getGame().getCurrentPlayer();
        Map currentMap = GameExecutor.getGame().getCurrentMap();
        switch (direction) {
            case "up":
                currentPlayer.setY(currentPlayer.getY() - 1);
                if (currentPlayer.getY() < 0) {
                    currentPlayer.setY(0);
                    Messenger.helpMessage("You stuck in the edge of map");
                }
                break;
            case "down":
                currentPlayer.setY(currentPlayer.getY() + 1);
                if (currentPlayer.getY() >= currentMap.getY()) {
                    currentPlayer.setY(currentMap.getY() - 1);
                    Messenger.helpMessage("You stuck in the edge of map");
                }
                break;
            case "right":
                currentPlayer.setX(currentPlayer.getX() + 1);
                if (currentPlayer.getX() >= currentMap.getX()) {
                    currentPlayer.setX(currentMap.getX() - 1);
                    Messenger.helpMessage("You stuck in the edge of map");
                }
                break;
            case "left":
                currentPlayer.setX(currentPlayer.getX() - 1);
                if (currentPlayer.getX() < 0) {
                    currentPlayer.setX(0);
                    Messenger.helpMessage("You stuck in the edge of map");
                }
                break;
        }
        renderObjects();
        updateMapAfterMove(currentPlayer);
    }

    public static void updateMapAfterMove(Player currentPlayer) {
        GameExecutor.getGame().getCurrentMap().setObject(currentPlayer);
        GameExecutor.getGame().getCurrentMap().clearCellsFromObject(currentPlayer);
        GameExecutor.getGame().getCurrentMap().showMap();
    }

    /**
     * renders objects of cell where player located
     */
    public static void renderObjects() {
        Map cm = GameExecutor.getGame().getCurrentMap();
        Player cp = GameExecutor.getGame().getCurrentPlayer();
        Set<Entity> objects = cm.getObjects(cp.getX(), cp.getY());
        boolean isMaterial = false, isEntrance = false;
        for (Entity object : objects) {
            if (Material.getMaterialById(object.getId()) != null) {
                isMaterial = true;
            }
            if (object instanceof Enterable) {
                isEntrance = true;
            }
        }
        if (isMaterial) {
            Messenger.ingameMessage("Here is some materials you can get by command 'get'");
        }
        if (isEntrance) {
            Messenger.ingameMessage("Here is entrance you can come in by command 'come'");
        }
    }

    public static void showInventory(Character person) {
        person.showInventory();
    }

    /**
     * pre-realizes action 'get'
     *
     * @param timecounter
     * @throws CloneNotSupportedException
     */
    public static void getAction(TimeCounter timecounter) throws CloneNotSupportedException {
        Player cp = GameExecutor.getGame().getCurrentPlayer();
        Cell cc = cp.getCurrentCell();
        List<Material> cellMaterials = new ArrayList<>();
        for (Entity entity : cc.getObjects()) {
            if (Material.getMaterialById(entity.getId()) != null) {
                cellMaterials.add((Material) entity);
            }
        }
        switch (cellMaterials.size()) {
            case 0:
                Messenger.ingameMessage("Here is nothing to get");
                break;
            case 1:
                extract(cp, timecounter, cc, cellMaterials.get(0));
                break;
            default:
                askWhichMaterialToGet(cp, cellMaterials, timecounter, cc);
                break;
        }
    }

    /**
     * realizes action 'get'
     *
     * @param person
     * @param timecounter
     * @param cc
     * @param material
     * @throws CloneNotSupportedException
     */
    private static void extract(Character person, TimeCounter timecounter, Cell cc, Material material) throws CloneNotSupportedException {
        if (person.isPresence(material.getMaterial().getTOOLID())) {
            if (!person.putItem(Item.newInstance(material.getMaterial().toItem().getId()))) {
                Messenger.ingameMessage("Your inventory is full");
            } else {
                Tool ct = chooseTool((Player) person, material);
                playAnimation((long) material.getMaterial().getComplexity(), (long) ct.getEfficiency());
                timecounter.createObjectTimeline(material, cc);
                Messenger.ingameMessage("You got a " + material.getMaterial().toItem().getName());
            }
        } else {
            Messenger.ingameMessage("You don't have necessary tools to get this material");
        }
    }

    public static void askWhichMaterialToGet(Character cp, List<Material> materials, TimeCounter timecounter, Cell cc) throws CloneNotSupportedException {
        for (int i = 0; i < materials.size(); i++) {
            System.out.print(i + 1 + ") " + materials.get(i).getMaterial().getName() + " ");
        }
        System.out.println(" ");
        Messenger.ingameMessage("Type id of material you want to get");
        int answer = num.nextInt();
        if (answer > 0 && answer <= materials.size()) {
            extract(cp, timecounter, cc, materials.get(answer - 1));
        } else {
            Messenger.ingameMessage("You wrote wrong id");
        }
    }

    /**
     * realizes command 'come'
     */
    public static void come() {
        Player cp = GameExecutor.getGame().getCurrentPlayer();
        Cell cc = cp.getCurrentCell();
        cc.removeObject(cp);
        for (Entity entity : cc.getObjects()) {
            if (entity instanceof Enterable) {
                Enterable entrance = (Enterable) entity;
                Cell node = entrance.getNode();
                GameExecutor.getGame().setCurrentMap(Map.getMapById(entrance.getReferMapId()));
                cp.setX(node.getX());
                cp.setY(node.getY());
                GameExecutor.getGame().getCurrentMap().setObject(cp);
                Messenger.ingameMessage("You moved to new place");
                Messenger.systemMessage("Map changed to id: " + entrance.getReferMapId());
                showMap();
            }
        }
    }

    public static void showMap() {
        GameExecutor.getGame().getCurrentMap().showMap();
    }

    public static void switchSettings() {
        Scanner num = new Scanner(System.in);
        Messenger.ingameMessage("write which setting you want to switch");
        String status = num.nextLine();
        switch (status) {
            case "help":
                if (Messenger.isHelp()) {
                    Messenger.setHelp(false);
                } else {
                    Messenger.setHelp(true);
                }
                Messenger.ingameMessage("Help information switched");
                break;
            case "system":
                if (Messenger.isSystem()) {
                    Messenger.setSystem(false);
                } else {
                    Messenger.setSystem(true);
                }
                Messenger.ingameMessage("System information switched");
                break;
            default:
                Messenger.ingameMessage("setting's attribute is wrong");
        }
    }

    private static void playAnimation(Long complexity, Long efficiency) {
        Long interval = ((complexity * 1000) / efficiency);
        Timer timer = new Timer(interval);
        System.out.print("[");
        while (!timer.touch()) {
            Timer microtimer = new Timer((interval / 15));
            while (!microtimer.touch()) {
            }
            System.out.print("|");
        }
        System.out.println("]");
    }

    public static void showWallet() {
        Character cp = GameExecutor.getGame().getCurrentPlayer();
        Messenger.ingameMessage("You have " + cp.getWallet() + " $ in your bag");
    }

    private static Tool chooseTool(Player cp, Material cm) {
        ArrayList<Item> desired = cp.getAllOfKind(cm.getMaterial().getTOOLID());
        Scanner num = new Scanner(System.in);
        int id;
        if (desired.size() > 1) {
            Messenger.ingameMessage("Choose what a tool you want to use");
            showDesired(desired);
            try {
                id = num.nextInt();
                System.out.println(desired.get(id - 1).getUID());
                return (Tool) desired.get(id - 1);
            } catch (InputMismatchException | ArrayIndexOutOfBoundsException | NullPointerException ae) {
                Messenger.systemMessage("Exception in method chooseTool() catch", CommandManager.class);
            }
        } else {
            return (Tool) desired.get(0);
        }
        return null;
    }

    /**
     * shows desired tools for extract a resource
     *
     * @param items
     */
    private static void showDesired(ArrayList<Item> items) {
        for (int i = 0; i < items.size(); i++) {
            Messenger.ingameMessage("id: " + (i + 1) + "; name: " + items.get(i).getName());
        }
    }

    public static void talk() {
        GameExecutor ge = GameExecutor.getGame();
        Scanner num = new Scanner(System.in);
        Cell cc = ge.getCurrentPlayer().getCurrentCell();
        Set<Entity> objects = cc.getObjects();
        List<Character> c = new ArrayList<>();
        for (Entity e : objects) {
            if (e instanceof Talkable) {
                c.add((Character) e);
            }
        }
        c.remove(ge.getCurrentPlayer());
        if (c.size() == 0) {
            Messenger.ingameMessage("Here is no one to talk");
        }
        if (c.size() == 1) {
            c.get(0).talk(c.get(0));
        }
        if (c.size() > 1) {
            Messenger.ingameMessage("Here is more than 1 character, choose which one you want to talk");
            for (int i = 1; i < c.size() + 1; i++) {
                Messenger.ingameMessage(i + ") " + c.get(i - 1).getName());
            }
            Messenger.helpMessage("Write ID of character");
            try {
                int in = num.nextInt();
                Character ccr = c.get(in - 1);
                ccr.talk(ccr);
            } catch (InputMismatchException ime) {
                Messenger.ingameMessage("ID must be a number");
                Messenger.systemMessage("InputMismatchException caught in talk()", CommandManager.class);
            } catch (IndexOutOfBoundsException ibe) {
                Messenger.ingameMessage("Character with this ID doesn't exist");
                Messenger.systemMessage("IndexOutOfBoundsException caught in talk()", CommandManager.class);
            }
        }
    }

    public static void showJournal() {
        Player cp = GameExecutor.getGame().getCurrentPlayer();
        Journal cj = cp.getJournal();
        HashMap<Integer, Quest> p = cj.getPassed();
        Messenger.ingameMessage("passed quests: ");
        for (Quest q : p.values()) {
            for (int i = 0; i < 5; i++) {
                System.out.print(q.getID() + ") " + q.getName() + ". ");
            }
            System.out.println(" ");
        }
        cj.showActive();
        cj.showPossible();
    }
}