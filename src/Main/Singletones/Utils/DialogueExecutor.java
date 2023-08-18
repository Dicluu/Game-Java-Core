package Main.Singletones.Utils;

import Main.Objects.Characters.Character;
import Main.Objects.Characters.NPC.NonPlayerCharacter;
import Main.Objects.Characters.NPC.Speech;
import Main.Objects.Characters.Player.Player;
import Main.Singletones.GameExecutor;
import Main.Utils.Messenger;

import java.util.*;

public class DialogueExecutor {

    private static Player player;
    private static Character companion;
    private static Speech speech;
    private static boolean helpFlag = true;

    public static void start(Player player, NonPlayerCharacter companion, Speech speech) {
        DialogueExecutor.player = player;
        DialogueExecutor.companion = companion;
        DialogueExecutor.speech = speech;
        if (isDialogueExist(companion)) {
            render(speech);
        } else {
            randomizeSpeech();
        }
    }

    private static void render(Speech speech) {
        Messenger.ingameMessage(companion.getName() + ": " + speech.getSpeech());
        help();
        if (speech.isAnswerable()) {
            HashMap<Integer, Speech> ps = player.getSpeeches();
            HashMap<Integer, Speech> cs = companion.getSpeeches();
            List<Speech> ws = new ArrayList<>();
            for (int i = 0; i < ps.size(); i++) {
                for (int j = 0; j < speech.getAnswers().size(); j++) {
                    try {
                        if (ps.get(i).getId() == speech.getAnswers().get(j)) {
                            ws.add(ps.get(i));
                        }
                    } catch (NullPointerException e) {

                    }
                }
            }
            for (int i = 0; i < ws.size(); i++) {
                Messenger.ingameMessage(i + 1 + ") " + ws.get(i).getSpeech());
            }
            try {
                Scanner num = new Scanner(System.in);
                int in = num.nextInt();
                int range = speech.getAnswers().size();
                while (true) {
                    try {
                        if ((in <= 0) || (in > range)) {
                            if (in == 0) {
                                Messenger.helpMessage("You exit the dialogue");
                                return;
                            }
                            Messenger.ingameMessage("You wrote wrong number of speech");
                            num = new Scanner(System.in);
                            in = num.nextInt();
                            assert (player.getSpeeches().get(speech.getAnswers().get(in - 1)) != null);
                        } else {
                            break;
                        }
                    } catch (AssertionError e) {
                        Messenger.ingameMessage("You wrote number of non-existent speech");
                    } catch (InputMismatchException e) {
                        helpFlag = true;
                        Messenger.ingameMessage("You wrote not a number of speech");
                    }
                }
                Speech returned = player.getSpeeches().get(speech.getAnswers().get(in - 1));
                Messenger.ingameMessage("You: " + returned.getSpeech());
                if (returned.isAnswerable()) {
                    //DialogueExecutor.start(player, companion, cs.get(returned.getAnswers().get(0)));
                    DialogueExecutor.render(cs.get(returned.getAnswers().get(0)));
                }
            }
            catch (InputMismatchException e) {
                helpFlag = true;
                Messenger.ingameMessage("You wrote not a number of speech");
            }
        }
        if (speech.isQuest()) {
            QuestLineManager.getQuestById(speech.getQuestID()).initiate();
        }
        if (speech.isFinish()) {
            QuestLineManager.getQuestById(speech.getQuestID()).finish();
        }
        if (speech.isTrade()) {
            GameExecutor.initiateTrade(companion);
        }
    }

    private static boolean isDialogueExist(NonPlayerCharacter c) {
        HashMap<Integer, Speech> speeches = c.getSpeeches();
        for (Speech s : speeches.values()) {
            if (s.isAnswerable()) {
                return true;
            }
        }
        return false;
    }

    private static void randomizeSpeech() {
        HashMap<Integer, Speech> speeches = ((NonPlayerCharacter) companion).getSpeechesInRow();
        int r = (int) (Math.random() * (speeches.size()));
        Messenger.ingameMessage(companion.getName() + ": " + speeches.get(r).getSpeech());
    }

    private static void help() {
        if (helpFlag) {
            Messenger.helpMessage("Now you are in dialogue");
            Messenger.helpMessage("Use numbers to choose an answer");
            Messenger.helpMessage("Write '0' to exit the dialogue");
        }
        helpFlag = false;
    }


}
