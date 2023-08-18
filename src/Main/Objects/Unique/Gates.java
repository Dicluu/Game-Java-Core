package Main.Objects.Unique;

import Main.Objects.Entity;

public class Gates extends UniqueEntity implements Lockable{

    static {
        try {
            Entity.addInstance(12, Gates.class);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

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
        return 12;
    }

    @Override
    public String getName() {
        return "Gates";
    }
}
