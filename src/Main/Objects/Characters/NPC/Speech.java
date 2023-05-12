package Main.Objects.Characters.NPC;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Speech implements Serializable {

    private String speech;
    private boolean isAnswerable;
    private List<Integer> answers = new ArrayList<>();
    private int id;

    public Speech(String speech, boolean isAnswerable, List<Integer> answers, int id) {
        this.speech = speech;
        this.isAnswerable = isAnswerable;
        this.answers = answers;
        this.id = id;
    }

    /**
     * only if isAnswerable = false
     * @param speech
     * @param isAnswerable
     */
    public Speech(String speech, boolean isAnswerable, int id) {
        this.speech = speech;
        this.isAnswerable = isAnswerable;
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
}
