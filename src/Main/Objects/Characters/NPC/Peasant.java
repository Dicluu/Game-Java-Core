package Main.Objects.Characters.NPC;

import Main.Objects.Characters.Character;
import Main.Objects.Entity;
import Main.Utils.Messenger;

import java.util.List;

public class Peasant extends Character {


    static {
        try {
            Entity.addInstance(7, Peasant.class);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private static final int ID = 7;

    public Peasant() {
        super("peasant");
    }

    @Override
    public int getId() {
        return ID;
    }


    @Override
    public void talk() {
        List<Speech> speeches = getSpeeches();
        int r = (int) (Math.random() * (speeches.size()));
        Messenger.ingameMessage(speeches.get(r).getSpeech());
    }
}
