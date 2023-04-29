package Main.Objects.Characters;

import Main.Items.Item;
import Main.Objects.Characters.NPC.Speech;
import Main.Objects.Entity;
import Main.Objects.Priority;
import Main.Singletones.GameExecutor;
import Main.Utils.Messenger;
import Main.Utils.PersonLoader;

import java.util.ArrayList;
import java.util.List;

public abstract class Character extends Entity implements Talkable{
    String name;
    private float wallet = 0;
    int x, y;
    private static int freeID = 0;
    private int UID;
    private final static int priority = Priority.MAX.toInt();
    private Item[] inventory;
    private final int ID;
    private static List<Character> allCharacters = new ArrayList<>();
    private List<Speech> speeches = new ArrayList<>();
    private Speech introduce;

    public Character(String name, int x, int y, int id, Item[] inventory) {
        super(x, y);
        this.name = name;
        this.x = x;
        this.y = y;
        this.ID = id;
        this.UID = freeID++;
        this.inventory = inventory;
        allCharacters.add(this);
    }

    public Character(int x, int y, int ID) {

        super(x, y);
        this.ID = ID;
        try {
            this.name = PersonLoader.loadName(ID);
        }
        catch (Exception e) {
            Messenger.systemMessage("Exception Character(int,int,int)", Character.class);
        }
        this.UID = freeID;
        allCharacters.add(this);
    }

    public Character(String name, int x, int y, int id) {
        super(x, y);
        this.name = name;
        this.x = x;
        this.y = y;
        this.ID = id;
        this.UID = freeID++;
        this.inventory = new Item[10];
        allCharacters.add(this);
    }


    public Character(String name, int x, int y, int id, float wallet) {
        super(x, y);
        this.name = name;
        this.x = x;
        this.y = y;
        this.ID = id;
        this.inventory = new Item[10];
        this.wallet = wallet;
        this.UID = freeID++;
        allCharacters.add(this);
    }

    /**
     * Constructor for initializing instances
     * @param name
     */
    public Character(String name) {
        super();
        this.name = name;
        this.ID = -1;
        this.UID = freeID++;
        allCharacters.add(this);
    }

    public Character(String name, List<Speech> speeches) {
        super();
        this.name = name;
        this.speeches = speeches;
        this.introduce = speeches.get(0);
        this.ID = -1;
        this.UID = freeID++;
        allCharacters.add(this);
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

    public int getUID() {
        return UID;
    }

    public int getY() {
        return y;
    }

    @Override
    public int getId() {
        return ID;
    }

    public List<Speech> getSpeeches() {
        return speeches;
    }

    public void showAllSpeeches() {
        for (Speech s : speeches) {
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
        allCharacters.add(c);
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

   @Override
    public void talk(Character c) {
            GameExecutor.initiateDialogue(c, c.getIntroduce());
    }


}
