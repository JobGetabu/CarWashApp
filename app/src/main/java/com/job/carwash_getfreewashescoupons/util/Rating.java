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

    public float setRating(double myvisits){
        if (isBetween(myvisits, 1, 2)) {
            return 0.2F * 5;
        } else if (isBetween(visits, 2, 4)) {
            return 0.4F * 5;
        }else if (isBetween(visits, 5, 6)) {
            return 0.5F * 5;
        }else if (isBetween(visits, 7, 10)) {
            return 0.6F * 5;
        }else if (isBetween(visits, 11, 13)) {
            return 0.7F * 5;
        }else if (isBetween(visits, 14, 16)) {
            return 0.8F * 5;
        }else if (isBetween(visits, 17, 100)) {
            return 1.0F * 5;
        }
        return 0;
    }

    public String getPriceMock(String vehicletype){
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

    public String getPrice(String vehicletype){
        switch (vehicletype){
            case "Car": return "KES 150";

            case "Canter": return "KES 300";

            case "Motorbike": return "KES 50";

            case "Nissan": return "KES 150";

            case "Pickup": return "KES 150";

            case "Tipper": return "KES 500";

            case "Trailer": return "KES 800";

            default: return "";
        }
    }

    public String getDay(int day){
        switch (day){
            case 7: return "SAT";

            case 6: return "FRI";

            case 5: return "THUR";

            case 4: return "WED";

            case 3: return "TUE";

            case 2: return "MON";

            case 1: return "SUN";

            default: return "";
        }
    }

    public int getVisits() {
        return visits;
    }

    public void setVisits(int visits) {
        this.visits = visits;
    }
    private boolean isBetween(double x, double lower, double upper) {
        return lower <= x && x <= upper;
    }
}
