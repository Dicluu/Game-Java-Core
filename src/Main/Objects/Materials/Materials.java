package Main.Objects.Materials;

import Main.Items.Item;
import Main.Items.Materials.Material;

public enum Materials {

    Tree(1, Item.getItemByName("Wood"), 5,"Tree",  5000L, 10f, "1".charAt(0));

    private final int id;
    private final Item item;
    private final int TOOLID;
    private final String name;
    private Long respawnTime;
    private float complexity;
    private char symbol;

    Materials(int id, Item item, int TOOLID, String name, Long respawnTime, float complexity, char symbol) {
        this.id = id;
        this.item = item;
        this.TOOLID = TOOLID;
        this.name = name;
        this.respawnTime = respawnTime;
        this.complexity = complexity;
        this.symbol = symbol;
    }

    public Item toItem() {
        return item;
    }

    public int getTOOLID() {
        return TOOLID;
    }

    public String getName() {
        return name;
    }

    public Long getRespawnTime() {
        return respawnTime;
    }

    public float getComplexity() {
        return complexity;
    }

    public char getSymbol() {
        return symbol;
    }

    public int getId() {
        return id;
    }
}
