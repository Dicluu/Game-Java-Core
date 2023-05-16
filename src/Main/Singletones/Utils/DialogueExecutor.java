package Main.Singletones.Utils;

import Main.Objects.Characters.Character;
import Main.Objects.Characters.NPC.Speech;
import Main.Objects.Characters.Player;
import Main.Singletones.GameExecutor;
import Main.Utils.Messenger;

import java.util.*;

public class DialogueExecutor {

    private static Player player;
    private static Character companion;
    private static Speech speech;
    private static boolean helpFlag = true;

    public static void start(Player player, Character companion, Speech speech) {
        DialogueExecutor.player = player;
        DialogueExecutor.companion = companion;
        DialogueExecutor.speech = speech;
        if (isDialogueExist(companion)) {
            render();
        } else {
            randomizeSpeech();
        }
    }

    private static void render() {
        Messenger.ingameMessage(companion.getName() + ": " + speech.getSpeech());
        help();
        if (speech.isAnswerable()) {
            HashMap<Integer, Speech> ps = player.getSpeeches();
            HashMap<Integer, Speech> cs = companion.getSpeeches();
            List<Speech> ws = new ArrayList<>();
            for (int i = 0; i < ps.size(); i++) {
                for (int j = 0; j < speech.getAnswers().size(); j++) {
                    if (ps.get(i).getId() == speech.getAnswers().get(j)) {
                        ws.add(ps.get(i));
                    }
                }
            }
            for (int i = 0; i < ws.size(); i++) {
                Messenger.ingameMessage(i+1 + ") " + ws.get(i).getSpeech());
            }
            Scanner num = new Scanner(System.in);
            try {
                int in = num.nextInt();
                int range = speech.getAnswers().size();
                while (true) {
                    if ((in <= 0) || (in > range)) {
                        if (in == 0) {
                            Messenger.helpMessage("You exit the dialogue");
                            return;
                        }
                        Messenger.ingameMessage("You wrote wrong number of speech");
                        in = num.nextInt();
                    } else {
                        break;
                    }
                }

                Speech returned = player.getSpeeches().get(speech.getAnswers().get(in - 1));
                Messenger.ingameMessage("You: " + returned.getSpeech());
                if (returned.isAnswerable()) {
                    DialogueExecutor.start(player, companion, cs.get(returned.getAnswers().get(0)));
                }
            } catch (InputMismatchException e) {
                helpFlag = true;
                Messenger.systemMessage("Exception render()", DialogueExecutor.class);
            }
        }
    }

    private static boolean isDialogueExist(Character c) {
        HashMap<Integer, Speech> speeches = c.getSpeeches();
        for (Speech s : speeches.values()) {
            if (s.isAnswerable()) {
                return true;
            }
        }
        return false;
    }

    private static void randomizeSpeech() {
        HashMap<Integer, Speech> speeches = companion.getSpeeches();
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
