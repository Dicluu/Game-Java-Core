package Main.Objects.Characters.NPC;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Speech implements Serializable {

    private String speech;
    private boolean isAnswerable, isQuest, isDynamic, isFinish, isTrade;
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

    public Speech(String speech, boolean trade, int id) {
        this.speech = speech;
        this.isTrade = trade;
        this.id = id;
    }

    public Speech(String speech, boolean isFinish, int questID, int id) {
        this.speech = speech;
        this.questID = questID;
        this.id = id;
        this.isFinish = isFinish;
    }

    public boolean isDynamic() {
        return isDynamic;
    }

    public boolean isTrade() {
        return isTrade;
    }

    public void setTrade(boolean trade) {
        isTrade = trade;
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

    public boolean isFinish() {
        return isFinish;
    }

    private void setFinish(boolean isComplete) {
        if (isComplete) {
            setAnswerable(false);
        }
        this.isFinish = isComplete;
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
            this.setFinish(form.isFinish());
            this.setAnswers(form.getAnswers());
            }
        }
    }
