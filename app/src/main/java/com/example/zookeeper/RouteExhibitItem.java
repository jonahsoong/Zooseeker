
package com.example.zookeeper;

import java.io.Serializable;
import java.util.ArrayList;

public class RouteExhibitItem implements Serializable {
    public int id = 0;
    public String name;
    public double distance;
    public ArrayList<String> directions;

    RouteExhibitItem(String name, double distance , ArrayList<String> directions){
        this.name = name;
        this.distance = distance;
        this.directions = directions;
    }


}