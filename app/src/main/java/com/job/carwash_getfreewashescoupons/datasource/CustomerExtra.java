package com.job.carwash_getfreewashescoupons.datasource;

/**
 * Created by Job on Saturday : 7/14/2018.
 */
public class CustomerExtra {
    private String customerId;
    private String ownerid;
    private String name;
    private int visits;
    private int coupons;
    private double rating;

    public CustomerExtra() {
    }

    public CustomerExtra(String customerId, String ownerid, String name, int visits, int coupons, double rating) {
        this.customerId = customerId;
        this.ownerid = ownerid;
        this.name = name;
        this.visits = visits;
        this.coupons = coupons;
        this.rating = rating;
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

    public int getVisits() {
        return visits;
    }

    public void setVisits(int visits) {
        this.visits = visits;
    }

    public int getCoupons() {
        return coupons;
    }

    public void setCoupons(int coupons) {
        this.coupons = coupons;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
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
                ", rating=" + rating +
                '}';
    }
}
