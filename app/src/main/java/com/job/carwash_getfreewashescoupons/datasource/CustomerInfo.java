package com.job.carwash_getfreewashescoupons.datasource;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.Timestamp;

/**
 * Created by Job on Saturday : 7/14/2018.
 */
public class CustomerInfo implements Parcelable {

    private String customerId;
    private String firstname;
    private String lastname;
    private String phonenumber;
    private String vehiclereg;
    private String vehicletype;
    private String ownerid;
    private Timestamp regdate;

    public CustomerInfo() {
    }

    public CustomerInfo(String customerId, String firstname, String lastname,
                        String phonenumber, String vehiclereg, String vehicletype, String ownerid, Timestamp regdate) {
        this.customerId = customerId;
        this.firstname = firstname;
        this.lastname = lastname;
        this.phonenumber = phonenumber;
        this.vehiclereg = vehiclereg;
        this.vehicletype = vehicletype;
        this.ownerid = ownerid;
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

    public String getOwnerid() {
        return ownerid;
    }

    public void setOwnerid(String ownerid) {
        this.ownerid = ownerid;
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

    //does not parcel timestamp

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.customerId);
        dest.writeString(this.firstname);
        dest.writeString(this.lastname);
        dest.writeString(this.phonenumber);
        dest.writeString(this.vehiclereg);
        dest.writeString(this.vehicletype);
        dest.writeString(this.ownerid);

    }

    protected CustomerInfo(Parcel in) {
        this.customerId = in.readString();
        this.firstname = in.readString();
        this.lastname = in.readString();
        this.phonenumber = in.readString();
        this.vehiclereg = in.readString();
        this.vehicletype = in.readString();
        this.ownerid = in.readString();
    }

    public static final Creator<CustomerInfo> CREATOR = new Creator<CustomerInfo>() {
        @Override
        public CustomerInfo createFromParcel(Parcel source) {
            return new CustomerInfo(source);
        }

        @Override
        public CustomerInfo[] newArray(int size) {
            return new CustomerInfo[size];
        }
    };
}
