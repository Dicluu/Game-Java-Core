package Main.Items.Materials;

public class Wood extends Material{

    private final int ID = 1;
    private final String name = "Wood";
    private final float rare = 0.5f;

    @Override
    public float getRare() {
        return rare;
    }

    public String getName() {
        return name;
    }

    @Override
    public int getId() {
        return ID;
    }
}
