/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.sql.Date;

/**
 *
 * @author datho
 */
public class SaleInvoice {
    private int invoiceID;
    private Date invoiceDate;
    private long saleID;
    private long carID;
    private long custID;

    public SaleInvoice() {
    }

    public SaleInvoice(int invoiceID, Date invoiceDate, long saleID, long carID, long custID) {
        this.invoiceID = invoiceID;
        this.invoiceDate = invoiceDate;
        this.saleID = saleID;
        this.carID = carID;
        this.custID = custID;
    }

    public int getInvoiceID() {
        return invoiceID;
    }

    public void setInvoiceID(int invoiceID) {
        this.invoiceID = invoiceID;
    }

    public Date getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public long getSaleID() {
        return saleID;
    }

    public void setSaleID(long saleID) {
        this.saleID = saleID;
    }

    public long getCarID() {
        return carID;
    }

    public void setCarID(long carID) {
        this.carID = carID;
    }

    public long getCustID() {
        return custID;
    }

    public void setCustID(long custID) {
        this.custID = custID;
    }
    
    
}
