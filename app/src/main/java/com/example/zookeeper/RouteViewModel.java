package com.example.zookeeper;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


import java.util.ArrayList;

import java.util.HashSet;
import java.util.List;

public class RouteViewModel extends AndroidViewModel {
    private LiveData<List<RouteItem>> routeItems;
    private final RouteDao routeDao;
    static HashSet<String> existingIds;

    public RouteViewModel(@NonNull Application application) {
        super(application);
        //existingIds = new HashSet<>();
        Context context = getApplication().getApplicationContext();
        RouteDatabase db = RouteDatabase.getSingleton(context);
        routeDao = db.routeDao();
    }

    public LiveData<List<RouteItem>> getRoute() {
        if (routeItems == null) {
            loadUsers();
        }
        return routeItems;
    }
    public List<RouteItem> getList(){
        return routeDao.getAll();
    }


    public List<String> getIds(){
        //pass parent_ids
        List<String> ids = new ArrayList<>();
        for(RouteItem r : getList()){
            if(!r.databaseID.equals("entrance_exit_gate"))
                ids.add(r.databaseID);
        }
        return ids;
    }


    private void loadUsers() {
        routeItems = routeDao.getAllLive();
    }
    //creates a RouteItem in db
    public void createRouteItem(String id, String name, double lat, double lng) {
        List<RouteItem> exist = routeDao.getAll();
        //check
        for(RouteItem i : exist){
            if(i.animal.equals(name))
                return;
        }
        RouteItem newItem = new RouteItem(name, id, lat, lng);
        routeDao.insert(newItem);
    }
//deletes an item, perhaps update animation?
    public void deleteTodo(RouteItem routeItem, TextView update) {
        routeDao.delete(routeItem);
        if(routeDao != null)
            update.setText("Number of Exhibits: " + routeDao.getAll().size());
    }

    public void deleteAll(TextView update) {
        List<RouteItem> exist = routeDao.getAll();
        for(RouteItem i : exist){
            routeDao.delete(i);
        }
        update.setText("Number of Exhibits: 0");
    }

    public void deleteAllNoUpdate() {
        List<RouteItem> exist = routeDao.getAll();
        for(RouteItem i : exist){
            routeDao.delete(i);
        }

    }
}