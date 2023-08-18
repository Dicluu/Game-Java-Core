package Main.Objects.Unique;

import Main.Objects.Entity;
import Main.Objects.Priority;

public abstract class UniqueEntity extends Entity {

    private final static int priority = Priority.MEDIUM.toInt();
    private int CID;

    public UniqueEntity(int x, int y, int ID) {
        super(x, y, ID);
    }
    public UniqueEntity() {super();}

    public int getCID() {
        return CID;
    }

    public void setCID(int CID) {
        this.CID = CID;
        super.setObjectID(CID);
        super.setCID(CID);
        //super.setID(CID);
    }

    public int getPriority() {
        return priority;
    }

}
