package com.job.carwash_getfreewashescoupons.datasource;


import com.google.firebase.Timestamp;

/**
 * Created by Job on Saturday : 7/14/2018.
 */
public class Wash {
    private Timestamp timestamp;
    private String customerId;
    private String paid;

    public Wash(Timestamp timestamp, String customerId, String paid) {
        this.timestamp = timestamp;
        this.customerId = customerId;
        this.paid = paid;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getPaid() {
        return paid;
    }

    public void setPaid(String paid) {
        this.paid = paid;
    }

    @Override
    public String toString() {
        return "Wash{" +
                "timestamp=" + timestamp +
                ", customerId='" + customerId + '\'' +
                ", paid='" + paid + '\'' +
                '}';
    }
}