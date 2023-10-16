package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.InHouse;
import model.Part;
import model.Outsourced;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

/**
 * Controller for modify parts screen
 * @author Steven Volovar
 */


public class ModifyPartController implements Initializable {
    public Label modifyPartCategoryLabel;
    public TextField modifyPartId;
    public TextField modifyPartName;
    public TextField modifyPartInv;
    public TextField modifyPartCost;
    public TextField modifyPartMax;
    public TextField modifyPartMachineId;
    public TextField modifyPartMin;

    private static Part partToModify = null;

    public RadioButton modifyPartInHouseRadioButton;
    public RadioButton modifyPartOutSourcedButton;
    public ToggleGroup modifyPartToggleGroup;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //pre-populate fields with part data
        modifyPartId.setText(String.valueOf(partToModify.getId()));
        modifyPartName.setText(partToModify.getName());
        modifyPartInv.setText(String.valueOf(partToModify.getStock()));
        modifyPartCost.setText(String.valueOf(partToModify.getPrice()));
        modifyPartMax.setText(String.valueOf(partToModify.getMax()));
        modifyPartMin.setText(String.valueOf(partToModify.getMin()));

        //set labels based on inhouse or outsourced
        if (partToModify instanceof InHouse) {
            //set label to machineID
            modifyPartCategoryLabel.setText("Machine ID");
            modifyPartInHouseRadioButton.setSelected(true);
            //modifyPartInHouseRadioButton.fire();
            //modifyPartInHouseRadioButton.isSelected();
        } else {
            //set label to companyName
            modifyPartCategoryLabel.setText("Company Name");
            modifyPartOutSourcedButton.setSelected(true);
        }

        //show either part ID or company name based on type of part
        if (partToModify instanceof InHouse) {
            //show the machine id
            modifyPartMachineId.setText(String.valueOf(((InHouse) partToModify).getMachineId()));
        } else {
            //show company name
            modifyPartMachineId.setText(String.valueOf(((Outsourced) partToModify).getCompanyName()));
        }

    }

    /**
     * hold data for part to modify while switching screens
     * @param selectedPart
     */
    public static void partToModifyData(Part selectedPart) {
        partToModify = selectedPart;
    }

    /**
     * Save modified part
     * @param actionEvent
     * @throws IOException
     */
    public void onModifyPartSaveButton(ActionEvent actionEvent) throws IOException {
        if (parseInt(modifyPartMax.getText()) < parseInt(modifyPartMin.getText())) {
            //error: min bigger than max
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("Min bigger than max");
            alert.showAndWait();
        }else if (parseInt(modifyPartMin.getText()) > parseInt(modifyPartInv.getText())) {
            //INV less than min
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("INV less than min");
            alert.showAndWait();
        } else if (parseInt(modifyPartInv.getText()) > parseInt(modifyPartMax.getText())){
            //INV greater than max
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("INV bigger than max");
            alert.showAndWait();
        } else {

            try {
                partToModify.setName(modifyPartName.getText());
                partToModify.setStock(parseInt(modifyPartInv.getText()));
                partToModify.setPrice(parseDouble(modifyPartCost.getText()));
                partToModify.setMax(parseInt(modifyPartMax.getText()));
                partToModify.setMin(parseInt(modifyPartMin.getText()));

                if (partToModify instanceof InHouse) {
                    //set parameters for selected inhouse part to the newly entered value
                    partToModify.setName(modifyPartName.getText());
                    partToModify.setStock(parseInt(modifyPartInv.getText()));
                    partToModify.setPrice(parseDouble(modifyPartCost.getText()));
                    partToModify.setMax(parseInt(modifyPartMax.getText()));
                    partToModify.setMin(parseInt(modifyPartMin.getText()));
                    ;
                    ((InHouse) partToModify).setMachineId(parseInt(modifyPartMachineId.getText()));
                } else {
                    partToModify.setName(modifyPartName.getText());
                    partToModify.setStock(parseInt(modifyPartInv.getText()));
                    partToModify.setPrice(parseDouble(modifyPartCost.getText()));
                    partToModify.setMax(parseInt(modifyPartMax.getText()));
                    partToModify.setMin(parseInt(modifyPartMin.getText()));
                    ((Outsourced) partToModify).setCompanyName(modifyPartMachineId.getText());
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
     * Go back to main screen without saving
     * @param actionEvent
     * @throws IOException
     */
    public void onModifyPartCancelButton(ActionEvent actionEvent) throws IOException {
        System.out.println("modify part cancel button clicked");
        Parent root = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 600);
        stage.setTitle("Inventory Management");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Sets text for last label to "Machine ID"
     * @param actionEvent
     */
    public void onModifyPartInHouseRadioButton(ActionEvent actionEvent) {
        modifyPartCategoryLabel.setText("Machine ID");
    }

    /**
     * Sets text for last label to "Company Name"
     * @param actionEvent
     */
    public void onModifyPartOutSourcedButton(ActionEvent actionEvent) {
        modifyPartCategoryLabel.setText("Company Name");
    }
}
