package Main.Items.Tools;

import Main.Items.Item;

public class Axe extends Item {

    private final int ID = 2;
    private final String NAME = "Axe";

    @Override
    public int getId() {
        return ID;
    }

    @Override
    public String getName() {
        return NAME;
    }
}
