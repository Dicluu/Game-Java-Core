package Main.Objects.Unique;

import Main.Objects.Entity;

public class WaterSurface extends UniqueEntity implements Irresistible {


    static {
        try {
            Entity.addInstance(9, WaterSurface.class);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public char getSymbol() {
        return '~';
    }

    @Override
    public int getId() {
        return 9;
    }

    @Override
    public String getName() {
        return "Water Surface";
    }
}
