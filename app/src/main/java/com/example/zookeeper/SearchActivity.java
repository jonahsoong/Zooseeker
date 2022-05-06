package com.example.zookeeper;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
    ArrayAdapter<String> arrayAdapter;
    ArrayAdapter<String> originalIdsAd;
    List<SearchItem> animals;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        int index = 0;
        animals =  SearchItem.loadJSON(this,"nodes.json");
        Log.d("SearchItems", animals.toString());
        for (SearchItem animal: animals) {
            name.add(animal.name);
//            filteredId.add(animal.id);
            for (String tag: animal.tags){
                if (!tags.contains(tag)){
                    tags.add(tag);
                }
            }
        }
//        filteredId = new ArrayList<String>(id);
//        filteredId = id;
        listView = findViewById(R.id.listview);
        listView.setOnItemClickListener((adapter, v, position, arg3) -> {
            String value = (String)adapter.getItemAtPosition(position);
            AlertDialog.Builder builder = new AlertDialog.Builder(SearchActivity.this);
            builder.setMessage("Are you sure you want to add " + value + " to the route?")
            .setPositiveButton("Yes", (dialogInterface, i) -> {
                String currentId;
                Log.d("item on click", value);
//                get the id of the animal
                for (SearchItem animal: animals){
                    if (value.equals(animal.name)){
                        currentId = animal.id;
                        Log.d("Animal id for the name", "onCreate: " + currentId);
                    }
                }
            })
            .setNegativeButton("No",null);
            AlertDialog alert = builder.create();
            alert.show();

            // assuming string and if you want to get the value on click of list item
            // do what you intend to do on click of listview row
        });
        arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,tags);
        originalIdsAd = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, name);

        listView.setAdapter(originalIdsAd);
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
                    name.add(animal.name);
                }
                if (!newText.isEmpty()) {
                    Log.d("filtered list before clear: ", name.toString());
                    name.clear();
                    List<String> filteredTags = new ArrayList<String>();

//                for (String tag: tags){
//                    // filter out the tags with the user input
//                    if(tag.contains(newText)){
//                        filteredTags.add(tag);
//                    }
//                }
//                filter the array of tags
                    arrayAdapter.getFilter().filter(newText);
//                add the tags filtered to a list
                    for (int i = 0; i < arrayAdapter.getCount(); i++) {
                        if (!filteredTags.contains(arrayAdapter.getItem(i)))
                            filteredTags.add(arrayAdapter.getItem(i));
                    }
//                for (String tag: filteredTags){
//                    Log.d("filteredTags" , tag);
//                }
//                Log.d("number count: ", "filtered " + filteredTags.size());
                    for (String filteredTag : filteredTags) {
                        for (SearchItem animal : animals) {
//                        if the tags are in an animal and the animal is not already in the list we add it
                            if (animal.tags.contains(filteredTag) && !name.contains(animal.name)) {
                                name.add(animal.name);
                            }
                        }
                    }
                }
//                originalIdsAd.setNotifyOnChange(true);
                listView.setAdapter(originalIdsAd);
                Log.d("input: ",newText + "\n");
                Log.d("filtered list", name.toString() + "\n");
                return false;
            }

        });
        return super.onCreateOptionsMenu(menu);
    }
}