package com.surajgoel.musicsync.appdata;

import androidx.room.TypeConverter;

public class Converters {
    @TypeConverter
    public static DeviceType toDeviceType(int value) {
        return DeviceType.values()[value];
    }
@TypeConverter
    public static  int toInt(DeviceType type){
        return type.getVal();
}

}
