package Main.Objects.Unique;

import Main.Objects.Entity;

public class Bridge extends UniqueEntity{

    static {
        try {
            Entity.addInstance(10, Bridge.class);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public char getSymbol() {
        return 'B';
    }

    @Override
    public int getId() {
        return 10;
    }

    @Override
    public String getName() {
        return "Bridge";
    }
}
