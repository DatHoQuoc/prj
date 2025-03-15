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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import models.Customer;
import models.ServiceTicket;
import mylib.DBUtils;

/**
 *
 * @author datho
 */
public class CustomerDAO implements Serializable {

    private ArrayList<Customer> custList;

    public ArrayList<Customer> getListCustomers() {
        return custList;
    }

    public void searchCustbyName(String searchCustName, long saleID) {
        Connection cn = null;
        PreparedStatement stm = null;
        ResultSet table = null;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "select *\n"
                        + "from Customer c\n"
                        + "where c.custName like ? and exists (select 1\n"
                        + "			  from SalesInvoice s\n"
                        + "			  where c.custID = s.custID and exists (select 1\n"
                        + "													from SalesPerson sp\n"
                        + "													where sp.salesID = s.salesID and sp.salesID = ?\n"
                        + "													)\n"
                        + "													)";
                stm = cn.prepareStatement(sql);
                stm.setString(1, "%" + searchCustName + "%");
                stm.setLong(2, saleID);

                custList = new ArrayList<>();
                table = stm.executeQuery();
                if (table != null) {
                    while (table.next()) {
                        long custID = table.getLong("custID");
                        String custName = table.getString("custName");
                        long phone = table.getLong("phone");
                        String sex = table.getString("sex");
                        String custAddress = table.getString("cusAddress");

                        Customer customer = new Customer(custID, custName, phone, sex, custAddress);

                        custList.add(customer);
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

    public boolean isExistCustomer(String name, long phone) {
        Connection cn = null;
        PreparedStatement stm = null;
        ResultSet table = null;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "SELECT COUNT(*) AS count FROM Customer WHERE custName = ? AND phone = ?";
                stm = cn.prepareStatement(sql);
                stm.setString(1, name);
                stm.setLong(2, phone);
                table = stm.executeQuery();
                if (table != null && table.next()) {
                    return table.getInt("count") > 0;
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
        return false;
    }

    public long insertCustomer(String name, long phone, String address, String sex) {
        Connection cn = null;
        PreparedStatement stmGetID = null;
        PreparedStatement stmInsert = null;
        long customerID = -1;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                customerID = DBUtils.generateUniqueId();
                String insertSQL = "INSERT INTO [Car_Dealership].[dbo].[Customer] "
                        + "([custID], [custName], [phone], [sex], [cusAddress]) "
                        + "VALUES (?, ?, ?, ?, ?)";
                stmInsert = cn.prepareStatement(insertSQL);
                stmInsert.setLong(1, customerID);
                stmInsert.setString(2, name);
                stmInsert.setLong(3, phone);
                stmInsert.setString(4, sex);
                stmInsert.setString(5, address);
                int rows = stmInsert.executeUpdate();
                if (rows == 0) {
                    customerID = -1;
                    throw new SQLException("Insert failed, no rows affected.");

                }
            }

        } catch (Exception e) {
            customerID = -1;
            e.printStackTrace();
        } finally {
            try {
                if (stmGetID != null) {
                    stmGetID.close();
                }
                if (stmInsert != null) {
                    stmInsert.close();
                }
                if (cn != null) {
                    cn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return customerID;
    }

    public boolean updateCustomer(long id, String name, long phone, String address, String sex) {
        Connection cn = null;
        PreparedStatement stm = null;
        boolean success = false;
        try {
            cn = DBUtils.getConnection();
            String sql = "UPDATE [Car_Dealership].[dbo].[Customer] \n"
                    + "            SET [custName] = ?, [phone] = ?, [sex] = ?, [cusAddress] = ?\n"
                    + "           WHERE [custID] = ?";

            stm = cn.prepareStatement(sql);
            stm.setString(1, name);
            stm.setLong(2, phone);
            stm.setString(3, sex);
            stm.setString(4, address);
            stm.setLong(5, id);

            int rows = stm.executeUpdate();
            if (rows > 0) {
                success = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
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
        return success;
    }

    public Long selectCustomerID(String name, long phone) {
        Connection cn = null;
        PreparedStatement stm = null;
        ResultSet table = null;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "SELECT custID  FROM Customer WHERE custName = ? AND phone = ? ";
                stm = cn.prepareStatement(sql);
                stm.setString(1, name);
                stm.setLong(2, phone);

                table = stm.executeQuery();
                if (table != null && table.next()) {
                    return table.getLong("custID");
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

    public Customer selectCustomer(long id) {
        Connection cn = null;
        PreparedStatement stm = null;
        ResultSet table = null;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "SELECT [custID]\n"
                        + "      ,[custName]\n"
                        + "      ,[phone]\n"
                        + "      ,[sex]\n"
                        + "      ,[cusAddress]\n"
                        + "  FROM [Car_Dealership].[dbo].[Customer]\n"
                        + "  WHERE [custID] = ? ";
                stm = cn.prepareStatement(sql);
                stm.setLong(1, id);
                table = stm.executeQuery();
                if (table != null) {
                    while (table.next()) {
                        String custName = table.getString("custName");
                        long phone = table.getLong("phone");
                        String sex = table.getString("sex");
                        String custAddress = table.getString("cusAddress");

                        return new Customer(id, custName, phone, sex, custAddress);
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
