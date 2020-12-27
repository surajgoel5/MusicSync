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
    public DeviceType type=DeviceType.RGB;

    public boolean isAvailable(){


        try {
            URL url= new URL("http://"+ip+"/light/"+devid+"/");
            HttpURLConnection client = (HttpURLConnection) url.openConnection();
            client.setRequestMethod("POST");
            client.getResponseCode();
            if(client != null)
                client.disconnect();
            return true;
        }
        catch(Exception e){
            return false;
        }
    }


    public boolean setColor(int r, int g, int b,double transition){

        int brightness= (int) (r+g+b)/3 ;
        URL url=null;
        try {
            if(type==DeviceType.RGB) {
                url = new URL("http://" + ip + "/light/" + devid + "/turn_on?transition=" + transition + "&brightness=" + brightness + "&b=" + b + "&g=" + g + "&r=" + r);
            }
            else{
                url = new URL("http://" + ip + "/light/" + devid + "/turn_on?transition=" + transition + "&brightness=" + brightness);

            }


            HttpURLConnection client = (HttpURLConnection) url.openConnection();
            client.setRequestMethod("POST");
            client.getResponseCode();
            if(client != null)
                client.disconnect();
            return true;
        }
        catch(Exception e){
            return false;
        }

    }

}
