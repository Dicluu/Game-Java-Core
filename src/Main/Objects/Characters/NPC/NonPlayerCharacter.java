package Main.Objects.Characters.NPC;

import Main.Objects.Characters.Character;
import Main.Objects.Characters.Player.Quest;
import Main.Objects.Entity;
import Main.Singletones.Utils.QuestLineManager;
import Main.Utils.Annotations.NeedRevision;
import Main.Utils.FileLoaders.PersonLoader;

import javax.lang.model.type.ArrayType;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

@NeedRevision(comment = "make enum instead of classes")
public class NonPlayerCharacter extends Character {

    static {
        try {
            Entity.addInstance(3, NonPlayerCharacter.class);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private String name = "NonPlayerCharacter";
    private static final int ID = 3;
    private Quest quest;
    private HashMap<Integer, Speech> activeSpeeches;
    private char symbol;

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

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int getId() {
        return ID;
    }

    @Override
    public char getSymbol() {
        return symbol;
    }

    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }
}
