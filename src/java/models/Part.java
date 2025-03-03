/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author datho
 */
public class Part {
    private long partID;
    private String partName;
    private double purchasePrice;
    private double retailPrice;

    public Part() {
    }

    public Part(long partID, String partName, double purchasePrice, double retailPrice) {
        this.partID = partID;
        this.partName = partName;
        this.purchasePrice = purchasePrice;
        this.retailPrice = retailPrice;
    }

    public long getPartID() {
        return partID;
    }

    public void setPartID(long partID) {
        this.partID = partID;
    }

    public String getPartName() {
        return partName;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }

    public double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public double getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(double retailPrice) {
        this.retailPrice = retailPrice;
    }
    
    
}
