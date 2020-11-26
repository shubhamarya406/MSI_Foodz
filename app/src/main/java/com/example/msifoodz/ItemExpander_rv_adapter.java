package com.example.msifoodz;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import soup.neumorphism.NeumorphCardView;

public class ItemExpander_rv_adapter extends RecyclerView.Adapter <ItemExpander_rv_adapter.viewHolder>{
    private Context mContext;
    private List<String> food_name;

    public ItemExpander_rv_adapter(Context mContext, List<String> food_name) {
        this.mContext = mContext;
        this.food_name = food_name;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflator=LayoutInflater.from(mContext);
        view=mInflator.inflate(R.layout.card_view_food_item,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        holder.food_name.setText(food_name.get(position));
        holder.food_item_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext,food_list.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return food_name.size();
    }

    public static class viewHolder extends RecyclerView.ViewHolder{
        TextView food_name;
        NeumorphCardView food_item_card;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            food_name=itemView.findViewById(R.id.food_name_card_view);
            food_item_card=itemView.findViewById(R.id.food_item_card_view);
        }
    }

}
