package com.job.carwash_getfreewashescoupons.ui;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Spinner;
import android.widget.TextView;

import com.job.carwash_getfreewashescoupons.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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
    private PhoneNumberUtil mPhoneNumberUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_client);
        ButterKnife.bind(this);

        setSupportActionBar(clientToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mPhoneNumberUtil = PhoneNumberUtil.createInstance(this);
    }

    @OnClick(R.id.client_add_btn)
    public void onViewClicked() {

        if (validate()){
            String firstname = clientFirstname.getEditText().getText().toString();
            String lname = clientLastname.getEditText().getText().toString();
            String phone = clientPhone.getEditText().getText().toString();
            String vehReg = clientVehiclereg.getEditText().getText().toString();
            String vehType = clientSpinner.getSelectedItem().toString();


        }
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
