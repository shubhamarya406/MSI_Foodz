package com.example.msifoodz;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import soup.neumorphism.NeumorphCardView;

public class Dashboard_rv_Adapter extends RecyclerView.Adapter <Dashboard_rv_Adapter.viewHolder>{

    private Context mContext;
    private List<Dashboard_food_category_details> mData;

    public Dashboard_rv_Adapter(Context mContext, List<Dashboard_food_category_details> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflator=LayoutInflater.from(mContext);
        view=mInflator.inflate(R.layout.dashboard_card_view_item,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, final int position) {
        holder.food_category.setText(mData.get(position).getFoodCategory());
        holder.food_image.setImageResource(mData.get(position).getThumbnail());
        holder.foodCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext,food_list.class);
                intent.putExtra("category",mData.get(position).getFoodCategory());
                //intent.putExtra("image",mData.get(position).getThumbnail());
                mContext.startActivity(intent);
            }

        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class viewHolder extends RecyclerView.ViewHolder{
        TextView food_category;
        ImageView food_image;
        NeumorphCardView foodCard;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            food_category= itemView.findViewById(R.id.food_category);
            food_image=itemView.findViewById(R.id.food_image);
            foodCard=itemView.findViewById(R.id.card_view);
        }
    }
}
