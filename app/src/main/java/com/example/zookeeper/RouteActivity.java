package com.example.zookeeper;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class RouteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);
        generatePlan();
    }

    public void generatePlan(){
        // "source" and "sink" are graph terms for the start and end
        List<String> input = Arrays.asList("lions","gorillas", "gators","arctic_foxes");
        boolean[] isVisited = new boolean[input.size()];
        String start = "entrance_exit_gate";

        // 1. Load the graph...
        Graph<String, IdentifiedWeightedEdge> g = ZooData.loadZooGraphJSON(this,"zoo_graph.json");
        List<GraphPath<String,IdentifiedWeightedEdge>> totalPath = new ArrayList<GraphPath<String, IdentifiedWeightedEdge>>();
        GraphPath<String, IdentifiedWeightedEdge> path;

        // 2. Load the information about our nodes and edges...
        Map<String, ZooData.VertexInfo> vInfo = ZooData.loadVertexInfoJSON(this,"node_info.json");
        Map<String, ZooData.EdgeInfo> eInfo = ZooData.loadEdgeInfoJSON(this,"edge_info.json");

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
                    //List<IdentifiedWeightedEdge> edges = path.getEdgeList();
                    //source = g.getEdgeTarget(edges.get(edges.size()-1)).toString();
                }
            }
            totalPath.add(tempPath);
            isVisited[lowIndex] = true;
            source = src;
        }
        totalPath.add(DijkstraShortestPath.findPathBetween(g,source,start));
        for(GraphPath<String,IdentifiedWeightedEdge> gr : totalPath){
            String lastEntry = "";
            for(String s : gr.getVertexList()){
                    Log.d("LOOKHERE", vInfo.get(s).name);
            }
        }



        /*for(IdentifiedWeightedEdge e : path.getEdgeList()) {
            System.out.printf("  %d. Walk %.0f meters along %s from '%s' to '%s'.\n",
                    i,
                    g.getEdgeWeight(e),
                    eInfo.get(e.getId()).street,
                    vInfo.get(g.getEdgeSource(e).toString()).name,
                    vInfo.get(g.getEdgeTarget(e).toString()).name);
            i++;
        }*/
    }

}