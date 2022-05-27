package com.example.zookeeper;

import android.content.Context;
import android.util.Log;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.alg.util.Pair;

import java.util.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PathGenerator {
    private Map<String, ZooData.VertexInfo> vInfo;
    private Map<String, ZooData.EdgeInfo> eInfo;
    private Graph<String, IdentifiedWeightedEdge> g;
    private List<GraphPath<String, IdentifiedWeightedEdge>> totalPath;
    // for user story #73 Detailed Directions
    private ArrayList<ArrayList<String>> detailedDirections;
    private ArrayList<ArrayList<String>> simpleDirections;
    public PathGenerator(Context context){
        // 2. Load the information about our nodes and edges...
        vInfo = ZooData.loadVertexInfoJSON(context,"exhibit_info.json");
        eInfo = ZooData.loadEdgeInfoJSON(context,"trail_info.json");
        // 1. Load the graph...
        g = ZooData.loadZooGraphJSON(context,"zoo_graph.json");
        //conditional values
        totalPath = new ArrayList<>();
        detailedDirections = new ArrayList<>();
        simpleDirections = new ArrayList<>();

    }

    public void generatePlan(ArrayList<String> input){
        // ensure that input ArrayList's first element is the starting element in the entire list.
        // "source" and "sink" are graph terms for the start and end

        //assume input will come as List<String> format
        //check and delete duplicates
        for(int j = 0; j < input.size();j++){
            for(int i = j+1; i < input.size(); i++){
                if(input.get(j).equals(input.get(i))){
                    input.remove(i);
                }
            }
        }
        GraphPath<String, IdentifiedWeightedEdge> path;
        // assign the source node to the starting element,
        // need that to be entrance_exit_gate for initial plan
        String source = input.get(0);;
        // we want to be able to revise a path without completely getting rid of it
        // so we need to know whether or not this is a replan or a first plan
        // when the totalPlan is not empty, we know that at least one plan has been
        // generated, so we can delete any GraphPath's after the GraphPath where
        // the last replan node is the source.
        if(!totalPath.isEmpty()){
            //removes all paths that begin with a node we have not visited
            //and removes the path back to the entrance gate
            //what remains should be all traversed paths, plus whatever path starts
            //with the new closest node at input.get(0)
            for(int m = 1; m < input.size(); m++){
                String s = input.get(m);
                for(int k = 0; k < totalPath.size(); k++){
                    if(totalPath.get(k).getStartVertex() == s){
                        totalPath.remove(k);
                        k--;
                    }
                    if(totalPath.get(k).getEndVertex() == "entrance_exit_gate"){
                        totalPath.remove(k);
                        k--;
                    }
                }
            }

            //finds the path which occurs just before the desired first position
            //stores the start vertex value and deletes the path

            String temp = totalPath.get(totalPath.size()-1).getStartVertex();
            totalPath.remove(totalPath.size()-1);

            //connects a shortest path between the last visited vertex
            //and the desired first position
            totalPath.add(DijkstraShortestPath.findPathBetween(g,temp,source));
            input.remove(0);
        }
        boolean[] isVisited = new boolean[input.size()];
        for(int i = 0; i < input.size(); i++){
            double weight = 1000000;
            //values to be stored after inner loop completes
            GraphPath<String,IdentifiedWeightedEdge> tempPath = null;
            int lowIndex = 0;
            String src = "";
            for(int j = 0; j < input.size(); j++){
                if(!isVisited[j]){
                    //finds path from node to node
                    Log.i("TEST" , "" + j);
                    path = DijkstraShortestPath.findPathBetween(g, source, input.get(j));
                    if(path.getWeight() < weight){
                        weight = path.getWeight();
                        src = path.getEndVertex();
                        tempPath = path;
                        lowIndex = j;
                    }
                }
            }
            //storing values
            totalPath.add(tempPath);
            isVisited[lowIndex] = true;
            source = src;
        }
        // the last path is always from wherever you are to the exit gate, so this is hardcoded
        totalPath.add(DijkstraShortestPath.findPathBetween(g,source,"entrance_exit_gate"));
    }

    public ArrayList<RouteExhibitItem> getRoute(){

        int i = 0;
        ArrayList<RouteExhibitItem> output = new ArrayList<RouteExhibitItem>();
        //dummy route exhibit to show where you are currently
        String lastIn = "";
        for(GraphPath<String,IdentifiedWeightedEdge> gr: totalPath){
            String vName = vInfo.get(gr.getEndVertex()).name;
            double distance = 0;
            distance = gr.getWeight();
            ArrayList<String> directions = new ArrayList<String>();
            List<IdentifiedWeightedEdge> edges = gr.getEdgeList();
            List<String> vertices = gr.getVertexList();
            for(IdentifiedWeightedEdge e : gr.getEdgeList()){
                String intro = "Continue on ";
                if(directions.size() == 0 || directions.size() == edges.size()){
                    intro = "Proceed on ";
                }
                String destination = "";
                // big logic thing for parsing whether or not the source node is the last node
                // we visited. allows us to navigate things bidirectionally, despite having
                // source/target relationship in the data
                if(!lastIn.equals("")){
                    if(lastIn.equals(vInfo.get(g.getEdgeSource(e).toString()).name)){
                        destination = vInfo.get(g.getEdgeTarget(e).toString()).name;
                    } else{
                        destination = vInfo.get(g.getEdgeSource(e).toString()).name;
                    }
                } else {
                    if(vInfo.get(g.getEdgeTarget(e).toString()).name.equals("Entrance and Exit Gate")){
                        destination = vInfo.get(g.getEdgeSource(e).toString()).name;
                    } else{
                        destination = vInfo.get(g.getEdgeTarget(e).toString()).name;
                    }
                }

                lastIn = destination;

                String direction = intro
                        + eInfo.get(e.getId()).street + " "
                        + g.getEdgeWeight(e) + " ft towards "
                        + destination;
                directions.add(direction);
            }
            RouteExhibitItem temp = new RouteExhibitItem(vName,distance,directions);
            output.add(temp);

        }
        return output;

    }

    public ArrayList<String> getNodes(){
        ArrayList<String> boop = new ArrayList<>();
        for(GraphPath<String,IdentifiedWeightedEdge> gr : totalPath){
            for(String s: gr.getVertexList()){
                boop.add(s);
            }
        }
        return boop;
    }

    public ArrayList<String> getEdge(){
        ArrayList<String> boop = new ArrayList<>();
        for(GraphPath<String,IdentifiedWeightedEdge> gr : totalPath){
            for(IdentifiedWeightedEdge s: gr.getEdgeList()){
                boop.add(vInfo.get(g.getEdgeSource(s).toString()).name);
                boop.add(vInfo.get(g.getEdgeTarget(s).toString()).name);

            }
        }


        return boop;

    }

    public Map<String,Pair<Double, Double>> getLocation(){
        Map<String, Pair<Double, Double>> res = new HashMap<>();
        ArrayList<String>NodesName = this.getNodes();
        for(String s : NodesName){
            for(ZooData.VertexInfo info : vInfo.values()){
                if(s == info.name){
                    Pair<Double, Double> lo= new Pair<>(info.lat, info.lng);
                    res.put(info.name, lo);
                }
            }
        }
        return res;
    }


}
