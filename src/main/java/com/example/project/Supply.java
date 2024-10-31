package com.example.project;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;

public class Supply {
    private final IntegerProperty supplyid;
    private final StringProperty supplyname;
    private final IntegerProperty quantity;
    private final IntegerProperty total_cost;
    private final IntegerProperty employeeid;

    public Supply(int supplyid, String supplyname, int quantity, int total_cost, int employeeid) {
        this.supplyid = new SimpleIntegerProperty(supplyid);
        this.supplyname = new SimpleStringProperty(supplyname);
        this.quantity = new SimpleIntegerProperty(quantity);
        this.total_cost = new SimpleIntegerProperty(total_cost);
        this.employeeid = new SimpleIntegerProperty(employeeid);
    }

    public int getsupplyid() {
        return supplyid.get();
    }

    public String getSupplyname() {
        return supplyname.get();
    }

    public int getQuantity() {
        return quantity.get();
    }

    public int getTotal_cost() {
        return total_cost.get();
    }

    public int getEmployeeid() {
        return employeeid.get();
    }

    // Optional: Add property getters for binding if needed
    public IntegerProperty supplyidProperty() {
        return supplyid;
    }

    public StringProperty supplynameProperty() {
        return supplyname;
    }

    public IntegerProperty quantityProperty() {
        return quantity;
    }

    public IntegerProperty total_costProperty() {
        return total_cost;
    }

    public IntegerProperty employeeidProperty() {
        return employeeid;
    }
}
