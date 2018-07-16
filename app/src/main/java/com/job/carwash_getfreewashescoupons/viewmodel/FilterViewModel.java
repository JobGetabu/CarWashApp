package com.job.carwash_getfreewashescoupons.viewmodel;

import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.ViewModel;

import com.job.carwash_getfreewashescoupons.util.Filter;

/**
 * Created by Job on Monday : 7/16/2018.
 */
public class FilterViewModel extends ViewModel {

    private MediatorLiveData<String> dateMediatorLiveData = new MediatorLiveData<>();
    private MediatorLiveData<String> vehicleMediatorLiveData = new MediatorLiveData<>();

    private Filter mFilters;

    public FilterViewModel() {
        mFilters = Filter.getDefault();
    }

    public MediatorLiveData<String> getDateMediatorLiveData() {
        return dateMediatorLiveData;
    }

    public void setDateMediatorLiveData(String dateMediatorLiveData) {
        this.dateMediatorLiveData.setValue(dateMediatorLiveData);
    }

    public MediatorLiveData<String> getVehicleMediatorLiveData() {
        return vehicleMediatorLiveData;
    }

    public void setVehicleMediatorLiveData(String vehicleMediatorLiveData) {
        this.vehicleMediatorLiveData.setValue(vehicleMediatorLiveData);
    }

    public Filter getmFilters() {
        return mFilters;
    }

    public void setmFilters(Filter mFilters) {
        this.mFilters = mFilters;
    }
}
