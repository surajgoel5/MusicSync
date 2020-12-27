package com.surajgoel.musicsync.ui.devices;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DevicesViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public DevicesViewModel() {

    }

    public LiveData<String> getText() {
        return mText;
    }
}