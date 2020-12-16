package com.example.msifoodz;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by User on 04-Jul-18.
 */

public class Database //extends SQLiteOpenHelper
  {

    private FirebaseFirestore db;
    FirebaseAuth firebaseAuth;
   // private static final String DB_NAME = "MsiFoodzDB.db";
   // private static final String TABLE_NAME = "OrderDetails";
   // private static final int DB_VERSION = 1;
      Context context;
    private static final String TAG = "Basel";
    private static final String ERROR = "Error";
    private static final String SUCCESS = "Success";

      public Database(Context context) {
          this.context = context;
      }

//      @Override
//    public void onCreate(SQLiteDatabase db) {
//        db.execSQL("CREATE TABLE " + TABLE_NAME + " (Food_name TEXT, Food_Price INTEGER, Food_Quantity INTEGER)");
//    }
//
//    @Override
//    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//
//    }


    public List<Cart_food_item_list> getCarts() {

        db = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        final List<Cart_food_item_list> results = new ArrayList<>();;



        DocumentReference docIdRef = db.collection("carts").document(firebaseAuth.getCurrentUser().getUid());
        docIdRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(SUCCESS,document.getData().toString());
                        String arr[] = document.getData().toString().replace("{","").replace("}","").split(",");
                        for(int i = 0; i < arr.length; i++){
                            results.add(new Cart_food_item_list(arr[i].split("=")[0],
                                    Long.parseLong(arr[i].split("=")[1].split(";")[1]),
                                    Integer.parseInt(arr[i].split("=")[1].split(";")[0])));
                        }
                    } else {
                       Log.d(ERROR,"cart is empty!");
                    }
                } else {
                    Log.d(ERROR, "Failed with: ", task.getException());
                }
            }
        });
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return results;
        }

    public void addToCarts(Cart_food_item_list order){

        Map<String, Object> data = new HashMap<>();
        data.put(order.getFood_name(),order.getQuantity()+";"+order.getFood_price());


        db = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        CollectionReference carts = db.collection("carts");
        DocumentReference docIdRef = db.collection("carts").document(firebaseAuth.getCurrentUser().getUid());
        docIdRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        carts.document(firebaseAuth.getCurrentUser().getUid()).update(data);
                    } else {
                        carts.document(firebaseAuth.getCurrentUser().getUid()).set(data);
                    }
                } else {
                    Log.d(ERROR, "Failed with: ", task.getException());
                }
            }
        });

    }
}
