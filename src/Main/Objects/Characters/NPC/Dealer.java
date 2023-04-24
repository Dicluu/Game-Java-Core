package Main.Objects.Characters.NPC;

import Main.Objects.Entity;
import Main.Utils.Messenger;

public class Dealer extends NonPlayerCharacter {


    private static final int ID = 4;

    static {
        try {
            Entity.addInstance(4, Dealer.class);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public Dealer() {
        super("Dealer");
        Messenger.systemMessage("instance initiated", Dealer.class);
    }

    public Dealer(String name, int x, int y) {
        super(name, x, y, ID);
    }

    @Override
    public char getSymbol() {
        return "$".charAt(0);
    }

    @Override
    public int getId() {
        return ID;
    }

    @Override
    public void talk() {

    }
}
