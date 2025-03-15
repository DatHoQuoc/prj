/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dto.ServiceDTO;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import models.Service;
import mylib.DBUtils;

/**
 *
 * @author datho
 */
public class ServiceDAO implements Serializable {

    private ArrayList<ServiceDTO> listOfService;
    private ArrayList<Service> allServices;

    public ArrayList<ServiceDTO> getListOfService() {
        return listOfService;
    }

    public ArrayList<Service> getAllServices() {
        return allServices;
    }
    

    public void SelectServiceByServiceTicketID(int id) {
        Connection cn = null;
        PreparedStatement stm = null;
        ResultSet table = null;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "SELECT S.serviceID, M.mechanicName,SM.hours,SM.comment,S.serviceName,SM.rate,SM.hours*SM.rate AS [total]\n"
                        + "                         FROM ServiceMehanic SM\n"
                        + "                         JOIN Service S ON SM.serviceID = S.serviceID\n"
                        + "						 JOIN Mechanic M ON SM.mechanicID = M.mechanicID\n"
                        + "                         WHERE SM.serviceTicketID = ?";
                stm = cn.prepareStatement(sql);
                stm.setInt(1, id);
                table = stm.executeQuery();
                if (table != null) {
                    while (table.next()) {
                        int serviceId = table.getInt("serviceID");
                        String mechanicName = table.getString("mechanicName");
                        int hours = table.getInt("hours");
                        String comment = table.getString("comment");
                        String serviceName = table.getString("serviceName");
                        double rate = table.getDouble("rate");
                        double total = table.getDouble("total");

                        ServiceDTO s = new ServiceDTO(serviceId, mechanicName, serviceName, hours, rate, comment, total);
                        if (listOfService == null) {
                            listOfService = new ArrayList<>();
                        }
                        listOfService.add(s);
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

    public void SelectAllService() {
        Connection cn = null;
        PreparedStatement stm = null;
        ResultSet table = null;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "SELECT [serviceID]\n"
                        + "      ,[serviceName]\n"
                        + "      ,[hourlyRate]\n"
                        + "  FROM [Car_Dealership].[dbo].[Service]";
                stm = cn.prepareStatement(sql);
                table = stm.executeQuery();
                if (table != null) {
                    while (table.next()) {
                        int serviceId = table.getInt("serviceID");
                        String serviceName = table.getString("serviceName");
                        double rate = table.getDouble("hourlyRate");

                        Service service = new Service(serviceId, serviceName, rate);
                        if (allServices == null) {
                            allServices = new ArrayList<>();
                        }
                        allServices.add(service);
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
