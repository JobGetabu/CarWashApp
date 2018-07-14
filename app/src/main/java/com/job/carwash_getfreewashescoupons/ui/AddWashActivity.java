package com.job.carwash_getfreewashescoupons.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Spinner;
import android.widget.TextView;

import com.job.carwash_getfreewashescoupons.R;

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

    public static final String CUSTOMERINFOEXTRA = "CUSTOMERINFOEXTRA";
    public static final String CUSTOMERIDEXTRA = "CUSTOMERIDEXTRA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_wash);
        ButterKnife.bind(this);

        setSupportActionBar(washToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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

    private void saveToDb(){

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
}
