package Main.Objects.Characters.NPC;

import Main.Objects.Characters.Character;
import Main.Utils.Annotations.NeedRevision;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

@NeedRevision(comment = "make enum instead of classes")
public abstract class NonPlayerCharacter extends Character {
    public NonPlayerCharacter(String name, int CID) {super(name, CID);}
    public NonPlayerCharacter(String name, int x, int y, int id, int cid) {
        super(name, x, y, id, cid);
    }
}
