/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

/**
 *
 * @author datho
 */
public class CustomerDTO {
    private String custID = "";
    private String custName ="";
    private String phone = "";
    private String sex ="";
    private String cusAddress ="";

    public CustomerDTO() {
        
    }
    public CustomerDTO(String custID, String custName, String phone, String sex, String cusAddress) {
        this.custID = custID;
        this.custName = custName;
        this.phone = phone;
        this.sex = sex;
        this.cusAddress = cusAddress;
    }
    public String getCustID() {
        return custID;
    }

    public void setCustID(String custID) {
        this.custID = custID;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getCusAddress() {
        return cusAddress;
    }

    public void setCusAddress(String cusAddress) {
        this.cusAddress = cusAddress;
    }
    
    
    
}
