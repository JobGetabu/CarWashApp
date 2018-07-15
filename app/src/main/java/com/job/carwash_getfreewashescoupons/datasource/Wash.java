package com.job.carwash_getfreewashescoupons.datasource;


import com.google.firebase.Timestamp;

/**
 * Created by Job on Saturday : 7/14/2018.
 */
public class Wash {
    private Timestamp timestamp;
    private String customerid;
    private Boolean paid;

    public Wash(Timestamp timestamp, String customerid, Boolean paid) {
        this.timestamp = timestamp;
        this.customerid = customerid;
        this.paid = paid;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getCustomerid() {
        return customerid;
    }

    public void setCustomerid(String customerid) {
        this.customerid = customerid;
    }

    public Boolean getPaid() {
        return paid;
    }

    public void setPaid(Boolean paid) {
        this.paid = paid;
    }

    @Override
    public String toString() {
        return "Wash{" +
                "timestamp=" + timestamp +
                ", customerid='" + customerid + '\'' +
                ", paid='" + paid + '\'' +
                '}';
    }
}
