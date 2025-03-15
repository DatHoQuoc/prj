/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dto.PartDTO;
import dto.ServiceDTO;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import models.Part;
import mylib.DBUtils;

/**
 *
 * @author datho
 */
public class PartDAO implements Serializable {

    private ArrayList<PartDTO> listOfParts;
    private ArrayList<Part> listParts;

    public ArrayList<Part> getListParts() {
        return listParts;
    }

    public ArrayList<PartDTO> getListOfPart() {
        return listOfParts;
    }

    public void SelectPartyServiceTicketID(int id) {
        Connection cn = null;
        PreparedStatement stm = null;
        ResultSet table = null;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "SELECT P.partName, PU.numberUsed,PU.price,PU.price*PU.numberUsed AS[total]\n"
                        + "FROM PartsUsed PU\n"
                        + "JOIN Parts P ON PU.partID = P.partID\n"
                        + "WHERE PU.serviceTicketID = ?";
                stm = cn.prepareStatement(sql);
                stm.setInt(1, id);
                table = stm.executeQuery();
                if (table != null) {
                    while (table.next()) {
                        String name = table.getString("partName");
                        int number = table.getInt("numberUsed");
                        double price = table.getDouble("price");
                        double total = table.getDouble("total");

                        PartDTO dto = new PartDTO(name, price, number, total);
                        if (listOfParts == null) {
                            listOfParts = new ArrayList<>();
                        }
                        listOfParts.add(dto);
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

    public void SelectAllPart() {
        Connection cn = null;
        PreparedStatement stm = null;
        ResultSet table = null;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "SELECT  [partID]\n"
                        + "      ,[partName]\n"
                        + "      ,[purchasePrice]\n"
                        + "      ,[retailPrice]\n"
                        + "  FROM [Car_Dealership].[dbo].[Parts]";
                stm = cn.prepareStatement(sql);
                table = stm.executeQuery();
                if (table != null) {
                    while (table.next()) {
                        long id = table.getLong("partID");
                        String name = table.getString("partName");
                        double price = table.getDouble("purchasePrice");
                        double retailPrice = table.getDouble("retailPrice");

                        Part p = new Part(id, name, price, retailPrice);
                        if (listParts == null) {
                            listParts = new ArrayList<>();
                        }
                        listParts.add(p);
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
