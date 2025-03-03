/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dto.PartReportDTO;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import mylib.DBUtils;

/**
 *
 * @author datho
 */
public class PartUsedDAO implements Serializable {

    private ArrayList<PartReportDTO> bestListUsedParts;

    public ArrayList<PartReportDTO> getListBestUsedParts() {
        return bestListUsedParts;
    }

    public void findBestUsedParts(long saleID) {
        Connection cn = null;
        PreparedStatement stm = null;
        ResultSet table = null;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "SELECT TOP 3 \n"
                        + "    p.partName,\n"
                        + "    pu.partID,\n"
                        + "    COUNT(pu.partID) as used_parts\n"
                        + "FROM PartsUsed pu\n"
                        + "INNER JOIN Parts p ON pu.partID = p.partID\n"
                        + "WHERE EXISTS (\n"
                        + "    SELECT 1\n"
                        + "    FROM ServiceTicket st \n"
                        + "    WHERE st.serviceTicketID = pu.serviceTicketID \n"
                        + "    AND EXISTS (\n"
                        + "        SELECT 1\n"
                        + "        FROM Customer c \n"
                        + "        WHERE c.custID = st.custID \n"
                        + "        AND EXISTS (\n"
                        + "            SELECT 1\n"
                        + "            FROM SalesInvoice si\n"
                        + "            WHERE c.custID = si.custID and si.salesID = ?\n"
                        + "        )\n"
                        + "    )\n"
                        + ")\n"
                        + "GROUP BY pu.partID, p.partName\n"
                        + "ORDER BY used_parts DESC";
                stm = cn.prepareStatement(sql);
                stm.setLong(1, saleID);
                table = stm.executeQuery();

                if (table != null) {
                    while (table.next()) {
                        String name = table.getString("partName");
                        long id = table.getLong("partID");
                        int number = table.getInt("used_parts");

                        PartReportDTO dto = new PartReportDTO(id, name, number);
                        if (bestListUsedParts == null) {
                            bestListUsedParts = new ArrayList<>();
                        }
                        bestListUsedParts.add(dto);
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
