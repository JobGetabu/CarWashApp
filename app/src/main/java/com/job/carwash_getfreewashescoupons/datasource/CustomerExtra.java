package com.job.carwash_getfreewashescoupons.datasource;

import android.support.annotation.Keep;

/**
 * Created by Job on Saturday : 7/14/2018.
 */

@Keep
public class CustomerExtra {
    private String customerId;
    private String ownerid;
    private String name;
    private double visits;
    private double coupons;


    public CustomerExtra() {
    }

    public CustomerExtra(String customerId, String ownerid, String name, int visits, int coupons) {
        this.customerId = customerId;
        this.ownerid = ownerid;
        this.name = name;
        this.visits = visits;
        this.coupons = coupons;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getVisits() {
       return visits;
    }

    public void setVisits(int visits) {
        this.visits = visits;
    }

    public double getCoupons() {
        return coupons;
    }

    public void setCoupons(int coupons) {
        this.coupons = coupons;
    }


    public String getOwnerid() {
        return ownerid;
    }

    public void setOwnerid(String ownerid) {
        this.ownerid = ownerid;
    }

    @Override
    public String toString() {
        return "CustomerExtra{" +
                "customerId='" + customerId + '\'' +
                ", name='" + name + '\'' +
                ", visits=" + visits +
                ", coupons=" + coupons +
                '}';
    }
}
