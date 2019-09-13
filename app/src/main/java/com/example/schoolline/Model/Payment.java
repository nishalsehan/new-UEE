package com.example.schoolline.Model;

import java.util.Date;

/**
 * Created by Nethu Yahampath on 2019-08-31.
 */

public class Payment {

    String studentNo;
    float amount;
    String invoiceNo;
    Date date;

    public Payment(){

    }

    public Payment(String studentNo, float amount, String invoiceNo, Date date) {
        this.studentNo = studentNo;
        this.amount = amount;
        this.invoiceNo = invoiceNo;
        this.date = date;
    }

    public String getStudentNo() {
        return studentNo;
    }

    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
