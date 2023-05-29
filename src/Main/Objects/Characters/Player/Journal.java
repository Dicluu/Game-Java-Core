package Main.Objects.Characters.Player;

import Main.Singletones.GameExecutor;
import Main.Utils.Annotations.NeedRevision;
import Main.Utils.Messenger;

import java.io.Serializable;
import java.util.HashMap;

public class Journal implements Serializable {

    @NeedRevision(comment = "Maybe should use LinkedList")
    HashMap<Integer, Quest> passed = new HashMap();
    HashMap<Integer, Quest> possible = new HashMap();
    HashMap<Integer, Quest> active = new HashMap<>();

    public Journal() {
        possible = scanPossible();
    }

    public void showActive() {
        Messenger.ingameMessage("active quests: ");
        for (Quest q : active.values()) {
            Messenger.ingameMessage(q.getID() + ") " + q.getName());
        }
    }

    public void complete(int id) {
        passed.put(id, active.get(id));
        active.remove(id);
    }

    public void proceed(int id) {
        active.put(id, possible.get(id));
        possible.remove(id);
    }

    private HashMap<Integer, Quest> scanPossible() {
        return GameExecutor.getGame().getQuestLineManager().getAvailable();
    }

    public HashMap<Integer, Quest> getPassed() {
        return passed;
    }

    public void showPossible() {
        possible = scanPossible();
        Messenger.ingameMessage("possible quests: ");
        for (Quest q : possible.values()) {
            Messenger.ingameMessage(q.getID() + ") " + q.getName());
        }
    }
}
