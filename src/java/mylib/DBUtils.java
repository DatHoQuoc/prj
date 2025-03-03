/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mylib;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author datho
 */
public class DBUtils {
    private static final String DB_NAME="Car_Dealership";
    private static final String USER_NAME="sa";
    private static final String PASSWORD="12345";
    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        Connection conn= null;
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String url="jdbc:sqlserver://localhost:1433;databaseName="+ DB_NAME;
        conn= DriverManager.getConnection(url, USER_NAME, PASSWORD);
        return conn;
    }
    
    public static long generateUniqueId(){
        long timestamp = System.currentTimeMillis();
        int random = (int)(Math.random() * 1000);
        long uniqueId = (timestamp << 10) | random;
        return uniqueId;
    }
}
//Connection cn=null;
//        try {
//            
//        } catch (Exception e) {
//            e.printStackTrace();
//        }finally{
//            try {
//                if(cn!=null) cn.close();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
