package Main.Objects.Unique;

import Main.Objects.Entity;

public class IrresistibleEntity extends UniqueEntity implements Irresistible{

    static {
        try {
            Entity.addInstance(8, IrresistibleEntity.class);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private static final int ID = 8;
    private static final String name = "IrresistibleEntity";

    @Override
    public int getId() {
        return ID;
    }

    @Override
    public String getName() {
        return name;
    }
}
