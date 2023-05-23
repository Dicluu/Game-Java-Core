package Main.Objects.Characters.Player;

import Main.Objects.Characters.NPC.NonPlayerCharacter;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class Quest implements Serializable {

    private String name;
    private NonPlayerCharacter owner;
    private int ID, link = -1;
    private boolean complete, available;
    private List<Action> before = new LinkedList<>();
    private List<Action> during = new LinkedList<>();
    private List<Action> after = new LinkedList<>();

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    public void putActionBefore(String... args) {
        try {
            before.add(new Action(args));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void putActionDuring(String... args) {
        during.add(new Action(args));
    }

    public void putActionAfter(String... args) {
        after.add(new Action(args));
    }

    public void setLink(int id) {
        this.link = id;
    }

    public int getLink() {
        return link;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
