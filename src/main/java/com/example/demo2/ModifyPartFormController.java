package com.example.demo2;


import com.example.demo2.Model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static com.example.demo2.Model.Inventory.addPart;

/**
 * Modify part controller
 * @author cadefisher
 */
public class ModifyPartFormController implements Initializable {
    //Declared FXIDs
    /**
     * Part ID text field
     */
    public TextField modpartidtxt;
    /**
     * Part Name text field
     */
    public TextField modpartnametxt;
    /**
     * Part inventory text field
     */
    public TextField modpartivntxt;
    /**
     * Part price text field
     */
    public TextField modpartcosttxt;
    /**
     * Max part inventory text field
     */
    public TextField modpartmaxtxt;
    /**
     * Min part inventory text field
     */
    public TextField modpartmintxt;
    /**
     * Outsource radio button
     */
    public RadioButton modpartoutRbt;
    /**
     * Inhouse radio button
     */
    public RadioButton modPartInRbt;
    /**
     * Change me label to machine ID or company name
     */
    public Label modChangeMe;
    /**
     * Mock part for thepart
     */
    public  Part thePart;
    /**
     * MachineID or company name text field
     */
    public TextField modMachineIdtxt;
    private static int theindex;

    /**
     * Allows for the data to pass from the selected part to the modify part controller
     * @param selectedPart the selected part
     * @param selectedindex the selected index
     */
    public void passPart(Part selectedPart, int selectedindex){
        thePart = selectedPart;
        theindex = selectedindex;
        modpartidtxt.setText(Integer.toString(thePart.getId()));
        modpartnametxt.setText(thePart.getName());
        modpartivntxt.setText(Integer.toString(thePart.getStock()));
        modpartcosttxt.setText(Double.toString(thePart.getPrice()));
        modpartmaxtxt.setText(Integer.toString(thePart.getMax()));
        modpartmintxt.setText(Integer.toString(thePart.getMin()));


        if(thePart instanceof Inhouse) {
            modMachineIdtxt.setText(Integer.toString(((Inhouse) thePart).getMachineid()));
            modPartInRbt.setSelected(true);
            modChangeMe.setText("Machine ID");
        }
        if (thePart instanceof Outsourced) {
            modMachineIdtxt.setText(((Outsourced) thePart).getCompanyName());
            modpartoutRbt.setSelected(true);
            modChangeMe.setText("Company Name");
        }

    }

    /**
     * Will save the part information using the updatePart method in the inventory class
     * Has constraints for min,max, and inventory
     * returns to main screen
     * @param event saves and returns to main screen
     * @throws IOException loads main screen
     */
    public void onActionSavePart(ActionEvent event) throws IOException {
        try {
            int id = Integer.parseInt(modpartidtxt.getText());
            String name = modpartnametxt.getText();
            int stock = Integer.parseInt(modpartivntxt.getText());
            double price = Double.parseDouble(modpartcosttxt.getText());
            int max = Integer.parseInt(modpartmaxtxt.getText());
            int min = Integer.parseInt(modpartmintxt.getText());
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



            if (modPartInRbt.isSelected()) {
                int machineId = Integer.parseInt(modMachineIdtxt.getText());
                Part p = new Inhouse(id, name, price, stock, max, min, machineId);
                Inventory.updatePart(theindex,p);

            }
            else {
                String companyName = (modMachineIdtxt.getText());
                Part p = new Outsourced(id, name, price, stock, max, min, companyName);
                Inventory.updatePart(theindex,p);
            }



            Parent root = FXMLLoader.load(getClass().getResource("/com/example/demo2/MainForm.fxml"));
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1000, 400);
            stage.setTitle("Inventory Management");
            stage.setScene(scene);
            stage.show();

        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setContentText("Invalid value for text field");
            alert.showAndWait();
        }
    }

    /**
     * Allows users to return to main screen and asks confirmation that the information will be lost
     * @param event Goes back to main screen
     * @throws IOException loads main screen
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

    /**
     * Radio button will change the label to machin ID when selected
     * @param event changes changeMe label
     */
    public void modInHouseOnRDB(ActionEvent event) {
        modChangeMe.setText("Machine ID");
    }

    /**
     * Outsource radio button will change the label to company Name
     * @param event changes changeMe label
     */
    public void modOutsourceOnRDB(ActionEvent event) {
        modChangeMe.setText("Company Name");
    }

    /**
     * Initializes controller
     * @param url url
     * @param resourceBundle resources
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {



    }
}

    