package Main.Utils.FileLoaders;

import Main.Objects.Characters.Player.Quest;
import Main.Utils.Messenger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class ScriptLoader {

    public static Quest loadQuest(int ID) {
        Quest q = new Quest();
        String str, tmp;
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
                br.readLine();
                brs.readLine();
                while (!br.readLine().equals("End")) {
                    str = brs.readLine();
                    String[] args = str.split(":");
                    q.putActionAfter(args);
                }
            }
            return q;
        }
        catch (Exception e) {
            Messenger.systemMessage("Exception loadQuest()", ScriptLoader.class);
        }
        return null;
    }


}
