package Main.Objects.Unique;


import Main.Objects.Entity;

public class Building extends UniqueEntity {

    private static char symbol = "B".charAt(0);
    private static int ID = 5;
    private static String name = "Building";

    static {
        try {
            Entity.addInstance(ID, Building.class);
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
