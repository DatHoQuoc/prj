/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dto.BestMechanicDTO;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.naming.NamingException;
import models.Mechanic;
import mylib.DBUtils;

/**
 *
 * @author datho
 */
public class MechanicDAO implements Serializable {

    private ArrayList<BestMechanicDTO> listOfTop3Mechanic;

    public ArrayList<BestMechanicDTO> getListTop3OfMechanic() {
        return listOfTop3Mechanic;
    }

    public Mechanic checkMechanicLogin(String mechanicName) {
        Mechanic rs = null;
        Connection cn = null;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "select mechanicID,mechanicName\n"
                        + "from dbo.Mechanic\n"
                        + "where mechanicName=?";
                PreparedStatement st = cn.prepareStatement(sql);
                st.setString(1, mechanicName);

                ResultSet table = st.executeQuery();
                if (table != null) {
                    while (table.next()) {
                        long mechenicid = table.getLong("mechanicID");
                        String mechanicname = table.getString("mechanicName");
                        rs = new Mechanic(mechenicid, mechanicname);

                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (cn != null) {
                    cn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return rs;
    }

    public void selectTop3Mechanic(long saleID) {
        Connection cn = null;
        PreparedStatement stm = null;
        ResultSet table = null;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "WITH NEWTABLE AS(SELECT SM.mechanicID,SUM(SM.hours) AS TOTAL_HOURS,SUM(SM.hours * SM.rate) AS REVENUE,COUNT(*) AS TOTAL_REPAIR\n"
                        + "				FROM ServiceMehanic SM\n"
                        + "				GROUP BY SM.mechanicID\n"
                        + "				)\n"
                        + "SELECT TOP 3  M.mechanicID,M.mechanicName,N.TOTAL_HOURS,N.REVENUE,N.TOTAL_REPAIR\n"
                        + "FROM Mechanic M\n"
                        + "JOIN NEWTABLE N ON M.mechanicID = N.mechanicID\n"
                        + "WHERE EXISTS(SELECT 1\n"
                        + "			FROM ServiceMehanic SM \n"
                        + "			WHERE SM.mechanicID = M.mechanicID AND\n"
                        + "			EXISTS(SELECT 1\n"
                        + "			FROM ServiceTicket ST\n"
                        + "			WHERE SM.serviceTicketID = ST.serviceTicketID AND\n"
                        + "			EXISTS(SELECT 1\n"
                        + "			FROM Customer C \n"
                        + "			WHERE C.custID = ST.custID AND\n"
                        + "			EXISTS(SELECT 1\n"
                        + "			FROM SalesInvoice SI\n"
                        + "			WHERE SI.custID = C.custID AND SI.salesID = ?\n"
                        + "			)\n"
                        + "			)\n"
                        + "			)\n"
                        + "			)\n"
                        + "ORDER BY N.TOTAL_HOURS DESC";
                stm = cn.prepareStatement(sql);
                stm.setLong(1, saleID);
                
                table = stm.executeQuery();
                if(table!=null){
                    while(table.next()){
                        long mechanicID = table.getLong("mechanicID");
                        String name = table.getString("mechanicName");
                        int hours = table.getInt("TOTAL_HOURS");
                        double revenue = table.getLong("REVENUE");
                        int totalRepair = table.getInt("TOTAL_REPAIR");
                        
                        BestMechanicDTO dto = new BestMechanicDTO(mechanicID, name, hours, revenue, totalRepair);
                        if(listOfTop3Mechanic==null){
                            listOfTop3Mechanic = new ArrayList<>();
                        }
                        listOfTop3Mechanic.add(dto);
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
