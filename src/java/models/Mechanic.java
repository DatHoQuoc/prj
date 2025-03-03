/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author datho
 */
public class Mechanic {
    private long mechanicID;
    private String mechanicName = "";

    public Mechanic() {

    }

    public Mechanic(long mechanicID, String mechanicName) {
        this.mechanicID = mechanicID;
        this.mechanicName = mechanicName;
    }

    public long getMechanicID() {
        return mechanicID;
    }

    public void setMechanicID(long mechanicID) {
        this.mechanicID = mechanicID;
    }

    public String getMechanicName() {
        return mechanicName;
    }

    public void setMechanicName(String mechanicName) {
        this.mechanicName = mechanicName;
    }
    
    
    
}
