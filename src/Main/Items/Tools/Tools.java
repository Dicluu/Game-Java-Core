package Main.Items.Tools;

public enum Tools {
    AXE(5, "Axe"), PICKAXE(6, "Pickaxe");

    private int id;
    private String name;

    Tools(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
