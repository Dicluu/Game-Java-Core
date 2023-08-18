package Main.Utils.FileLoaders;

import Main.Items.Item;
import Main.Items.Tools.Tiers;
import Main.Items.Tools.Tool;
import Main.Objects.Characters.NPC.Speech;
import Main.Objects.Characters.Player.Action;
import Main.Utils.Annotations.NeedRevision;
import Main.Utils.Messenger;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;

public class PersonLoader {

    private static int currentID;

    public static String loadName(int ID) {
        try {
            File file = new File("src/Main/Resource/Characters/" + ID + "/name.txt");
            BufferedReader br = new BufferedReader(new FileReader(file));
            return br.readLine();
        } catch (Exception e) {
            Messenger.systemMessage("Exception loadName()", PersonLoader.class);
            return null;
        }
    }

    public static Item[] loadInventory(int ID) {
        List<Item> inv = new ArrayList<>();
        try {
            File file = new File("src/Main/Resource/Characters/" + ID + "/inventory.txt");
            BufferedReader br = new BufferedReader(new FileReader(file));
            while (br.ready()) {
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
            } else {
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
        } catch (FileNotFoundException e) {
            Messenger.systemMessage("FileNotFoundException loadInventory()", PersonLoader.class);
            return new Item[10];
        } catch (IOException e) {
            Messenger.systemMessage("IOException caught in loadInventory()", PersonLoader.class);
        }
        return null;
    }

    @NeedRevision(comment = "Make one constructor for all kind of speeches")
    public static HashMap<Integer, Speech> loadSpeeches(int ID) {
        currentID = ID;
        try {
            HashMap<Integer, Speech> speeches = new HashMap<>();
            File file = new File("src/Main/Resource/Characters/" + ID + "/speeches.txt");
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
        } catch (IOException e) {
            Messenger.systemMessage("Exception in loadPerson()", PersonLoader.class);
            return null;
        }
    }

    public static Speech loadSpeech(String s) throws IOException {
        String[] args = s.split(":");
        int id = Integer.parseInt(args[0]);
        Speech speech = new Speech(args[1], Integer.parseInt(args[0]));
        //String speech = args[1];
        boolean answerable = false, quest = false, trade = false, dynamic = false, complete = false;
        int counter = 2;
        while (counter < args.length) {
            switch (args[counter]) {
                case "true":
                    speech.setAnswerable(true);
                    String rawStrAnswers = args[++counter];
                    String[] strAnswers = rawStrAnswers.split(",");
                    List<Integer> answers = new ArrayList<>();
                    for (int i = 0; i < strAnswers.length; i++) {
                        answers.add(Integer.parseInt(strAnswers[i]));
                    }
                    speech.setAnswers(answers);
                    break;
                case "false":
                    speech.setAnswerable(false);
                    break;
                case "quest":
                    speech.setQuest(true);
                    speech.setQuestID(Integer.parseInt(args[++counter]));
                    break;
                case "trade":
                    speech.setTrade(true);
                    break;
                case "complete":
                    speech.setFinish(true);
                    speech.setQuestID(Integer.parseInt(args[++counter]));
                    break;
                case "functional":
                    int groupID = Integer.parseInt(args[++counter]);
                    String act = args[++counter];
                    int CID = Integer.parseInt(args[++counter]);
                    Action a = new Action(currentID, false, groupID, act, String.valueOf(CID));
                    speech.setGroupID(groupID);
                    speech.setFunctional(true);
                    speech.setAction(a);
                    break;
                case "group":
                    speech.setGroupID(Integer.parseInt(args[++counter]));
                    break;
                case "after":
                    speech.setParentID(Integer.parseInt(args[++counter]));
                    speech.setBlocked(true);
                    break;
            }
            if (args[counter].equals("dynamic")) {
                speech.setDynamic(true);
            }
            counter++;
        }
        /*
        try {
            switch (args[2]) {
                case "true":
                    speech.setAnswerable(true);
                    String rawStrAnswers = args[3];
                    String[] strAnswers = rawStrAnswers.split(",");
                    List<Integer> answers = new ArrayList<>();
                    for (int i = 0; i < strAnswers.length; i++) {
                        answers.add(Integer.parseInt(strAnswers[i]));
                    }
                    speech.setAnswers(answers);
                    break;
                case "false":
                    speech.setAnswerable(false);
                    break;
                case "quest":
                    speech.setQuest(true);
                    speech.setQuestID(Integer.parseInt(args[3]));
                    break;
                case "trade":
                    speech.setTrade(true);
                    break;
                case "complete":
                    speech.setFinish(true);
                    speech.setQuestID(Integer.parseInt(args[3]));
                    break;
            }
            if (args[4].equals("dynamic")) {
                speech.setDynamic(true);
            }
        } catch (IndexOutOfBoundsException e) {

        }

         */

        return speech;

        /*
        if (trade) {
            return new Speech(speech, true, id);
        }

        if (quest) {
            return new Speech(speech, Integer.parseInt(args[3]), id);
        }


        if (complete) {
            return new Speech(speech, true, Integer.parseInt(args[3]), id);
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

         */
    }
}
