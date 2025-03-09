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
                        if (dto == null) {
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

    public RevuenueDTO selectRevenueList(int yearStart, int yearEnd) {
        Connection cn = null;
        PreparedStatement stm = null;
        ResultSet table = null;
        RevuenueDTO dto = new RevuenueDTO();
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "WITH YearRange AS (\n"
                        + "    SELECT CAST(? AS INT) AS year\n"
                        + "    UNION ALL\n"
                        + "    SELECT year + 1 FROM YearRange WHERE year < ? \n"
                        + "),\n"
                        + "AllMonthsYears AS (\n"
                        + "    SELECT \n"
                        + "        m.MonthNumber,\n"
                        + "        y.year AS YearNumber\n"
                        + "    FROM \n"
                        + "        (SELECT 1 AS MonthNumber UNION ALL\n"
                        + "         SELECT 2 UNION ALL\n"
                        + "         SELECT 3 UNION ALL\n"
                        + "         SELECT 4 UNION ALL\n"
                        + "         SELECT 5 UNION ALL\n"
                        + "         SELECT 6 UNION ALL\n"
                        + "         SELECT 7 UNION ALL\n"
                        + "         SELECT 8 UNION ALL\n"
                        + "         SELECT 9 UNION ALL\n"
                        + "         SELECT 10 UNION ALL\n"
                        + "         SELECT 11 UNION ALL\n"
                        + "         SELECT 12) AS m\n"
                        + "    CROSS JOIN YearRange y\n"
                        + ")\n"
                        + "SELECT \n"
                        + "    my.YearNumber,\n"
                        + "    my.MonthNumber,\n"
                        + "\n"
                        + "    COALESCE(SUM(pu.numberUsed * pu.price),0) AS RevenuePart\n"
                        + "FROM \n"
                        + "    AllMonthsYears my\n"
                        + "    LEFT JOIN ServiceTicket st ON MONTH(st.dateReturned) = my.MonthNumber \n"
                        + "                              AND YEAR(st.dateReturned) = my.YearNumber\n"
                        + "    LEFT JOIN PartsUsed pu ON st.serviceTicketID = pu.serviceTicketID\n"
                        + "    LEFT JOIN ServiceMehanic sm ON st.serviceTicketID = sm.serviceTicketID\n"
                        + "GROUP BY \n"
                        + "    my.YearNumber,\n"
                        + "    my.MonthNumber\n"
                        + "ORDER BY \n"
                        + "    my.YearNumber,\n"
                        + "    my.MonthNumber;";

                stm = cn.prepareStatement(sql);
                stm.setInt(1, yearStart);
                stm.setInt(2, yearEnd);
                table = stm.executeQuery();
                if (table != null) {
                    while (table.next()) {
                        int year = table.getInt("YearNumber");
                        int month = table.getInt("MonthNumber");
                        double revenue = table.getDouble("RevenuePart");

                        dto.addRevenueEntry(year, month, revenue);

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
        return dto;
    }

    public RevuenueDTO selectRevenueListOfService(int yearStart, int yearEnd) {
        Connection cn = null;
        PreparedStatement stm = null;
        ResultSet table = null;
        RevuenueDTO dto = new RevuenueDTO();
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "WITH YearRange AS (\n"
                        + "    SELECT CAST(? AS INT) AS year\n"
                        + "    UNION ALL\n"
                        + "    SELECT year + 1 FROM YearRange WHERE year < ? \n"
                        + "),\n"
                        + "AllMonthsYears AS (\n"
                        + "    SELECT \n"
                        + "        m.MonthNumber,\n"
                        + "        y.year AS YearNumber\n"
                        + "    FROM \n"
                        + "        (SELECT 1 AS MonthNumber UNION ALL\n"
                        + "         SELECT 2 UNION ALL\n"
                        + "         SELECT 3 UNION ALL\n"
                        + "         SELECT 4 UNION ALL\n"
                        + "         SELECT 5 UNION ALL\n"
                        + "         SELECT 6 UNION ALL\n"
                        + "         SELECT 7 UNION ALL\n"
                        + "         SELECT 8 UNION ALL\n"
                        + "         SELECT 9 UNION ALL\n"
                        + "         SELECT 10 UNION ALL\n"
                        + "         SELECT 11 UNION ALL\n"
                        + "         SELECT 12) AS m\n"
                        + "    CROSS JOIN YearRange y\n"
                        + ")\n"
                        + "SELECT \n"
                        + "    my.YearNumber,\n"
                        + "    my.MonthNumber,\n"
                        + "\n"
                        + "    COALESCE(SUM(sm.hours * sm.rate),0) AS RevenueService\n"
                        + "FROM \n"
                        + "    AllMonthsYears my\n"
                        + "    LEFT JOIN ServiceTicket st ON MONTH(st.dateReturned) = my.MonthNumber \n"
                        + "                              AND YEAR(st.dateReturned) = my.YearNumber\n"
                        + "    LEFT JOIN PartsUsed pu ON st.serviceTicketID = pu.serviceTicketID\n"
                        + "    LEFT JOIN ServiceMehanic sm ON st.serviceTicketID = sm.serviceTicketID\n"
                        + "GROUP BY \n"
                        + "    my.YearNumber,\n"
                        + "    my.MonthNumber\n"
                        + "ORDER BY \n"
                        + "    my.YearNumber,\n"
                        + "    my.MonthNumber;";

                stm = cn.prepareStatement(sql);
                stm.setInt(1, yearStart);
                stm.setInt(2, yearEnd);
                table = stm.executeQuery();
                if (table != null) {
                    while (table.next()) {
                        int year = table.getInt("YearNumber");
                        int month = table.getInt("MonthNumber");
                        double revenue = table.getDouble("RevenueService");

                        dto.addRevenueEntry(year, month, revenue);

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
        return dto;
    }
}
