package com.surajgoel.musicsync.appdata;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DeviceDao {
    @Query("SELECT * FROM device ORDER BY uid")
    List<Device> getAll();

    @Query("SELECT * FROM device WHERE devid = (:id)")
    Device getDevice(int id);

    @Query("SELECT * FROM device WHERE devname = (:name)")
    Device getDevice(String name);
    @Insert
    void insert(Device device);

    @Delete
    void delete(Device device);


}
