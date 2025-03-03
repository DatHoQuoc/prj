/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.sql.Date;

/**
 *
 * @author datho
 */
public class SalePerson {
    private long saleID;
    private String saleName ="";
    private Date birthday;
    private String sex="";
    private String saleAddress ="";

    public SalePerson() {
        
    }

    public SalePerson(long saleID, String saleName, Date birthday, String sex, String saleAddress) {
        this.saleID = saleID;
        this.saleName = saleName;
        this.birthday = birthday;
        this.sex = sex;
        this.saleAddress = saleAddress;
    }

    public long getSaleID() {
        return saleID;
    }

    public void setSaleID(long saleID) {
        this.saleID = saleID;
    }

    public String getSaleName() {
        return saleName;
    }

    public void setSaleName(String saleName) {
        this.saleName = saleName;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSaleAddress() {
        return saleAddress;
    }

    public void setSaleAddress(String saleAddress) {
        this.saleAddress = saleAddress;
    }
    
    
}
