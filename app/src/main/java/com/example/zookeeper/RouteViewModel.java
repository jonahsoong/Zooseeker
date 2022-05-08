package com.example.zookeeper;

import android.app.Application;
import android.content.Context;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


import java.util.ArrayList;
import java.util.Arrays;
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


    private void loadUsers() {
        routeItems = routeDao.getAllLive();
    }

    public void createRouteItem(String id, String name) {

        //TODO: pull distances from JSON file and actual name!
        List<RouteItem> exist = routeDao.getAll();
        for(RouteItem i : exist){
            //existingIds.add(text);
            if(i.animal.equals(name))
                return;
        }
        RouteItem newItem = new RouteItem(name, id);
        routeDao.insert(newItem);
    }

    public ArrayList<String> getIds() { return new ArrayList<>(Arrays.asList("lions", "gorillas", "gorillas", "gators", "arctic_foxes", "gorillas"));}
//deletes an item, perhaps update animation?
    public void deleteTodo(RouteItem routeItem, TextView update) {
        routeDao.delete(routeItem);
        if(routeDao != null)
            update.setText("Number of Exhibits: " + routeDao.getAll().size());
    }
}