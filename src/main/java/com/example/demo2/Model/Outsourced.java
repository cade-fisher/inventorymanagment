package com.example.demo2.Model;


/**
 * Outsource class extends the Part class
 * @author cadefisher
 */
public class Outsourced extends Part{

    private String companyName;

    /**
     * his is used to call the constructors of the part class and extends the companyName variable
     * @param id part ID
     * @param name part Name
     * @param price part price
     * @param stock part inventory
     * @param max part max inventory
     * @param min part min inventory
     * @param companyName company name for outsourced part
     */
    public Outsourced(int id, String name, double price, int stock, int max, int min, String companyName) {
        //Adds companyName to part
        super(id, name, price, stock, max, min);
        this.companyName = companyName;
    }

    /**
     * Getter for companyName
     * @return company name
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * Setter for companyName
     * @param companyName company name for outsource part
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
