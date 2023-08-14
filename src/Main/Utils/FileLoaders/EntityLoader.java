package Main.Utils.FileLoaders;

import Main.Items.Item;
import Main.Items.Materials.Material;
import Main.Items.Tools.Tool;
import Main.Objects.Characters.Player.Player;
import Main.Singletones.GameExecutor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class EntityLoader {

    public static String loadDescription(int ID) throws IOException {
        File file = new File("src/Main/Resource/Entities/Descriptions/" + ID + ".txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        Player player = GameExecutor.getGame().getCurrentPlayer();
        int intelligence = player.getIntelligence();
        String[] rawStr = br.readLine().split(":");
        String result = null;
        int currentIntelligence = Integer.parseInt(rawStr[0]);
        if (currentIntelligence < intelligence) {
            result = rawStr[1];
        }
        while (br.ready()) {
            rawStr = br.readLine().split(":");
            if ((Integer.parseInt(rawStr[0]) > currentIntelligence) && (Integer.parseInt(rawStr[0]) < intelligence)) {
                currentIntelligence = Integer.parseInt(rawStr[0]);
                result = rawStr[1];
            }
        }
        return result;
    }

}
