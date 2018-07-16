package com.job.carwash_getfreewashescoupons.ui;

import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Transaction;
import com.job.carwash_getfreewashescoupons.R;
import com.job.carwash_getfreewashescoupons.datasource.CustomerInfo;
import com.job.carwash_getfreewashescoupons.util.CouponLogic;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class AddWashActivity extends AppCompatActivity {

    @BindView(R.id.wash_toolbar)
    Toolbar washToolbar;
    @BindView(R.id.wash_name)
    TextInputLayout washName;
    @BindView(R.id.wash_vehiclereg)
    TextInputLayout washVehiclereg;
    @BindView(R.id.wash_spinner)
    Spinner washSpinner;
    @BindView(R.id.wash_add_btn)
    TextView washAddBtn;

    private static final String TAG = "AddWash";

    public static final String CUSTOMERIDEXTRA = "CUSTOMERIDEXTRA";
    public static final String CUSTOMERNAMEEXTRA = "CUSTOMERNAMEEXTRA";
    public static final String CUSTOMERPHONEEXTRA = "CUSTOMERPHONEEXTRA";

    private static final String CUSTOMERINFOCOL = "CustomerInfo";
    private static final String CUSTOMEREXTRACOL = "CustomerExtra";

    private static final String WASHCOL = "Wash";

    private FirebaseAuth mAuth;
    private FirebaseFirestore mFirestore;

    private String clientId;
    private String clientName;
    private String clientPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_wash);
        ButterKnife.bind(this);

        setSupportActionBar(washToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //firebase
        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();


        clientName = getIntent().getStringExtra(CUSTOMERNAMEEXTRA);
        clientPhone = getIntent().getStringExtra(CUSTOMERPHONEEXTRA);
        clientId = getIntent().getStringExtra(CUSTOMERIDEXTRA);
        if (clientId != null) {
            setUpUi(clientId);
        }
    }

    @OnClick(R.id.wash_add_btn)
    public void onViewClicked() {
        if (validate()) {

            saveToDb();
        }
    }

    private void errorPrompt() {
        new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                .setTitleText("Oops...")
                .setContentText("Something went wrong!")
                .show();
    }

    private void saveToDb() {

        final SweetAlertDialog pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#e61cb2d7"));
        pDialog.setTitleText("Saving Entry...");
        pDialog.setCancelable(false);
        pDialog.show();

        Map<String, Object> washMap = new HashMap<>();
        washMap.put("customerid", clientId);
        washMap.put("timestamp", FieldValue.serverTimestamp());
        washMap.put("paid", true);

        mFirestore.collection(WASHCOL).document()
                .set(washMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {

                            updateWashesCount(pDialog);


                        } else {
                            pDialog.dismiss();
                            Log.d(TAG, "onComplete: error" + task.getException().toString());
                            errorPrompt();
                        }
                    }
                });
    }

    public boolean validate() {
        boolean valid = true;

        String name = washName.getEditText().getText().toString();
        String vehReg = washVehiclereg.getEditText().getText().toString();

        if (name.isEmpty()) {
            washName.setError("enter a name");
            valid = false;
        } else {
            washName.setError(null);
        }

        if (vehReg.isEmpty()) {
            washVehiclereg.setError("enter vehicle registration");
            valid = false;
        } else {
            washVehiclereg.setError(null);
        }
        return valid;
    }

    private void setUpUi(String clientId) {
        final SweetAlertDialog pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#e61cb2d7"));
        pDialog.setTitleText("Loading...");
        pDialog.setCancelable(false);
        pDialog.show();

        mFirestore.collection(CUSTOMERINFOCOL).document(clientId)
                .get()
                .addOnCompleteListener(this, new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> dbtask) {
                        if (dbtask.isSuccessful()) {
                            pDialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                            pDialog.dismissWithAnimation();

                            CustomerInfo customerInfo = dbtask.getResult().toObject(CustomerInfo.class);
                            if (customerInfo != null) {
                                //set up ui
                                washName.getEditText().setText(customerInfo.getFirstname() + " " + customerInfo.getLastname());
                                washVehiclereg.getEditText().setText(customerInfo.getVehiclereg());

                                washSpinner.setSelection(spinPosition(customerInfo.getVehicletype()), true);
                            }


                        } else {
                            pDialog.dismiss();
                            Log.d(TAG, "onComplete: error" + dbtask.getException().toString());
                            errorPrompt();
                        }
                    }
                });
    }

    private int spinPosition(String vehicletype) {
        switch (vehicletype) {
            case "Car":
                return 0;

            case "Canter":
                return 1;

            case "Motorbike":
                return 2;

            case "Nissan":
                return 3;

            case "Pickup":
                return 4;

            case "Tipper":
                return 5;

            case "Trailer":
                return 6;

            default:
                return 0;
        }
    }

    //run an update transaction and send sms here
    private void updateWashesCount(final SweetAlertDialog pDialog) {
        final DocumentReference cusRef = mFirestore.collection(CUSTOMEREXTRACOL).document(clientId);

        mFirestore.runTransaction(new Transaction.Function<Void>() {
            @Override
            public Void apply(Transaction transaction) throws FirebaseFirestoreException {
                DocumentSnapshot snapshot = transaction.get(cusRef);

                double newVisit = snapshot.getDouble("visits") + 1;
                transaction.update(cusRef, "visits", newVisit);

                int s = new Double(newVisit).intValue();

                if (CouponLogic.IsCouponReady(s)) {
                    //send message

                    String message = "Hi " + clientName + " you're our valued customer \n enjoy your next car wash is COMPLETELY FREE";

                    sendSMS(clientPhone, message);

                }

                // Success
                return null;
            }
        }).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "Transaction success!");

                pDialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                pDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();

                        finish();
                    }
                });
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Transaction failure.", e);
                        pDialog.dismiss();
                        errorPrompt();
                    }
                });
    }

    //---sends an SMS message to another device---
    private void sendSMS(String phoneNumber, String message) {
        PendingIntent pi = PendingIntent.getActivity(this, 0,
                new Intent(this, AddWashActivity.class), 0);
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, pi, null);
    }
}
