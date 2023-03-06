package Main.Items.Tools;

import Main.Items.Item;

public class Tool extends Item {

    private final Tools tool;
    private Tiers tier;

    public Tool(Tools tool) {
        super(tool.getId(), tool.getName());
        this.tool = tool;
    }

    public Tool(Tools tool, Tiers tier) {
        super(tool.getId(), tool.getName());
        this.tool = tool;
        this.tier = tier;
    }


}
