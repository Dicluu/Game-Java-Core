package Main.Utils;

import Main.Objects.Characters.NPC.Speech;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class PersonLoader {

    public static String loadName(int ID) {
        try {
            File file = new File("src/Main/Resource/" + ID + "/name.txt");
            BufferedReader br = new BufferedReader(new FileReader(file));
            return br.readLine();
        }
        catch (Exception e) {
            Messenger.systemMessage("Exception loadName()", PersonLoader.class);
            return null;
        }
    }

    public static List<Speech> loadSpeeches(int ID) {
        try {
            List<Speech> speeches = new ArrayList<>();
            File file = new File("src/Main/Resource/" + ID + "/speeches.txt");
            BufferedReader br = new BufferedReader(new FileReader(file));
            while (br.ready()) {
                try {
                    String line = br.readLine();
                    String[] args = line.split(":");
                    int id = Integer.parseInt(args[0]);
                    String speech = args[1];
                    boolean answerable = Boolean.parseBoolean(args[2]);
                    if (answerable) {
                        String rawStrAnswers = args[3];
                        String[] strAnswers = rawStrAnswers.split(",");
                        List<Integer> answers = new ArrayList<>();
                        for (int i = 0; i < strAnswers.length; i++) {
                            answers.add(Integer.parseInt(strAnswers[i]));
                        }
                        speeches.add(new Speech(speech, true, answers, id));
                    } else {
                        speeches.add(new Speech(speech, false, id));
                    }
                } catch (Exception e) {
                    Messenger.systemMessage("Exception in loadPerson()", PersonLoader.class);
                }
            }
            return speeches;
        }
        catch (Exception e) {
            Messenger.systemMessage("Exception in loadPerson()", PersonLoader.class);
            return null;
        }
    }
}
