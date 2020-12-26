package com.surajgoel.musicsync.ui.devices;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.surajgoel.musicsync.MainActivity;
import com.surajgoel.musicsync.R;
import com.surajgoel.musicsync.appdata.Device;
import com.surajgoel.musicsync.appdata.DeviceType;

import java.util.List;

public class DeviceViewAdaptor extends RecyclerView.Adapter<DeviceViewAdaptor.ViewHolder> {

    private List<Device> mDevices;
    private DevicesFragment devicesFragment;

    public DeviceViewAdaptor(List<Device> devices,DevicesFragment fragment) {
        mDevices = devices;
        devicesFragment=fragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View deviceView = inflater.inflate(R.layout.item_device, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(deviceView);



        return viewHolder;



    }

    private void showPopupMenu(View view, int position) {
        PopupMenu popup = new PopupMenu(view.getContext(), view, Gravity.END);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.device_item_menu, popup.getMenu());


        popup.setOnMenuItemClickListener(new DeviceMenuItemClickListener(position,devicesFragment));
        popup.show();
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        Device device=mDevices.get(position);


        TextView nameTextView = holder.nameTextView;
        TextView ipTextView = holder.ipTextView;
        TextView devidTextView = holder.devidTextView;
        ImageView typeImageView=holder.typeImageView;

        nameTextView.setText(device.devname);
        ipTextView.setText(device.ip);
        devidTextView.setText(device.devid);
        if (device.type==DeviceType.RGB){
            typeImageView.setImageResource(R.drawable.rgb_light);
        }
        else{
            typeImageView.setImageResource(R.drawable.monochrome_light);
        }
        final ImageView settingsImageView= holder.settingsImageView;
        settingsImageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                showPopupMenu(settingsImageView, position);
            }
        });


    }

    @Override
    public int getItemCount() {
        return mDevices.size();
    }


    public static class  ViewHolder extends RecyclerView.ViewHolder{
        public TextView nameTextView;
        public  TextView ipTextView;
        public  TextView devidTextView;
        public ImageView typeImageView;
        public ImageView settingsImageView;



        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            nameTextView = (TextView) itemView.findViewById(R.id.device_name);
            ipTextView=(TextView) itemView.findViewById(R.id.device_ip);
            devidTextView=(TextView) itemView.findViewById(R.id.device_id);
            typeImageView=(ImageView) itemView.findViewById(R.id.type_image);
            settingsImageView=(ImageView) itemView.findViewById(R.id.device_settings);


        }

    }

}
class DeviceMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {
    int position;
    DevicesFragment devicesFragment;

    DeviceMenuItemClickListener(int position,DevicesFragment fragment) {

        this.position = position;
        this.devicesFragment=fragment;
    }

    /**
     * Click listener for popup menu items
     */

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.remove:
                devicesFragment.deleteDevice(position);
                return true;
            default:
        }
        return false;
    }
}
