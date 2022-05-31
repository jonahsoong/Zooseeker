package com.example.zookeeper;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    ListView listView;
//    String[] name = {"Hippo","Grizzly Bear", "Baboon", "Bat", "Antelope", "Tiger", "Lion"};
    List<String> name = new ArrayList<String>();
    List<String> tags = new ArrayList<String>();
//    List<String> filteredId = new ArrayList<String>();
    ArrayAdapter<String> tagsAdapter;
    ArrayAdapter<String> nameAdapter;
    List<SearchItem> animals;
    private RouteViewModel viewModel;
    Hashtable<String, SearchItem> animalList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        viewModel = new ViewModelProvider(this)
                .get(RouteViewModel.class);


        animals =  SearchItem.loadJSON(this, "exhibit_info.json");
        animalList = new Hashtable();
        Log.d("SearchItems", animals.toString());
        for (SearchItem animal: animals) {
//            don't add exhibit group in search
            if (animal.kind.equals("exhibit") ||animal.kind.equals("gate")) {
                name.add(animal.name);
                animalList.put(animal.name, animal);
            }
//            filteredId.add(animal.id);
            for (String tag: animal.tags){
                if (!tags.contains(tag)){
                    if (animal.kind.equals("exhibit") ||animal.kind.equals("gate"))
                        tags.add(tag);
                }
            }
        }

        listView = findViewById(R.id.listview);
//        takes in an adapter, a view and a position and uses them
        listView.setOnItemClickListener((adapter, v, position, arg3) -> {
            String animalClickedName = (String)adapter.getItemAtPosition(position);
            AlertDialog.Builder builder = new AlertDialog.Builder(SearchActivity.this);
            builder.setMessage("Are you sure you want to add " + animalClickedName + " to the route?")
            .setPositiveButton("Yes", (dialogInterface, i) -> {
                String currentId = animalList.get(animalClickedName).id;
                double lat = animalList.get(animalClickedName).lat;
                double lng = animalList.get(animalClickedName).lng;

                Log.d("item on click", animalClickedName);
//                get the id of the animal

                viewModel.createRouteItem(currentId, animalClickedName, lat, lng);
            })
            .setNegativeButton("No",null);
            AlertDialog alert = builder.create();
            alert.show();

            // assuming string and if you want to get the value on click of list item
            // do what you intend to do on click of listview row
        });
        tagsAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,tags);
        nameAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, name);

        listView.setAdapter(nameAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_search,menu);

        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Type here to search for your favorite animal!");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextSubmit(String query){
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText){
                name.clear();
                for (SearchItem animal: animals) {
                    if (animal.kind.equals("exhibit") ||animal.kind.equals("gate"))
                        name.add(animal.name);
                }
                if (!newText.isEmpty()) {
                    Log.d("filtered list before clear: ", name.toString());
                    name.clear();
                    List<String> filteredTags = new ArrayList<String>();

                for (String tag: tags){
                    // filter out the tags with the user input
                    if(tag.indexOf(newText) != -1 && !filteredTags.contains(tag)){
                        filteredTags.add(tag);
                    }
                }

                    for (String filteredTag : filteredTags) {
                        for (SearchItem animal : animals) {
//                        if the tags are in an animal and the animal is not already in the list we add it
                            if (animal.tags.contains(filteredTag) && !name.contains(animal.name)) {
                                if (animal.kind.equals("exhibit") ||animal.kind.equals("gate"))
                                    name.add(animal.name);
                            }
                        }
                    }
                }

                listView.setAdapter(nameAdapter);
                Log.d("input: ",newText + "\n");
                Log.d("filtered list", name.toString() + "\n");
                return false;
            }

        });
        return super.onCreateOptionsMenu(menu);
    }

//    create a plan when the button is clicked
    public void onPlanClicked(View view) {
        Intent intent = new Intent(this,PlanActivity.class);
        Log.d("ids", viewModel.getIds().toString());
        startActivity(intent);
    }
}