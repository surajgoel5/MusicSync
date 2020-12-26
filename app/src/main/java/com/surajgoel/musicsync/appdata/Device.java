package com.surajgoel.musicsync.appdata;

import android.util.Log;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.net.HttpURLConnection;
import java.net.URL;

@Entity
public class Device {

    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo
    public String ip;

    @ColumnInfo
    public  String devid;

    @ColumnInfo
    public  String devname;

    @ColumnInfo
    public DeviceType type;

    public boolean isAvailable(){


        try {
            URL url= new URL("http://"+ip+"/light/"+devid+"/");
            Log.e("LOL",url.toString());
            HttpURLConnection client = (HttpURLConnection) url.openConnection();
            client.setRequestMethod("POST");
            client.getResponseCode();
            if(client != null)
                client.disconnect();
            return true;
        }
        catch(Exception e){
            Log.e("LOLOL",e+"");
            return false;
        }
    }

}
