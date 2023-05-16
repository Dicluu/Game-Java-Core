package Main.Utils.FileLoaders;

import Main.Utils.Messenger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class TextReader {

    public static void read(String name) {
        try {
            File file = new File("src/Main/Resource/" + name);
            BufferedReader br = new BufferedReader(new FileReader(file));
            while (br.ready()){
                Messenger.ingameMessage(br.readLine());
            }
        }
        catch (Exception e) {
            Messenger.systemMessage("Exception in method read() caught", TextReader.class);
        }
    }
}
