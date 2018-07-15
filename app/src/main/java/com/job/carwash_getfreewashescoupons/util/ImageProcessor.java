package com.job.carwash_getfreewashescoupons.util;

import android.content.Context;
import android.widget.ImageView;

/**
 * Created by Job on Thursday : 5/3/2018.
 */
public class ImageProcessor {

    private Context context;

    public ImageProcessor(Context context) {
        this.context = context;
    }

    //set images to ImageView

    public void setMyImage(ImageView imageView, final String vehicletype) {
        switch (vehicletype) {
            case "Car":
                break;

            case "Canter":
                break;

            case "Motorbike":
                break;

            case "Nissan":
                break;

            case "Pickup":
                break;

            case "Tipper":
                break;

            case "Trailer":
                break;

            default:
                break;
        }
    }
}
