package com.surajgoel.musicsync.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.surajgoel.musicsync.R;
import com.surajgoel.musicsync.appdata.Device;
import com.surajgoel.musicsync.appdata.DeviceDao;
import com.surajgoel.musicsync.appdata.DeviceDatabase;
import com.surajgoel.musicsync.ui.devices.DeviceViewAdaptor;

import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private DeviceDatabase db;
    private DeviceDao deviceDao;
    HomeDeviceViewAdaptor homeDeviceViewAdaptor;
    List<Device> devices;
    RecyclerView homeDeviceRecyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        db = Room.databaseBuilder( getContext(),DeviceDatabase.class, "device-database").allowMainThreadQueries().build();

        deviceDao = db.deviceDao();
        devices=deviceDao.getAll();

        homeDeviceRecyclerView =(RecyclerView) root.findViewById(R.id.rv_home_device);
        homeDeviceViewAdaptor= new HomeDeviceViewAdaptor(devices);
        homeDeviceRecyclerView.setAdapter(homeDeviceViewAdaptor);
        homeDeviceRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));



        return root;
    }
}