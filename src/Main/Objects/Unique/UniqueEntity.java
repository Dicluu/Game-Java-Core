package Main.Objects.Unique;

import Main.Objects.Entity;
import Main.Objects.Priority;

public abstract class UniqueEntity extends Entity {

    private final static int priority = Priority.MEDIUM.toInt();

    public UniqueEntity(int x, int y) {
        super(x, y);
    }

    public int getPriority() {
        return priority;
    }

}
