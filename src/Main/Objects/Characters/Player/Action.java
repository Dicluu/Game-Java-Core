package Main.Objects.Characters.Player;

import Main.Items.Item;
import Main.Items.Tools.Tiers;
import Main.Items.Tools.Tool;
import Main.Objects.Characters.Character;
import Main.Objects.Characters.NPC.NonPlayerCharacter;
import Main.Objects.Entity;
import Main.Singletones.GameExecutor;
import Main.Utils.Messenger;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

public class Action implements Serializable {

    private boolean done = false;
    private Method method;
    private List<String> args;

    public Action(String... a) {
        args = Arrays.asList(a);
        try {
            switch (args.get(0)) {
                case "remove":
                    method = Action.class.getMethod("remove", List.class);
                    break;
                case "have":
                    method = Action.class.getMethod("have", String[].class);
                    break;
            }
        }
        catch (NoSuchMethodException e) {
            Messenger.systemMessage("NoSuchMethodException caught in Action() constructor", Action.class);
        }
    }

    public void execute() {
        try {
            method.invoke(args);
        } catch (InvocationTargetException e) {
            Messenger.systemMessage("InvocationTargetException caught in execute()", Action.class);
        } catch (IllegalAccessException e) {
            Messenger.systemMessage("IllegalAccessException caught in execute()", Action.class);
        }
    }

    public void remove(List<String> args) {
        int c = 0;
        int count = Integer.parseInt(args.get(2));
        int ID = Integer.parseInt(args.get(1));
        Player cp = GameExecutor.getGame().getCurrentPlayer();
        for (Item i : cp.getInventory()) {
            if (i == Item.getItemById(ID)) {
                c++;
            }
            if (c == count) {
                break;
            }
        }
        if (c != count) {
            Messenger.helpMessage("You don't have enough items to complete this quest");
            return;
        }
        Item[] inv = cp.getInventory();
        for (int i = 0; i < inv.length; i++) {
            if (Item.getItemById(ID) == inv[i]) {
                cp.deleteItemFromInventory(i);
                c++;
                if (c == count) {
                    done = true;
                    break;
                }
            }
        }
    }

    public void give(List<String> args) {
        int ID = Integer.parseInt(args.get(1));
        int value = Integer.parseInt(args.get(2));
        Item item = Item.getItemById(ID);
        Player cp = GameExecutor.getGame().getCurrentPlayer();
        Item[] inv = cp.getInventory();
        int count = 0;
        if (item instanceof Tool) {
            int TierID = Integer.parseInt(args.get(3));
            ((Tool) item).setTier(Tiers.getById(TierID));
        }
        for (int i = 0; i < inv.length; i++) {
            if (inv[i] == null) {
                count++;
            }
            if (count == value) {
                break;
            }
        }
        if (count == value) {
            for (int i = 0; i < value; i++) {
                cp.putItem(item);
            }
            done = true;
        } else {
            Messenger.helpMessage("Your inventory is full, you need free some space to get this quest");
        }
    }

    public void say(List<String> args) {
        NonPlayerCharacter c = (NonPlayerCharacter) Entity.getEntityById(Integer.parseInt(args.get(0)));
        Messenger.ingameMessage(c.getName() + ": " + args.get(2));
        done = true;
    }

    public void have(List<String> args) {
        Player cp = GameExecutor.getGame().getCurrentPlayer();
        Item[] inv = cp.getInventory();
        int count = Integer.parseInt(args.get(2));
        int ID = Integer.parseInt(args.get(1));
        int c = 0;
        for (Item i : inv) {
            if (i == Item.getItemById(ID)) {
                c++;
            }
            if (c == count) {
                break;
            }
        }
        if (c != count) {
            return;
        } else {
            done = true;
        }
    }
}