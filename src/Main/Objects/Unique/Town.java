package Main.Objects.Unique;

import Main.Objects.Entity;

public class Town extends UniqueEntity {

    private static char symbol = "T".charAt(0);
    private static int ID = 6;
    private static String name = "Town";

    static {
        try {
            Entity.addInstance(ID, Town.class);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }


    @Override
    public char getSymbol() {
        return symbol;
    }

    @Override
    public int getId() {
        return ID;
    }

    @Override
    public String getName() {
        return name;
    }
}
