package Main.Objects.Unique;

import Main.Objects.Entity;

public class RedirectingEntity extends UniqueEntity implements Redirecting {

    static {
        try {
            Entity.addInstance(9, RedirectingEntity.class);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private static final int ID = 9;
    private static final String name = "RedirectingEntity";

    @Override
    public int getId() {
        return ID;
    }

    @Override
    public String getName() {
        return name;
    }
}
