/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

/**
 *
 * @author datho
 */
public class ServiceDTO {
    private int serviceId;
    private String mechanicName = "";
    private String serviceName = "";
    private int hours;
    private double rate;
    private String comment = "";
    private double total;

    public ServiceDTO(int serviceId, String mechanicName, String serviceName, int hours, double rate, String comment, double total) {
        this.serviceId = serviceId;
        this.mechanicName = mechanicName;
        this.serviceName = serviceName;
        this.hours = hours;
        this.rate = rate;
        this.comment = comment;
        this.total = total;
    }
    
    

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public String getMechanicName() {
        return mechanicName;
    }

    public void setMechanicName(String mechanicName) {
        this.mechanicName = mechanicName;
    }

    

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
    
    
}
