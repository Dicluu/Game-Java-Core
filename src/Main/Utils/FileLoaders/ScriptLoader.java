package Main.Utils.FileLoaders;

import Main.Objects.Characters.NPC.Speech;
import Main.Objects.Characters.Player.Quest;
import Main.Singletones.GameExecutor;
import Main.Utils.Annotations.NeedImprovement;
import Main.Utils.Messenger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class ScriptLoader {

    @NeedImprovement(comment = "make link args as array for improve branching quest lines")
    public static Quest loadQuest(int ID) {
        Quest q = new Quest(ID);
        String str = "", tmp;
        try {
            File file = new File("src/Main/Resource/Quests/" + ID + "/script");
            BufferedReader brs = new BufferedReader(new FileReader(file));
            BufferedReader br = new BufferedReader(new FileReader(file));
            while (br.ready()) {
                while (!br.readLine().equals("Scene")) {
                    String[] args = brs.readLine().split(":");
                    switch (args[0]) {
                        case "Name":
                            q.setName(args[1]);
                            break;
                        case "Delegate":
                            q.setDelegateID(Integer.parseInt(args[1]));
                            break;
                    }
                }
                br.readLine();
                brs.readLine();
                brs.readLine();
                while (!br.readLine().equals("During")) {
                    str = brs.readLine();
                    String[] args = str.split(":");
                    q.putActionBefore(args);
                }
                brs.readLine();
                while (!br.readLine().equals("After")) {
                    str = brs.readLine();
                    String[] args = str.split(":");
                    q.putActionDuring(args);
                }
                brs.readLine();
                while (!br.readLine().equals("End")) {
                    str = brs.readLine();
                    String[] args = str.split(":");
                    if (args[0].equals("Link")) {
                        break;
                    }
                    q.putActionAfter(args);
                }
                str = brs.readLine();
                if (str.split(":")[0].equals("Link")) {
                    q.setLink(Integer.parseInt(str.split(":")[1]));
                }
            }
            return q;
        } catch (Exception e) {
            Messenger.systemMessage("Exception loadQuest()", ScriptLoader.class);
        }
        return null;
    }

    public static Map<Integer, Quest> LoadAllQuests() {
        Map<Integer, Quest> quests = new HashMap<>();
        int count = 0;
        do {
            quests.put(count, loadQuest(count));
            count++;
        } while (loadQuest(count) != null);
        return quests;
    }

    public static Speech loadQuestSpeech(int QuestID, int speechID) {
        try {
            File file = new File("src/Main/Resource/Quests/" + QuestID + "/speeches");
            BufferedReader br = new BufferedReader(new FileReader(file));
            while (br.ready()) {
                String s = br.readLine();
                if (s.split(":")[0].equals(String.valueOf(speechID))) {
                    return PersonLoader.loadSpeech(s);
                }
            }
        } catch (Exception e) {
            Messenger.systemMessage("Exception loadQuest()", ScriptLoader.class);
        }
        return null;
    }


}
