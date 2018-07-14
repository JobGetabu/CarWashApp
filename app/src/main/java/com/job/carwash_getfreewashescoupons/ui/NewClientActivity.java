package com.job.carwash_getfreewashescoupons.ui;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_client);
        ButterKnife.bind(this);

        setSupportActionBar(clientToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @OnClick(R.id.client_add_btn)
    public void onViewClicked() {
    }
}
