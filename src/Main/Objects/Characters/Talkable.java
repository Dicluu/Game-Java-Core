package Main.Objects.Characters;

import Main.Objects.Characters.NPC.NonPlayerCharacter;

public interface Talkable {

    void talk();
    void talk(NonPlayerCharacter c);
}
