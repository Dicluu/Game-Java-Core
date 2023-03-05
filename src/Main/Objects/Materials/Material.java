package Main.Objects.Materials;

import Main.Items.Item;
import Main.Objects.Entity;
import Main.Objects.Priority;

public class Material extends Entity {

    private final static int priority = Priority.MIN.toInt();
    private final Materials material;

    public Material(int x, int y, Materials material) {
        super(x, y);
        this.material = material;
    }

    @Override
    public char getSymbol() {
        return material.getSymbol();
    }

    public int getPriority() {
        return priority;
    }

    @Override
    public int getId() {
        return material.getId();
    }

    public Materials getMaterial() {
        return material;
    }

    public static Material getMaterialById(int id, int x, int y) {
        switch (id) { // temp
            case 1:
                return new Material(x,y, Materials.Tree);
            default:
                return null;
        }
    }


}
