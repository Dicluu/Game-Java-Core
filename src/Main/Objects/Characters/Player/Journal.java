package Main.Objects.Characters.Player;

import Main.Objects.Characters.Character;
import Main.Objects.Characters.NPC.Speech;
import Main.Singletones.GameExecutor;
import Main.Utils.Annotations.NeedRevision;
import Main.Utils.Messenger;

import java.io.Serializable;
import java.security.cert.PolicyQualifierInfo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Journal implements Serializable {

    @NeedRevision(comment = "Maybe should use LinkedList")
    HashMap<Integer, Quest> passed = new HashMap();
    HashMap<Integer, Quest> possible = new HashMap();
    HashMap<Integer, Quest> active = new HashMap<>();

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

    @NeedRevision(comment = "this method doesn't observe sequence of quests, need to refactor in detached class maybe")
    public static HashMap<Integer, Quest> scanPossible() {
        HashMap<Integer, Character> characters = Character.getAllCharacters();
        HashMap<Integer, Quest> p = new HashMap();
        for (Character c : characters.values()) {
            for (Speech s : c.getSpeeches().values()) {
                if (s.isQuest()) {
                    p.put(s.getQuestID(), Quest.getQuestById(s.getQuestID()));
                }
            }
        }
        return p;
    }

    public HashMap<Integer, Quest> getPassed() {
        return passed;
    }

    public void showPossible() {
        possible = scanPossible();
        Messenger.ingameMessage("possible quests: ");
        for (Quest q : active.values()) {
            Messenger.ingameMessage(q.getID() + ") " + q.getName());
        }
    }
}
