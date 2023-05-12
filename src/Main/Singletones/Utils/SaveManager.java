package Main.Singletones.Utils;

import Main.Singletones.GameExecutor;
import Main.Utils.Annotations.NeedImprovement;
import Main.Utils.Messenger;

import java.io.*;

@NeedImprovement(comment = "make slots for saves")
public class SaveManager {

    public static void save() {
        try {
            FileOutputStream fos = new FileOutputStream("test");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(GameExecutor.getGame());
        }
        catch (Exception e) {
            Messenger.systemMessage("Exception in saveGE", SaveManager.class);
        }
    }

    public static GameExecutor load() {
        try {
            FileInputStream fis = new FileInputStream("test");
            ObjectInputStream ois = new ObjectInputStream(fis);
            return ((GameExecutor) ois.readObject());
        }
        catch (Exception e) {
            Messenger.systemMessage("Exception in loadGE", SaveManager.class);
        }
        return null;
    }


}
