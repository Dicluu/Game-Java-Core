package Main.Objects;

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
    /*
        ID - Identifier of Entity abstract parent.
        UID - Unique ID - auto increment.
        CID - Concrete ID - ID from Replicator's list
    */
    private int ID, UID, CID;
    private char symbol;
    private static HashMap<Integer, Entity> allEntities = new HashMap<>();
    private static HashMap<Integer, Entity>  instances = new HashMap<>();
    private String description;

    static {
        addEnumsInstances();
    }

    public Entity(int x, int y, int ID) {
        this.x = x;
        this.y = y;
        this.ID = ID;
        this.UID = freeID;
        freeID++;
        allEntities.put(UID, this);
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

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getCID() {
        return CID;
    }

    public void setCID(int CID) {
        this.CID = CID;
    }

    public char getSymbol()
    {
        return symbol;
    };

    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }

    public abstract int getPriority();

    public abstract int getId();

    public abstract String getName();

    public int getObjectID() {
        return UID;
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

    public void setDescription(String description) {
        this.description = description;
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

    public void setObjectID(int UID) {
        allEntities.remove(UID);
        this.UID = UID;
        allEntities.put(UID, this);
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
        if (description == null) {
            try {
                return EntityLoader.loadDescription(this.CID);
            } catch (IOException e) {
                Messenger.systemMessage("IOException getDescription()", Entity.class);
                return "You don't even know what to say";
            }
        } else {
            return description;
        }
    }
}
