package controller;

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
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

/**
 * Controller for add modify products screen
 * @author Steven Volovar
 */

public class ModifyProductController implements Initializable {
    //product to hold data for when this screen loads
    private static Product productToModify = null;
    public TableView modifyProductPartsTable;
    public TableColumn modPartsId;
    public TableColumn modPartsName;
    public TableColumn modPartsInv;
    public TableColumn modPartsCost;

    public TableView modifyProductLowerTable;
    public TableColumn modLowerId;
    public TableColumn modLowerName;
    public TableColumn modLowerInv;
    public TableColumn modLowerCost;

    public Button modifyProductAddButton;
    public Button removeAssociatedPartButton;
    public Button modifyProductCancelButton;
    public Button modifyProductSaveButton;

    public TextField modifyProductSearchField;
    public TextField modifyProductIdField;
    public TextField modifyProductNameField;
    public TextField modifyProductInvField;
    public TextField modifyProductPriceField;
    public TextField modifyProductMaxField;
    public TextField modifyProductMinField;

    public static ObservableList<Part> partsForModifiedProduct = FXCollections.observableArrayList();

    public static void productToModifyData(Product selectedItem) {
        productToModify = selectedItem;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //populate table with all parts
        modifyProductPartsTable.setItems(Inventory.getAllParts());
        modPartsId.setCellValueFactory(new PropertyValueFactory<>("id"));
        modPartsName.setCellValueFactory(new PropertyValueFactory<>("name"));
        modPartsInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        modPartsCost.setCellValueFactory(new PropertyValueFactory<>("price"));
        //populate lower table with its current parts
        modifyProductLowerTable.setItems(productToModify.getAllAssociatedParts());
        modLowerId.setCellValueFactory(new PropertyValueFactory<>("id"));
        modLowerName.setCellValueFactory(new PropertyValueFactory<>("name"));
        modLowerInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        modLowerCost.setCellValueFactory(new PropertyValueFactory<>("price"));
        //populate textfields with selected product data
        modifyProductIdField.setText(String.valueOf(productToModify.getId()));
        modifyProductNameField.setText(productToModify.getName());
        modifyProductInvField.setText(String.valueOf(productToModify.getStock()));
        modifyProductPriceField.setText(String.valueOf(productToModify.getPrice()));
        modifyProductMaxField.setText(String.valueOf(productToModify.getMax()));
        modifyProductMinField.setText(String.valueOf(productToModify.getMin()));
    }

    /**
     * Go back to main screen without saving
     * @param actionEvent
     * @throws IOException
     */
    public void onModifyProductCancelButton(ActionEvent actionEvent) throws IOException {
        System.out.println("modify product cancel button clicked");
        Parent root = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 600);
        stage.setTitle("Inventory Management");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Validate and save new product
     * @param actionEvent
     * @throws IOException
     */
    public void onModifyProductSaveButton(ActionEvent actionEvent) throws IOException {
        try {
            if (parseInt(modifyProductMaxField.getText()) < parseInt(modifyProductMinField.getText())) {
                //min has to be less than max
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setContentText("Please enter a value for min that does not exceed max");
                alert.showAndWait();
            } else if (parseInt(modifyProductInvField.getText()) > parseInt(modifyProductMaxField.getText())) {
                //inv has to be between max and min
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setContentText("Please enter a value for inv that is between max and min");
                alert.showAndWait();
            } else {
                //save new product parameters
                productToModify.setName(modifyProductNameField.getText());
                productToModify.setPrice(parseDouble(modifyProductPriceField.getText()));
                productToModify.setStock(parseInt(modifyProductInvField.getText()));
                productToModify.setMax(parseInt(modifyProductMaxField.getText()));
                productToModify.setMin(parseInt(modifyProductMinField.getText()));
                //load main screen
                Parent root = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
                Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root, 1000, 600);
                stage.setTitle("Inventory Management");
                stage.setScene(scene);
                stage.show();
            }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("Please enter a valid data type in each field");
            alert.showAndWait();
        }
    }

    /**
     * Add part to list of parts for modified product
     * @param actionEvent
     */
    public void onModifyProductAddButton(ActionEvent actionEvent) {
        productToModify.addAssociatedPart((Part) modifyProductPartsTable.getSelectionModel().getSelectedItem());
    }

    /**
     * Remove part from list of parts for modified product
     * @param actionEvent
     */
    public void onRemoveAssociatedPartButton(ActionEvent actionEvent) {
        if (modifyProductLowerTable.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("Please select a part first in order to remove it");
            alert.showAndWait();
        } else {
            productToModify.deleteAssociatedPart((Part) modifyProductLowerTable.getSelectionModel().getSelectedItem());
        }
    }


    /**
     * Search for parts
     * @param actionEvent
     */
    public void onModifyProductSearchField(ActionEvent actionEvent) {
        //set the query to text from searchbox
        String stringQuery = modifyProductSearchField.getText();

        //try to lookup part with the query as a string
        ObservableList<Part> searchParts = Inventory.lookupPart(stringQuery);
        modifyProductPartsTable.setItems(searchParts);
        try {
            //if the partsTable is still empty, search by ID
            if (searchParts.isEmpty()) {
                int searchInt = parseInt(modifyProductSearchField.getText());
                searchParts.add(Inventory.lookupPart(searchInt));
                modifyProductPartsTable.setItems(searchParts);
                modifyProductSearchField.setText("");
            }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("No results found. Please ensure you are only searching for Part ID Numbers or Part Names");
            alert.showAndWait();
        }
    }
}
