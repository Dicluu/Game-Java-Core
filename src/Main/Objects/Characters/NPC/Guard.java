package Main.Objects.Characters.NPC;

import Main.Objects.Entity;
import Main.Utils.FileLoaders.PersonLoader;
import Main.Utils.Messenger;

import java.util.HashMap;

public class Guard extends NonPlayerCharacter {

    private static final int ID = 13;
    public static final int DEFAULT_CID = -6;
    private final String name = PersonLoader.loadName(-6);
    private static HashMap<Integer, Speech> speeches = PersonLoader.loadSpeeches(-6);

    static {
        try {
            Entity.addInstance(13, Guard.class);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * ONLY FOR INITIALIZING
     */
    public Guard() {
        super("Guard", DEFAULT_CID, ID);
        Messenger.systemMessage("instance initiated", Guard.class);
    }

    @Override
    public char getSymbol() {
        return '♦';
    }

    @Override
    public int getId() {
        return ID;
    }

    @Override
    public void talk() {

    }

}
