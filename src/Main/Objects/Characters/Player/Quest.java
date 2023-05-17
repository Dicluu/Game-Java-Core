package Main.Objects.Characters.Player;

import java.io.Serializable;
import java.util.HashMap;

public class Quest implements Serializable {

    private String name;
    private int ID;
    private static HashMap<Integer, Quest> allQuests = new HashMap<>();

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public static Quest getQuestById(int id) {
        return allQuests.get(id);
    }
}
