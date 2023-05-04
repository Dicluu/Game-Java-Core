package Main.Items.Tools;

import Main.Items.Item;

public class Tool extends Item {

    private final Tools tool;
    private Tiers tier;
    private String name;

    public Tool(Tools tool) {
        super(tool.getId(),tool.getName());
        this.name = tool.getName();
        this.tier = Tiers.DIAMOND;
        this.tool = tool;
    }

    public Tool(Tools tool, Tiers tier) {
        super(tool.getId(), tool.getName());
        this.tool = tool;
        this.tier = tier;
        this.name = tier.name() + " " + tool.name();
    }

    @Override
    public String getName() {
        return name;
    }

    public void setTier(Tiers tier) {
        this.tier = tier;
    }

    public float getEfficiency() {
        return tier.getEfficiency();
    }
}
