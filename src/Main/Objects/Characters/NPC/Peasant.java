package Main.Objects.Characters.NPC;

import Main.Objects.Characters.Character;
import Main.Objects.Entity;
import Main.Utils.Messenger;
import Main.Utils.PersonLoader;

import java.util.List;

public class Peasant extends Character {

    private static final int ID = 7;
    private static final String name = PersonLoader.loadName(0);
    private static List<Speech> speeches = PersonLoader.loadSpeeches(0);

    static {
        try {
            Entity.addInstance(7, Peasant.class);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }


    public Peasant() {
        super("peasant", speeches);
        Messenger.systemMessage("instance initiated", Peasant.class);
    }

    @Override
    public int getId() {
        return ID;
    }


    @Override
    public void talk() {

    }
}
