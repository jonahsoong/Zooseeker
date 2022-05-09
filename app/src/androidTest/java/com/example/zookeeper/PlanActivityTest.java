package com.example.zookeeper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

@RunWith(AndroidJUnit4.class)
public class PlanActivityTest {
    RouteDatabase testDb;
    RouteDao routeDao;
    RouteViewModel viewModel;
    String numExhibitsText;

    private static void forceLayout(RecyclerView recyclerView) {
        recyclerView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        recyclerView.layout(0, 0, 1080, 2280);
    }

    @Before
    public void resetDatabase() {
        Context context = ApplicationProvider.getApplicationContext();
        testDb = Room.inMemoryDatabaseBuilder(context, RouteDatabase.class)
                .allowMainThreadQueries()
                .build();
        //viewModel = new ViewModelProvider(testDb)
                //.get(RouteViewModel.class);
        RouteDatabase.injectTestDatabase(testDb);
        routeDao = testDb.routeDao();

        RouteItem item1 = new RouteItem("Elephant", "elephant");
        RouteItem item2 = new RouteItem("Gorillas", "monkey");

        numExhibitsText = "Number of Exhibits: 2";
        long id1 = routeDao.insert(item1);
        long id2 = routeDao.insert(item2);

        //assertNotEquals(id1, id2);

//        List<TodoListItem> todos = TodoListItem.loadJSON(context, "demo_todos.json");
//        todoListItemDao = testDb.todoListItemDao();
//        todoListItemDao.insertAll(todos);
    }

    @Test
    public void testDeleteRouteItem() {
        ActivityScenario<PlanActivity> scenario = ActivityScenario.launch(PlanActivity.class);
        scenario.moveToState(Lifecycle.State.CREATED);
        scenario.moveToState(Lifecycle.State.STARTED);
        scenario.moveToState(Lifecycle.State.RESUMED);

        scenario.onActivity(activity -> {
            List<RouteItem> beforeRouteList = routeDao.getAll();

            RecyclerView recyclerView = activity.recyclerView;
            RecyclerView.ViewHolder firstVH = recyclerView.findViewHolderForAdapterPosition(0);
            assertNotNull(firstVH);
            long id = firstVH.getItemId();

            View deleteButton = firstVH.itemView.findViewById(R.id.del_btn);
            deleteButton.performClick();

            List<RouteItem> afterRouteList = routeDao.getAll();
            assertEquals(beforeRouteList.size() - 1, afterRouteList.size());

            RouteItem editedItem = routeDao.get(id);
            assertNull(editedItem);
        });
    }

    @Test
    public void testNumExhibitsUpdatesOnDelete() {
        ActivityScenario<PlanActivity> scenario = ActivityScenario.launch(PlanActivity.class);
        scenario.moveToState(Lifecycle.State.CREATED);
        scenario.moveToState(Lifecycle.State.STARTED);
        scenario.moveToState(Lifecycle.State.RESUMED);

        scenario.onActivity(activity -> {
            TextView numExhibits = activity.numAnimals;
           // System.out.println(numExhibits);
            assertEquals(numExhibits.getText(), numExhibitsText);

            RecyclerView recyclerView = activity.recyclerView;
            RecyclerView.ViewHolder firstVH = recyclerView.findViewHolderForAdapterPosition(0);
            assertNotNull(firstVH);
            long id = firstVH.getItemId();

            System.out.println("deleting!");
            View deleteButton = firstVH.itemView.findViewById(R.id.del_btn);
            deleteButton.performClick();

            //Log.d("testing", numExhibits + " q" + routeDao.getAll().size());
            assertEquals(numExhibits.getText(), "Number of Exhibits: 1");

        });
    }

    @Test
    public void testDuplicateInserts(){
        ActivityScenario<PlanActivity> scenario = ActivityScenario.launch(PlanActivity.class);
        scenario.moveToState(Lifecycle.State.CREATED);
        scenario.moveToState(Lifecycle.State.STARTED);
        scenario.moveToState(Lifecycle.State.RESUMED);

        scenario.onActivity(activity -> {
            RouteViewModel viewModel = activity.viewModel;

            viewModel.createRouteItem("elephant", "Elephant");

            TextView numExhibits = activity.numAnimals;

            assertEquals(numExhibits.getText(), numExhibitsText);
            assertEquals(2, routeDao.getAll().size());
        });
    }

    @Test
    public void testAddUpdatesPlan(){
        ActivityScenario<PlanActivity> scenario = ActivityScenario.launch(PlanActivity.class);
        scenario.moveToState(Lifecycle.State.CREATED);
        scenario.moveToState(Lifecycle.State.STARTED);
        scenario.moveToState(Lifecycle.State.RESUMED);

        scenario.onActivity(activity -> {
            RouteViewModel viewModel = activity.viewModel;

            viewModel.createRouteItem("elephant_odyssey", "Elephant Odyssey");

            assertEquals(3, routeDao.getAll().size());
        });
    }

}
