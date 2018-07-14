package com.job.carwash_getfreewashescoupons.ui;

import android.content.Intent;
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
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.job.carwash_getfreewashescoupons.R;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import io.michaelrocks.libphonenumber.android.NumberParseException;
import io.michaelrocks.libphonenumber.android.PhoneNumberUtil;
import io.michaelrocks.libphonenumber.android.Phonenumber;

public class NewClientActivity extends AppCompatActivity {

    @BindView(R.id.client_toolbar)
    Toolbar clientToolbar;
    @BindView(R.id.client_firstname)
    TextInputLayout clientFirstname;
    @BindView(R.id.client_lastname)
    TextInputLayout clientLastname;
    @BindView(R.id.client_phone)
    TextInputLayout clientPhone;
    @BindView(R.id.client_vehiclereg)
    TextInputLayout clientVehiclereg;
    @BindView(R.id.client_spinner)
    Spinner clientSpinner;
    @BindView(R.id.client_add_btn)
    TextView clientAddBtn;

    private static final String TAG = "NewClient";
    private static final String CUSTOMERINFOCOL = "CustomerInfo";
    private PhoneNumberUtil mPhoneNumberUtil;
    private FirebaseAuth mAuth;
    private FirebaseFirestore mFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_client);
        ButterKnife.bind(this);

        setSupportActionBar(clientToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //firebase
        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();

        mPhoneNumberUtil = PhoneNumberUtil.createInstance(this);

    }

    @OnClick(R.id.client_add_btn)
    public void onViewClicked() {

        if (validate()){
            saveToDb();
        }
    }

    private void saveToDb(){
        final SweetAlertDialog pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#e61cb2d7"));
        pDialog.setTitleText("Saving Changes...");
        pDialog.setCancelable(false);
        pDialog.show();

        String firstname = clientFirstname.getEditText().getText().toString();
        String lname = clientLastname.getEditText().getText().toString();
        String phone = clientPhone.getEditText().getText().toString();
        String vehReg = clientVehiclereg.getEditText().getText().toString();
        String vehType = clientSpinner.getSelectedItem().toString();

        String key = mFirestore.collection(CUSTOMERINFOCOL).document().getId();

        Map<String,Object> clientMap= new HashMap<>();
        clientMap.put("customerId",key);
        clientMap.put("firstname",firstname);
        clientMap.put("lastname",lname);
        clientMap.put("phonenumber",phone);
        clientMap.put("vehiclereg",vehReg);
        clientMap.put("vehicletype",vehType);
        clientMap.put("ownerid",mAuth.getCurrentUser().getUid());
        clientMap.put("regdate", FieldValue.serverTimestamp());

        mFirestore.collection(CUSTOMERINFOCOL).document(key)
                .set(clientMap)
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> dbtask) {
                        if (dbtask.isSuccessful()){
                            pDialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                            pDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();
                                    startActivity(new Intent(NewClientActivity.this,MainActivity.class));
                                }
                            });

                            finish();
                        }else {
                            pDialog.dismiss();
                            Log.d(TAG, "onComplete: error" + dbtask.getException().toString());
                            errorPrompt();
                        }
                    }
                });
    }

    private void errorPrompt() {
        new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                .setTitleText("Oops...")
                .setContentText("Something went wrong!")
                .show();
    }

    public boolean validate() {
        boolean valid = true;

        String firstname = clientFirstname.getEditText().getText().toString();
        String lname = clientLastname.getEditText().getText().toString();
        String phone = clientPhone.getEditText().getText().toString();
        String vehReg = clientVehiclereg.getEditText().getText().toString();

        if (firstname.isEmpty()) {
            clientFirstname.setError("enter a name");
            valid = false;
        } else {
            clientFirstname.setError(null);
        }
        if (lname.isEmpty()) {
            clientLastname.setError("enter a name");
            valid = false;
        } else {
            clientLastname.setError(null);
        }
        if (vehReg.isEmpty()) {
            clientVehiclereg.setError("enter vehicle registration");
            valid = false;
        } else {
            clientVehiclereg.setError(null);
        }

        Phonenumber.PhoneNumber kenyaNumberProto = null;
        try {
            kenyaNumberProto = mPhoneNumberUtil.parse(phone, "KE");
        } catch (NumberParseException e) {
            Log.e(TAG, "validate: NumberParseException was thrown: ",e);
        }

        if (phone.isEmpty() || !mPhoneNumberUtil.isValidNumber(kenyaNumberProto)) {
            clientPhone.setError("enter a valid phone number");
            valid = false;
        } else {
            clientPhone.setError(null);
        }

        return valid;
    }
}
