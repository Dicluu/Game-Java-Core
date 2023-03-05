package Main.Items.Materials;

import Main.Items.Item;

public class Material extends Item {

    Materials material;

    public Material(Materials material) {
        super(material.getID(), material.getNAME());
        this.material = material;
    }
}