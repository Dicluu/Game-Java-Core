package Main.Objects.Unique;

import Main.Objects.Entity;

public class Gates extends UniqueEntity implements Lockable{

    static {
        try {
            Entity.addInstance(7, Gates.class);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private static int ID = 7;
    private static String name = "Gates";
    boolean isLocked;
    String lockedDirection;

    public Gates() {
        isLocked = true;
    }

    @Override
    public boolean isLocked() {
        return isLocked;
    }

    public String getLockedDirection() {
        return lockedDirection;
    }

    public void setLockedDirection(String lockedDirection) {
        this.lockedDirection = lockedDirection;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }

    public void open() {
        setLocked(false);
    }

    public void lock() {
        setLocked(true);
    }





    @Override
    public char getSymbol() {
        if (isLocked) {
            return '▓';
        } else {
            return '░';
        }
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
