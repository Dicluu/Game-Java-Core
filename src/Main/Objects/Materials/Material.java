package Main.Objects.Materials;

import Main.Items.Item;
import Main.Objects.Entity;
import Main.Objects.Priority;

public abstract class Material extends Entity {

    private final static int priority = Priority.MIN.toInt();
    private final Item item;
    private final int TOOLID;
    private final String name;
    private Long respawnTime;
    private float complexity;

    public Material(int x, int y, Item item, String name, Long respawnTime, int TOOLID, float complexity) {
        super(x, y);
        this.item = item;
        this.TOOLID = TOOLID;
        this.name = name;
        this.respawnTime = respawnTime;
        this.complexity = complexity;
    }

    public int getPriority() {
        return priority;
    }

    public static Material getMaterialById(int id, int x, int y) {
        switch (id) {
            case 1:
                return new Tree(x,y);
            default:
                return null;
        }
    }

    public String getName() {
        return name;
    }

    public Item toItem() {
        return item;
    }

    public Long getRespawnTime() {
        return respawnTime;
    }

    public int getTOOLID() { return TOOLID; }
    public float getComplexity() {
        return complexity;
    }
}
