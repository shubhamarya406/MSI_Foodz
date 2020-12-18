package com.example.msifoodz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class cart extends AppCompatActivity {

    RecyclerView recyclerView;
    TextView totalAmount;
    private Context mContext = this;

    public cart() {
    }

    List<Cart_food_item_list> carts=new ArrayList<>();
    cart_rv_adapter cartAdapter;
    Button cart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        recyclerView=findViewById(R.id.recycler_view_cart);
        recyclerView.setHasFixedSize(true);
        totalAmount=findViewById(R.id.txtTotal);
        cart = findViewById(R.id.btn_payment_id_cart);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        loadCartList();
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(mContext,paymentpage.class);
                mContext.startActivity(in);
            }
        });

    }
    public void loadCartList(){
        carts=new Database(this).getCarts();
        cartAdapter=new cart_rv_adapter(this,carts);
        cartAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(cartAdapter);

        /*int totalprc=0;
        for(Cart_food_item_list i:carts)
            totalprc+=i.getFood_price()*i.getQuantity();
        Locale locale=new Locale("en","in");
        NumberFormat frmt=NumberFormat.getCurrencyInstance(locale);
        totalAmount.setText(frmt.format(totalprc));*/
        new Database(this).setTotalPrice(totalAmount);
    }
}