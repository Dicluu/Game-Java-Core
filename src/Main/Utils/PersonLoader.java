package Main.Utils;

import Main.Objects.Characters.NPC.Speech;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class PersonLoader {

    public static String loadName(int ID) throws Exception {
        File file = new File("src/Main/Resource/" + ID + "/name");
        BufferedReader br = new BufferedReader(new FileReader(file));
        return br.readLine();
    }

    public static List<Speech> loadSpeeches(int ID) throws Exception {
        List<Speech> speeches = new ArrayList<>();
        File file = new File("src/Main/Resource/" + ID + "/speeches.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        while (br.ready()) {
            String line = br.readLine();
            String[] args = line.split(":");
            String speech = args[0];
            boolean answerable = Boolean.parseBoolean(args[1]);
            if (answerable) {
                String rawStrAnswers = args[2];
                String[] strAnswers = rawStrAnswers.split(",");
                List<Integer> answers = new ArrayList<>();
                for (int i = 0; i < strAnswers.length; i++) {
                    answers.add(Integer.parseInt(strAnswers[i]));
                }
                speeches.add(new Speech(speech, true, answers));
            } else {
                speeches.add(new Speech(speech, false));
            }
        }
        return speeches;
    }

}
