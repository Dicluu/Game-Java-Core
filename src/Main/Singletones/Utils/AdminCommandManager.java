package Main.Singletones.Utils;

import Main.Maps.Map;
import Main.Objects.Characters.Player;
import Main.Objects.Entity;
import Main.Objects.Unique.Entrance;
import Main.Singletones.GameExecutor;
import Main.Utils.Messenger;
import com.sun.source.doctree.EntityTree;

import java.util.InputMismatchException;
import java.util.Scanner;

public class AdminCommandManager {
    public static void setObject(){
        try {
            Scanner num = new Scanner(System.in);
            Messenger.ingameMessage("write id of object you want to set");
            int id = num.nextInt();
            Messenger.ingameMessage("write x coordinate");
            int x = num.nextInt();
            if (GameExecutor.getGame().getCurrentMap().getX() < x || x < 0) {
                Messenger.ingameMessage("x coordinate is wrong");
                throw new ArrayIndexOutOfBoundsException();
            }
            Messenger.ingameMessage("write y coordinate");
            int y = num.nextInt();
            if (GameExecutor.getGame().getCurrentMap().getY() < y || y < 0) {
                Messenger.ingameMessage("y coordinate is wrong");
                throw new ArrayIndexOutOfBoundsException();
            }
            Entity newObject = Entity.getObjectById(id,x,y);
            if (newObject == null) {
                throw new NullPointerException();
            }
            if (id == 2) {
                setEntrance(x,y);
            } else {
                GameExecutor.getGame().getCurrentMap().setObject(newObject);
                Messenger.ingameMessage("object " + newObject + " has been set on coordinates " + "x = " + x + " y = " + y);
            }
        }
        catch (ArrayIndexOutOfBoundsException e ) {
            Messenger.systemMessage("ArrayIndexOutOfBoundsException in setObject() catched ", AdminCommandManager.class);
        }
        catch (NullPointerException e ) {
            Messenger.systemMessage("NullPointerException in setObject() catched ", AdminCommandManager.class);
        }
        catch (InputMismatchException e) {
            Messenger.ingameMessage("You wrote wrong value");
            Messenger.systemMessage("InputMismatchException int setObject() catched", AdminCommandManager.class );
        }
    }

    public static void getInfo() {
        Player cp = GameExecutor.getGame().getCurrentPlayer();
        Map cm = GameExecutor.getGame().getCurrentMap();
        Messenger.systemMessage("Cell objects: ");
        for (Entity object : cp.getCurrentCell().getObjects()) {
            System.out.print(object.getId() + " " + object);
        }
    }

    public static void setEntrance(int x, int y) {
        boolean isPortalAlreadyExistOnCell = false;
        for (Entity entity : GameExecutor.getGame().getCurrentMap().getCell(x,y).getObjects()) {
            if (entity.getId() == 2) {
                isPortalAlreadyExistOnCell = true;
            }
        }
        if (!isPortalAlreadyExistOnCell) {
            Scanner num = new Scanner(System.in);
            Messenger.ingameMessage("Write id map what entrance have to refer");
            int id = num.nextInt();
            if (Map.getMapById(id) != null) {
                GameExecutor.getGame().getCurrentMap().setObject(new Entrance(x, y, id));
                Messenger.ingameMessage("You set portal on x = " + x + " and y = " + y);
            } else {
                Messenger.ingameMessage("You wrote wrong id");
            }
        } else {
            Messenger.ingameMessage("Portal is already exist on this cell");
        }
    }

    public static void showAllEntities() {
        Entity.showAllEntities();
    }

}
