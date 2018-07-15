package com.job.carwash_getfreewashescoupons.util;

/**
 * Created by Job on Monday : 7/16/2018.
 */
public class CouponLogic {

    public static boolean IsCouponReady(int visits) {
        //sequence rule 7n or 6
        // n = visits

        if(visits == 6){
            return true;
        }

        for (int i = 1; i < Integer.MAX_VALUE; i++) {
            int res = 7 * i;
            if (visits == (res-1)) {
                return true;
            }

            if (i > visits){
                return false;
            }
        }

        return false;
    }

    public static boolean IsAcouponWashVisit(int visits){

        //sequence rule 7n or 6
        // n = visits

        if(visits == 6){
            return true;
        }

        for (int i = 1; i < Integer.MAX_VALUE; i++) {
            int res = 7 * i;
            if (visits == (res)) {
                return true;
            }

            if (i > visits){
                return false;
            }
        }

        return false;
    }
}
