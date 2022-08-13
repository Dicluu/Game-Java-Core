package Main.Objects;

public enum Priority {

    MIN(0), MEDIUM(5), MAX(10);

    int priority;

    Priority(int priority) {
        this.priority = priority;
    }

    public int toInt() {
        return priority;
    }

}
