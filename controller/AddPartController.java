package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

/**
 * Controller for add parts screen
 * @author Steven Volovar
 */
public class AddPartController implements Initializable {
    public Label addPartCategoryLabel;
    public TextField addPartIdTextField;
    public TextField addPartNameField;
    public TextField addPartInvField;
    public TextField addPartPriceField;
    public TextField addPartMaxField;
    public TextField addPartMachineIdField;
    public TextField addPartMinField;
    public Button addPartSaveButton;
    public Button addPartCancelButton;

    public static boolean inHouseRadioChecked;
    public RadioButton addPartInHouseRadioButton;
    public RadioButton addPartOutsourcedRadioButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Initiating add part screen");
    }

    /**
     * Saves new part with parameters assigned by user entered data in text fields
     * LOGICAL ERROR When I was writing this code the first time, I could not set the radio buttons to selected without
     * crashing my program. Both of them would return a null pointer exception. I eventually fixed the error by realizing
     * that I had set the radio buttons to be "static" when I changed this, it functioned properly.
     * @param actionEvent click button
     * @throws IOException
     */
    public void onAddPartSaveButton(ActionEvent actionEvent) throws IOException {
        if (parseInt(addPartMaxField.getText()) < parseInt(addPartMinField.getText())) {
            //error: min bigger than max
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("Min bigger than max");
            alert.showAndWait();
        } else if (parseInt(addPartMinField.getText()) > parseInt(addPartInvField.getText())) {
            //INV less than min
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("INV less than min");
            alert.showAndWait();
        } else if (parseInt(addPartInvField.getText()) > parseInt(addPartMaxField.getText())) {
            //INV greater than max
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("INV bigger than max");
            alert.showAndWait();
        } else {
            try {
                if (inHouseRadioChecked) {
                    InHouse newPart = new InHouse(Inventory.getPartIdCount(), addPartNameField.getText(),
                            parseDouble(addPartPriceField.getText()), parseInt(addPartInvField.getText()),
                            parseInt((addPartMinField.getText())), parseInt(addPartMaxField.getText()),
                            parseInt(addPartMachineIdField.getText()));
                    Inventory.addPart(newPart);
                } else {
                    Outsourced newPart = new Outsourced(Inventory.getPartIdCount(), addPartNameField.getText(),
                            parseDouble(addPartPriceField.getText()), parseInt(addPartInvField.getText()),
                            parseInt((addPartMinField.getText())), parseInt(addPartMaxField.getText()),
                            addPartMachineIdField.getText());
                    Inventory.addPart(newPart);
                }

                Parent root = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
                Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root, 1000, 600);
                stage.setTitle("Inventory Management");
                stage.setScene(scene);
                stage.show();
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setContentText("Please enter a valid data type in each field");
                alert.showAndWait();
            }

        }
    }

    /**
     * Exit add part screen without saving
     * @param actionEvent click button
     * @throws IOException
     */
        public void onAddPartCancelButton (ActionEvent actionEvent) throws IOException {
            System.out.println("add part cancel button clicked");
            Parent root = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1000, 600);
            stage.setTitle("Inventory Management");
            stage.setScene(scene);
            stage.show();
        }

    /**
     * Sets the new part to in-house
     * @param actionEvent click button
     */
        public void onAddPartInHouseRadioButton (ActionEvent actionEvent){
            System.out.println("add part in house radio button toggled");
            addPartCategoryLabel.setText("Machine ID");
            inHouseRadioChecked = true;
        }

    /**
     * sets the new part to outsourced
     * @param actionEvent click button
     */
        public void onAddPartOutsourcedRadioButton (ActionEvent actionEvent){
            System.out.println("add part outsourced radio button toggled");
            addPartCategoryLabel.setText("Company Name");
            inHouseRadioChecked = false;
        }
    }