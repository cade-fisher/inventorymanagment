package com.example.demo2.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Inventory class
 *
 * @author cadefisher
 */
public class Inventory {
    // Add all parts and products from observable list
    private static ObservableList<Part> allparts = FXCollections.observableArrayList();
    private static ObservableList<Product> allproducts = FXCollections.observableArrayList();
    // Allows for Auto gen ID numbers for parts and products
    private static int idCounterPart = 0;
    private static int idCounterProduct = 0;

    /**
     * Getter for part ID counter, increases the part ID in increments of 1
     * @return idCounter part
     */
    public static int getIdCounterPart() {
        return ++idCounterPart;
    }

    /**
     * Getter for product ID counter, increases the product ID in increments of 1
     * @return id counter for product
     */
    public static int getIdCounterProduct() {
        return ++idCounterProduct;
    }

    /**
     * Adds part for all parts in observable list
     * @param part list of parts
     */

    public static void addPart(Part part) {

        allparts.add(part);

    }

    /**
     * Getter for all parts in observable list
     * @return gets all parts
     */
    public static ObservableList<Part> getAllparts() {

        return allparts;
    }

    /**
     * adds product for all products
     * @param product list of products
     */
    public static void addProduct(Product product) {

        allproducts.add(product);

    }

    /**
     * Getter for products in observable list
     * @return gets all products
     */
    public static ObservableList<Product> getAllproducts() {

        return allproducts;
    }

    /**
     * Allows user to search for parts using ID number
     * @param id part ID
     * @return the part ID
     */
    public static Part lookupPartId(int id) {
        ObservableList<Part> allParts = getAllparts();

        for (int i = 0; i < allParts.size(); i++) {
            Part ip = allParts.get(i);

            if (ip.getId() == id) {
                return ip;

            }
        }

        return null;
    }

    /**
     * Allows user to look up part by name or using partial name
     * @param partialName partial part name
     * @return part by name
     */
    public static ObservableList<Part> lookupPartName(String partialName) {
        ObservableList<Part> namedPart = FXCollections.observableArrayList();

        ObservableList<Part> allParts = getAllparts();

        for (Part ip : allParts) {
            if (ip.getName().contains(partialName)) {
                namedPart.add(ip);
            }
        }


        return namedPart;
    }

    /**
     * Allows user to look up product by name or partial name
     * @param partialProName partial product name
     * @return product by name
     */
    public static ObservableList<Product> lookupProductName(String partialProName) {
        ObservableList<Product> namedPro = FXCollections.observableArrayList();

        ObservableList<Product> allProducts = getAllproducts();

        for (Product ipro : allProducts) {
            if (ipro.getName().contains(partialProName)) {
                namedPro.add(ipro);
            }
        }


        return namedPro;
    }

    /**
     * Allows user to look up product by ID number
     * @param id product ID
     * @return Product by ID
     */
    public static Product lookupProductId(int id) {
        ObservableList<Product> allproduct = getAllproducts();

        for (int i = 0; i < allproduct.size(); i++) {
            Product ipro = allproduct.get(i);

            if (ipro.getId() == id) {
                return ipro;

            }
        }

        return null;
    }

    /**
     * Allows user to delete selected product
     * @param selectedProduct selected product in table
     * @return deletes selected product
     */
    public static boolean deleteProduct(Product selectedProduct) {
        allproducts.remove(selectedProduct);
        return true;
    }

    /**
     * Allows modify product controller to update the selected product
     * @param index the index
     * @param selectedProduct the product that was selected
     */
    public static void updateProduct(int index, Product selectedProduct){
        allproducts.set(index,selectedProduct);
}

    /**
     * Allows modify part controller to update selected part
     * @param index the index
     * @param selectedPart the part that was selected
     */
    public static void updatePart(int index, Part selectedPart){
        allparts.set(index,selectedPart);
    }

    /**
     * Allows user to delete selected part
     * @param selectedPart part that was selected
     * @return deletes selected product
     */
    public static boolean deletePart(Part selectedPart) {

        allparts.remove(selectedPart);
        return true;
    }



}
