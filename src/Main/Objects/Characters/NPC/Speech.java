package Main.Objects.Characters.NPC;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Speech implements Serializable {

    private String speech;
    private boolean isAnswerable, isQuest, isDynamic;
    private List<Integer> answers = new ArrayList<>();
    private int id, questID;

    public Speech(String speech, boolean isAnswerable, List<Integer> answers, int id) {
        this.speech = speech;
        this.isAnswerable = isAnswerable;
        this.answers = answers;
        this.id = id;
        this.isQuest = false;
    }

    public Speech(String speech, boolean isAnswerable, List<Integer> answers, int id, boolean isDynamic) {
        this.speech = speech;
        this.isAnswerable = isAnswerable;
        this.answers = answers;
        this.id = id;
        this.isQuest = false;
        this.isDynamic = isDynamic;
    }

    public Speech(String speech, int id) {
        this.speech = speech;
        this.isAnswerable = false;
        this.id = id;
        this.isQuest = false;
    }

    public Speech(String speech, int questID, int id) {
        this.speech = speech;
        this.isAnswerable = false;
        this.isQuest = true;
        this.questID = questID;
        this.id = id;
    }

    public boolean isDynamic() {
        return isDynamic;
    }

    public int getId() {
        return id;
    }

    public String getSpeech() {
        return speech;
    }

    private void setSpeech(String speech) {
        this.speech = speech;
    }

    public boolean isAnswerable() {
        return isAnswerable;
    }

    public void setAnswerable(boolean answerable) {
        isAnswerable = answerable;
    }

    public boolean isQuest() {
        return isQuest;
    }

    private void setQuest(boolean quest) {
        isQuest = quest;
    }

    public List<Integer> getAnswers() {
        return answers;
    }

    private void setAnswers(List<Integer> answers) {
        this.answers = answers;
    }

    public int getQuestID() {
        return questID;
    }

    private void setQuestID(int questID) {
        this.questID = questID;
    }

    public void morph(Speech form) {
        if (isDynamic) {
            this.setSpeech(form.getSpeech());
            this.setAnswers(form.getAnswers());
        }
    }


}
