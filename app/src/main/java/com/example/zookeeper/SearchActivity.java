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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        viewModel = new ViewModelProvider(this)
                .get(RouteViewModel.class);
        int index = 0;
        animals =  SearchItem.loadJSON(this,"exhibit_info.json");
        Log.d("SearchItems", animals.toString());
        for (SearchItem animal: animals) {
            if (animal.kind.equals("exhibit") ||animal.kind.equals("gate"))
                name.add(animal.name);
//            filteredId.add(animal.id);
            for (String tag: animal.tags){
                if (!tags.contains(tag)){
                    if (animal.kind.equals("exhibit") ||animal.kind.equals("gate"))
                        tags.add(tag);
                }
            }
        }
//        filteredId = new ArrayList<String>(id);
//        filteredId = id;
        listView = findViewById(R.id.listview);
//        takes in an adapter, a view and a position and uses them
        listView.setOnItemClickListener((adapter, v, position, arg3) -> {
            String value = (String)adapter.getItemAtPosition(position);
            AlertDialog.Builder builder = new AlertDialog.Builder(SearchActivity.this);
            builder.setMessage("Are you sure you want to add " + value + " to the route?")
            .setPositiveButton("Yes", (dialogInterface, i) -> {
                String currentId = "";
                //String animalName = "";
                Log.d("item on click", value);
//                get the id of the animal
                for (SearchItem animal: animals){
                    if (value.equals(animal.name)){
                        currentId = animal.id;
                        Log.d("Animal id for the name", "onCreate: " + currentId);
                    }
                }
                viewModel.createRouteItem(currentId, value);
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
//                filteredId = new ArrayList<String>(id);
//                filteredId = id;
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
////                filter the array of tags
////                    tagsAdapter.getFilter().filter(newText);
////                add the tags filtered to a list
//                    for (int i = 0; i < tagsAdapter.getCount(); i++) {
////                        for (String filteredTag: filteredTags){
////                            if (filteredTag.indexOf(arrayAdapter.getItem(i)) == -1){
////                                filteredTags.add(arrayAdapter.getItem(i));
////                            }
////                        }
//                        Log.d("array adapter: ", tagsAdapter.getItem(i) + "\n");
//                        if (!filteredTags.contains(tagsAdapter.getItem(i))) {
//                            filteredTags.add(tagsAdapter.getItem(i));
//                        }
//                    }
//                for (String tag: filteredTags){
//                    Log.d("filteredTags" , tag);
//                }
//                Log.d("number count: ", "filtered " + filteredTags.size());
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
//                originalIdsAd.setNotifyOnChange(true);
                listView.setAdapter(nameAdapter);
                Log.d("input: ",newText + "\n");
                Log.d("filtered list", name.toString() + "\n");
                return false;
            }

        });
        return super.onCreateOptionsMenu(menu);
    }

    public void onPlanClicked(View view) {
        Intent intent = new Intent(this,PlanActivity.class);
        Log.d("ids", viewModel.getIds().toString());
        startActivity(intent);
    }
}