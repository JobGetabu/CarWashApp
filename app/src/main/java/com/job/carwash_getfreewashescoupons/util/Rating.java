package com.job.carwash_getfreewashescoupons.util;

/**
 * Created by Job on Saturday : 7/14/2018.
 */
public class Rating {
    //rate regular customers
    private int visits;

    public Rating(int visits) {
        this.visits = visits;
    }

    public Rating() {
    }

    public double getRating(){
        if (isBetween(visits, 1, 2)) {
            return 0.2;
        } else if (isBetween(visits, 2, 4)) {
            return 0.4;
        }else if (isBetween(visits, 5, 6)) {
            return 0.5;
        }else if (isBetween(visits, 7, 10)) {
            return 0.6;
        }else if (isBetween(visits, 11, 13)) {
            return 0.7;
        }else if (isBetween(visits, 14, 16)) {
            return 0.8;
        }else if (isBetween(visits, 17, 100)) {
            return 1.0;
        }
        return 0;
    }

    public String getPrice(String vehicletype){
        switch (vehicletype){
            case "Car": return "$$";

            case "Canter": return "$$$";

            case "Motorbike": return "$";

            case "Nissan": return "$$";

            case "Pickup": return "$$";

            case "Tipper": return "$$$";

            case "Trailer": return "$$$";

            default: return "";
        }
    }

    public int getVisits() {
        return visits;
    }

    public void setVisits(int visits) {
        this.visits = visits;
    }
    private boolean isBetween(int x, int lower, int upper) {
        return lower <= x && x <= upper;
    }
}
