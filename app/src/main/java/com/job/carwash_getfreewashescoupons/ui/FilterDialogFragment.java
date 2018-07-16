/**
 * Copyright 2017 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
 package com.job.carwash_getfreewashescoupons.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import com.google.firebase.firestore.Query;
import com.job.carwash_getfreewashescoupons.R;
import com.job.carwash_getfreewashescoupons.util.Filter;
import com.job.carwash_getfreewashescoupons.viewmodel.FilterViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Dialog Fragment containing filter form.
 */
public class FilterDialogFragment extends DialogFragment {

    interface FilterListener {

        void onFilter(Filter filters);

    }

    public static final String TAG = "FilterDialog";

    private View mRootView;

    @BindView(R.id.spinner_date)
    Spinner mDateSpinner;

    @BindView(R.id.spinner_vehicle)
    Spinner mVehicleSpinner;

    private FilterViewModel mViewModel;

    private FilterListener mFilterListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.dialog_filters, container, false);
        ButterKnife.bind(this, mRootView);

        return mRootView;
    }



    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // View model
        mViewModel = ViewModelProviders.of(getActivity()).get(FilterViewModel.class);
    }

    @Override
    public void onResume() {
        super.onResume();
        getDialog().getWindow().setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof FilterListener) {
            mFilterListener = (FilterListener) context;
        }
    }

    @OnClick(R.id.button_search)
    public void onSearchClicked() {


        mViewModel.setDateMediatorLiveData(mDateSpinner.getSelectedItem().toString());
        mViewModel.setVehicleMediatorLiveData(mVehicleSpinner.getSelectedItem().toString());

        if (mFilterListener != null) {
            mFilterListener.onFilter(getFilters());
        }
        //do set up here
        dismiss();
    }

    @OnClick(R.id.button_cancel)
    public void onCancelClicked() {
        dismiss();
    }

    @Nullable
    private String getSelectedVehicle() {
        String selected = (String) mVehicleSpinner.getSelectedItem();
        if (getString(R.string.allvehicles).equals(selected)) {
            return null;
        } else {
            return selected;
        }
    }


    @Nullable
    private Query.Direction getDateDirection() {
        String selected = (String) mDateSpinner.getSelectedItem();
        if (getString(R.string.fromrecent).equals(selected)) {
            return Query.Direction.DESCENDING;
        }
        if (getString(R.string.frompast).equals(selected)) {
            return Query.Direction.ASCENDING;
        }

        return null;
    }

    public void resetFilters() {
        if (mRootView != null) {
            mDateSpinner.setSelection(0);

            mVehicleSpinner.setSelection(0);
        }
    }

    public Filter getFilters() {
        Filter filters = new Filter();

        if (mRootView != null) {
            filters.setVehicle(getSelectedVehicle());
            filters.setDateDirection(getDateDirection());
        }

        return filters;
    }

}
