package Main.Items.Tools;

import Main.Items.Item;

public class Axe extends Tool {

    private static final int ID = 2;
    private static final String NAME = "Axe";

    public Axe() {
        super(NAME, ID,1,1,1);
    }

    @Override
    public int getId() {
        return ID;
    }

    @Override
    public String getName() {
        return NAME;
    }
}
