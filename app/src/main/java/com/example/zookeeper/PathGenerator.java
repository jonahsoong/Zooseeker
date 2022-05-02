package com.example.zookeeper;

import android.content.Context;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;

import java.util.ArrayList;
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

    public String[] getOrder(){
        int i = 0;
        String[] order = new String[totalPath.size()-1];
        double[] distance = new double[totalPath.size()-1];
        for(GraphPath<String,IdentifiedWeightedEdge> gr : totalPath){
            String vName = vInfo.get(gr.getEndVertex()).name;
            if(!vName.equals("Entrance and Exit Gate") && i != totalPath.size()-1){
                distance[i] = gr.getWeight();
                order[i] = vName;
            }
            i++;
        }
        return order;
    }

    public double[] getDist(){
        int i = 0;
        double[] distance = new double[totalPath.size()-1];
        for(GraphPath<String,IdentifiedWeightedEdge> gr : totalPath){
            String vName = vInfo.get(gr.getEndVertex()).name;
            if(!vName.equals("Entrance and Exit Gate") && i != totalPath.size()-1){
                distance[i] = gr.getWeight();
            }
            i++;
        }
        return distance;
    }

}
