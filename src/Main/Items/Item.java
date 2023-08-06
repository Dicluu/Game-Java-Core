package Main.Items;

import Main.Items.Materials.Material;
import Main.Items.Materials.Materials;
import Main.Items.Tools.Tool;
import Main.Items.Tools.Tools;
import Main.Utils.Annotations.NeedRevision;
import Main.Utils.FileLoaders.ItemLoader;
import Main.Utils.Messenger;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;

import static Main.Utils.FileLoaders.ItemLoader.loadItem;

public abstract class Item implements Cloneable, Serializable {
    private int ID, UID;
    private static int freeID = 0;
    private String name;
    private static List<Item> instances = new ArrayList<>();
    @NeedRevision(comment = "maybe should to use name of item for key?")
    private static Map<Integer, Item> instancesFromFile = new HashMap();
    private static ArrayList<Item> allItems = new ArrayList<>();
    private int price;

    static {
        loadItems();
        //addEnumsInstances();
    }

    public Item(int id, String name, int price) {
        this.ID = id;
        this.name = name;
        this.UID = freeID++;
        this.price = price;
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

    /*
    public static Item getItemById(int id) {
        for (Item instance : instances) {
            if (instance.getId() == id) {
                return instance;
            }
        }
        return null;
    }


     */
    public static void showAllItems() {
        for (Item item : allItems) {
            System.out.println(item.getUID() + ") " + item.getId() + " " + item);
        }
    }

    public static Item newInstance(int id)  {
        try {
            Item item = (Item) getItemById(id).clone();
            item.setUID(freeID++);
            allItems.add(item);
            return item;
        }
        catch (CloneNotSupportedException e) {

        }
        return null;
    }

    /*
    private static void addEnumsInstances() {
        for (Materials material : Materials.values()) {
            instances.add(new Material(material));
        }
        for (Tools tool : Tools.values()) {
            instances.add(new Tool(tool));
        }
    }

     */

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
        //Messenger.systemMessage("Last id of Item instance: " + instances.get(instances.size()-1).getId(), Item.class);
    }

    private void setUID(int UID) {
        this.UID = UID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return ID == item.ID;
    }

    public static Item getItemById(int id) {
        Item item = instancesFromFile.get(id);
        if (item == null) {
            try {
                return loadItem(id);
            } catch (IOException e) {
                Messenger.systemMessage("Exception in getItemById()", Item.class);
                return null;
            }
        } else {
            return item;
        }
    }

    public static Item getItemByName(String name) {
        Item[] instances = instancesFromFile.values().toArray(new Item[0]);
        for (Item i : instances) {
            if (i.getName().equals(name)) {
                return i;
            }
        }
        Item i = ItemLoader.loadItemByName(name);
        if (i == null) {
            Messenger.systemMessage("item '" + name + "' is null");
            return null;
        }
        instancesFromFile.put(i.getId(), i);
        return i;
    }

    private static void loadItems() {
        try {
            int counter = 0;
            while (true) {
                counter++;
                instancesFromFile.put(counter, loadItem(counter));
            }
        }
        catch (FileNotFoundException e) {
            Messenger.systemMessage("All instances loaded successfully", Item.class);
        }
        catch (IOException e) {
            Messenger.systemMessage("IOException caught in loadItems()", Item.class);
        }
    }

    public static void showAllInstanceFromFile() {
        for (Item i : instancesFromFile.values()) {
            System.out.println(i.getName());
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID);
    }
}
