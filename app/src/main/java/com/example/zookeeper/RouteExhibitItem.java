
package com.example.zookeeper;

import java.io.Serializable;
import java.util.ArrayList;

public class RouteExhibitItem implements Serializable {
    public int id = 0;
    public String source;
    public String sink;
    public double distance;
    public ArrayList<String> directionsBrief;
    public ArrayList<String> directionsDetailed;

    RouteExhibitItem(String source, String sink, double distance , ArrayList<String> directionsDetailed, ArrayList<String> directionsBrief){
        this.sink = sink;
        this.source = source;
        this.distance = distance;
        this.directionsDetailed = directionsDetailed;
        this.directionsBrief = directionsBrief;
    }


}
/**
 * fern canyon: "lat": 32.7337949159672,
 *         "lng": -117.1769866067953
 *
 * parker aviary: "lat": 32.73870593465047,
 *         "lng": -117.1695850705821
 */