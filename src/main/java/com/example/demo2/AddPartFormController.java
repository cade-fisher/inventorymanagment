package com.example.demo2;

import com.example.demo2.Model.Inhouse;
import com.example.demo2.Model.Inventory;
import com.example.demo2.Model.Outsourced;
import com.example.demo2.Model.Part;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

import static com.example.demo2.Model.Inventory.addPart;

/**
 * Add part controller
 * @author cadefisher
 */
public class AddPartFormController {

    // Decalared FXID
    /**
     * machineID or companyName text field
     */
    public TextField machidtxt;
    @FXML
    /**
     * Part ID text field
     */
    public TextField partIdtxt;

    @FXML
    /**
     * Part cost text field
     */
    public TextField partcosttxt;

    @FXML
    /**
     * Inhouse radio button
     */
    public RadioButton partinRbtn;

    @FXML
    /**
     * part inventory text field
     */
    public TextField partinvtxt;

    @FXML
    /**
     * Max part inventory text field
     */
    public TextField partmaxtxt;
    @FXML
    /**
     * Min part inventory text field
     */
    public TextField partmintxt;
    @FXML
    /**
     * Part name text field
     */
    public TextField partnametxt;

    @FXML
    /**
     * Outsource radio button
     */
    public RadioButton partoutRbtn;
    /**
     * change me label, changes to outsource or inhouse
     */
    public Label changeMe;

    @FXML
    // Allows back button to confirm and go back to main screen
    /**
     * The displays the main form
     * Asking confirmation that the information will not be saved
     */
   public void onActionDisplayMainForm(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"This will clear all values, do you want to continue?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK){
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/demo2/MainForm.fxml"));
            Stage stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root,1000,400);
            stage.setTitle("Inventory Management");
            stage.setScene(scene);
            stage.show();
        }

    }

    @FXML
    //Saves part
    /**
     * This saves the part and declares if the part is inhouse or outsource,allows user to add machineID or companyName
     * Has constraints for min, max, and inventory values.
     * Will throw an error if the user puts invalid values in text field
     * Will also return to main screen
     */
   public void onActionSavePart(ActionEvent event) throws IOException {
        try{
            int id = Inventory.getIdCounterPart();
            String name = partnametxt.getText();
            int stock = Integer.parseInt(partinvtxt.getText());
            double price = Double.parseDouble(partcosttxt.getText());
            int max = Integer.parseInt(partmaxtxt.getText());
            int min = Integer.parseInt(partmintxt.getText());
            //Adds constraints for inventory between min and max
            if(min < 0 | stock < 0 | min < 0){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error dialog");
                alert.setContentText("Value must be greater than 0");
                alert.showAndWait();
                return;
            }
            if (min > max) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error dialog");
                alert.setContentText("Min must be less than max");
                alert.showAndWait();
                return;
            }
               if (stock > max | stock < min) {
                   Alert alert = new Alert(Alert.AlertType.ERROR);
                   alert.setTitle("Error dialog");
                   alert.setContentText("Inventory must be between minimum and maximum values.");
                   alert.showAndWait();
                   return;
               }


            //Specifies Inhouse or Outsourced and allows for company name or machine ID
            if (partinRbtn.isSelected()) {
                int machineId = Integer.parseInt(machidtxt.getText());
                Inventory.addPart(new Inhouse(id, name, price, stock, max, min, machineId));
            }
            if (partoutRbtn.isSelected()) {
                String companyName = (machidtxt.getText());
                Inventory.addPart(new Outsourced(id,name,price,stock,max,min,companyName));
            }



            // Goes back to main screen
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/demo2/MainForm.fxml"));
            Stage stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root,1000,400);
            stage.setTitle("Inventory Management");
            stage.setScene(scene);
            stage.show();

        }// Warning dialog for number formatting
        catch (NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setContentText("Invalid value for text field");
            alert.showAndWait();

        }




    }

    @FXML
    void initialize() {

    }

    /**
     * Changes the label to company name
     * @param actionEvent when outsource radio button is selected
     */
    public void onOutsourced(ActionEvent actionEvent) {
        changeMe.setText("Company Name");
    }

    /**
     * Changes label to machine ID
     * @param actionEvent when Inhouse radio button is selected
     */
    public void onInHouse(ActionEvent actionEvent) {
        changeMe.setText("Machine ID");
    }
}


