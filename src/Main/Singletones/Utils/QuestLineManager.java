package Main.Singletones.Utils;

import Main.Objects.Characters.Player.Journal;
import Main.Objects.Characters.Player.Quest;
import Main.Utils.Annotations.NeedImprovement;
import Main.Utils.FileLoaders.ScriptLoader;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuestLineManager implements Serializable {

    private HashMap<Integer, QuestLine> lines;
    private List<Quest> history = new ArrayList<>();
    private static final Map<Integer, Quest> allQuests = ScriptLoader.LoadAllQuests();

    public QuestLineManager() {
    this.lines = initializeLines();
    }

    public List<Quest> getHistory() {
        return history;
    }

    public void putInHistory(Quest q) {
        history.add(q);
    }

    @NeedImprovement(comment = "add branching")
    public HashMap<Integer, QuestLine> initializeLines() {
        HashMap<Integer, QuestLine> lines = new HashMap<>();
        int count = 0;
        ArrayList<Quest> line = new ArrayList<>();
        for (Quest q : allQuests.values()) {
            do {
                line.add(q);
            } while (q.getLink() != -1);
            count++;
            lines.put(count,new QuestLine(line, count));
        }
        return lines;
    }

    public static Quest getQuestById(int ID) {
        return allQuests.get(ID);
    }

    public HashMap<Integer,Quest> getAvailable() {
        HashMap<Integer, Quest> available = new HashMap<>();
        int count = 0;
        for (QuestLine q: lines.values()) {
            if (q.getAvailable() != null) {
                available.put(count, q.getAvailable());
                count++;
            }
        }
        return available;
    }

    public void startQuestLine(Quest quest) {
        QuestLine line = findLineByQuest(quest);
        line.start();
    }

    private QuestLine findLineByQuest(Quest quest) {
        for (QuestLine line : lines.values()) {
            if (line.getLine().get(0).getID() == quest.getID()) {
                return line;
            }
        }
        return null;
    }

    public void touchQuests(Journal journal) {
        for (Quest q : journal.getActive().values()) {
            if (q.touch()) {
                journal.complete(q.getID());
            } else if (q.isComplete()) {
                journal.rollback(q.getID());
            }
        }
    }


}
