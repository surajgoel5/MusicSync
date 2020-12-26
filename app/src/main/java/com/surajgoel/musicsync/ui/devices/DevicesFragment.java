package com.surajgoel.musicsync.ui.devices;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
//import android.support.annotation.NonNull;
//import android.support.v4.app.Fragment;
//import android.arch.lifecycle.ViewModelProviders;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.surajgoel.musicsync.R;
import com.surajgoel.musicsync.appdata.Device;
import com.surajgoel.musicsync.appdata.DeviceDao;
import com.surajgoel.musicsync.appdata.DeviceDatabase;
import com.surajgoel.musicsync.appdata.DeviceType;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import static com.surajgoel.musicsync.appdata.DeviceType.Mono;
import static com.surajgoel.musicsync.appdata.DeviceType.RGB;

public class DevicesFragment extends Fragment {

    private DevicesViewModel devicesViewModel;
    private DeviceDatabase db;
    private DeviceDao deviceDao;
    DeviceViewAdaptor deviceViewAdaptor;
    List<Device> devices;
    RecyclerView deviceRecyclerView;
    FloatingActionButton addDeviceButton;
    AlertDialog alertDialog;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        devicesViewModel =  ViewModelProviders.of(this).get(DevicesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_devices, container, false);

        db = Room.databaseBuilder( getContext(),DeviceDatabase.class, "device-database").allowMainThreadQueries().build();

        deviceDao = db.deviceDao();
        devices=deviceDao.getAll();

        deviceRecyclerView =(RecyclerView) root.findViewById(R.id.rv_devices);
        deviceViewAdaptor= new DeviceViewAdaptor(devices,this);
        deviceRecyclerView.setAdapter(deviceViewAdaptor);
        deviceRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));

        addDeviceButton=(FloatingActionButton) root.findViewById(R.id.add_device_button);
        addDeviceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addNewDevice();

            }
        });
        /*Device dev= new Device();
        dev.devid="rgb";
        dev.devname="lolggg";
        dev.ip="192.168.1.1";
        dev.type=RGB;
        deviceDao.insert(dev);
*/
        return root;

         }

public void deleteDevice(int position){

        deviceDao.delete(deviceDao.getAll().get(position));
        devices.remove(position);
        deviceViewAdaptor.notifyItemRemoved(position);
        deviceViewAdaptor.notifyItemRangeChanged(position,devices.size());

}

public void addDevice(Device device){
        deviceDao.insert(device);
        Snackbar addedSnackbar = Snackbar.make(getView(),"Device has been added", Snackbar.LENGTH_SHORT);
        addedSnackbar.show();
    devices.add(device);
    deviceViewAdaptor.notifyItemInserted(devices.size());  //(position);
    deviceViewAdaptor.notifyItemRangeChanged(devices.size(),devices.size());


}
public void confirmAddition(final Device device){
    AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
            .setTitle("Device Not Found").setMessage("The device you were trying to add was not found on your network. Do you want to modify the entries or proceed anyway?")
            .setPositiveButton("Proceed Anyway", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    addDevice(device);
                    alertDialog.dismiss();
                }
            })
            .setNegativeButton("Modify",null);
    builder.show();
}

public  void addNewDevice(){
    AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
            .setTitle("Add Device")
            .setPositiveButton(android.R.string.ok,null)
            .setNegativeButton(android.R.string.cancel,null);
   View viewInflated = LayoutInflater.from(getContext()).inflate(R.layout.new_device_dialog, (ViewGroup) getView(), false);
    final EditText ip_input = (EditText) viewInflated.findViewById(R.id.ipaddr_input);
    final EditText devid_input=(EditText) viewInflated.findViewById(R.id.devid_input);
    final EditText devname_input=(EditText) viewInflated.findViewById(R.id.devname_input);
    final RadioButton devType_input=(RadioButton) viewInflated.findViewById(R.id.rgb_radio_button);

    builder.setView(viewInflated);
   alertDialog= builder.create();
    alertDialog.show();
    alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Device dev_in=new Device();
            dev_in.ip=ip_input.getText().toString();
            dev_in.devid=devid_input.getText().toString();
            dev_in.devname=devname_input.getText().toString();
            boolean isrgb=devType_input.isChecked();
            boolean added=false;
            dev_in.type= RGB;
            if(!isrgb)
                dev_in.type=Mono;
            if (dev_in.isAvailable()){
                alertDialog.dismiss();
                addDevice(dev_in);
            }
            else{

              confirmAddition(dev_in);
            }



        }
    });

}

}