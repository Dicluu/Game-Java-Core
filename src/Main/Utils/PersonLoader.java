package Main.Utils;

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

    public static List<String> loadSpeeches(int ID) throws Exception{
        List<String> speeches = new ArrayList<>();
        File file = new File("src/Main/Resource/" + ID + "/speeches.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        while (br.ready()) {
            speeches.add(br.readLine());
        }
        return speeches;
    }

}
