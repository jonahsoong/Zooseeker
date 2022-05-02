package com.example.zookeeper;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.List;
import java.util.concurrent.Executors;
@Database(entities = {RouteItem.class}, version = 1)
public abstract class RouteDatabase extends RoomDatabase {
    private static RouteDatabase singleton = null;
    public abstract RouteDao routeDao();
    public synchronized static RouteDatabase getSingleton(Context context) {
        if(singleton == null) {
            singleton = RouteDatabase.makeDatabase(context);
        }
        return singleton;
    }
    private static RouteDatabase makeDatabase(Context context) {
        return Room.databaseBuilder(context, RouteDatabase.class, "route_app.db")
                .allowMainThreadQueries()
                .addCallback(new Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
//                        Executors.newSingleThreadExecutor().execute(() -> {
//                            List<RouteItem> todos = TodoListItem.loadJSON(context, "demo_todos.json");
//                            getSingleton(context).todoListItemDao().insertAll(todos);
//                        });
                    }
                })
                .build();
    }

    public static void injectTestDatabase(RouteDatabase testDatabase) {
        if (singleton != null) {
            singleton.close();
        }
        singleton = testDatabase;
    }
}
