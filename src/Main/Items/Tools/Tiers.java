package Main.Items.Tools;

import Main.Items.Materials.Materials;

public enum Tiers {

    WOODEN(1,1,8,1, Materials.WOOD),
    STONE(2,2,16,2, Materials.STONE),
    IRON(3,3,64, 4, Materials.IRON),
    DIAMOND(4,6, 256, 10, Materials.DIAMOND);

    private float efficiency, durability, damage;
    private Materials material;
    private int ID;
    private String name;

    Tiers(int ID, float efficiency, float durability, float damage, Materials material) {
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

    public Materials getMaterial() {
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
