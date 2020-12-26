package com.surajgoel.musicsync.appdata;

import androidx.room.TypeConverter;

public enum DeviceType{
    RGB(0),Mono(1);
    private int val;

  DeviceType(int val){
    this.val=val;}

    public int getVal() {
        return val;
    }


}

