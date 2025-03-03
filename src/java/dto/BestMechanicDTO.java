/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

/**
 *
 * @author datho
 */
public class BestMechanicDTO {
    private long mechanicID;
    private String name;
    private int hours;
    private double revenue;
    private int totalRepair;

    public BestMechanicDTO(long mechanicID, String name, int hours, double revenue, int totalRepair) {
        this.mechanicID = mechanicID;
        this.name = name;
        this.hours = hours;
        this.revenue = revenue;
        this.totalRepair = totalRepair;
    }

    public long getMechanicID() {
        return mechanicID;
    }

    public void setMechanicID(long mechanicID) {
        this.mechanicID = mechanicID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public double getRevenue() {
        return revenue;
    }

    public void setRevenue(double revenue) {
        this.revenue = revenue;
    }

    public int getTotalRepair() {
        return totalRepair;
    }

    public void setTotalRepair(int totalRepair) {
        this.totalRepair = totalRepair;
    }

    
}
