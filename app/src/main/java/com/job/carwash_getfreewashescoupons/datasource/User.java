package com.job.carwash_getfreewashescoupons.datasource;

import android.support.annotation.Keep;

/**
 * Created by Job on Saturday : 7/14/2018.
 */
@Keep
public class User {

    private String username;
    private String devicetoken;

    public User(String username, String devicetoken) {
        this.username = username;
        this.devicetoken = devicetoken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDevicetoken() {
        return devicetoken;
    }

    public void setDevicetoken(String devicetoken) {
        this.devicetoken = devicetoken;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", devicetoken='" + devicetoken + '\'' +
                '}';
    }
}
