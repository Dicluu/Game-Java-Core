package Main.Objects;

import Main.Items.Item;
import Main.Objects.Characters.Character;
import Main.Objects.Materials.Material;
import Main.Objects.Materials.Materials;
import Main.Utils.Annotations.NeedImprovement;
import Main.Utils.FileLoaders.EntityLoader;
import Main.Utils.Messenger;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;
@NeedImprovement(comment = "make a variable 'description' and add methods to show description")
public abstract class Entity implements Cloneable, Serializable {
    private int x, y;
    private static int freeID;
    private int ID;
    private static HashMap<Integer, Entity> allEntities = new HashMap<>();
    private int objectID;
    private static HashMap<Integer, Entity>  instances = new HashMap<>();

    static {
        addEnumsInstances();
    }

    public Entity(int x, int y, int ID) {
        this.x = x;
        this.y = y;
        this.ID = ID;
        this.objectID = freeID;
        freeID++;
        allEntities.put(objectID, this);
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

    public int getID() {
        return ID;
    }

    public abstract char getSymbol();

    public abstract int getPriority();

    public abstract int getId();

    public abstract String getName();

    public int getObjectID() {
        return objectID;
    }

    public static Entity getObjectById(int id) {
        return instances.get(id);
    }

    public static HashMap<Integer, Entity> getAllEntities() {
        return allEntities;
    }

    public static Entity getEntityById(int id) {
         return allEntities.get(id);
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
            instances.put(material.getId(), new Material(0, 0, material, material.getId()));
        }
    }

    public static void addInstance(int id, Class<? extends Entity> clazz) throws InstantiationException, IllegalAccessException {
        Entity e = clazz.newInstance();
        e.ID = id;
        instances.put(id, e);
    }

    public void setObjectID(int objectID) {
        this.objectID = objectID;
    }

    public static void showInstancesSystem() {
        Messenger.systemMessage("isEmpty: " + instances.isEmpty(), Entity.class);
        for (Entity entity : instances.values()) {
            Messenger.systemMessage("instance id: " + entity.getId() + "; instance name: " + entity.getName(), Entity.class);
        }
    }
    public static Entity newInstance(int id) throws CloneNotSupportedException {
        try {
            Entity entity = (Entity) getObjectById(id).clone();
            ((Character) entity).getInventory();
            entity.setObjectID(freeID++);
            allEntities.put(entity.getObjectID(),entity);
            Character.register((Character) entity);
            return entity;
        }
        catch (CloneNotSupportedException e) {
            Messenger.systemMessage(e.getMessage());
        }
        catch (ClassCastException e) {
            Entity entity = (Entity) getObjectById(id).clone();
            entity.setObjectID(freeID++);
            allEntities.put(entity.getObjectID(),entity);
            return entity;
        }
        return null;
    }

    public static void showInstances() {
        for (Entity entity : instances.values()) {
            Messenger.helpMessage("id: " + entity.getId() + "; name: " + entity.getName());
        }
    }



    public static void showLastInstance() {
        Messenger.systemMessage("Last id of Entity instance: " + instances.get(instances.size()).getId(), Entity.class);
    }

    public static void setAllEntities(HashMap<Integer, Entity> entities) {
        allEntities = entities;
    }

    public String getDescription() {
        try {
            return EntityLoader.loadDescription(this.ID);
        }
        catch (IOException e) {
            Messenger.systemMessage("IOException getDescription()", Entity.class);
            return "You don't even know what to say";
        }
    }
}
