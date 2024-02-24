package com.example.demo2;

import com.example.demo2.Model.Inhouse;
import com.example.demo2.Model.Inventory;
import com.example.demo2.Model.Outsourced;
import com.example.demo2.Model.Product;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JAVADOCS FOLDER LOCATION: In this project under the scr folder
 * @author cadefisher
 * FUTURE ENHANCMENTS: In the future this application will allow for customer to be added and have products be associted with the custmer.
 */
public class HelloApplication extends Application {
    //Launches application
    @Override
    /**
     * Launches the application
     */
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("MainForm.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 400);
        stage.setTitle("Inventory Management");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Pre loads data for parts and products
     * @param args arguments
     */
    public static void main(String[] args) {
        Product product1 = new Product(Inventory.getIdCounterProduct(),"Car",1.99,2,1,3);
        Product product2 = new Product(Inventory.getIdCounterProduct(),"Bus",132.99,2,1,3);
        Product product3 = new Product(Inventory.getIdCounterProduct(),"Ski",10.99,2,1,3);
        ///Adds products
        Inventory.addProduct(product1);
        Inventory.addProduct(product2);
        Inventory.addProduct(product3);
        //System.out.println(product1.getAllAssociatedParts().size());


        Inhouse inhouse1 = new Inhouse(Inventory.getIdCounterPart(),"Car",2.99,1,2,1,123);
        Inhouse inhouse2 = new Inhouse(Inventory.getIdCounterPart(),"Book",43.99,1,2,1,334);
        Inhouse inhouse3 = new Inhouse(Inventory.getIdCounterPart(),"Pumpkin",92.99,1,2,1,323);

        Outsourced outsourced1 = new Outsourced(Inventory.getIdCounterPart(),"Food",20.45,2,3,1,"U-Haul");



        //Adds Parts
        Inventory.addPart(inhouse1);
        Inventory.addPart(inhouse2);
        Inventory.addPart(inhouse3);
        Inventory.addPart(outsourced1);



        //Launces application
        launch();
    }
}