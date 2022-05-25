package com.example.zookeeper;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "route_items")
public class RouteItem {

    @PrimaryKey(autoGenerate = true)
    public long id;
    @NonNull
    String animal;
    //equivalent to parent_id
    String databaseID;
    //location of each exhibit item
    double lat;
    double lng;
    //constructor for RouteItem
    public RouteItem(String animal, String databaseID, double lat, double lng){
        this.animal = animal;
        this.databaseID = databaseID;
        this.lat = lat;
        this.lng = lng;
    }


}
