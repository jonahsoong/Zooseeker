package com.example.zookeeper;

import android.content.Context;
import android.util.Log;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class PathGenerator {
    private Map<String, ZooData.VertexInfo> vInfo;
    private Map<String, ZooData.EdgeInfo> eInfo;
    private Graph<String, IdentifiedWeightedEdge> g;
    private List<GraphPath<String, IdentifiedWeightedEdge>> totalPath;
    public PathGenerator(Context context){
        // 2. Load the information about our nodes and edges...
        vInfo = ZooData.loadVertexInfoJSON(context,"node_info.json");
        eInfo = ZooData.loadEdgeInfoJSON(context,"edge_info.json");
        // 1. Load the graph...
        g = ZooData.loadZooGraphJSON(context,"zoo_graph.json");
        totalPath = new ArrayList<>();
    }
    public void generatePlan(ArrayList<String> input){
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
        boolean[] isVisited = new boolean[input.size()];
        String start = "entrance_exit_gate";

        GraphPath<String, IdentifiedWeightedEdge> path;

        String source = "entrance_exit_gate";
        for(int i = 0; i < input.size(); i++){
            double weight = 1000000;
            //values to be stored after inner loop completes
            GraphPath<String,IdentifiedWeightedEdge> tempPath = null;
            int lowIndex = 0;
            String src = "";
            for(int j = 0; j < input.size(); j++){
                if(!isVisited[j]){
                    //finds path from node to node
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
        totalPath.add(DijkstraShortestPath.findPathBetween(g,source,start));

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

}
