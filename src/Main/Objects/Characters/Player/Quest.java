package Main.Objects.Characters.Player;

import Main.Objects.Characters.NPC.NonPlayerCharacter;
import Main.Objects.Characters.NPC.Speech;
import Main.Singletones.GameExecutor;
import Main.Singletones.Utils.QuestLineManager;
import Main.Utils.FileLoaders.ScriptLoader;
import Main.Utils.Messenger;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class Quest implements Serializable {

    private String name;
    private NonPlayerCharacter owner;
    private Speech delegate;
    private int delegateID;
    private int ID, link = -1, status = 0;
    private boolean complete, available, active, finished;
    private List<Action> before = new LinkedList<>();
    private List<Action> during = new LinkedList<>();
    private List<Action> after = new LinkedList<>();

    public Quest(int ID) {
        this.ID = ID;
    }

    public void finish() {
        for (Action a : after) {
            a.execute();
        }
        setComplete(false);
        GameExecutor.getGame().getQuestLineManager().putInHistory(this);
        Messenger.helpMessage("You finished quest " + this.getName());
        GameExecutor.getGame().getCurrentPlayer().getSpeeches().remove(delegateID);
    }

    public Speech getDelegate() {
        return delegate;
    }

    public void setDelegate(Speech delegate) {
        this.delegate = delegate;
    }

    private void setDelegate(int status) {
        this.delegate.morph(ScriptLoader.loadQuestSpeech(this.ID, status));
    }

    public int getDelegateID() {
        return delegateID;
    }

    public void setDelegateID(int delegateID) {
        this.delegateID = delegateID;
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
        if ((!isComplete()) && (complete)) {
            Messenger.helpMessage("You can finish quest, talk with you hirer");
        }
        if (complete) {
            status = 2;
        }
        this.complete = complete;
    }

    public void putActionBefore(String... args) {
        try {
            before.add(new Action(this, false, args));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void putActionDuring(String... args) {
        during.add(new Action(this, true, args));
    }

    public void putActionAfter(String... args) {
        after.add(new Action(this, false, args));
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
        if (isAvailable()) {
            Player player = GameExecutor.getGame().getCurrentPlayer();
            Journal journal = player.getJournal();
            setDelegate(player.getSpeeches().get(delegateID));
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
                setActive(true);
                setComplete(false);
                setDelegate(status);
                return false;
            }
        }
        if (active) {
            setActive(false);
            setComplete(true);
            setDelegate(status);
            return true;
        }
        return false;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        if (active) {
            status = 1;
        }
        this.active = active;
    }
}
