package Main.Objects.Characters.Player;

import Main.Maps.Cell;
import Main.Objects.Characters.Character;
import Main.Objects.Characters.NPC.Speech;
import Main.Objects.Entity;
import Main.Objects.Priority;
import Main.Singletones.GameExecutor;
import Main.Utils.Messenger;
import Main.Utils.FileLoaders.PersonLoader;

import java.util.HashMap;

public class Player extends Character {


    private int intelligence = 7, strength = 9, agility = 5;


    static {
        try {
            Entity.addInstance(3, Player.class);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private String name;
    private static final int ID = 3;
    private static final int DEFAULT_CID = -3;
    private HashMap<Integer, Speech> speeches = PersonLoader.loadSpeeches(DEFAULT_CID);
    private Journal journal = new Journal();


    public Player(String name, int x, int y) {
        super(name, x, y, ID, 0f, DEFAULT_CID);
        this.name = name;
    }
    public Player() {
        super("Player", DEFAULT_CID, ID);
        Messenger.systemMessage("instance initiated", Player.class);
    }

    @Override
    public int getId() {
        return ID;
    }

    @Override
    public void talk() {

    }

    public Cell getCurrentCell() {
        return GameExecutor.getGame().getCurrentMap().getCell(super.getX(),super.getY());
    }

    public Journal getJournal() {
        return journal;
    }

    public HashMap<Integer, Speech> getSpeeches() {
        return speeches;
    }

    @Override
    public int getPriority() {
        return Priority.MAX.toInt()+1;
    }

    public int getIntelligence() {
        return intelligence;
    }

    public void setIntelligence(int intelligence) {
        this.intelligence = intelligence;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getAgility() {
        return agility;
    }

    public void setAgility(int agility) {
        this.agility = agility;
    }
}
