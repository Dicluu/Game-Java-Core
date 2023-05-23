package Main.Singletones.Utils;

import Main.Objects.Characters.Player.Quest;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * QuestLine class for tracking sequence of quests
 */
public class QuestLine implements Serializable {

    private ArrayList<Quest> line;
    private final int id;

    public QuestLine(ArrayList<Quest> line, int id) {
        this.line = line;
        this.id = id;
    }

    public ArrayList<Quest> getLine() {
        return line;
    }

    public void start() {
        line.get(0).setAvailable(true);
    }

    public void interrupt() {

    }

    /**
     * Returning one available quest
     * @return available quest
     */
    public Quest getAvailable() {
        for (Quest q : line) {
            // Only one quest in QuestLine able to be available
            if (q.isAvailable()) {
                return q;
            }
        }
        return null;
    }

    public int getId() {
        return id;
    }
}
