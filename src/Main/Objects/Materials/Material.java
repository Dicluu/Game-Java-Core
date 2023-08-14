package Main.Objects.Materials;

import Main.Objects.Entity;
import Main.Objects.Priority;
import Main.Utils.Messenger;

public class Material extends Entity {

    private final static int priority = Priority.MIN.toInt();
    private final Materials material;
    private final String name;

    public Material(int x, int y, Materials material, int ID) {
        super(x, y, ID);
        this.material = material;
        this.name = material.getName();
    }

    public Material(Materials material) {
        this.material = material;
        this.name = material.getName();
        Messenger.systemMessage("instance " + material.getName() + " initiated", Material.class);
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

    public static Materials getMaterialById(int id) {
        Materials[] materials = Materials.values();
        for (Materials material : materials) {
            if (id == material.getId()) {
                return material;
            }
        }
        return null;
    }

    @Override
    public String getName() {
        return name;
    }
}
