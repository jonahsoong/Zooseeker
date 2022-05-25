/*<<<<<<< HEAD
package com.example.zookeeper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.util.Consumer;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;
import java.util.function.BiConsumer;

public class RouteAdapter extends RecyclerView.Adapter<RouteAdapter.ViewHolder> {
    private List<RouteItem> routeItems = Collections.emptyList();
    private Consumer<RouteItem> onCheckBoxClicked;
    private BiConsumer<RouteItem, TextView> onDeleteButtonClicked;
    private BiConsumer<RouteItem, String> onTextEditedHandler;
    private TextView numAnimals;

    public void setRouteItems(List<RouteItem> newRouteItems) {
        this.routeItems.clear();
        this.routeItems = newRouteItems;
        notifyDataSetChanged();
    }

    public void setOnDeleteButtonClicked(BiConsumer<RouteItem, TextView> onDeleteButtonClicked, TextView numAnimals) {
        this.onDeleteButtonClicked = onDeleteButtonClicked;
        this.numAnimals = numAnimals;
        //  numAnimals = update;
        // update.setText("Number of Exhibits: " + routeItems.size());
        // Log.d("size", routeItems.size() + "");
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.route_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setRouteItem(routeItems.get(position));
    }

    @Override
    public int getItemCount() {
        return routeItems.size();
    }

    @Override
    public long getItemId(int position) {
        return routeItems.get(position).id;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;
        //private final CheckBox checkBox;
        private final TextView deleteBtn;
        private RouteItem routeItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.textView = itemView.findViewById(R.id.animal_text);
            this.deleteBtn = itemView.findViewById(R.id.del_btn);

            //delete button func
            this.deleteBtn.setOnClickListener(view -> {
                if (onDeleteButtonClicked == null) return;
                onDeleteButtonClicked.accept(routeItem, numAnimals);
                //numAnimals.setText("" + routeItems.size());
            });

        }
        public RouteItem getRouteItem() {return routeItem;}

        public void setRouteItem(RouteItem routeItem) {
            this.routeItem = routeItem;
            this.textView.setText(routeItem.animal);
        }

    }
}
=======*/
package com.example.zookeeper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.util.Consumer;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;
import java.util.function.BiConsumer;

public class RouteAdapter extends RecyclerView.Adapter<RouteAdapter.ViewHolder> {
    private List<RouteItem> routeItems = Collections.emptyList();
    //delete button and numAnimal TextView
    private BiConsumer<RouteItem, TextView> onDeleteButtonClicked;
    private BiConsumer<RouteItem, String> onTextEditedHandler;
    private TextView numAnimals;

    //update Route Items
    public void setRouteItems(List<RouteItem> newRouteItems) {
        this.routeItems.clear();
        this.routeItems = newRouteItems;
        notifyDataSetChanged();
    }
    //pass in TextView to help update number of exhibits
    public void setOnDeleteButtonClicked(BiConsumer<RouteItem, TextView> onDeleteButtonClicked, TextView numAnimals) {
        this.onDeleteButtonClicked = onDeleteButtonClicked;
        this.numAnimals = numAnimals;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.route_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setRouteItem(routeItems.get(position));
    }

    @Override
    //get number of items
    public int getItemCount() {
        return routeItems.size();
    }

    @Override
    public long getItemId(int position) {
        return routeItems.get(position).id;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;
        private final TextView deleteBtn;
        private RouteItem routeItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.textView = itemView.findViewById(R.id.animal_text);
            this.deleteBtn = itemView.findViewById(R.id.del_btn);

            //delete button func
            this.deleteBtn.setOnClickListener(view -> {
                if (onDeleteButtonClicked == null) return;
                onDeleteButtonClicked.accept(routeItem, numAnimals);
            });

        }
        public RouteItem getRouteItem() {return routeItem;}

        public void setRouteItem(RouteItem routeItem) {
            this.routeItem = routeItem;
            this.textView.setText(routeItem.animal);
        }

    }
}
