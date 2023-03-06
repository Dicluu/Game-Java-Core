package Main.Items;

import Main.Items.Materials.Material;
import Main.Items.Materials.Materials;
import Main.Items.Tools.Tool;
import Main.Items.Tools.Tools;
import Main.Utils.Messenger;


import java.util.ArrayList;
import java.util.List;

public abstract class Item {
    private int id;
    private String name;
    private static List<Item> instances = new ArrayList<>();

    static {
        addEnumsInstances();
    }

    public Item(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static Item getItemById(int id) {
        for (Item instance : instances) {
            if (instance.getId() == id) {
                return instance;
            }
        }
        return null;
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
}
