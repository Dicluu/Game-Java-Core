package Main.Objects.Characters.NPC;

import Main.Objects.Characters.Character;
import Main.Objects.Characters.Player.Quest;
import Main.Singletones.Utils.QuestLineManager;
import Main.Utils.Annotations.NeedRevision;

@NeedRevision(comment = "make enum instead of classes")
public abstract class NonPlayerCharacter extends Character {

    private Quest quest;

    //only for initializing
    public NonPlayerCharacter(String name, int CID) {super(name, CID);}


    public NonPlayerCharacter(String name, int x, int y, int id, int cid) {
        super(name, x, y, id, cid);
    }

    public NonPlayerCharacter(String name, int x, int y, int id, int cid, int questID) {
        super(name, x, y, id, cid);
        quest = QuestLineManager.getQuestById(questID);
    }

    public Quest getQuest() {
        return quest;
    }

    public void setQuest(Quest quest) {
        this.quest = quest;
    }
}
