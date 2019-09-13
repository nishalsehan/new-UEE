package com.example.schoolline.Model;

import java.util.Date;

/**
 * Created by Nethu Yahampath on 2019-08-31.
 */

public class Expense {

    String expenseName;
    String description;
    float amount;
    Date date;

    public String getExpenseName() {
        return expenseName;
    }

    public void setExpenseName(String expenseName) {
        this.expenseName = expenseName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
