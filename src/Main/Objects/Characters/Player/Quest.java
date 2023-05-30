package Main.Objects.Characters.Player;

import Main.Objects.Characters.NPC.NonPlayerCharacter;
import Main.Singletones.GameExecutor;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class Quest implements Serializable {

    private String name;
    private NonPlayerCharacter owner;
    private int ID, link = -1;
    private boolean complete, available, active;
    private List<Action> before = new LinkedList<>();
    private List<Action> during = new LinkedList<>();
    private List<Action> after = new LinkedList<>();

    public Quest(int ID) {
        this.ID = ID;
    }

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
            before.add(new Action(this, args));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void putActionDuring(String... args) {
        during.add(new Action(this, args));
    }

    public void putActionAfter(String... args) {
        after.add(new Action(this, args));
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

    public NonPlayerCharacter getOwner() {
        return owner;
    }

    public void setOwner(NonPlayerCharacter owner) {
        this.owner = owner;
    }

    public void initiate() {
        if(isAvailable()) {
            Player player = GameExecutor.getGame().getCurrentPlayer();
            Journal journal = player.getJournal();
            journal.proceed(this.ID);
            for (Action a : before) {
                a.execute();
            }
            setActive(true);
            setAvailable(false);
        }
    }

    public boolean touch() {
        for (Action a : during) {
            a.execute();
            if (!a.isDone()) {
                if (complete) {
                    setActive(true);
                    setComplete(false);
                }
                return false;
            }
        }
        if (active) {
            setActive(false);
            setComplete(true);
        }
        return true;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
