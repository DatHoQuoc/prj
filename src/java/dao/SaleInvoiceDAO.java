/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dto.*;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import mylib.DBUtils;

/**
 *
 * @author datho
 */
public class SaleInvoiceDAO implements Serializable {

    private ArrayList<RevuenueDTO> revenueList;


    public ArrayList<RevuenueDTO> getRevenueList() {
        return revenueList;
    }

    public ArrayList<SalesReportDTO> statisticCarsSoldByYear(int startYear, int endYear, long saleID) {
        Connection cn = null;
        PreparedStatement stm = null;
        ResultSet table = null;
        Map<Integer, SalesReportDTO> yearlyReports = new HashMap<>();
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "WITH YearRange AS (\n"
                        + "    SELECT CAST(? AS INT) AS year  -- Start year (Dynamic)\n"
                        + "    UNION ALL\n"
                        + "    SELECT year + 1 FROM YearRange WHERE year < ?\n"
                        + ")\n"
                        + "SELECT \n"
                        + "    yr.year AS sale_year,\n"
                        + "    c.model,\n"
                        + "    COUNT(si.carID) AS cars_sold\n"
                        + "FROM YearRange yr\n"
                        + "LEFT JOIN SalesInvoice si ON YEAR(si.invoiceDate) = yr.year \n"
                        + "LEFT JOIN Cars c ON si.carID = c.carID\n"
                        + "WHERE EXISTS(\n"
                        + "    SELECT 1\n"
                        + "    FROM SalesPerson sp \n"
                        + "    WHERE sp.salesID = si.salesID AND sp.salesID = ?\n"
                        + ")\n"
                        + "GROUP BY yr.year, c.model\n"
                        + "ORDER BY yr.year, c.model;";
                stm = cn.prepareStatement(sql);
                stm.setInt(1, startYear);
                stm.setInt(2, endYear);
                stm.setLong(3, saleID);

                table = stm.executeQuery();

                if (table != null) {
                    while (table.next()) {
                        int saleYear = table.getInt("sale_year");
                        String model = table.getString("model");
                        int carSold = table.getInt("cars_sold");

                        SalesReportDTO dto = yearlyReports.get(saleYear);
                        if(dto == null){
                            dto = new SalesReportDTO(saleYear);
                            yearlyReports.put(saleYear, dto);
                        }
                        dto.addModelSales(model, carSold);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (table != null) {
                    table.close();
                }
                if (stm != null) {
                    stm.close();
                }
                if (cn != null) {
                    cn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new ArrayList<>(yearlyReports.values());
    }

    public void selectRevenueList(long saleID) {
        Connection cn = null;
        PreparedStatement stm = null;
        ResultSet table = null;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "WITH ServiceRevenue AS (\n"
                        + "    SELECT st.serviceTicketID,\n"
                        + "           SUM(sm.hours * sm.rate) as laborCost,\n"
                        + "           (SELECT COALESCE(SUM(pu.price * pu.numberUsed), 0)\n"
                        + "            FROM PartsUsed pu\n"
                        + "            WHERE pu.serviceTicketID = st.serviceTicketID) as partsCost\n"
                        + "    FROM ServiceTicket st\n"
                        + "    LEFT JOIN ServiceMehanic sm ON st.serviceTicketID = sm.serviceTicketID\n"
                        + "    GROUP BY st.serviceTicketID\n"
                        + ")\n"
                        + "SELECT YEAR(ST.dateReturned) AS YearRevenue,SUM(SV.laborCost) AS laborCost,SUM(SV.partsCost) AS partsCost\n"
                        + "FROM SalesInvoice si\n"
                        + "LEFT JOIN ServiceTicket st ON si.custID = st.custID\n"
                        + "LEFT JOIN ServiceRevenue sv ON st.serviceTicketID = sv.serviceTicketID\n"
                        + "WHERE si.salesID = ?  \n"
                        + "GROUP BY YEAR(ST.dateReturned)";

                stm = cn.prepareStatement(sql);
                stm.setLong(1, saleID);

                table = stm.executeQuery();
                if (table != null) {
                    while (table.next()) {
                        int year = table.getInt("YearRevenue");
                        double laborCost = table.getDouble("laborCost");
                        double partCost = table.getDouble("partsCost");

                        RevuenueDTO dto = new RevuenueDTO(year, laborCost, partCost);

                        if (revenueList == null) {
                            revenueList = new ArrayList<>();
                        }
                        revenueList.add(dto);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (table != null) {
                    table.close();
                }
                if (stm != null) {
                    stm.close();
                }
                if (cn != null) {
                    cn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
