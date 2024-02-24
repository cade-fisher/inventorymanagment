package com.example.demo2;

import com.example.demo2.Model.Inventory;
import com.example.demo2.Model.Part;
import com.example.demo2.Model.Product;
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
 * Controller for main screen
 * @author cadefisher
 */
public class HelloController implements Initializable {
        //Declared FXIDs
    /**
     * Parts table
     */
    public TableView<Part> partsTV;
    /**
     * Part ID column
     */
    public TableColumn partidcol;
    /**
     * Part name column
     */
    public TableColumn partnamecol;
    /**
     * Part Inventory column
     */
    public TableColumn partinvcol;
    /**
     * Part price column
     */
    public TableColumn partcostcol;
    /**
     * Product table
     */
    public TableView <Product> proTV;
    /**
     * Product ID column
     */
    public TableColumn proidcol;
    /**
     * Product name column
     */
    public TableColumn pronamecol;
    /**
     * Product Inventory column
     */
    public TableColumn proinvcol;
    /**
     * Product price column
     */
    public TableColumn procostcol;
    /**
     * Search part text field
     */
    public TextField queryPart;
    /**
     * Search product text field
     */
    public TextField queryPro;

    /**
     * Allows user to open add part screen
     * @param actionEvent displays AddPart.FXML
     * @throws IOException allows FXML to load
     */
    public void onActionAddParts(ActionEvent actionEvent) throws IOException {
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/demo2/AddPartForm.fxml"));
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 516, 400);
            stage.setTitle("Add Part");
            stage.setScene(scene);
            stage.show();
        }

    /**
     * Allows user to select a part and modify the part
     * @param event opens modifypart.FXML
     * @throws IOException allows FXML to load
     */
    public void onActionModPart(ActionEvent event) throws IOException {


            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo2/ModifyPartForm.fxml"));
            Parent root = loader.load();
            ModifyPartFormController pass = loader.getController();
            pass.passPart((Part) partsTV.getSelectionModel().getSelectedItem(),partsTV.getSelectionModel().getSelectedIndex());
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 516, 400);
            stage.setTitle("Modify Part");
            stage.setScene(scene);
            stage.show();


        }

    /**
     * Allows user to delete part by using inventory method to delete selected part
     * @param actionEvent deletes selected part
     */
    public void onActionDeletePart(ActionEvent actionEvent) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure you would like to delete part?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                Part selectedPart = partsTV.getSelectionModel().getSelectedItem();
                Inventory.deletePart(selectedPart);
            }

        }

    /**
     * Allows user to open the add product screen
     * @param actionEvent opens addproduct.FXML
     * @throws IOException allows FXML to load
     */
    public void onActionAddProduct(ActionEvent actionEvent) throws IOException {
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/demo2/AddProduct.fxml"));
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 800, 600);
            stage.setTitle("Add Product");
            stage.setScene(scene);
            stage.show();
        }

    /**
     * Allows user to select a product and open the modify screen
     * @param event opens modifyproduct.FXML
     * @throws IOException allows FXML to load
     */
    public void onActionModProduct(ActionEvent event) throws IOException {


            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo2/ModifyProduct.fxml"));
            Parent root = loader.load();
            ModifyProductController pass = loader.getController();
            pass.passPro((Product) proTV.getSelectionModel().getSelectedItem(),proTV.getSelectionModel().getSelectedIndex());
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 800, 600);
            stage.setTitle("Modify Product");
            stage.setScene(scene);
            stage.show();
        }

    /**
     * Allows user to select the product and delete the product by using the inventory method
     * @param actionEvent deletes selected product
     */
    public void onActionDeleteProduct(ActionEvent actionEvent) {
            Product product = proTV.getSelectionModel().getSelectedItem();
            if(product.getAllAssociatedParts().size() != 0){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error dialog");
                alert.setContentText("Product cannot contain associated parts");
                alert.showAndWait();
                return;
            }
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure you would like to delete product?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                Product selectedPro = proTV.getSelectionModel().getSelectedItem();
                Inventory.deleteProduct(selectedPro);
            }

        }

    /**
     * This will close the application
     * @param actionEvent closes application
     */
    public void onActionExit(ActionEvent actionEvent) {
            System.exit(0);
        }

    /**
     * Allows user to search for a product by using the lookup methos in inventory class
     * @param event searches for product
     */
    public void onActionSearchPro(ActionEvent event) {
            String pro = queryPro.getText();

            ObservableList<Product> products = Inventory.lookupProductName(pro);

            if (products.size() == 0) {
                try {

                    int id = Integer.parseInt(pro);
                    Product ipro = Inventory.lookupProductId(id);
                    if (ipro != null)
                        products.add(ipro);
                } catch (NumberFormatException e) {
                    //ignore
                }
            }

            proTV.setItems(products);
            queryPro.setText("");

        }


    /**
     * Allows user to search for parts using the lookup method in the inventory class
     * @param event searches for parts
     */
    public void onActionSearchPart(ActionEvent event) {
            String q = queryPart.getText();

            ObservableList<Part> parts = Inventory.lookupPartName(q);

            if (parts.size() == 0) {
                try {

                    int id = Integer.parseInt(q);
                    Part ip = Inventory.lookupPartId(id);
                    if (ip != null)
                        parts.add(ip);
                } catch (NumberFormatException e) {
                }
            }

            partsTV.setItems(parts);
            queryPart.setText("");

        }




        @Override
        /**
         * Initializes the main screen
         * Has the data preload into the tables on main screen
         * lets only one product or part be selected at once
         */
        public void initialize(URL url, ResourceBundle resourceBundle) {
            //Displays parts in tables
            partsTV.setItems(Inventory.getAllparts());
            partidcol.setCellValueFactory(new PropertyValueFactory<>("id"));
            partnamecol.setCellValueFactory(new PropertyValueFactory<>("name"));
            partcostcol.setCellValueFactory(new PropertyValueFactory<>("price"));
            partinvcol.setCellValueFactory(new PropertyValueFactory<>("stock"));


            proTV.setItems(Inventory.getAllproducts());
            proidcol.setCellValueFactory(new PropertyValueFactory<>("id"));
            pronamecol.setCellValueFactory(new PropertyValueFactory<>("name"));
            procostcol.setCellValueFactory(new PropertyValueFactory<>("price"));
            proinvcol.setCellValueFactory(new PropertyValueFactory<>("stock"));

            partsTV.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
            proTV.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        }
}