package com.example.zookeeper;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import androidx.constraintlayout.utils.widget.MockView;
import androidx.test.core.app.ActivityScenario;

import android.app.Application;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class RouteTest {
    private RouteDao dao;
    private RouteDatabase db;

    @Before
    public void createDb(){
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, RouteDatabase.class)
                .allowMainThreadQueries()
                .build();
        dao = db.routeDao();

    }

    @After
    public void closeDb() throws IOException {
        db.close();
    }
    //test insert
    @Test
    public void testInsert(){
        RouteItem item1 = new RouteItem("Elephant", "elephant", 0, 0);
        RouteItem item2 = new RouteItem("Gorillas", "monkey", 0 ,0);

        long id1 = dao.insert(item1);
        long id2 = dao.insert(item2);

        assertNotEquals(id1, id2);
    }
    @Test
    public void testDelete() {
        RouteItem item = new RouteItem("Elephant", "elephant", 0, 0);
        long id = dao.insert(item);

        item = dao.get(id);
        int itemsDeleted = dao.delete(item);
        assertEquals(1, itemsDeleted);
        assertNull(dao.get(id));
    }

    @Test
    public void testMultipleInserts(){
        int i = 20;
        for(int j = 0; j < i; j++){
            RouteItem item = new RouteItem("" + j , ""  , 0, 0);
            dao.insert(item);
        }
        assertEquals(dao.getAll().size(), i);
    }

//    @Test
//    public void testDuplicateInserts() {
//        MockView
//        ActivityScenario.launch(SearchActivity.class).onActivity{ activity ->
//            // do something with your activity instance
//        }
//    }
//    @Test
//    public void testDuplicateInserts(){
//
//        RouteViewModel viewModel = new RouteViewModel(new Application());
//
//        viewModel.createRouteItem("Elephant", "elephant");
//        viewModel.createRouteItem("Elephant", "elephant");
//
//        assertEquals(viewModel.getList().size(), 1);
//    }
}
