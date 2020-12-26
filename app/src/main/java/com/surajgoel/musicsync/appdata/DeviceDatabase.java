package com.surajgoel.musicsync.appdata;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

@Database(entities = {Device.class}, version = 1)
@TypeConverters(Converters.class)
public abstract class DeviceDatabase extends RoomDatabase {
private static final String DB_NAME="device-database";

    public abstract DeviceDao deviceDao();
}