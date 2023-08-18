package Main.Objects.Characters.NPC;

import Main.Objects.Characters.Character;
import Main.Objects.Characters.Player.Quest;
import Main.Singletones.Utils.QuestLineManager;
import Main.Utils.Annotations.NeedRevision;

import javax.lang.model.type.ArrayType;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

@NeedRevision(comment = "make enum instead of classes")
public abstract class NonPlayerCharacter extends Character {

    private Quest quest;
    HashMap<Integer, Speech> activeSpeeches;

    //only for initializing
    public NonPlayerCharacter(String name, int CID, int ID) {
        super(name, CID, ID);
    }


    public NonPlayerCharacter(String name, int x, int y, int id, int cid) {
        super(name, x, y, id, cid);
    }

    public NonPlayerCharacter(String name, int x, int y, int id, int cid, int questID) {
        super(name, x, y, id, cid);
        quest = QuestLineManager.getQuestById(questID);
        quest.setOwner(this);
    }

    public Quest getQuest() {
        return quest;
    }

    public void setQuest(Quest quest) {
        this.quest = quest;
    }

    public HashMap<Integer, Speech> getSpeeches() {
        HashMap<Integer, Speech> speeches = new HashMap<>();
        for (Speech s : super.getSpeeches().values()) {
            if (!s.isBlocked()) {
                speeches.put(s.getId(), s);
            }
        }
        return speeches;
    }



    public void blockSpeeches(int groupID) {
        for (Speech s : super.getSpeeches().values()) {
            if (groupID == s.getGroupID()) {
                s.setBlocked(true);
            }
        }
        unblockSpeeches(groupID);
    }

    private void unblockSpeeches(int parentID) {
        for (Speech s : super.getSpeeches().values()) {
            if (s.getParentID() == parentID) {
                s.setBlocked(false);
                if (super.getIntroduce().isBlocked()) {
                    super.setIntroduce(s);
                }
            }
        }
        updateActiveSpeeches();
    }

    private void updateActiveSpeeches() {
        activeSpeeches = new HashMap<>();
        int counter = 0;
        for (Speech s : super.getSpeeches().values()) {
            if (!s.isBlocked()) {
                activeSpeeches.put(counter++, s);
            }
        }
    }

    public HashMap<Integer, Speech> getSpeechesInRow() {
        if (activeSpeeches != null) {
            return activeSpeeches;
        }
        updateActiveSpeeches();
        return activeSpeeches;
    }
}
