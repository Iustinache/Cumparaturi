package Repository;

import Domain.Produs;

import org.sqlite.SQLiteDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SQLProdusRepository extends RepoMemory<Produs> {
    Connection connection;
    //change URL here to relative path or your (absolute) path
    String db_url = "jdbc:sqlite:cumparaturi.db";

    public SQLProdusRepository() {
        openConnection();
        createTable();
       // initProduseTable();
        loadData();
    }

    private void loadData() {
        entities.addAll(this.findAll());
    }


    private void initProduseTable() {
        List<Produs> produseList = new ArrayList<>();
//
        produseList.add(new Produs(100,"Lenovo","ThinkPad S100",9500,14));
        produseList.add(new Produs(101,"Asus","Strix 45",7700,4));
        produseList.add(new Produs(102,"Ariston","WSL-1002",2240,2));
        produseList.add(new Produs(103,"Bosch","Series 4",1900,11));
        produseList.add(new Produs(104,"Whirlpool","SuperFridge 100LE",3200,10));
//
        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO produse VALUES (?,?,?,?,?);")) {
            for (Produs p : produseList) {
                statement.setInt(1, p.getId());
                statement.setString(2, p.getMarca());
                statement.setString(3, p.getNume());
                statement.setDouble(4, p.getPret());
                statement.setInt(5, p.getCantitate());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void openConnection() {
        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl(db_url);
        try {
            if (connection == null || connection.isClosed()) {
                connection = dataSource.getConnection();
            }
        } catch (SQLException e) {
            System.out.println("eroare la crearea conexiuni" + e);
        }
    }

    private void createTable() {
        String s = "Create Table if not exists produse( id_pro int, marca varchar(30), nume varchar(30), pret double, cantitate int,  PRIMARY KEY (id_pro) )";
        try {

            Statement statement = connection.createStatement();
            boolean execution_result = statement.execute(s);
            System.out.println("Execution result from createTable()" + execution_result);
        } catch (SQLException e) {
            System.out.println("eroare la crearea tabelei pacienti" + e);
        }
    }

    @Override
    public void add(Produs p) throws RepositoryException {
        super.add(p);
        String s = "INSERT INTO produse VALUES (?,?,?,?,?);";
        try {
            PreparedStatement add_statement = connection.prepareStatement(s);

            add_statement.setInt(1, p.getId());
            add_statement.setString(2, p.getMarca());
            add_statement.setString(3, p.getNume());
            add_statement.setDouble(4, p.getPret());
            add_statement.setInt(5, p.getCantitate());

            add_statement.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage());
        }

    }

    @Override
    public void removeById(int id) {
        super.removeById(id);
        String s = "DELETE FROM produse WHERE id_pro=?";
        //try-with-resources
        //https://docs.oracle.com/javase/8/docs/technotes/guides/language/try-with-resources.html
        try (PreparedStatement remove_statement = connection.prepareStatement(s)) {
            remove_statement.setInt(1, id);
            remove_statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Produs oldProdus, Produs newProdus) throws RepositoryException {
        int index = entities.indexOf(oldProdus);
        if (index == -1) {
            throw new RepositoryException("Produsul cu ID-ul " + oldProdus.getId() + " nu există.");
        }

        String sql = "UPDATE produse SET marca = ?, nume = ?, pret = ?, cantitate = ? WHERE id_pro = ?;";
        try (PreparedStatement updateStatement = connection.prepareStatement(sql)) {
            updateStatement.setString(1, newProdus.getMarca());
            updateStatement.setString(2, newProdus.getNume());
            updateStatement.setDouble(3, newProdus.getPret());
            updateStatement.setInt(4, newProdus.getCantitate());
            updateStatement.setInt(5, oldProdus.getId());

            int rowsAffected = updateStatement.executeUpdate();

            if (rowsAffected == 0) {
                throw new RepositoryException("Eroare la actualizare: nu a fost găsit niciun produs cu ID-ul " + oldProdus.getId());
            }
        } catch (SQLException e) {
            throw new RepositoryException("Eroare SQL la actualizare: " + e.getMessage());
        }

        entities.set(index, newProdus);
    }


    @Override
    public List<Produs> findAll() {
        List<Produs> resultList = new ArrayList<>();
        String s = "SELECT * FROM produse";
        try (PreparedStatement getAllSstatement = connection.prepareStatement(s)) {
            ResultSet result = getAllSstatement.executeQuery();
            while (result.next()) {
                Produs p = new Produs(result.getInt("id_pro"), result.getString("marca"),
                        result.getString("nume"), result.getDouble("pret"), result.getInt("cantitate"));
                resultList.add(p);
            }
            return resultList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}