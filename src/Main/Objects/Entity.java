package Main.Objects;

import Main.Objects.Characters.NPC.Dealer;
import Main.Objects.Characters.Player;
import Main.Objects.Materials.Material;
import Main.Objects.Materials.Materials;
import Main.Objects.Unique.Entrance;

import java.util.ArrayList;
import java.util.List;

public abstract class Entity {
    private int x,y;
    private static int freeID;
    private static List<Entity> allEntities = new ArrayList<>();
    private int objectID;

    public Entity(int x, int y) {
        this.x = x;
        this.y = y;
        this.objectID = freeID;
        freeID++;
        allEntities.add(this);
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

    public int getObjectID() {
        return objectID;
    }

    public static Entity getObjectById(int id, int x, int y) {
            switch (id) {
                case 1:
                    return new Material(x,y, Materials.Tree);
                case 2:
                    return new Entrance(x,y,0);
                case 3:
                    return new Player("default",x,y);
                case 4:
                    return new Dealer("default",x,y);
                default:
                    return null;
            }
        }

        public static void showAllEntities() {
            for (Entity entity : allEntities) {
                System.out.println(entity.getObjectID() + ") " + entity.getId() + " " + entity.getSymbol() + " " + entity);
            }
        }

        public static Entity getObjectByObjectID(int id) {
            for (Entity entity : allEntities) {
                if (entity.getObjectID() == id) {
                    return entity;
                }
            }
            return null;
        }
    }
