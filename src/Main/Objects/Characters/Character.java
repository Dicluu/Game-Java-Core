package Main.Objects.Characters;

import Main.Items.Item;
import Main.Objects.Entity;
import Main.Objects.Priority;

public abstract class Character extends Entity {
    String name;
    int x,y;
    private final static int priority = Priority.MAX.toInt();
    private Item[] inventory;
    private final int ID;

    public Character(String name, int x, int y, int id, Item[] inventory) {
        super(x,y);
        this.name = name;
        this.x = x;
        this.y = y;
        this.ID = id;
        this.inventory = inventory;
    }

    public Character(String name, int x, int y, int id) {
        super(x,y);
        this.name = name;
        this.x = x;
        this.y = y;
        this.ID = id;
        this.inventory = new Item[10];
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

    public void showInventory() {
        for (int i = 0; i < inventory.length; i++) {
            if (inventory[i] != null) {
                System.out.print(i + ") " + inventory[i].getName() + " ");
            }
            else {
                System.out.print(i + ") " + "- ");
            }
        }
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
}
