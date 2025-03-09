/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dto.BestSellerModelReportDTO;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import models.Car;
import mylib.DBUtils;

/**
 *
 * @author datho
 */
public class CarDAO implements Serializable {

    private ArrayList<BestSellerModelReportDTO> bestSellerCarsList;
    private ArrayList<Car> listOfCars;
    private ArrayList<Long> listOfIds;

    public ArrayList<BestSellerModelReportDTO> getBestSellerCarsList() {
        return bestSellerCarsList;
    }

    public ArrayList<Car> getListOfCars() {
        return listOfCars;
    }

    public ArrayList<Long> getListOfIds() {
        return listOfIds;
    }

    public void selectBestSellerCars(long saleID) {
        Connection cn = null;
        PreparedStatement stm = null;
        ResultSet table = null;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "SELECT  c.model,count(*) as number_cars\n"
                        + "            FROM Cars C\n"
                        + "            JOIN SalesInvoice SI ON C.carID = SI.carID\n"
                        + "			WHERE EXISTS(SELECT 1\n"
                        + "						FROM SalesPerson SP \n"
                        + "						WHERE SP.salesID = SI.salesID AND SP.salesID = ?\n"
                        + "						)\n"
                        + "            GROUP BY c.model";
                stm = cn.prepareStatement(sql);
                stm.setLong(1, saleID);

                table = stm.executeQuery();
                if (table != null) {
                    while (table.next()) {
                        String model = table.getString("model");
                        int number = table.getInt("number_cars");

                        BestSellerModelReportDTO dto = new BestSellerModelReportDTO(model, number);
                        if (bestSellerCarsList == null) {
                            bestSellerCarsList = new ArrayList<>();
                        }
                        bestSellerCarsList.add(dto);
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

    public void selectListOfCars(long saleID) {
        Connection cn = null;
        PreparedStatement stm = null;
        ResultSet table = null;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "SELECT *\n"
                        + "FROM Cars C\n"
                        + "WHERE EXISTS (SELECT 1\n"
                        + "			FROM SalesInvoice SI \n"
                        + "			WHERE SI.carID = C.carID AND SI.salesID = ? )";
                stm = cn.prepareStatement(sql);
                stm.setLong(1, saleID);

                table = stm.executeQuery();
                if (table != null) {
                    while (table.next()) {
                        long id = table.getLong("carID");
                        String serial = table.getString("serialNumber");
                        String model = table.getString("model");
                        String color = table.getString("colour");
                        int year = table.getInt("year");

                        Car car = new Car(id, serial, model, color, year);
                        if (listOfCars == null) {
                            listOfCars = new ArrayList<>();
                        }
                        listOfCars.add(car);
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

    public void selectListOfCars() {
        Connection cn = null;
        PreparedStatement stm = null;
        ResultSet table = null;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "select *\n"
                        + "from Cars";
                stm = cn.prepareStatement(sql);

                table = stm.executeQuery();
                if (table != null) {
                    while (table.next()) {
                        long id = table.getLong("carID");
                        String serial = table.getString("serialNumber");
                        String model = table.getString("model");
                        String color = table.getString("colour");
                        int year = table.getInt("year");

                        Car car = new Car(id, serial, model, color, year);
                        if (listOfCars == null) {
                            listOfCars = new ArrayList<>();
                        }
                        listOfCars.add(car);
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

    public void selectListOfId() {
        Connection cn = null;
        PreparedStatement stm = null;
        ResultSet table = null;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "SELECT DISTINCT(carID) FROM SalesInvoice";
                stm = cn.prepareStatement(sql);

                table = stm.executeQuery();
                if (table != null) {
                    while (table.next()) {
                        long id = table.getLong("carID");
                        if (listOfIds == null) {
                            listOfIds = new ArrayList<>();
                        }
                        listOfIds.add(id);
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

    public long insertNewCar(String serial, String model, String color, int year) {
        Connection cn = null;
        PreparedStatement stm = null;
        long carId = -1;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "INSERT INTO [Car_Dealership].[dbo].[Cars] ([carID], [serialNumber],[model],[colour],[year])\n"
                        + "VALUES (? , ?, ?, ?, ?)";
                carId = DBUtils.generateUniqueId();
                stm = cn.prepareStatement(sql);

                stm.setLong(1, carId);
                stm.setString(2, serial);
                stm.setString(3, model);
                stm.setString(4, color);
                stm.setInt(5, year);
                int rows = stm.executeUpdate();
                if (rows == 0) {
                    carId = -1;
                    throw new SQLException("Insert failed, no rows affected.");
                }
            }
        } catch (Exception e) {
            carId = -1;
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
        return carId;
    }

    public boolean deleteCar(long id) {
        Connection cn = null;
        PreparedStatement stm = null;
        boolean success = false;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "DELETE FROM Cars \n"
                        + "WHERE carID = ? \n"
                        + "AND NOT EXISTS (\n"
                        + "    SELECT 1 \n"
                        + "    FROM SalesInvoice \n"
                        + "    WHERE SalesInvoice.carID = ?\n"
                        + ")";
                stm = cn.prepareStatement(sql);

                stm.setLong(1, id);
                stm.setLong(2, id);
                int rows = stm.executeUpdate();
                success = (rows > 0);
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
    
    public boolean updateCar(String id, String colour, String year){
        Connection cn = null;
        PreparedStatement stm = null;
        boolean success = false;
        try{
            cn = DBUtils.getConnection();
            String sql = "UPDATE Cars SET colour = ?, year = ? WHERE carID = ?"; 
            
            stm = cn.prepareStatement(sql);
            stm.setString(1, colour);
            stm.setString(2, year);
            stm.setString(3, id);
            
            int rows = stm.executeUpdate();
            if(rows > 0) success = true;
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try{
                if(stm!=null) stm.close();
                if(cn!=null)cn.close();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        return success;
    }
}
