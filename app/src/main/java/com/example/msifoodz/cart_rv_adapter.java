package com.example.msifoodz;

import android.content.Context;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;

import org.w3c.dom.Text;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import soup.neumorphism.NeumorphCardView;

public class cart_rv_adapter extends RecyclerView.Adapter <cart_rv_adapter.viewHolder>{

    private final cart Cart;

    private final List<Cart_food_item_list> mData;

    public cart_rv_adapter(cart cart, List<Cart_food_item_list> mData) {
        Cart = cart;
        this.mData = mData;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflator=LayoutInflater.from(Cart);
        view=mInflator.inflate(R.layout.card_view_cart_food_items,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, final int position) {

        long total = 0;
        Locale locale=new Locale("en","in");
        NumberFormat frmt=NumberFormat.getCurrencyInstance(locale);
        holder.quantity.setText(String.valueOf((mData.get(position).getQuantity())));
        holder.food_name.setText(mData.get(position).getFood_name());
        long itemPrice = Long.parseLong(String.valueOf(mData.get(position).getFood_price() * mData.get(position).getQuantity()));
        holder.food_price.setText(frmt.format(itemPrice));
        total = total + itemPrice;
        Cart.totalAmount.setText(frmt.format(total));
        holder.foodCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent=new Intent(mContext,food_list.class);
//                intent.putExtra("item_name",mData.get(position).getFood_name());
//                intent.putExtra("price",mData.get(position).getFood_price());
//                intent.putExtra("quantity",mData.get(position).getQuantity());
//                mContext.startActivity(intent);
            }

        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class viewHolder extends RecyclerView.ViewHolder implements View.OnClickListener , View.OnCreateContextMenuListener{

        TextView food_name,food_price;
        TextView quantity;
        NeumorphCardView foodCard;
        clickListener ClickListener;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            food_name=itemView.findViewById(R.id.item_name_id_cart_card_view);
            food_price=itemView.findViewById(R.id.Price_id_cart_card_view);
            quantity=itemView.findViewById(R.id.Quantity_id_cart_card_view);
            foodCard=itemView.findViewById(R.id.card_view_cart_food_item);
            itemView.setOnCreateContextMenuListener(this);
        }

        public void set_cart_item_name(TextView food_name){
            this.food_name=food_name;
        }


        @Override
        public void onClick(View v) {

        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        }

        public  interface clickListener{
            void onItemLongClick(View view,int position);
        }

    }
}
