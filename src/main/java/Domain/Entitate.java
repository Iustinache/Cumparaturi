package Domain;

public abstract class Entitate {
    protected int id;

    Entitate(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}

