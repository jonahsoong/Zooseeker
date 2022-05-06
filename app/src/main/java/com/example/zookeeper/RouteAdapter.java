package com.example.zookeeper;
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
    private Consumer<RouteItem> onDeleteButtonClicked;
    private BiConsumer<RouteItem, String> onTextEditedHandler;

    public void setRouteItems(List<RouteItem> newRouteItems) {
        this.routeItems.clear();
        this.routeItems = newRouteItems;
        notifyDataSetChanged();
    }

//    public void setOnCheckBoxClickedHandler(Consumer<TodoListItem> onCheckBoxClicked) {
//        this.onCheckBoxClicked = onCheckBoxClicked;
//    }

//    public void setOnTextEditedHandler(BiConsumer<TodoListItem, String> onTextEdited) {
//        this.onTextEditedHandler = onTextEdited;
//    }
//
    public void setOnDeleteButtonClicked(Consumer<RouteItem> onDeleteButtonClicked) {
        this.onDeleteButtonClicked = onDeleteButtonClicked;
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
        //    this.checkBox = itemView.findViewById(R.id.completed);
            this.deleteBtn = itemView.findViewById(R.id.del_btn);

//            this.checkBox.setOnClickListener(view -> {
//                if (onCheckBoxClicked == null) return;
//                onCheckBoxClicked.accept(todoItem);
//            });

//            this.textView.setOnFocusChangeListener((view, hasFocus) -> {
//                if (!hasFocus) {
//                    onTextEditedHandler.accept(todoItem, textView.getText().toString());
//                }
//            });

            this.deleteBtn.setOnClickListener(view -> {
                if (onDeleteButtonClicked == null) return;
                onDeleteButtonClicked.accept(routeItem);
            });

        }
        public RouteItem getRouteItem() {return routeItem;}

        public void setRouteItem(RouteItem routeItem) {
            this.routeItem = routeItem;
            this.textView.setText(routeItem.animal);
            //this.checkBox.setChecked(todoItem.completed);
        }

    }
}

