package Main.Objects.Characters.Player;

import Main.Objects.Characters.Character;
import Main.Objects.Characters.NPC.Speech;
import Main.Singletones.GameExecutor;
import Main.Utils.Messenger;

import java.security.cert.PolicyQualifierInfo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Journal {

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
}
