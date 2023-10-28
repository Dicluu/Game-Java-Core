package Main.Objects.Materials;

import Main.Items.Item;
import Main.Objects.Entity;
import Main.Objects.Priority;
import Main.Utils.Messenger;

public class Material extends Entity {

    static {
        try {
            Entity.addInstance(1, Material.class);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private final static int ID = 1;
    private final static int priority = Priority.MIN.toInt();
    private Materials material;
    private final String name;
    private Item item;
    private int TOOLID;
    private Long respawnTime;
    private float complexity;
    private char symbol;

    public Material() {
        material = null;
        name = "Material";
    }

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


    public int getPriority() {
        return priority;
    }

    @Override
    public int getId() {
        return ID;
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

    public void setMaterial(Materials material) {
        this.material = material;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public int getTOOLID() {
        return TOOLID;
    }

    public void setTOOLID(int TOOLID) {
        this.TOOLID = TOOLID;
    }

    public Long getRespawnTime() {
        return respawnTime;
    }

    public void setRespawnTime(Long respawnTime) {
        this.respawnTime = respawnTime;
    }

    public float getComplexity() {
        return complexity;
    }

    public void setComplexity(float complexity) {
        this.complexity = complexity;
    }

    @Override
    public char getSymbol() {
        return symbol;
    }

    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }
}
