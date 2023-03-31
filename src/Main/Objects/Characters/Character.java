package Main.Objects.Characters;

import Main.Items.Item;
import Main.Objects.Entity;
import Main.Objects.Priority;
import Main.Utils.Messenger;

import java.util.ArrayList;

public abstract class Character extends Entity {
    String name;
    private float wallet = 0;
    int x, y;
    private final static int priority = Priority.MAX.toInt();
    private Item[] inventory;
    private final int ID;

    public Character(String name, int x, int y, int id, Item[] inventory) {
        super(x, y);
        this.name = name;
        this.x = x;
        this.y = y;
        this.ID = id;
        this.inventory = inventory;
    }

    public Character(String name, int x, int y, int id) {
        super(x, y);
        this.name = name;
        this.x = x;
        this.y = y;
        this.ID = id;
        this.inventory = new Item[10];
    }


    public Character(String name, int x, int y, int id, float wallet) {
        super(x, y);
        this.name = name;
        this.x = x;
        this.y = y;
        this.ID = id;
        this.inventory = new Item[10];
        this.wallet = wallet;
    }

    public Character(String name) {
        super();
        this.name = name;
        this.ID = -1;
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

    public int getY() {
        return y;
    }

    @Override
    public int getId() {
        return ID;
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

    public char getSymbol() {
        return name.toUpperCase().charAt(0);
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


    public void showInventoryId() {
        for (int i = 0; i < inventory.length; i++) {
            if (inventory[i] != null) {
                System.out.print(i + ") " + inventory[i].getId() + " ");
            } else {
                System.out.print(i + ") " + "- ");
            }
        }
    }

    public void showInventoryHash() {
        for (int i = 0; i < inventory.length; i++) {
            if (inventory[i] != null) {
                System.out.print(i + ") " + inventory[i].toString() + " ");
            } else {
                System.out.print(i + ") " + "- ");
            }
        }
    }

    public void showInventoryUID() {
        for (int i = 0; i < inventory.length; i++) {
            if (inventory[i] != null) {
                System.out.print(i + ") " + inventory[i].getUID() + " ");
            } else {
                System.out.print(i + ") " + "- ");
            }
        }
    }

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
}
