package com.surajgoel.musicsync.ui.home;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.surajgoel.musicsync.R;
import com.surajgoel.musicsync.appdata.Device;

import java.util.List;

public class HomeDeviceViewAdaptor extends RecyclerView.Adapter<HomeDeviceViewAdaptor.ViewHolder> {
    private List<Device> mDevices;
     public HomeDeviceViewAdaptor(List<Device> devices){
         mDevices=devices;
     }

    @NonNull
    @Override
    public HomeDeviceViewAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View deviceView = inflater.inflate(R.layout.item_device_home, parent, false);

        // Return a new holder instance
        HomeDeviceViewAdaptor.ViewHolder viewHolder = new HomeDeviceViewAdaptor.ViewHolder(deviceView);

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull HomeDeviceViewAdaptor.ViewHolder holder, int position) {
        Device device=mDevices.get(position);


        TextView nameTextView = holder.nameTextView;
        Log.e("LOL",nameTextView.getText().toString());
        nameTextView.setText(device.devname);


    }

    @Override
    public int getItemCount() {
        return mDevices.size();
    }


    public static class  ViewHolder extends RecyclerView.ViewHolder{
        public TextView nameTextView;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            nameTextView = (TextView) itemView.findViewById(R.id.dev_name_home);

        }

    }
}
