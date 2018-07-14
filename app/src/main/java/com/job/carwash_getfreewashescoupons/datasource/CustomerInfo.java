package com.job.carwash_getfreewashescoupons.datasource;

import com.google.firebase.Timestamp;

/**
 * Created by Job on Saturday : 7/14/2018.
 */
public class CustomerInfo {

    private String customerId;
    private String firstname;
    private String lastname;
    private String phonenumber;
    private String vehiclereg;
    private String vehicletype;
    private Timestamp regdate;

    public CustomerInfo(String customerId, String firstname, String lastname,
                        String phonenumber, String vehiclereg, String vehicletype, Timestamp regdate) {
        this.customerId = customerId;
        this.firstname = firstname;
        this.lastname = lastname;
        this.phonenumber = phonenumber;
        this.vehiclereg = vehiclereg;
        this.vehicletype = vehicletype;
        this.regdate = regdate;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getVehiclereg() {
        return vehiclereg;
    }

    public void setVehiclereg(String vehiclereg) {
        this.vehiclereg = vehiclereg;
    }

    public String getVehicletype() {
        return vehicletype;
    }

    public void setVehicletype(String vehicletype) {
        this.vehicletype = vehicletype;
    }

    public Timestamp getRegdate() {
        return regdate;
    }

    public void setRegdate(Timestamp regdate) {
        this.regdate = regdate;
    }

    @Override
    public String toString() {
        return "CustomerInfo{" +
                "customerId='" + customerId + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", phonenumber='" + phonenumber + '\'' +
                ", vehiclereg='" + vehiclereg + '\'' +
                ", vehicletype='" + vehicletype + '\'' +
                ", regdate=" + regdate +
                '}';
    }
}
