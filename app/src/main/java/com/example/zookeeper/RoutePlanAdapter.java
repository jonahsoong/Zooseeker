package com.example.zookeeper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;

public class RoutePlanAdapter extends RecyclerView.Adapter<RoutePlanAdapter.ViewHolder>{
    private List<RouteExhibitItem> routeItems = Collections.emptyList();
    double prevDist = 0;

    public void setRouteExhibitItems(List<RouteExhibitItem> newRouteItems){
        this.routeItems.clear();
        this.routeItems = newRouteItems;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.exhibit_list_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.setRouteItem(routeItems.get(position), prevDist);
        prevDist += routeItems.get(position).distance;

    }

    @Override
    public int getItemCount() {
        return routeItems.size();
    }

    public long getItemId(int position){
        return routeItems.get(position).id;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView exhibitView;
        private final TextView distView;
        private RouteExhibitItem routeItem;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.exhibitView = itemView.findViewById(R.id.exhibit_name_text);
            this.distView = itemView.findViewById(R.id.exhibit_dist_text);
        }

        public RouteExhibitItem getRouteItem() { return routeItem;}

        public void setRouteItem(RouteExhibitItem routeItem, double prevDist){
            this.routeItem = routeItem;
            this.exhibitView.setText(routeItem.sink);
            this.distView.setText((routeItem.distance+prevDist) + "ft");
        }
    }

}

