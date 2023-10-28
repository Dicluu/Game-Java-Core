package Main.Objects.Unique;

import Main.Objects.Entity;
import Main.Objects.Priority;

public class UniqueEntity extends Entity {

    static {
        try {
            Entity.addInstance(4, UniqueEntity.class);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private final static int ID = 4;
    private final static String name = "UniqueEntity";
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

    @Override
    public int getId() {
        return ID;
    }

    @Override
    public String getName() {
        return name;
    }

}
