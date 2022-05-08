package com.example.zookeeper;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface RouteDao {
    @Insert
    long insert(RouteItem routeItem);

    @Insert
    List<Long> insertAll(List<RouteItem> routeItem);

    @Query("SELECT * FROM `route_items` where `id`=:id")
    RouteItem get(long id);

    @Query("SELECT * FROM `route_items` ORDER BY 'animal'")
    List<RouteItem> getAll();

    @Query("SELECT * FROM `route_items` ORDER BY 'animal'")
    LiveData<List<RouteItem>> getAllLive();

//    @Query("SELECT `order` + 1 FROM `todo_list_items` ORDER BY `order` DESC LIMIT 1")
//    int getOrderForAppend();


    @Update
    int update(RouteItem routeItem);

    @Delete
    int delete(RouteItem routeItem);
}
