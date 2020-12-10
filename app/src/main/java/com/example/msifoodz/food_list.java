package com.example.msifoodz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import soup.neumorphism.NeumorphButton;


public class food_list extends AppCompatActivity {

    TextView food_nm_tv;
    TextView amount;
    ImageView detailImage;
    ElegantNumberButton elegantNumberButton;
    NeumorphButton neumorphButton;
    private FirebaseFirestore db,cart;
    Map<String, Object> food_item_lists=new HashMap<>();
    int quantity;
    String fname;
    long fprice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Bundle data= getIntent().getExtras();

        //hooks
        food_nm_tv= findViewById(R.id.food_name_food_list);
        amount = findViewById(R.id.amount);
        detailImage = findViewById(R.id.detail_image);
        elegantNumberButton=findViewById(R.id.elegant_number_button_id_food_list);
        neumorphButton=findViewById(R.id.add_to_cart_btn_id_food_list);
        cart=FirebaseFirestore.getInstance();
        neumorphButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                elegantNumberButton.setVisibility(View.VISIBLE);
                neumorphButton.setVisibility(View.INVISIBLE);
                elegantNumberButton.setNumber("1");
                new Database(getBaseContext()).addToCarts(new Cart_food_item_list(fname,fprice,quantity));
            }
        });
        elegantNumberButton.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
            @Override
            public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {
                quantity=newValue;
                if(newValue==0){
                    elegantNumberButton.setVisibility(View.INVISIBLE);
                    neumorphButton.setVisibility(View.VISIBLE);
                }
                food_item_lists.put("Food_Quantity",quantity);
            }
        });
        String food = data.getString("food_name_key");
        food_nm_tv.setText(food);
        fname=food;
        food_item_lists.put("Food_name",food);
        String category = data.getString("category");
        StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl("gs://msi-foodz.appspot.com/images/"+food+".jpg");
        GlideApp.with(this).load(storageReference).into(detailImage);

        try {
            db = FirebaseFirestore.getInstance();
            db.collection(category)
                    .whereEqualTo("category", category)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                                    Log.d("Success", document.getId() + " => " + document.getData());
                                    Long value = document.getLong(food);
                                    fprice=value;
                                    amount.setText(value.toString());
                                    food_item_lists.put("Food_price",value);
                                }
                            } else {
                                Log.d("Error", "Error getting documents: ", task.getException());
                            }
                        }
                    });
        } catch (Exception e) {
            Log.d("Error", Arrays.toString(e.getStackTrace()));
            e.printStackTrace();

        }
    }

}