package com.example.zookeeper;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    ListView listView;
//    String[] name = {"Hippo","Grizzly Bear", "Baboon", "Bat", "Antelope", "Tiger", "Lion"};
    List<String> id = new ArrayList<String>();
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
            id.add(animal.id);
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
        arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,tags);
        originalIdsAd = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,id);

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
//                filteredId = new ArrayList<String>(id);
//                filteredId = id;
                for (SearchItem animal: animals) {
                    id.add(animal.id);
                }
                if (!newText.isEmpty()) {
                    Log.d("filtered list before clear: ", id.toString());
                    id.clear();
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
                            if (animal.tags.contains(filteredTag) && !id.contains(animal.id)) {
                                id.add(animal.id);
                            }
                        }
                    }
                }
//                originalIdsAd.setNotifyOnChange(true);
                listView.setAdapter(originalIdsAd);
                Log.d("input: ",newText + "\n");
                Log.d("filtered list",id.toString() + "\n");
                return false;
            }

        });
        return super.onCreateOptionsMenu(menu);
    }
}