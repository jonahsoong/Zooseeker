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
    //to be implemented later, needed to route generation
    String databaseID;
    String location;
    int distance;
    @Ignore
    public RouteItem(String animal, String location, int distance){
        this.animal = animal;
        this.location = location;
        this.distance = distance;
    }
    public RouteItem(String animal, String databaseID){
        this.animal = animal;
        this.databaseID = databaseID;
    }
    @Override
    public String toString() {
        return "RouteItem{" +
                "animal='" + animal + '\'' +
                ", location='" + location + '\'' +
                ", distance=" + distance +
                '}';
    }
//    public RouteItem(String animal, String location, int order){
//        this.animal = animal;
//        this.location = location;
//        this.order = order;
//        this
//    }


}
