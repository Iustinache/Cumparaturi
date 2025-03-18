package com.example.cumparaturi;

import Domain.Produs;
import Service.Service;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.List;

public class HelloController {
    private Service service;

    @FXML
    private ListView<String> productListView;
    @FXML
    private TextField minPriceField;
    @FXML
    private TextField maxPriceField;
    @FXML
    private TextField productIdField;
    @FXML
    private Label totalLabel;

    public void setService(Service service) {
        this.service = service;
        loadProducts();
    }

    @FXML
    private void loadProducts() {
        productListView.getItems().clear();
        List<Produs> produse = service.getProduseDisponibile();
        for (Produs produs : produse) {
            String text = produs.getCantitate() == -1
                    ? String.format("ID: %d | %s | %s | %.2f | n/a", produs.getId(), produs.getMarca(), produs.getNume(), produs.getPret())
                    : String.format("ID: %d | %s | %s | %.2f | %d", produs.getId(), produs.getMarca(), produs.getNume(), produs.getPret(), produs.getCantitate());
            productListView.getItems().add(text);
        }
    }

    @FXML
    private void onFilterButtonClick() {
        try {
            double minPrice = minPriceField.getText().isEmpty() ? 0 : Double.parseDouble(minPriceField.getText());
            double maxPrice = maxPriceField.getText().isEmpty() ? Double.MAX_VALUE : Double.parseDouble(maxPriceField.getText());

            productListView.getItems().clear();
            List<Produs> produse = service.filtreazaProduse(minPrice, maxPrice);
            for (Produs produs : produse) {
                String text = produs.getCantitate() == -1
                        ? String.format("ID: %d | %s | %s | %.2f | n/a", produs.getId(), produs.getMarca(), produs.getNume(), produs.getPret())
                        : String.format("ID: %d | %s | %s | %.2f | %d", produs.getId(), produs.getMarca(), produs.getNume(), produs.getPret(), produs.getCantitate());
                productListView.getItems().add(text);
            }
        } catch (NumberFormatException e) {
            showAlert("Input invalid", "Introduceți valori numerice pentru prețuri!");
        } catch (RuntimeException e) {
            showAlert("Eroare", e.getMessage());
        }
    }

    @FXML
    private void onBuyButtonClick() {
        try {
            int productId = Integer.parseInt(productIdField.getText());
            service.cumparaProdus(productId);
            loadProducts();
            totalLabel.setText(String.format("%.2f", service.getTotalCumparaturi()));
        } catch (NumberFormatException e) {
            showAlert("Input invalid", "Introduceți un ID valid!");
        } catch (RuntimeException e) {
            showAlert("Eroare", e.getMessage());
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
