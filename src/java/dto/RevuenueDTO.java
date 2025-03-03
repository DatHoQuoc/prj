/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

/**
 *
 * @author datho
 */
public class RevuenueDTO {
    private int year;
    private double laborCost;
    private double partsCost;

    public RevuenueDTO(int year, double laborCost, double partsCost) {
        this.year = year;
        this.laborCost = laborCost;
        this.partsCost = partsCost;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getLaborCost() {
        return laborCost;
    }

    public void setLaborCost(double laborCost) {
        this.laborCost = laborCost;
    }

    public double getPartsCost() {
        return partsCost;
    }

    public void setPartsCost(double partsCost) {
        this.partsCost = partsCost;
    }
    
    
}
