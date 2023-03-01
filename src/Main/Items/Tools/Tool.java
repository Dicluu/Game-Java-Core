package Main.Items.Tools;

import Main.Items.Item;

public class Tool extends Item {

    private final String NAME;
    private final int ID;
    private float efficiency, durability, damage;
    //private final Tier TIER;

    public Tool(String NAME, int ID, int efficiency, int durability, int damage) {
        this.NAME = NAME;
        this.ID = ID;
        this.efficiency = efficiency;
        this.durability = durability;
        this.damage = damage;
    }

    @Override
    public int getId() {
        return ID;
    }

    @Override
    public String getName() {
        return NAME;
    }

    public float getEfficiency() {
        return efficiency;
    }

    public void setEfficiency(float efficiency) {
        this.efficiency = efficiency;
    }

    public float getDurability() {
        return durability;
    }

    public void setDurability(float durability) {
        this.durability = durability;
    }

    public float getDamage() {
        return damage;
    }

    public void setDamage(float damage) {
        this.damage = damage;
    }
}
