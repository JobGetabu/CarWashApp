package com.job.carwash_getfreewashescoupons.util;

import android.text.TextUtils;

import com.google.firebase.firestore.Query;

/**
 * Created by Job on Monday : 7/16/2018.
 */
public class Filter {
    private String vehicle = null;
    private Query.Direction dateDirection = null;

    public Filter() {
    }

    public static Filter getDefault() {
        Filter filters = new Filter();
        filters.setVehicle("ALL Vehicles");
        filters.setDateDirection(Query.Direction.DESCENDING);

        return filters;
    }

    public boolean hasVehicle() {
        return !(TextUtils.isEmpty(vehicle));
    }


    public String getVehicle() {
        return vehicle;
    }


    public Query.Direction getDateDirection() {
        return dateDirection;
    }

    public void setDateDirection(Query.Direction dateDirection) {
        this.dateDirection = dateDirection;
    }

    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }

}
