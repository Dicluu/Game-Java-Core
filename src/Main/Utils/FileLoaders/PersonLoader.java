package Main.Utils.FileLoaders;

import Main.Items.Item;
import Main.Items.Tools.Tiers;
import Main.Items.Tools.Tool;
import Main.Objects.Characters.NPC.Speech;
import Main.Utils.Annotations.NeedRevision;
import Main.Utils.Messenger;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
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

    public static Item[] loadInventory(int ID) {
        List<Item> inv = new ArrayList<>();
        try {
            File file = new File("src/Main/Resource/" + ID + "/inventory.txt");
            BufferedReader br = new BufferedReader(new FileReader(file));
            while(br.ready()) {
                String arg = br.readLine();
                String[] item = arg.split(":");
                if (item.length == 1) {
                    inv.add(Item.newInstance(Integer.parseInt(item[0])));
                }
                if (item.length == 2) {
                    Tool i = (Tool) Item.newInstance(Integer.parseInt(item[0]));
                    i.setTier(Tiers.getById(Integer.parseInt(item[1])));
                    inv.add(i);
                }
            }
            Item[] tr;
            if (inv.size() > 10) {
                tr = new Item[inv.size()];
            }
            else {
                tr = new Item[10];
            }
            for (int i = 0; i < tr.length; i++) {
                if (inv.size() == i) {
                    break;
                } else {
                    tr[i] = inv.get(i);
                }
            }
            return tr;
        }
        catch (FileNotFoundException e) {
            Messenger.systemMessage("FileNotFoundException loadInventory()", PersonLoader.class);
            return new Item[10];
        }
        catch (IOException | CloneNotSupportedException e) {
            Messenger.systemMessage("Exception caught in loadInventory()", PersonLoader.class);
        }
        return null;
    }

    @NeedRevision(comment = "Make one constructor for all kind of speeches")
    public static HashMap<Integer, Speech> loadSpeeches(int ID) {
        try {
            HashMap<Integer, Speech> speeches = new HashMap<>();
            File file = new File("src/Main/Resource/" + ID + "/speeches.txt");
            BufferedReader br = new BufferedReader(new FileReader(file));
            while (br.ready()) {
                try {
                    String line = br.readLine();
                    Speech s = loadSpeech(line);
                    speeches.put(s.getId(), s);
                } catch (InputMismatchException e) {
                    Messenger.systemMessage("InputMismatchException in loadPerson()", PersonLoader.class);
                }
            }
            return speeches;
        }
        catch (IOException e) {
            Messenger.systemMessage("Exception in loadPerson()", PersonLoader.class);
            return null;
        }
    }

    public static Speech loadSpeech(String s) throws IOException {
        String[] args = s.split(":");
        int id = Integer.parseInt(args[0]);
        String speech = args[1];
        boolean answerable = false, quest = false, trade = false, dynamic = false;
        try {
            switch (args[2]) {
                case "true":
                    answerable = true;
                    break;
                case "false":
                    answerable = false;
                    break;
                case "quest":
                    quest = true;
                    break;
                case "trade":
                    trade = true;
            }
            if (args[4].equals("dynamic")) {
                dynamic = true;
            }
        }
        catch (IndexOutOfBoundsException e) {

        }

        if (quest) {
            return new Speech(speech, Integer.parseInt(args[3]), id);
        }

        if (answerable) {
            String rawStrAnswers = args[3];
            String[] strAnswers = rawStrAnswers.split(",");
            List<Integer> answers = new ArrayList<>();
            for (int i = 0; i < strAnswers.length; i++) {
                answers.add(Integer.parseInt(strAnswers[i]));
            }
            if (dynamic) {
                return new Speech(speech, true, answers, id, true);
            } else {
                return new Speech(speech, true, answers, id);
            }
        } else {
            return new Speech(speech, id);
        }
    }
}
