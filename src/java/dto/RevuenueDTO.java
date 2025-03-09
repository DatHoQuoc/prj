/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

import java.util.*;

/**
 *
 * @author datho
 */
public class RevuenueDTO {

    HashMap<Integer, ArrayList<Double>> revenue;

    public RevuenueDTO() {
        revenue = new HashMap<>();
    }

    public HashMap<Integer, ArrayList<Double>> getRevenue() {
        return revenue;
    }

    public void setRevenue(HashMap<Integer, ArrayList<Double>> revenue) {
        this.revenue = revenue;
    }

    public void addRevenueEntry(int year, int month, double revenueAmount) {
        if (!revenue.containsKey(year)) {
            revenue.put(year, new ArrayList<>(12));
            for (int i = 0; i < 12; i++) {
                revenue.get(year).add(0.0);
            }
        }
        revenue.get(year).set(month - 1, revenueAmount);
    }

    public Double getRevenueForMoth(int year, int month) {
        if (revenue.containsKey(year)) {
            return revenue.get(year).get(month - 1);
        }
        return 0.0;
    }

    public double[] getHighestAndAverageRevenue() {
        double highest = 0.0;
        double sum = 0.0;
        int count = 0;

        for (Map.Entry<Integer, ArrayList<Double>> entry : revenue.entrySet()) {
          

            for (Double monthRevenue : entry.getValue()) {

                if (monthRevenue > 0.0) {
                    if (monthRevenue > highest) {
                        highest = monthRevenue;
                    }
                    sum += monthRevenue;
                    count++;
                }
            }
        }


        double average = count > 0 ? sum / count : 0.0;
        return new double[]{highest, average};
    }
}
