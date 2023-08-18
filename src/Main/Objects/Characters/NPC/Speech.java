package Main.Objects.Characters.NPC;

import Main.Objects.Characters.Player.Action;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Speech implements Serializable {

    private String speech;
    private boolean isAnswerable, isQuest, isDynamic, isFinish, isTrade, isBlocked, isFunctional;
    private List<Integer> answers = new ArrayList<>();
    private int id, questID, groupID, parentID;

    private Action action;

    /*
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

     */

    public Speech(String speech, int id) {
        this.speech = speech;
        this.id = id;
    }

    /*
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

     */

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


    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public boolean isFunctional() {
        return isFunctional;
    }

    public void setFunctional(boolean functional) {
        isFunctional = functional;
    }

    public int getParentID() {
        return parentID;
    }

    public void setParentID(int parentID) {
        this.parentID = parentID;
    }

    public int getGroupID() {
        return groupID;
    }

    public void setGroupID(int groupID) {
        this.groupID = groupID;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }

    public String getSpeech() {
        if (isFunctional) {
            action.execute();
        }
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

    public void setDynamic(boolean dynamic) {
        isDynamic = dynamic;
    }

    public void setFinish(boolean isComplete) {
        if (isComplete) {
            setAnswerable(false);
        }
        this.isFinish = isComplete;
    }

    public void setQuest(boolean quest) {
        isQuest = quest;
    }

    public List<Integer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Integer> answers) {
        this.answers = answers;
    }

    public int getQuestID() {
        return questID;
    }

    public void setQuestID(int questID) {
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
