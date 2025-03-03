/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author datho
 */
public class SalesReportDTO {
    private int saleYear;
    private Map<String, Integer> models;

    public SalesReportDTO(int saleYear) {
        this.saleYear = saleYear;
        this.models = new HashMap<>();
    }

    public int getSaleYear() {
        return saleYear;
    }

    public void setSaleYear(int saleYear) {
        this.saleYear = saleYear;
    }

    public Map<String, Integer> getModels() {
        return models;
    }

    public void setModels(Map<String, Integer> models) {
        this.models = models;
    }

    public void addModelSales(String model, int carsSold){
        this.models.put(model, carsSold);
    }
    
}
