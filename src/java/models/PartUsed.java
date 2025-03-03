/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author datho
 */
public class PartUsed {
    private int serviceTicketID;
    private long partID;
    private int numberUsed;
    private double price;

    public PartUsed() {
    }

    public PartUsed(int serviceTicketID, long partID, int numberUsed, double price) {
        this.serviceTicketID = serviceTicketID;
        this.partID = partID;
        this.numberUsed = numberUsed;
        this.price = price;
    }

    public int getServiceTicketID() {
        return serviceTicketID;
    }

    public void setServiceTicketID(int serviceTicketID) {
        this.serviceTicketID = serviceTicketID;
    }

    public long getPartID() {
        return partID;
    }

    public void setPartID(long partID) {
        this.partID = partID;
    }

    public int getNumberUsed() {
        return numberUsed;
    }

    public void setNumberUsed(int numberUsed) {
        this.numberUsed = numberUsed;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    
    
}
