package Main.Items.Materials;

import Main.Items.Item;

public class Wood extends Material{

    private final int ID = 1;
    private final String NAME = "Wood";
    private final float RARE = 0.5f;
    private final int TOOLID = 2;

    @Override
    public float getRare() {
        return RARE;
    }

    public String getName() {
        return NAME;
    }

    @Override
    public int getId() {
        return ID;
    }
}
