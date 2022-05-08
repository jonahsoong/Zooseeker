package com.example.zookeeper;

import android.content.Context;

import androidx.annotation.RequiresPermission;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class SearchItem {
    public String id = "";
    public List<String> tags;
    public String name = "";
    public String kind = "";
    SearchItem(String id, List<String> tags, String name, String kind){
        this.id = id;
        this.tags = tags;
        this.name = name;
        this.kind = kind;
    }

    @Override
    public String toString() {
        return "SearchItem{" +
                "id='" + id + '\'' +
                ", tags=" + tags +
                ", name='" + name + '\'' +
                ", kind='" + kind + '\'' +
                '}';
    }

    //    load id and tags
    public static List<SearchItem> loadJSON(Context context, String path){
        try{
            InputStream input = context.getAssets().open(path);
            Reader reader = new InputStreamReader(input);
            Gson gson = new Gson();
            Type type = new TypeToken<List<SearchItem>>(){}.getType();
            return gson.fromJson(reader,type);
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}
