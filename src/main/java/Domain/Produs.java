package Domain;

public class Produs extends Entitate {
    private String marca;
    private String nume;
    private double pret;
    private int cantitate;

    public Produs(int id, String marca, String nume, double pret, int cantitate) {
        super(id);
        this.marca = marca;
        this.nume = nume;
        this.pret = pret;
        this.cantitate = cantitate;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public double getPret() {
        return pret;
    }

    public void setPret(double pret) {
        this.pret = pret;
    }

    public int getCantitate() {
        return cantitate;
    }

    public void setCantitate(int cantitate) {
        this.cantitate = cantitate;
    }

    @Override
    public String toString() {
        if (cantitate > 0) {
            return "\nProdus{" +
                    "id='" + id + '\'' +
                    ", marca='" + marca + '\'' +
                    ", nume=" + nume +
                    ", pret=" + pret +
                    ", cantitate=" + cantitate +
                    '}';
        }
        else {
            return "\nProdus{" +
                    "id='" + id + '\'' +
                    ", marca='" + marca + '\'' +
                    ", nume=" + nume +
                    ", pret=" + pret +
                    ", cantitate= n/a"  +
                    '}';
        }

    }
}
