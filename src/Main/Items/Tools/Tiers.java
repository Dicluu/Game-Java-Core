package Main.Items.Tools;

import Main.Items.Item;
import Main.Items.Materials.Material;
import Main.Items.Materials.Materials;
import Main.Utils.FileLoaders.ItemLoader;

public enum Tiers {

    WOODEN(1,1,8,1, (Material) Item.getItemByName("Wood")),
    STONE(2,2,16,2, (Material) Item.getItemByName("Stone")),
    IRON(3,3,64, 4, (Material) Item.getItemByName("Iron")),
    DIAMOND(4,6, 256, 10, (Material) Item.getItemByName("Diamond"));

    private float efficiency, durability, damage;
    private Material material;
    private int ID;
    private String name;

    Tiers(int ID, float efficiency, float durability, float damage, Material material) {
        this.efficiency = efficiency;
        this.durability = durability;
        this.damage = damage;
        this.material = material;
        this.ID = ID;
        String name = name().toLowerCase();
        this.name = name.substring(0,1).toUpperCase() + name.substring(1);
    }

    public String getName() {
        return name;
    }

    public float getEfficiency() {
        return efficiency;
    }

    public float getDurability() {
        return durability;
    }

    public float getDamage() {
        return damage;
    }

    public Material getMaterial() {
        return material;
    }

    public int getID() {
        return ID;
    }

    public static Tiers getById(int ID) {
        for (Tiers t : Tiers.values()) {
            if (t.getID() == ID) {
                return t;
            }
        }
        return null;
    }
}
