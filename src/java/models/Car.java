/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author datho
 */
public class Car {
    private long carID;
    private String serialNumber="";
    private String model="";
    private String colour="";
    private int year;

    public Car() {
    
    }

    public Car(long carID, String serialNumber, String model, String colour, int year) {
        this.carID = carID;
        this.serialNumber = serialNumber;
        this.model = model;
        this.colour = colour;
        this.year = year;
    }

    public long getCarID() {
        return carID;
    }

    public void setCarID(long carID) {
        this.carID = carID;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
    
    
}
