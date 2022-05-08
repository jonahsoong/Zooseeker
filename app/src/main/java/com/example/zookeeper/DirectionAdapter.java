package com.example.zookeeper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DirectionAdapter extends RecyclerView.Adapter<DirectionAdapter.ViewHolder>{
    private List<String> DirectionItems = Collections.emptyList();

    public void setDirectionItems(List<String> newDirectionItems){
        this.DirectionItems.clear();
        this.DirectionItems =  newDirectionItems;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.direction_list_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DirectionAdapter.ViewHolder holder, int position) {
        holder.setDirectionText(DirectionItems.get(position));

    }

    @Override
    public int getItemCount() {
        return DirectionItems.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView DirectionView;
        private String direction;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.DirectionView = itemView.findViewById(R.id.direction_name_text);
        }

        public String getDirection() { return direction;}

        public void setDirectionText(String direction){
            this.direction = direction;
            this.DirectionView.setText(direction);

        }
    }



}
