package Main.Objects.Characters.NPC;

import java.util.ArrayList;
import java.util.List;

public class Speech {

    private String speech;
    private boolean isAnswerable;
    private List<Integer> answers = new ArrayList<>();

    public Speech(String speech, boolean isAnswerable, List<Integer> answers) {
        this.speech = speech;
        this.isAnswerable = isAnswerable;
        this.answers = answers;
    }

    /**
     * only if isAnswerable = false
     * @param speech
     * @param isAnswerable
     */
    public Speech(String speech, boolean isAnswerable) {
        this.speech = speech;
        this.isAnswerable = isAnswerable;
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
}
