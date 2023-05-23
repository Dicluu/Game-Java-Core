package Main.Singletones.Utils;

import Main.Objects.Characters.Player.Quest;
import Main.Utils.Annotations.NeedImprovement;
import Main.Utils.FileLoaders.ScriptLoader;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class QuestLineManager implements Serializable {

    private HashMap<Integer, QuestLine> lines;
    private static final Map<Integer, Quest> allQuests = ScriptLoader.LoadAllQuests();

    public QuestLineManager() {
    this.lines = initializeLines();
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
                available.put(count,q.getAvailable());
                count++;
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


}
