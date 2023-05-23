package Main.Utils.FileLoaders;

import Main.Objects.Characters.Player.Quest;
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
        Quest q = new Quest();
        String str = "", tmp;
        try {
            File file = new File("src/Main/Resource/Scripts/" + ID);
            BufferedReader brs = new BufferedReader(new FileReader(file));
            BufferedReader br = new BufferedReader(new FileReader(file));
            while (br.ready()) {
                while (!br.readLine().equals("Scene")) {
                    str = brs.readLine();
                    q.setName(str);
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


}
