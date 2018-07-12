package com.job.carwash_getfreewashescoupons;

import android.support.multidex.MultiDexApplication;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

/**
 * Created by Job on Friday : 7/13/2018.
 */
public class CarWashApp extends MultiDexApplication {

    FirebaseAuth mAuth;
    FirebaseFirestore mFirestore;

    @Override
    public void onCreate() {
        super.onCreate();

        //firebase init
        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();

        if (mAuth != null){
            FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                    .setTimestampsInSnapshotsEnabled(true)
                    .build();
            mFirestore.setFirestoreSettings(settings);
        }
    }
}
