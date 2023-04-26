package Main.Objects.Characters.NPC;

import Main.Objects.Characters.Character;

import java.util.List;

public abstract class NonPlayerCharacter extends Character {


    public NonPlayerCharacter(String name, List<Speech> speeches) {super(name, speeches);}
    public NonPlayerCharacter(String name, int x, int y, int id) {
        super(name, x, y, id);
    }
}
