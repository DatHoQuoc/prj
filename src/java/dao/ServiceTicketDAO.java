/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import models.*;
import mylib.DBUtils;

/**
 *
 * @author datho
 */
public class ServiceTicketDAO implements Serializable {

    private ArrayList<ServiceTicket> listServiceTickets;

   

    public ArrayList<ServiceTicket> getServiceTicketList() {
        return listServiceTickets;
    }

    public void selectListOfServiceTicket() {
        Connection cn = null;
        PreparedStatement stm = null;
        ResultSet table = null;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "SELECT  [serviceTicketID]\n"
                        + "      ,[dateReceived]\n"
                        + "      ,[dateReturned]\n"
                        + "      ,[custID]\n"
                        + "      ,[carID]\n"
                        + "  FROM [Car_Dealership].[dbo].[ServiceTicket]";
                stm = cn.prepareStatement(sql);

                table = stm.executeQuery();
                if (table != null) {
                    while (table.next()) {
                        int id = table.getInt("serviceTicketID");
                        Date dateReceived = table.getDate("dateReceived");
                        Date dateReturned = table.getDate("dateReturned");
                        long custID = table.getLong("custID");
                        long carID = table.getLong("carID");

                        ServiceTicket s = new ServiceTicket(id, dateReceived, dateReturned, custID, carID);
                        if (listServiceTickets == null) {
                            listServiceTickets = new ArrayList<>();
                        }
                        listServiceTickets.add(s);
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
    
    public ServiceTicket selectServiceTicket(int id) {
        Connection cn = null;
        PreparedStatement stm = null;
        ResultSet table = null;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "SELECT  [serviceTicketID]\n"
                        + "      ,[dateReceived]\n"
                        + "      ,[dateReturned]\n"
                        + "      ,[custID]\n"
                        + "      ,[carID]\n"
                        + "  FROM [Car_Dealership].[dbo].[ServiceTicket] WHERE serviceTicketID= ? ";
                stm = cn.prepareStatement(sql);
                stm.setInt(1, id);
                table = stm.executeQuery();
                if (table != null) {
                    while (table.next()) {
                        Date dateReceived = table.getDate("dateReceived");
                        Date dateReturned = table.getDate("dateReturned");
                        long custID = table.getLong("custID");
                        long carID = table.getLong("carID");

                        return new ServiceTicket(id, dateReceived, dateReturned, custID, carID);
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
        return null;
    }

    
}
