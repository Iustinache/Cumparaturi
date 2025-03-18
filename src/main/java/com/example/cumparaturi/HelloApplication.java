package com.example.cumparaturi;

import Domain.Produs;
import Repository.IRepository;
import Repository.RepoMemory;
import Repository.SQLProdusRepository;
import Service.Service;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 400, 400);

        // Creează repo-ul și service-ul
        IRepository<Produs> repo = new SQLProdusRepository();
        Service service = new Service(repo);

        // Setează service-ul în controller
        HelloController controller = fxmlLoader.getController();
        controller.setService(service);

        stage.setTitle("Aplicație Cumpărături");
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        launch();
    }
}