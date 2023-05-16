package Main.Utils.FileLoaders;

import Main.Utils.Messenger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class ItemLoader {

    public static int getItemPrice(int ID) {
        try {
            File file = new File("src/Main/Resource/Items/items.txt");
            BufferedReader br = new BufferedReader(new FileReader(file));
            while (br.ready()) {
                String arg = br.readLine();
                String[] item = arg.split(":");
                if (ID == Integer.parseInt(item[0])) {
                    return Integer.parseInt(item[1]);
                }
            }
        }
        catch (Exception e) {
            Messenger.systemMessage("Exception getItemPrice()", ItemLoader.class);
        }
        return 0;
    }
}
