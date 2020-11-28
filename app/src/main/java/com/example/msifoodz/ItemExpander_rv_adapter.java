package com.example.msifoodz;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.time.Instant;
import java.util.List;

import soup.neumorphism.NeumorphCardView;
import soup.neumorphism.NeumorphImageView;


public class ItemExpander_rv_adapter extends RecyclerView.Adapter <ItemExpander_rv_adapter.viewHolder>{
    private Context mContext;
    private List<String> food_name;
    private String category;
    ImageView food_image_card_view;

    public ItemExpander_rv_adapter(Context mContext, List<String> food_name, String category) {
        this.mContext = mContext;
        this.food_name = food_name;
        this.category = category;
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
        StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl("gs://msi-foodz.appspot.com/images/"+food_name.get(position)+".jpg");
        GlideApp.with(mContext).load(storageReference).fitCenter().into(holder.food_image_card_view);
        holder.food_item_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext,food_list.class);
                intent.putExtra("food_name_key",food_name.get(position));
                intent.putExtra("category",category);
                mContext.startActivity(intent);
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
        ImageView food_image_card_view;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            food_name=itemView.findViewById(R.id.food_name_card_view);
            food_item_card=itemView.findViewById(R.id.food_item_card_view);
            food_image_card_view = itemView.findViewById(R.id.food_image_card_view);
        }
    }

}
