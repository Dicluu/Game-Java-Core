package Main.Utils.FileLoaders;

import Main.Items.Item;
import Main.Items.Materials.Material;
import Main.Items.Tools.Tool;
import Main.Utils.Messenger;

import java.io.*;

public class ItemLoader {

    public static Item loadItem(int ID) throws IOException {
        File file = new File("src/Main/Resource/Items/Instances/" + ID + ".txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String rawItem = br.readLine();
        String[] sRawItem = rawItem.split(":");
        switch (sRawItem[0]) {
            case "material":
                return new Material(ID, sRawItem[1], Integer.parseInt(sRawItem[2]));
            case "tool":
                return new Tool(ID, sRawItem[1], Integer.parseInt(sRawItem[2]));
            default:
                return null;
        }
    }

    public static Item loadItemByName(String name)  {
        int count = 0;
        while (true) {
            try {
                File file = new File("src/Main/Resource/Items/Instances/" + count + ".txt");
                count++;
                BufferedReader br = new BufferedReader(new FileReader(file));
                String rawItem = br.readLine();
                String[] sRawItem = rawItem.split(":");
                if (sRawItem[1].equals(name)) {
                    switch (sRawItem[0]) {
                        case "material":
                            return new Material(count, sRawItem[1], Integer.parseInt(sRawItem[2]));
                        case "tool":
                            return new Tool(count, sRawItem[1], Integer.parseInt(sRawItem[2]));
                        default:
                            return null;
                    }
                }
            }
            catch (FileNotFoundException e) {
                Messenger.systemMessage("Item with name '" + name + "' not found", ItemLoader.class);
                return null;
            }
            catch (IOException e) {
                Messenger.systemMessage("IOException caught in loadItemByName()", ItemLoader.class);
                return null;
            }
        }
    }

    /*
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

     */
}
