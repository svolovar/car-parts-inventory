package controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static java.lang.Integer.parseInt;

/**
 * Controller for main screen
 * @author Steven Volovar
 */

public class MainController implements Initializable {
    //Fields for parts table
    public TableView partsTable;
    public TableColumn partNumCol;
    public TableColumn partNameCol;
    public TableColumn partInventoryCol;
    public TableColumn partPriceCol;
    //buttons and search field for parts table
    public Button addPart;
    public Button modifyPart;
    public Button deletePart;
    public TextField partSearchField;

    //Fields for products table
    public TableView productsTable;
    public TableColumn productNumCol;
    public TableColumn productNameCol;
    public TableColumn productInventoryCol;
    public TableColumn productPriceCol;
    //buttons and search field for products table
    public Button addProduct;
    public Button modifyProduct;
    public Button deleteProduct;
    public TextField productSearchField;

    public Button mainExitButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Set items to parts and product tables
        partsTable.setItems(Inventory.getAllParts());
        productsTable.setItems(Inventory.getAllProducts());
        //Set up cell values for products and parts tables
        partNumCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        productNumCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /**
     * Open add parts screen
     * @param actionEvent
     * @throws IOException
     */
    public void onAddPart(ActionEvent actionEvent) throws IOException {
        System.out.println("add part button clicked");
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddPart.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 600);
        stage.setTitle("Add Parts");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Open modify parts screen
     * @param actionEvent
     * @throws IOException
     */
    public void onModifyPart(ActionEvent actionEvent) throws IOException {
        if(partsTable.getSelectionModel().getSelectedItem() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("Please highlight a part to modify");
            alert.showAndWait();
        } else {
            //save data for selected part so the modifyPartController fields populate when opened
            ModifyPartController.partToModifyData((Part) partsTable.getSelectionModel().getSelectedItem());
            //load modify parts screen
            Parent root = FXMLLoader.load(getClass().getResource("/view/ModifyPart.fxml"));
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1000, 600);
            stage.setTitle("Modify Part");
            stage.setScene(scene);
            stage.show();
        }
    }

    /**
     * Delete selected part
     * @param actionEvent
     */
    public void onDeletePart(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Part Deletion");
        alert.setHeaderText("Confirm part deletion");
        alert.setContentText("Are you sure you want to delete this part?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            // ... user chose OK
            if (partsTable.getSelectionModel().getSelectedItem() == null) {
                Alert alert2 = new Alert(Alert.AlertType.ERROR);
                alert2.setTitle("ERROR");
                alert2.setContentText("Please select a part first in order to delete it");
                alert2.showAndWait();
            } else {
                Inventory.deletePart((Part) partsTable.getSelectionModel().getSelectedItem());
            }
        } else {
            // ... user chose CANCEL or closed the dialog
        }
    }

    /**
     * Open add product screen
     * @param actionEvent
     * @throws IOException
     */
    public void onAddProduct(ActionEvent actionEvent) throws IOException {
        System.out.println("add product button clicked");
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddProduct.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 900, 650);
        stage.setTitle("Add Product");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Open modify product screen
     * @param actionEvent
     * @throws IOException
     */
    public void onModifyProduct(ActionEvent actionEvent) throws IOException {
        if (productsTable.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("Please highlight a product to modify");
            alert.showAndWait();
        } else {
            //save data for selected product so the modifyProductController fields are populated when opened
            ModifyProductController.productToModifyData((Product) productsTable.getSelectionModel().getSelectedItem());
            System.out.println("modify product button clicked");
            Parent root = FXMLLoader.load(getClass().getResource("/view/ModifyProduct.fxml"));
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 900, 650);
            stage.setTitle("Modify Product");
            stage.setScene(scene);
            stage.show();
        }
    }

    /**
     * Delete selected product
     * @param actionEvent
     */
    public void onDeleteProduct(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm product deletion");
        alert.setHeaderText("Confirm product deletion");
        alert.setContentText("Are you sure you want to delete this product?");

        Product p = (Product) productsTable.getSelectionModel().getSelectedItem();

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            // ... user chose OK
            if (productsTable.getSelectionModel().getSelectedItem() == null) {
                Alert alert2 = new Alert(Alert.AlertType.ERROR);
                alert2.setTitle("ERROR");
                alert2.setContentText("Please select a product first in order to delete it");
                alert2.showAndWait();
            } else if (p.getAllAssociatedParts().size() > 0){
                Alert alert3 = new Alert(Alert.AlertType.ERROR);
                alert3.setTitle("ERROR");
                alert3.setContentText("Product with associated parts can not be deleted.");
                alert3.showAndWait();
            } else {
                Inventory.deleteProduct((Product) productsTable.getSelectionModel().getSelectedItem());
            }
        } else {
            // ... user chose CANCEL or closed the dialog
        }
    }

    /**
     * Exit program
     * @param actionEvent
     */
    public void onMainExitButton(ActionEvent actionEvent) {
        System.out.println("main exit button clicked");
        Platform.exit();
        System.exit(0);
    }

    /**
     * Search for parts by name or ID
     * @param actionEvent
     */
    public void onPartSearchField(ActionEvent actionEvent) {
        //set the query to text from searchbox
        String stringQuery = partSearchField.getText();

        //try to lookup part with the query as a string
        ObservableList<Part> searchParts = Inventory.lookupPart(stringQuery);
        partsTable.setItems(searchParts);

        try {
            //if the partsTable is still empty, search by ID
            if (searchParts.isEmpty()) {
                int searchInt = parseInt(partSearchField.getText());
                searchParts.add(Inventory.lookupPart(searchInt));
                partsTable.setItems(searchParts);
                partSearchField.setText("");
            }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("No results found. Please ensure you are only searching for Part ID Numbers or Part Names");
            alert.showAndWait();
        }
    }

    /**
     * Search for products by name or ID
     * @param actionEvent
     */
    public void onProductSearchField(ActionEvent actionEvent) {
        //set the query to text from searchbox
        String stringQuery = productSearchField.getText();
        //try to lookup product with the query as a string
        ObservableList<Product> searchProducts = Inventory.lookupProduct(stringQuery);
        productsTable.setItems(searchProducts);
        //if the productsTable is still empty, search by ID

        try {
            if (searchProducts.size() == 0) {
                int searchInt = parseInt(productSearchField.getText());
                searchProducts.add(Inventory.lookupProduct(searchInt));
                productsTable.setItems(searchProducts);
                productSearchField.setText("");
            }
        }  catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("No results found. Please ensure you are only searching for Product ID Numbers or Product Names");
            alert.showAndWait();
        }
    }
}
