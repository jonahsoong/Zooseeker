package com.example.zookeeper;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


import java.util.List;

public class RouteViewModel extends AndroidViewModel {
    private LiveData<List<RouteItem>> routeItems;
    private final RouteDao routeDao;

    public RouteViewModel(@NonNull Application application) {
        super(application);
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

    public void createRouteItem(String text) {
        //old parser
//        String name = "";
//        String location = "";
//        int distance = 0;
//        int prev = 0;
//        int counter = 0;
//        //simple string parser, must not have spaces after comma(especially for distance!)
//        for(int i = 0; i < text.length(); i++){
//            if(text.charAt(i) == ',' || i == text.length()-1){
//                if(counter == 0)
//                    name = text.substring(prev, i);
//                else if(counter == 1 )
//                    location = text.substring(prev, i);
//                else
//                    distance = Integer.parseInt(text.substring(prev));
//                counter++;
//                prev = i+1;
//            }
//        }
        //int endOfListOrder = routeDao.getOrderForAppend();
        //TODO: pull distances from JSON file and actual name!
        RouteItem newItem = new RouteItem(text);
        routeDao.insert(newItem);
    }
//deletes an item, perhaps update animation?
    public void deleteTodo(RouteItem routeItem) {
        routeDao.delete(routeItem);
    }
}