package com.example.demo2.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Product class
 * @author cadefisher
 *
 */
public class Product {

    // Declared Fields
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    /**
     * Allows products to get associated parts
     * @param part all parts associated to product
     */
    public void addAssociatedPart(Part part) {

        getAllAssociatedParts().add(part);
    }

    /**
     * Getter for all associated parts
     * @return a list of parts
     */
    public ObservableList<Part> getAllAssociatedParts(){


        return associatedParts;
    }

    /**
     * Allows user to remove associated parts
     * @param selectAssociatedPart associated part that was selected
     * @return true
     */
    public boolean deleteAssociatedPart(Part selectAssociatedPart) {
        associatedParts.remove(selectAssociatedPart);
        return true;
    }




    /**
     * These are constructors
     * @param id product ID
     * @param name product Name
     * @param price product price
     * @param stock product inventory
     * @param min min product inventory
     * @param max man product invenotry
     */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * Getter for Id
     * @return product id
     */
    public int getId() {
        return id;
    }

    /**
     * Setter for ID
     * @param id sets id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Getter for name
     * @return product name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for name
     * @param name sets name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * getter for price
     * @return product price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Setter for price
     * @param price sets price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * getter for stock
     * @return product stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * Setter for stock
     * @param stock sets stock
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * getter for min
     * @return the min
     */
    public int getMin() {
        return min;
    }

    /**
     * setter for min
     * @param min sets the min
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * getter for max
     * @return the max
     */
    public int getMax() {
        return max;
    }

    /**
     * setter for max
     * @param max sets max
     */
    public void setMax(int max) {
        this.max = max;
    }
}
