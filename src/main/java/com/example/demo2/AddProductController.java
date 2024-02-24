package com.example.demo2;


import com.example.demo2.Model.Inhouse;
import com.example.demo2.Model.Inventory;
import com.example.demo2.Model.Part;
import com.example.demo2.Model.Product;
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

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static com.example.demo2.Model.Inventory.addPart;
import static com.example.demo2.Model.Inventory.addProduct;

/**
 * Add product controller
 * @author cadefisher
 */

public class AddProductController implements Initializable {
    // Declared FXIDs
    /**
     * Part top table view
     */
    public TableView<Part> addProTV;
    /**
     * part ID column
     */
    public TableColumn addProPartIdcol;
    /**
     * Part name column
     */
    public TableColumn addProPartnamecol;
    /**
     * Part inventory column
     */
    public TableColumn addpropartinvcool;
    /**
     * Part price column
     */
    public TableColumn addpropartcostcol;
    /**
     * Associated part table
     */
    public TableView<Part> assAssPartTV;
    /**
     * Part ID column
     */
    public TableColumn addAssPartIDcol;
    /**
     * Part name column
     */
    public TableColumn addAssNamecol;
    /**
     * Part inventory column
     */
    public TableColumn addAssinvcol;
    /**
     * Part price column
     */
    public TableColumn addAsscostcol;
    /**
     * Product ID text field
     */
    public TextField productidtxt;
    /**
     * Product name text field
     */
    public TextField productnametxt;
    /**
     * Product inventory text field
     */
    public TextField productinvtxt;
    /**
     * Product price text field
     */
    public TextField productcosttxt;
    /**
     * Max product inventory text field
     */
    public TextField productmaxtxt;
    /**
     * Min product inventory text field
     */
    public TextField productmintxt;
    /**
     * Search for part text field
     */
    public TextField queryAssPart;
    //Have to add product for the Add part to product button
    Product product1 = new Product(Inventory.getIdCounterPart(),"Test",1.99,1,1,1);

    /**
     * Adds part to bottom table, adds associated part to product
     * @param event adds part to product
     */
    public void onActionAddParttoProd(ActionEvent event) {
       Part selectedItem = (Part) addProTV.getSelectionModel().getSelectedItem();
       product1.addAssociatedPart(selectedItem);
       assAssPartTV.setItems(product1.getAllAssociatedParts());


    }


    /**
     * Removes part for associated part table and confirms with user
     * @param event removes part
     */
    public void onActionRemovePartFromPro(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure you would like to remove part?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Part selectAssociatedPart = assAssPartTV.getSelectionModel().getSelectedItem();
            product1.deleteAssociatedPart(selectAssociatedPart);
        }
    }

    //Saves product

    /**
     * Allows user to save product
     * checks for min,max, and inventory constraints
     * Adds associated parts to product
     * Loads main screen
     * @param event when button is pushed it saves product
     * @throws IOException allows main screen to load
     */
    public void onActionSaveProduct(ActionEvent event) throws IOException {
        try {
            int id = Inventory.getIdCounterProduct();
            String name = productnametxt.getText();
            int stock = Integer.parseInt(productinvtxt.getText());
            double price = Double.parseDouble(productcosttxt.getText());
            int max = Integer.parseInt(productmaxtxt.getText());
            int min = Integer.parseInt(productmintxt.getText());

            // Creates contrains for invenotry based on min and max
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
            //Saves associated parts to product
            Product product = new Product(id,name,price,stock,min,max);
            int index = 0;
            while (index < assAssPartTV.getItems().size()) {
                Part partToAdd = (Part) assAssPartTV.getItems().get(index);
                product.addAssociatedPart(partToAdd);
                index++;
            }
            Inventory.addProduct(product);

            //Loads main screen
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/demo2/MainForm.fxml"));
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1000, 400);
            stage.setTitle("Inventory Management");
            stage.setScene(scene);
            stage.show();
            // Warning dialog for invalid number formats
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setContentText("Invalid value for text field");
            alert.showAndWait();
        }
    }

    /**
     * Displays main screen and confirm with user that the data will not be saved
     * @param event returns to main screen
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
     * Allows user to search for a part using the lookupPart name and ID in inventory class
     * @param event looks up from user input iin text field
     */
    public void onActionSearchAssPart(ActionEvent event) {
        String q = queryAssPart.getText();

        ObservableList<Part>parts = Inventory.lookupPartName(q);

        if (parts.size() == 0) {
            try {

                int id = Integer.parseInt(q);
                Part ip = Inventory.lookupPartId(id);
                if (ip != null)
                    parts.add(ip);
            } catch (NumberFormatException e) {
                //ignore
            }
        }

        addProTV.setItems(parts);
        queryAssPart.setText("");

    }



    @Override
    /**
     * Initializes controller, allows for data to be in tables
     */
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Displays values in tables
        addProTV.setItems(Inventory.getAllparts());
        addProPartIdcol.setCellValueFactory(new PropertyValueFactory<>("id"));
        addProPartnamecol.setCellValueFactory(new PropertyValueFactory<>("name"));
        addpropartcostcol.setCellValueFactory(new PropertyValueFactory<>("price"));
        addpropartinvcool.setCellValueFactory(new PropertyValueFactory<>("stock"));

        addAssPartIDcol.setCellValueFactory(new PropertyValueFactory<>("id"));
        addAssNamecol.setCellValueFactory(new PropertyValueFactory<>("name"));
        addAsscostcol.setCellValueFactory(new PropertyValueFactory<>("price"));
        addAssinvcol.setCellValueFactory(new PropertyValueFactory<>("stock"));


    }


}



