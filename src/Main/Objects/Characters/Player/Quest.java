package Main.Objects.Characters.Player;

import Main.Objects.Characters.NPC.NonPlayerCharacter;

import java.io.Serializable;
import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Quest implements Serializable {

    private String name;
    private NonPlayerCharacter owner;
    private int ID;
    private static HashMap<Integer, Quest> allQuests = new HashMap<>();
    private List<Action> before = new LinkedList<>();
    private List<Action> during = new LinkedList<>();
    private List<Action> after = new LinkedList<>();

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public static Quest getQuestById(int id) {
        return allQuests.get(id);
    }

    public void setName(String name) {
        this.name = name;
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
}
