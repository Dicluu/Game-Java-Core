package Main.Objects.Characters.NPC;

import Main.Objects.Characters.Character;

public abstract class NonPlayerCharacter extends Character {


    public NonPlayerCharacter(String name) {super(name);}
    public NonPlayerCharacter(String name, int x, int y, int id) {
        super(name, x, y, id);
    }
}
