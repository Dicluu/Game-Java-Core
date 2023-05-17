package Main.Objects.Characters.NPC;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Speech implements Serializable {

    private String speech;
    private boolean isAnswerable, isQuest;
    private List<Integer> answers = new ArrayList<>();
    private int id, questID;

    public Speech(String speech, boolean isAnswerable, List<Integer> answers, int id) {
        this.speech = speech;
        this.isAnswerable = isAnswerable;
        this.answers = answers;
        this.id = id;
    }

    public Speech(String speech, int id) {
        this.speech = speech;
        this.isAnswerable = false;
        this.id = id;
    }

    public Speech(String speech, int questID, int id) {
        this.speech = speech;
        this.isAnswerable = false;
        this.isQuest = true;
        this.questID = questID;
        this.id = id;
    }

    public String getSpeech() {
        return speech;
    }

    public boolean isAnswerable() {
        return isAnswerable;
    }

    public List<Integer> getAnswers() {
        return answers;
    }

    public int getId() {
        return id;
    }

    public boolean isQuest() {
        return isQuest;
    }

    public int getQuestID() {
        return questID;
    }
}
