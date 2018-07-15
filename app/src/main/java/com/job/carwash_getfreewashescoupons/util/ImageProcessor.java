package com.job.carwash_getfreewashescoupons.util;

import android.content.Context;

/**
 * Created by Job on Thursday : 5/3/2018.
 */
public class ImageProcessor {

    private Context context;

    public ImageProcessor(Context context) {
        this.context = context;
    }

    //set images to ImageView
    /*
    public void setMyImage(final ImageView imageView, final String url) {
        if (url.isEmpty()){
            imageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.avatar_placeholder));
        }else {
            Picasso
                    .get()
                    .load(url)
                    .placeholder(R.drawable.avatar_placeholder)
                    .error(R.drawable.avatar_placeholder)
                    .into(imageView, new Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError(Exception e) {
                            //no cache download new image
                            Picasso
                                    .get()
                                    .load(url)
                                    .placeholder(R.drawable.avatar_placeholder)
                                    .error(R.drawable.avatar_placeholder)
                                    .into(imageView);
                        }
                    });
        }
    */
}
