package Main.Objects.Characters.Player;

import Main.Items.Item;
import Main.Items.Tools.Tiers;
import Main.Items.Tools.Tool;
import Main.Objects.Characters.Character;
import Main.Objects.Characters.NPC.NonPlayerCharacter;
import Main.Objects.Entity;
import Main.Objects.Unique.Lockable;
import Main.Singletones.GameExecutor;
import Main.Utils.Annotations.NeedImprovement;
import Main.Utils.Messenger;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

public class Action implements Serializable {

    private boolean done = false;
    private transient Method method;
    private String rawMethod;
    private List<String> args;
    private Quest quest;
    private int ownerID, groupID;
    private boolean isCondition;

    public Action(Quest quest, boolean isCondition, String... a) {
        this.quest = quest;
        this.isCondition = isCondition;
        args = Arrays.asList(a);
        rawMethod = args.get(0);
        try {
            method = analyzeMethod(rawMethod);
        } catch (NoSuchMethodException e) {
            Messenger.systemMessage("NoSuchMethodException caught in Action() constructor", Action.class);
        }
    }

    public Action(int ownerID, boolean isCondition, int groupID, String... a) {
        this.ownerID = ownerID;
        this.groupID = groupID;
        this.isCondition = isCondition;
        args = Arrays.asList(a);
        rawMethod = args.get(0);
        try {
            method = analyzeMethod(rawMethod);
        } catch (NoSuchMethodException e) {
            Messenger.systemMessage("NoSuchMethodException caught in Action() constructor", Action.class);
        }
    }
    public void execute() {
            try {
                if ((!isDone()) || (isCondition())) {
                    if (method != null) {
                        method.invoke(this, args);
                    } else {
                        method = analyzeMethod(rawMethod);
                        method.invoke(this, args);
                    }
                }
            } catch (InvocationTargetException e) {
                Messenger.systemMessage("InvocationTargetException caught in execute()", Action.class);
            } catch (IllegalAccessException e) {
                Messenger.systemMessage("IllegalAccessException caught in execute()", Action.class);
            } catch (NoSuchMethodException e) {
                Messenger.systemMessage("NoSuchMethodException caught in execute()", Action.class);
            }
        }

    public void remove(List<String> args) {
        int c = 0;
        int count = Integer.parseInt(args.get(2));
        int ID = Integer.parseInt(args.get(1));
        Player cp = GameExecutor.getGame().getCurrentPlayer();
            for (Item i : cp.getInventory()) {
                try {
                    if (Item.getItemById(i.getId()) instanceof Tool) {
                        if (((Tool) i).getTier() == Tiers.getById(Integer.parseInt(args.get(3)))) {
                            c++;
                        }
                    } else if (Item.getItemById(i.getId()) == Item.getItemById(ID)) {
                        c++;
                    }
                    if (c == count) {
                        break;
                    }
                }
                catch (NullPointerException e) {

            }
        }
        if (c != count) {
            Messenger.helpMessage("You don't have enough items to complete this quest");
            return;
        }
        Item[] inv = cp.getInventory();
        for (int i = 0; i < inv.length; i++) {
            try {
                if (Item.getItemById(ID) == Item.getItemById(inv[i].getId())) {
                    cp.deleteItemFromInventory(i);
                    c++;
                    if (c == count) {
                        done = true;
                        break;
                    }
                }
            }
            catch (NullPointerException e) {

            }
        }
    }

    public void give(List<String> args) {
            if (args.get(1).equals("m")) {
                GameExecutor.getGame().getCurrentPlayer().addMoney(Integer.parseInt(args.get(2)));
                Messenger.helpMessage("you have been added " + args.get(2) + "$");
                done = true;
                return;
            }
            int ID = Integer.parseInt(args.get(1));
            int value = Integer.parseInt(args.get(2));
            Item item = Item.newInstance(ID);
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
        NonPlayerCharacter c = quest.getOwner();
        Messenger.ingameMessage(c.getName() + ": " + args.get(1));
        done = true;
    }
    public void have(List<String> args) {
        Player cp = GameExecutor.getGame().getCurrentPlayer();
        Item[] inv = cp.getInventory();
        int count = Integer.parseInt(args.get(2));
        int ID = Integer.parseInt(args.get(1));
        int c = 0;

        for (Item i : inv) {
            try {
                if (i instanceof Tool) {
                    if (args.size() > 3) {
                        if (((Tool) i).getTier() == Tiers.getById(Integer.parseInt(args.get(3)))) {
                            c++;
                        }
                    }
                } else {
                    if (i.equals(Item.getItemById(ID))) {
                        c++;
                    }
                }
                if (c == count) {
                    done = true;
                    return;
                } else {
                    done = false;
                }
            } catch (NullPointerException e) {
            }
        }
    }

    public void open(List<String> args) {
        int CID = Integer.parseInt(args.get(1));
        Lockable e = (Lockable) Entity.getEntityById(CID);
        e.open();
        if (ownerID != 0) {
            NonPlayerCharacter c = (NonPlayerCharacter) Character.getAllCharacters().get(ownerID);
            c.blockSpeeches(groupID);
        }
    }

    public Method analyzeMethod(String rawMethod) throws NoSuchMethodException {
        switch (rawMethod) {
            case "remove":
                method = Action.class.getMethod("remove", List.class);
                break;
            case "have":
                method = Action.class.getMethod("have", List.class);
                break;
            case "say":
                method = Action.class.getMethod("say", List.class);
                break;
            case "give":
                method = Action.class.getMethod("give", List.class);
                break;
            case "open":
                method = Action.class.getMethod("open", List.class);
                break;
        }
        return method;
    }

    public boolean isDone() {
        return done;
    }

    public boolean isCondition() {
        return isCondition;
    }
}
