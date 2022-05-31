package com.example.zookeeper;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

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
    private ArrayList<String> input;
    // for user story #73 Detailed Directions
    private ArrayList<RouteExhibitItem> route;
    public int position;
    public PathGenerator(Context context){
        // 2. Load the information about our nodes and edges...
        vInfo = ZooData.loadVertexInfoJSON(context,"exhibit_info.json");
        eInfo = ZooData.loadEdgeInfoJSON(context,"trail_info.json");
        // 1. Load the graph...
        g = ZooData.loadZooGraphJSON(context,"zoo_graph.json");
        //conditional values

        totalPath = new ArrayList<>();
        route = new ArrayList<>();

        position = 0;

    }
    public void updateInputs(ArrayList<String> input){
        this.input = input;
    }
    /*
    generatePlan turns the input into a full Plan. It filters the input, then computes the shortest
    paths between all vertices in the input.
    Whatever is in the first position of the input ArrayList is the first vertex of the path to be
    computed. If this is the first time generatePlan has been called, then it simply generates
    from the start vertex. If the plan is pre-populated, then any verticies in the stored plan
    which are not part of the input are deleted, and the plan is repopulated from the start vertex
    onwards.

    it automatically calls and stores the result of getRoute, for use in the methods getNext(), getPrev(),
    and getCurrent()

     */
    public void generatePlan(ArrayList<String> input){
        // ensure that input ArrayList's first element is the starting element in the entire list.
        // "source" and "sink" are graph terms for the start and end
        // find grouped exhibits and replace them with their parent exhibit
        int l = 0;
        for(String s : input){
            if(vInfo.get(s).group_id != null){
                input.set(l,vInfo.get(s).group_id);
            }
            l++;
        }
        for(String x : input){
            Log.i("HOLO",x);
        }
        //assume input will come as List<String> format
        //check and delete duplicates
        for(int j = 0; j < input.size();j++){
            for(int i = j+1; i < input.size(); i++){
                if(input.get(j).equals(input.get(i))){
                    input.remove(i);
                }
            }
        }
        for(String x : input){
            Log.i("HOLO",x);
        }
        for(GraphPath<String, IdentifiedWeightedEdge> ggg : totalPath){
            Log.d("HAHA" , ggg.getStartVertex() + " | " + ggg.getEndVertex());
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
            //what remains should be all traversed paths, plus whatever path starts
            //with the new closest node at input.get(0)
            Log.d("huh" , totalPath.size()+"");
            int startSize = totalPath.size() - input.size();
            if(input.get(0) == "entrance_exit_gate"){
                while(totalPath.size() != 0){
                    totalPath.remove(totalPath.size()-1);
                }
            } else{
                int count = 1;
                int n = totalPath.size()-1;
                while(!totalPath.get(n).getEndVertex().equals(input.get(0))){
                        count++;
                        n--;
                }

                while(count >= 0) {
                    totalPath.remove(totalPath.size() - 1);
                    count--;
                }

                //finds the path which occurs just before the desired first position
                //stores the start vertex value and deletes the path

                String temp = totalPath.get(totalPath.size()-1).getStartVertex();

                //connects a shortest path between the last visited vertex
                //and the desired first position
                totalPath.add(DijkstraShortestPath.findPathBetween(g,temp,source));
                input.remove(0);
            }

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

                    Log.d("HELP", input.get(j));
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
        totalPath.remove(0);
        getRoute();
    }

    public ArrayList<RouteExhibitItem> getRoute(){
        route = new ArrayList<>();

        int i = 0;

        //dummy route exhibit to show where you are currently
        String lastIn = "";
        for(GraphPath<String,IdentifiedWeightedEdge> gr: totalPath){
            Log.d("hello","loop");
            String vSink = vInfo.get(gr.getEndVertex()).id;
            String vSource = vInfo.get(gr.getStartVertex()).id;
            double distance = 0;
            distance = gr.getWeight();
            ArrayList<String> directions1 = new ArrayList<String>();
            ArrayList<String> directions2 = new ArrayList<String>();
            List<IdentifiedWeightedEdge> edges = gr.getEdgeList();
            List<String> vertices = gr.getVertexList();

            //lets brief directions determine when a trail is traversed across landmarks (prevStreet)
            //allows the summation of weights when encountering multiple edges with the same trail
            String prevStreet = "";
            double aggregate = 0.0;

            for(IdentifiedWeightedEdge e : gr.getEdgeList()){
                String intro = "Continue on ";
                if(directions1.size() == 0 || directions1.size() == edges.size()){
                    intro = "Proceed on ";
                }
                String destination = "";
                // big logic thing for parsing whether or not the source node is the last node
                // we visited. allows us to navigate things bidirectionally, despite having
                // source/target relationship in the data
                if(!lastIn.equals("")){
                    if(lastIn.contains(vInfo.get(g.getEdgeSource(e).toString()).name)){
                        destination = vInfo.get(g.getEdgeTarget(e).toString()).name;
                    } else{
                        destination = vInfo.get(g.getEdgeSource(e).toString()).name;
                    }
                } else {
                    if(vInfo.get(g.getEdgeTarget(e).toString()).name.contains("Entrance and Exit Gate")){
                        destination = vInfo.get(g.getEdgeSource(e).toString()).name;
                    } else{
                        destination = vInfo.get(g.getEdgeTarget(e).toString()).name;
                    }
                }

                lastIn = destination;
                String direction;
                //adds all strings to detailed directions Arrays, and
                //sums up direction distance over repeated streets for brief directions.
                if(!eInfo.get(e.getId()).street.equals(prevStreet)){
                    aggregate = g.getEdgeWeight(e);
                    direction = intro
                            + eInfo.get(e.getId()).street + " "
                            + g.getEdgeWeight(e) + " ft towards "
                            + destination;
                    directions1.add(direction);
                    directions2.add(direction);
                } else{

                    double fullWeight = g.getEdgeWeight(e) + aggregate;
                    direction = intro
                            + eInfo.get(e.getId()).street + " "
                            + fullWeight + " ft towards "
                            + destination;
                    directions1.add(direction);
                    directions2.set(directions2.size()-1,direction);
                    aggregate = fullWeight;
                }
                prevStreet = eInfo.get(e.getId()).street;
            }
            RouteExhibitItem temp = new RouteExhibitItem(vSource,vSink,distance,directions1,directions2);
            route.add(temp);
        }
        return route;

    }
    /*
    whenever the route state changes, we change what the directions say. calling recalcPath
    should do that.
    TODO: have recalcPath set the source for findPathBetween to the closest current vertex, call from LocationChecker?
    for now, just using whatever is at the current iterator count
     */
    private void recalcPath(String sink, String source){
        GraphPath<String,IdentifiedWeightedEdge> temp = totalPath.get(position);
        totalPath.set(position,DijkstraShortestPath.findPathBetween(g,source,sink));
        getRoute();
        totalPath.set(position,temp);
    }
    //iterates forward along route, returning the next RouteExhibitItem
    public RouteExhibitItem getNext(){
        if(position < route.size()-1){
            position++;
            recalcPath(totalPath.get(position).getEndVertex(),totalPath.get(position).getStartVertex());
            return route.get(position);
        } else{
            return null;
        }
    }
    public RouteExhibitItem peekNext(){
        if(position < route.size()-1) {
            return route.get(position + 1);
        }
        return null;
    }
    //iterates backwards along route list, returning
    public RouteExhibitItem getPrev(){
        if(position > 0){
            position--;
            recalcPath(totalPath.get(position).getStartVertex(),totalPath.get(position).getEndVertex());
            return route.get(position);
        } else{
            return null;
        }
    }


    public RouteExhibitItem getCurrent(){
        recalcPath(totalPath.get(position).getEndVertex(),totalPath.get(position).getStartVertex());
        return route.get(position);
    }

    public void skipExhibit(){
        ArrayList<String> temp = new ArrayList<>();
        for(GraphPath<String, IdentifiedWeightedEdge> gr : totalPath){
            Log.d("help" ,gr.getStartVertex()+ gr.getEndVertex() );
            temp.add(gr.getStartVertex());
            temp.add(gr.getEndVertex());
        }
        Log.d("what" ,position+ "");
        Log.d("what", totalPath.get(position).getEndVertex());
        int n = position;
        for(int i = 0; i < temp.size(); i++){
            temp.remove(totalPath.get(position).getEndVertex());
            if(temp.contains("entrance_exit_gate")){
                temp.remove("entrance_exit_gate");
            }
        }
        if(position == 0){
            temp.add(0,"entrance_exit_gate");
        } else{
            temp.add(0,totalPath.get(position).getStartVertex());
        }

        generatePlan(temp);




    }

    public boolean isFinished(){
        if(position == route.size() -1){
            return true;
        } else{
            return false;
        }
    }

    public boolean isEntrance(){
        if(position == 0){
            return true;
        } else {
            return false;
        }
    }

    public int size(){
        return route.size();
    }

    public ArrayList<RouteExhibitItem> getRemaining(){
        ArrayList<RouteExhibitItem> a = new ArrayList<>();
        for(int i = position; i < route.size(); i++){
            a.add(route.get(i));
        }
        return a;
    }
    public ArrayList<String> getExhibitString(){
        ArrayList<RouteExhibitItem> b = getRemaining();
        ArrayList<String> a = new ArrayList<>();
        for(int i = 0; i < b.size(); i++){
            a.add(b.get(i).sink);
        }
        return a;
    }
    public ArrayList<LatLng> getRemainingLocations(){
        ArrayList<RouteExhibitItem> b = getRemaining();
        ArrayList<LatLng> a = new ArrayList<>();
        for(int i = 0; i < b.size(); i++){
            a.add(new LatLng(vInfo.get(b.get(i).sink).lat, vInfo.get(b.get(i).sink).lng));
        }
        return a;
    }
    public ArrayList<LatLng> getLocations(ArrayList<String> verticies){
        ArrayList<LatLng> a = new ArrayList<>();
        for(String s: verticies){
            a.add(new LatLng(vInfo.get(s).lat,vInfo.get(s).lng));
        }
        return a;
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
