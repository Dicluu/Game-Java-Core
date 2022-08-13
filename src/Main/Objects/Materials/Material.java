package Main.Objects.Materials;

import Main.Items.Item;
import Main.Objects.Entity;
import Main.Objects.Priority;

public abstract class Material extends Entity {

    private final static int priority = Priority.MIN.toInt();
    private final Item item;
    private final String name;
    private Long respawnTime;

    public Material(int x, int y, Item item, String name, Long respawnTime) {
        super(x, y);
        this.item = item;
        this.name = name;
        this.respawnTime = respawnTime;
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
}
