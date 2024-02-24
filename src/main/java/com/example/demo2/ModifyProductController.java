package com.example.demo2;


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

/**
 * Modify product controller
 * @author cadefisher
 */
public class ModifyProductController implements Initializable {

    /**
     * Part table
     */
    public TableView<Part> modProTV;
    /**
     * Part ID column
     */
    public TableColumn modProIDcol;
    /**
     * Part name column
     */
    public TableColumn modProNamecol;
    /**
     * Part inventory column
     */
    public TableColumn modProInvcol;
    /**
     * Part price column
     */
    public TableColumn modProCostcol;
    /**
     * Associated part table
     */
    public TableView <Part>modAssTV;
    /**
     * Part ID column
     */
    public TableColumn modAssIdcol;
    /**
     * Part name column
     */
    public TableColumn modAssNamecol;
    /**
     * Part inventory column
     */
    public TableColumn modAssinvcol;
    /**
     * Part price column
     */
    public TableColumn modAsscostcol;
    /**
     * Product ID text field
     */
    public TextField modproidtxt;
    /**
     * Product name text field
     */
    public TextField modpronametxt;
    /**
     * Product inventory text field
     */
    public TextField modproinvtxt;
    /**
     * Product price text field
     */
    public TextField modprocosttxt;
    /**
     * Product inventory max
     */
    public TextField modpromaxtxt;
    /**
     * Product inventory min
     */
    public TextField modpromintxt;
    /**
     * Search for part text field
     */
    public TextField queryModAssPart;
    private static Product theproduct;
    private static int theindex;
    private ObservableList<Part> myPartList = null;
    Product product1 = new Product(Inventory.getIdCounterProduct(), "Test", 1.99, 1, 1, 1);

    /**
     * Allows for the data to pass from the selected product to the modify product controller
     * @param selectedPro the selected product
     * @param selectedindex the selected index
     */
    public void passPro(Product selectedPro,int selectedindex) {
        theproduct = selectedPro;
        theindex = selectedindex;
        myPartList = FXCollections.observableArrayList(theproduct.getAllAssociatedParts());
        modproidtxt.setText(Integer.toString(theproduct.getId()));
        modpronametxt.setText(theproduct.getName());
        modproinvtxt.setText(Integer.toString(theproduct.getStock()));
        modprocosttxt.setText(Double.toString(theproduct.getPrice()));
        modpromaxtxt.setText(Integer.toString(theproduct.getMax()));
        modpromintxt.setText(Integer.toString(theproduct.getMin()));
        modAssTV.setItems(myPartList);
        int index = 0;
        while(index < modAssTV.getItems().size()){
            product1.addAssociatedPart((Part) modAssTV.getItems().get(index));
            index++;
        }
    }

    /**
     * Adds part to bottom table, adds associated part to product
     * @param event adds part to associated part table
     */
    public void onActionAddParttoProd(ActionEvent event) {
        Part selectedItem = (Part) modProTV.getSelectionModel().getSelectedItem();
        product1.addAssociatedPart(selectedItem);
        modAssTV.setItems(product1.getAllAssociatedParts());


    }

    /**
     * LOGICAL ERROR: Allows for user to remove an associated part if the product didn't already have a part, but if the product already has associated parts it will not remove the part when modifying.
     *FIX: Had to re-display the list after deleting associated parts by using "modAssTV.setItems(product1.getAllAssociatedParts());"
     * Removes part from associated parts and confirms with user
     * @param event removes part
     */
    public void onActionRemovePartFromPro(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure you would like to remove part?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Part selectAssociatedPart = modAssTV.getSelectionModel().getSelectedItem();
           product1.deleteAssociatedPart(selectAssociatedPart);

        }
        modAssTV.setItems(product1.getAllAssociatedParts());
    }

    /**
     * Allows user to save modification
     * checks for min,max, and inventory constraints
     * Adds associated parts to product
     * Loads main screen
     * @param event when button is pushed it saves product
     * @throws IOException allows main screen to load
     */
    public void onActionSaveProduct(ActionEvent event) throws IOException {
        try {
            int id =Integer.parseInt(modproidtxt.getText()) ;
            String name = modpronametxt.getText();
            int stock = Integer.parseInt(modproinvtxt.getText());
            double price = Double.parseDouble(modprocosttxt.getText());
            int max = Integer.parseInt(modpromaxtxt.getText());
            int min = Integer.parseInt(modpromintxt.getText());

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
            while (index <modAssTV.getItems().size()) {
                Part partToAdd = (Part) modAssTV.getItems().get(index);
                product.addAssociatedPart(partToAdd);
                index++;
            }
            Inventory.updateProduct(theindex,product);


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
    public void onActionModSearchAssPart(ActionEvent event) {
        String q = queryModAssPart.getText();

        ObservableList<Part> parts = Inventory.lookupPartName(q);

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

        modProTV.setItems(parts);
        queryModAssPart.setText("");

    }


    @Override
    /**
     * Initializes controller, allows for data to be in tables
     */
    public void initialize(URL url, ResourceBundle resourceBundle) {

        modProTV.setItems(Inventory.getAllparts());
        modProIDcol.setCellValueFactory(new PropertyValueFactory<>("id"));
        modProNamecol.setCellValueFactory(new PropertyValueFactory<>("name"));
        modProCostcol.setCellValueFactory(new PropertyValueFactory<>("price"));
        modProInvcol.setCellValueFactory(new PropertyValueFactory<>("stock"));

        modAssIdcol.setCellValueFactory(new PropertyValueFactory<>("id"));
        modAssNamecol.setCellValueFactory(new PropertyValueFactory<>("name"));
        modAsscostcol.setCellValueFactory(new PropertyValueFactory<>("price"));
        modAssinvcol.setCellValueFactory(new PropertyValueFactory<>("stock"));

        }




    }


