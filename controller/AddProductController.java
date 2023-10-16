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
 * Controller for add products screen
 * @author Steven Volovar
 */

public class AddProductController implements Initializable {
    //top table
    public TableView addProductPartsTable;
    public TableColumn allPartsId;
    public TableColumn allPartsName;
    public TableColumn allPartsInv;
    public TableColumn allPartsCost;
    //bottom table
    public TableView addProductLowerTable;
    public TableColumn assocPartsId;
    public TableColumn assocPartsName;
    public TableColumn assocPartsInv;
    public TableColumn assocPartsCost;
    //buttons
    public Button addProductCancelButton;
    public Button addProductSaveButton;
    //text-fields
    public TextField addProductNameField;
    public TextField addProductInvField;
    public TextField addProductPriceField;
    public TextField addProductMaxField;
    public TextField addProductMinField;
    //search field
    public TextField addProductSearchField;
    public Button addProductAddButton;
    public Button removeAssociatedPartButton;
    //observable list for parts that will be added to new product when "Save" button is clicked
    public static ObservableList<Part> partsForProduct = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addProductPartsTable.setItems(Inventory.getAllParts());
        allPartsId.setCellValueFactory(new PropertyValueFactory<>("id"));
        allPartsName.setCellValueFactory(new PropertyValueFactory<>("name"));
        allPartsInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        allPartsCost.setCellValueFactory(new PropertyValueFactory<>("price"));

        addProductLowerTable.setItems(partsForProduct);
        assocPartsId.setCellValueFactory(new PropertyValueFactory<>("id"));
        assocPartsName.setCellValueFactory(new PropertyValueFactory<>("name"));
        assocPartsInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        assocPartsCost.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /**
     * Closes add part screen and returns to main screen without saving
     * @param actionEvent
     * @throws IOException
     */
    public void onAddProductCancelButton(ActionEvent actionEvent) throws IOException {
        System.out.println("add product cancel button clicked");
        Parent root = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 600);
        stage.setTitle("Inventory Management");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * LOGICAL ERROR when I was writing this method, I had a hard time figuring out how to properly enter the data from
     * the textfields into the parameters for a product. I eventually found the "parseInt" and "parseDouble" functions.
     * These allowed me to convert the strings to the proper data types for my product parameters.
     * @param actionEvent
     * @throws IOException
     */
    public void onAddProductSaveButton(ActionEvent actionEvent) throws IOException {
        try {
            //make sure product is valid
            if (parseInt(addProductMaxField.getText()) < parseInt(addProductMinField.getText())) {
                //min has to be less than max
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setContentText("Please enter a value for min that does not exceed max");
                alert.showAndWait();
            } else if (parseInt(addProductInvField.getText()) > parseInt(addProductMaxField.getText())) {
                //inv has to be between max and min
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setContentText("Please enter a value for inv that is between max and min");
                alert.showAndWait();
            } else {

                //add data from text fields and the list of selected part to parameters for new product
                Product newProduct = new Product(Inventory.getProductIdCount(), addProductNameField.getText(),
                        parseDouble(addProductPriceField.getText()), parseInt(addProductInvField.getText()),
                        parseInt(addProductMinField.getText()), parseInt(addProductMaxField.getText()));
                //add the associated parts to the new product
                newProduct.setAssociatedParts(partsForProduct);
                Inventory.addProduct(newProduct);

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
     * search for products by name or ID
     * @param actionEvent
     */
    public void onAddProductSearchField(ActionEvent actionEvent) {
        //set the query to text from searchbox
        String stringQuery = addProductSearchField.getText();
        //try to lookup part with the query as a string
        ObservableList<Part> searchParts = Inventory.lookupPart(stringQuery);
        addProductPartsTable.setItems(searchParts);
        try {
            //if the partsTable is still empty, search by ID
            if (searchParts.isEmpty()) {
                int searchInt = parseInt(addProductSearchField.getText());
                searchParts.add(Inventory.lookupPart(searchInt));
                addProductPartsTable.setItems(searchParts);
                addProductSearchField.setText("");
            }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("No results found. Please ensure you are only searching for Part ID Numbers or Part Names");
            alert.showAndWait();
        }
    }

    /**
     * Add parts to product
     * @param actionEvent
     */
    public void onAddProductAddButton(ActionEvent actionEvent) {
        partsForProduct.add((Part) addProductPartsTable.getSelectionModel().getSelectedItem());
    }

    /**
     * remove selected part from product to add
     * @param actionEvent
     */
    public void onRemoveAssociatedPartButton(ActionEvent actionEvent) {
        if (addProductLowerTable.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("Please select a part first in order to remove it");
            alert.showAndWait();
        } else {
            partsForProduct.remove((Part) addProductLowerTable.getSelectionModel().getSelectedItem());
        }
    }
}
