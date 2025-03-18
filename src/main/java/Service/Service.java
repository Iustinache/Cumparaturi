package Service;

import Domain.Produs;
import Repository.IRepository;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.*;
import java.util.stream.Collectors;

public class Service {
    protected IRepository<Produs> repoPro;

    public Service(IRepository<Produs> repo1) {
        repoPro = repo1;
    }
// nu se vor folosi add, update si remove
    public List<Produs> getProduseDisponibile() {
        List<Produs> produse = null;
        try {
            produse = repoPro.findAll();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        produse.sort(Comparator.comparingInt(Produs::getId));
        return produse.stream()
                .map(p -> p.getCantitate() == 0 ? new Produs(p.getId(), p.getMarca(), p.getNume(), p.getPret(), -1) : p)
                .toList();
    }

    public List<Produs> filtreazaProduse(double pretMin, double pretMax) {
        if (pretMin < 0 || pretMax < 0 || pretMin > pretMax) {
            throw new IllegalArgumentException("Limite de preț invalide!");
        }
        try {
            return repoPro.findAll().stream()
                    .filter(p -> p.getPret() >= pretMin && p.getPret() <= pretMax)
                    .toList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    private final List<Produs> produseCumparate = new ArrayList<>();

    public void cumparaProdus(int id) {
        Produs produs = repoPro.findEntityById(id);
        if (produs == null)
            throw new RuntimeException("Produsul nu există!");
        if (produs.getCantitate() == 0) {
            throw new RuntimeException("Produsul nu mai este disponibil!");
        }
        Produs produsNou = new Produs(produs.getId(),produs.getMarca(), produs.getNume(), produs.getPret(), produs.getCantitate()-1);
        try {
            repoPro.update(produs, produsNou);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        produseCumparate.add(produs);
    }

    public double getTotalCumparaturi() {
        return produseCumparate.stream()
                .mapToDouble(Produs::getPret)
                .sum();
    }




//------CRUD---------------(nu se vor folosi)------------


    public void addProdus(int idProdus, String marca, String nume, double pret, int cantitate) throws Exception {
        Produs p = new Produs(idProdus, marca, nume, pret, cantitate);
        //validare
        repoPro.add(p);
    }

    public Collection<Produs> findAllProduse() throws Exception {
        return repoPro.findAll();
    }

    public void updateProdus(int id, String marca, String nume, double pret, int cantitate) throws Exception {
        repoPro.update(repoPro.findEntityById(id), new Produs(id, marca, nume, pret, cantitate));
    }

    public void removeProdus(int id) throws Exception {
        repoPro.remove(repoPro.findEntityById(id));
    }

    public void removeProdusById(int id) throws Exception {
        repoPro.removeById(id);
    }


}
