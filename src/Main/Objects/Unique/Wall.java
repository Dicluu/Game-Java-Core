package Main.Objects.Unique;

import Main.Objects.Entity;

public class Wall extends UniqueEntity implements Irresistible {

    static {
        try {
            Entity.addInstance(11, Wall.class);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public char getSymbol() {
        return '█';
    }

    @Override
    public int getId() {
        return 11;
    }

    @Override
    public String getName() {
        return "Wall";
    }
}
