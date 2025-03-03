/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import models.SalePerson;
import mylib.DBUtils;

/**
 *
 * @author datho
 */
public class SalePersonDAO implements Serializable {
    
    public SalePerson checkSalePersonLogin(String staffName){
        SalePerson result = null;
        Connection cn=null;
        try{
          cn=DBUtils.getConnection();
          if(cn!=null){
              String sql = "SELECT *\n"
                      + "FROM SalesPerson\n"
                      + "WHERE salesName = ?";
              PreparedStatement st=cn.prepareStatement(sql);
              st.setString(1, staffName);
              
              ResultSet table=st.executeQuery();
                if(table!=null){
                  while(table.next()){
                      long saleID =table.getLong("salesID");
                      String saleName= table.getString("salesName");
                      Date birthday = table.getDate("birthday");
                      String sex = table.getString("sex");
                      String saleAddress = table.getString("salesAddress");
                      result = new SalePerson(saleID, saleName, birthday, sex, saleAddress);   
                  }
              }
          }
        
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try {
                if(cn!=null) cn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
