package Main.Items;

import Main.Items.Materials.Material;
import Main.Items.Materials.Materials;
import Main.Items.Tools.Tool;
import Main.Items.Tools.Tools;
import Main.Utils.ItemLoader;
import Main.Utils.Messenger;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class Item implements Cloneable, Serializable {
    private int ID, UID;
    private static int freeID = 0;
    private String name;
    private static List<Item> instances = new ArrayList<>();
    private static ArrayList<Item> allItems = new ArrayList<>();
    private int price;

    static {
        addEnumsInstances();
    }

    public Item(int id, String name) {
        this.ID = id;
        this.name = name;
        this.UID = freeID++;
        this.price = ItemLoader.getItemPrice(ID);
        allItems.add(this);
    }

    public int getId() {
        return ID;
    }

    public int getUID() {
        return UID;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public static Item getItemById(int id) {
        for (Item instance : instances) {
            if (instance.getId() == id) {
                return instance;
            }
        }
        return null;
    }

    public static void showAllItems() {
        for (Item item : allItems) {
            System.out.println(item.getUID() + ") " + item.getId() + " " + item);
        }
    }

    public static Item newInstance(int id) throws CloneNotSupportedException {
        Item item = (Item) getItemById(id).clone();
        item.setUID(freeID++);
        allItems.add(item);
        return item;
    }

    private static void addEnumsInstances() {
        for (Materials material : Materials.values()) {
            instances.add(new Material(material));
        }
        for (Tools tool : Tools.values()) {
            instances.add(new Tool(tool));
        }
    }

    public static void showInstances() {
        for (Item instance : instances) {
            Messenger.helpMessage("id: " + instance.getId() + "; name: " + instance.getName());
        }
    }

    public static void showInstancesSystem() {
        for (Item instance : instances) {
            Messenger.systemMessage("id: " + instance.getId() + "; name: " + instance.getName(), Item.class);
        }
    }

    public static void showLastInstance() {
        Messenger.systemMessage("Last id of Item instance: " + instances.get(instances.size()-1).getId(), Item.class);
    }

    private void setUID(int UID) {
        this.UID = UID;
    }
}
