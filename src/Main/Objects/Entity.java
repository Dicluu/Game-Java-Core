package Main.Objects;

import Main.Items.Item;
import Main.Objects.Materials.Material;
import Main.Objects.Materials.Materials;
import Main.Utils.Messenger;

import java.util.ArrayList;
import java.util.List;

public abstract class Entity {
    private int x, y;
    private static int freeID;
    private static List<Entity> allEntities = new ArrayList<>();
    private int objectID;
    private static List<Entity> instances = new ArrayList<>();

    static {
        addEnumsInstances();
    }

    public Entity(int x, int y) {
        this.x = x;
        this.y = y;
        this.objectID = freeID;
        freeID++;
        allEntities.add(this);
    }

    public Entity() {

    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public abstract char getSymbol();

    public abstract int getPriority();

    public abstract int getId();

    public abstract String getName();

    public int getObjectID() {
        return objectID;
    }

    public static Entity getObjectById(int id) {
        for (Entity entity : instances) {
            if (entity.getId() == id) {
                return entity;
            }
        }
        return null;
    }

    public static void showAllEntities() {
        for (Entity entity : allEntities) {
            Messenger.systemMessage(entity.getObjectID() + ") " + entity.getId() + " " + entity.getSymbol() + " " + entity);
        }
    }

    /*
    public static Entity getObjectByObjectID(int id) {
        for (Entity entity : allEntities) {
            if (entity.getObjectID() == id) {
                return entity;
            }
        }
        return null;
    }
     */
    private static void addEnumsInstances() {
        for (Materials material : Materials.values()) {
            instances.add(new Material(0, 0, material));
        }
    }

    public static void addInstance(int id, Class<? extends Entity> clazz) throws InstantiationException, IllegalAccessException {
        instances.add(clazz.newInstance());
    }

    public static void showInstancesSystem() {
        Messenger.systemMessage("isEmpty: " + instances.isEmpty(), Entity.class);
        for (Entity entity : instances) {
            Messenger.systemMessage("instance id: " + entity.getId() + "; instance name: " + entity.getName(), Entity.class);
        }
    }

    public static void showInstances() {
        for (Entity entity : instances) {
            Messenger.helpMessage("id: " + entity.getId() + "; name: " + entity.getName());
        }
    }

    public static void showLastInstance() {
        Messenger.systemMessage("Last id of Entity instance: " + instances.get(instances.size()-1).getId(), Entity.class);
    }
}
