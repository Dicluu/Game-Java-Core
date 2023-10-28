package Main.Objects.Characters;

import Main.Items.Item;
import Main.Objects.Characters.NPC.NonPlayerCharacter;
import Main.Objects.Characters.NPC.Speech;
import Main.Objects.Entity;
import Main.Objects.Priority;
import Main.Singletones.GameExecutor;
import Main.Utils.Messenger;
import Main.Utils.FileLoaders.PersonLoader;

import java.io.Serializable;
import java.util.*;

public class Character extends Entity implements Serializable {

    static {
        try {
            Entity.addInstance(2, Character.class);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    String name;
    private float wallet = 0;
    int x, y;
    private static int freeID = 0;
    private int UID, CID;
    private final static int priority = Priority.MAX.toInt();
    private Item[] inventory;
    private final static int ID = 2;
    private static HashMap<Integer, Character> allCharacters = new HashMap();
    private HashMap<Integer, Speech> speeches = new HashMap<>();
    private Speech introduce;

    private int intelligence, strength, agility;

    public Character() {
        name = "Character";
    }

    public Character(String name, int x, int y, float wallet, int CID) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.wallet = wallet;
        this.CID = CID;
        this.inventory = PersonLoader.loadInventory(CID);
        this.UID = freeID++;
        allCharacters.put(UID, this);
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    /**
     * @return UID - Unique ID - auto increment.
     */
    public int getUID() {
        return UID;
    }

    public int getY() {
        return y;
    }

    /**
     * @return CID - Character ID - ID from Replicator's list
     */
    public int getCID() {
        return CID;
    }

    public void setCID(int CID) {
        try {
            name = PersonLoader.loadName(CID);
            speeches = PersonLoader.loadSpeeches(CID);
            introduce = speeches.get(0);
            inventory = PersonLoader.loadInventory(CID);
        }
        catch (Exception e) {
            Messenger.systemMessage("Exception caught in setCID()", Character.class);
        }
        this.CID = CID;
        allCharacters.remove(UID);
        this.UID = CID;
        allCharacters.put(UID, this);
        super.setCID(CID);
    }

    /**
     * @return ID - Identifier person's class (Like Dealer, Peasant, etc)
     */
    @Override
    public int getId() {
        return ID;
    }

    public HashMap<Integer, Speech> getSpeeches() {
        return speeches;
    }

    public void showAllSpeeches() {
        for (Speech s : speeches.values()) {
            Messenger.ingameMessage("[" + s.getId() + "] " + s.getSpeech());
        }
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getPriority() {
        return priority;
    }

    public float getWallet() {
        return wallet;
    }

    public Item[] getInventory() {
        return inventory;
    }

    public char getSymbol() {
        return name.toUpperCase().charAt(0);
    }

    public void setUID(int UID) {
        this.UID = UID;
    }

    public Speech getIntroduce() {
        return introduce;
    }

    public void setIntroduce(Speech introduce) {
        this.introduce = introduce;
    }

    private void setWallet(float value) {
        if (value < 0) {
            Messenger.systemMessage("setWallet() catch encapsulation violation", Character.class);
        } else {
            this.wallet = value;
        }
    }

    public void showInventory() {
        for (int i = 0; i < inventory.length; i++) {
            if (inventory[i] != null) {
                System.out.print(i + ") " + inventory[i].getName() + " ");
            } else {
                System.out.print(i + ") " + "- ");
            }
        }
        System.out.println("");
    }

    public Item getItem(int idx) {
        return inventory[idx];
    }

    public void deleteItemFromInventory(int idx) {
        inventory[idx] = null;
    }

    public void addMoney(int value) {
        this.wallet = wallet + value;
    }

    public void takeMoney(int value) {
        this.wallet = wallet - value;
    }

    /**
     * shows ID of things that keeps in inventory
     */
    public void showInventoryId() {
        for (int i = 0; i < inventory.length; i++) {
            if (inventory[i] != null) {
                System.out.print(i + ") " + inventory[i].getId() + " ");
            } else {
                System.out.print(i + ") " + "- ");
            }
        }
        System.out.println("");
    }
    /**
     * shows Hash of things that keeps in inventory
     */
    public void showInventoryHash() {
        for (int i = 0; i < inventory.length; i++) {
            if (inventory[i] != null) {
                System.out.print(i + ") " + inventory[i].toString() + " ");
            } else {
                System.out.print(i + ") " + "- ");
            }
        }
        System.out.println("");
    }

    /**
     * shows UID of things that keeps in inventory
     */
    public void showInventoryUID() {
        for (int i = 0; i < inventory.length; i++) {
            if (inventory[i] != null) {
                System.out.print(i + ") " + inventory[i].getUID() + " ");
            } else {
                System.out.print(i + ") " + "- ");
            }
        }
        System.out.println("");
    }
    /**
     * shows price of things that keeps in inventory
     */
    public void showInventoryPrice() {
        for (int i = 0; i < inventory.length; i++) {
            if (inventory[i] != null) {
                System.out.print(i + ") " + inventory[i].getPrice() + "$ ");
            } else {
                System.out.print(i + ") " + "- ");
            }
        }
        System.out.println("");
    }

    /**
     * looks for desired thing in inventory
     * @param desired
     * @return
     */
    public boolean isPresence(int desired) {
        for (Item item : inventory) {
            if (item != null) {
                if (item.getId() == desired) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean putItem(Item item) {
        for (int i = 0; i < inventory.length; i++) {
            if (inventory[i] == null) {
                inventory[i] = item;
                return true;
            }
        }
        return false;
    }

    public void changeBalance(float value) {
        setWallet(this.wallet + value);
    }

    /**
     * registers a new objects that was cloned by instance and giving a unique ID
     * @param c
     */
    public static void register(Character c) {
        c.setUID(freeID++);
        allCharacters.put(c.getUID(), c);
    }

    /**
     * looks for a desired thing
     * @param id
     * @return
     */
    public ArrayList<Item> getAllOfKind(int id) {
        ArrayList<Item> desired = new ArrayList<>();
        for (Item item : inventory) {
            try {
                if (item.getId() == id) {
                    desired.add(item);
                }
            }
            catch (NullPointerException e) {

            }
        }
        if (desired.isEmpty()) {
            return null;
        } else {
            return desired;
        }
    }

    public void load() {
        this.speeches = PersonLoader.loadSpeeches(CID);
        this.introduce = speeches.get(0);
        this.name = PersonLoader.loadName(CID);
    }

    public void talk(NonPlayerCharacter c) {
            GameExecutor.initiateDialogue(c, c.getIntroduce());
    }

    public static HashMap<Integer, Character> getAllCharacters() {
        return allCharacters;
    }

    public static void setAllCharacters(HashMap<Integer, Character> characters) {
        allCharacters = characters;
    }
}
