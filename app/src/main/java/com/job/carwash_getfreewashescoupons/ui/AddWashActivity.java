package com.job.carwash_getfreewashescoupons.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.job.carwash_getfreewashescoupons.R;
import com.job.carwash_getfreewashescoupons.datasource.CustomerInfo;

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
    public static final String CUSTOMERINFOEXTRA = "CUSTOMERINFOEXTRA";
    public static final String CUSTOMERIDEXTRA = "CUSTOMERIDEXTRA";
    private static final String CUSTOMERINFOCOL = "CustomerInfo";

    private FirebaseAuth mAuth;
    private FirebaseFirestore mFirestore;
    private String clientId;

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

        clientId = getIntent().getStringExtra(CUSTOMERIDEXTRA);
        if (clientId != null){
            setUpUi(clientId);
        }
    }

    @OnClick(R.id.wash_add_btn)
    public void onViewClicked() {
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
                                washName.getEditText().setText(customerInfo.getFirstname()+" "+customerInfo.getLastname());
                                washVehiclereg.getEditText().setText(customerInfo.getVehiclereg());

                                washSpinner.setSelection(spinPosition(customerInfo.getVehicletype()),true);
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
        switch (vehicletype){
            case "Car": return 0;

            case "Canter": return 1;

            case "Motorbike": return 2;

            case "Nissan": return 3;

            case "Pickup": return 4;

            case "Tipper": return 5;

            case "Trailer": return 6;

            default: return 0;
        }
    }
}
