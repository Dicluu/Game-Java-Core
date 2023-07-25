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
        System.out.print(("active quests: "));
        for (Quest q : active.values()) {
            System.out.print((q.getID() + ") " + q.getName() + "; "));
        }
        System.out.println();
    }

    public void showPassed() {
        System.out.print(("complete quests: "));
        for (Quest q : passed.values()) {
            System.out.print((q.getID() + ") " + q.getName() + "; "));
        }
        System.out.println();
    }

    public void showPossible() {
        possible = scanPossible();
        System.out.print(("possible quests: "));
        for (Quest q : possible.values()) {
            System.out.print((q.getID() + ") " + q.getName() + "; "));
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

    public void rollback(int id) {
        active.put(id, passed.get(id));
        passed.remove(id);
    }

    private HashMap<Integer, Quest> scanPossible() {
        return GameExecutor.getGame().getQuestLineManager().getAvailable();
    }

    public HashMap<Integer, Quest> getPassed() {
        return passed;
    }

    public HashMap<Integer, Quest> getActive() {
        return active;
    }
}
