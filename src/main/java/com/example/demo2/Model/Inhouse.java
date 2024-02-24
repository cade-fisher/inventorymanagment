package com.example.demo2.Model;

/**
 * Inhouse class extends the part class
 *
 * @author cadefisher
 *
 */
public class Inhouse extends Part{

    private int machineid;

    /**
     * This is used to call the constructors of the part class and extends the machineid variable
     * @param id part ID
     * @param name part Name
     * @param price Part price
     * @param stock part inventory
     * @param max part Max value
     * @param min part minimum value
     * @param machineid machineID for inhouse part
     */
    public Inhouse(int id, String name, double price, int stock, int max, int min, int machineid) {
        //Adding machine ID
        super(id, name, price, stock, max, min);
        this.machineid = machineid;
    }

    /**
     * Getter for machineid
     * @return machineID
     */
    public int getMachineid() {
        return machineid;
    }

    /**
     * setter for machineid
     * @param machineid the machineID
     */
    public void setMachineid(int machineid) {
        this.machineid = machineid;
    }
}
