package Main.Items.Tools;

import Main.Items.Item;

public class Tool extends Item {

    private Tools tool;
    private Tiers tier;
    private String name;
    private int price;
/*
    public Tool(Tools tool) {
        super(tool.getId(),tool.getName());
        this.tier = Tiers.DIAMOND;
        this.name = tool.getName();
        this.tool = tool;
    }

    public Tool(Tools tool, Tiers tier) {
        super(tool.getId(), tool.getName());
        this.tool = tool;
        this.tier = tier;
        this.name = tool.getName();
    }

 */

    public Tool(int id, String name, int price) {
        super(id, name, price);
        this.name = name;
        this.tier = Tiers.DIAMOND;
        this.price = price;
    }

    @Override
    public String getName() {
        //return tier.getName() + " " + tool.getName();
        return tier.getName() + " " + this.name;
    }

    public void setTier(Tiers tier) {
        this.tier = tier;
    }

    public Tiers getTier() {
        return tier;
    }

    public float getEfficiency() {
        return tier.getEfficiency();
    }
}
