package Main.Items.Materials;

import Main.Items.Item;

public class Material extends Item {

    private String name;
    private int price;

    /*
    Materials material;

    public Material(Materials material) {
        super(material.getID(), material.getNAME());
        this.material = material;
    }

     */

    public Material(int id, String name, int price) {
        super(id, name, price);
        this.name = name;
        this.price = price;
    }
}