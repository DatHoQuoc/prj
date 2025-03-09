/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

/**
 *
 * @author datho
 */
public class Test {

    public static void main(String[] args) {
        RevuenueDTO dto = new RevuenueDTO();
        dto.addRevenueEntry(2024, 1, 1500.0); // January 2024
        dto.addRevenueEntry(2024, 2, 1800.0); // February 2024
        double highest = 0.0;
        double ave = 0.0;
// Debug check
        System.out.println("Year 2024, Month 1: " + dto.getRevenueForMoth(2024, 1));
        double[] highestAndAve = dto.getHighestAndAverageRevenue();
        highest = highestAndAve[0];
        ave = highestAndAve[1];
        System.out.println(highest + " " + ave);
    }
}
