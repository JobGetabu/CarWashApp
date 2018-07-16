package com.job.carwash_getfreewashescoupons.viewmodel;

import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.ViewModel;

/**
 * Created by Job on Monday : 7/16/2018.
 */
public class FilterViewModel extends ViewModel {

    private MediatorLiveData<String> dateMediatorLiveData = new MediatorLiveData<>();
    private MediatorLiveData<String> vehicleMediatorLiveData = new MediatorLiveData<>();
    private MediatorLiveData<String> priceMediatorLiveData = new MediatorLiveData<>();

    public FilterViewModel() {
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

    public MediatorLiveData<String> getPriceMediatorLiveData() {
        return priceMediatorLiveData;
    }

    public void setPriceMediatorLiveData(String priceMediatorLiveData) {
        this.priceMediatorLiveData.setValue(priceMediatorLiveData);
    }
}
