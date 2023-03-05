package Main.Objects.Materials;

import Main.Items.Materials.Wood;

public class Tree extends Material{

    private final int ID = 1;
    private static final String name = "Tree";
    private static final float complexity = 10f;
    public Tree(int x, int y) {
        super(x,y, new Wood(), name, 5000L, 2, complexity);
    }

    @Override
    public char getSymbol() {
        return "1".charAt(0);
    }

    @Override
    public int getId() {
        return ID;
    }
}
