package com.example.msifoodz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class ItemExpander extends AppCompatActivity {

    private FirebaseFirestore db;

    public void setTextFromDb(String category, TextView tv){
        try{
            db = FirebaseFirestore.getInstance();
            db.collection("category")
                    .whereEqualTo("Category", category)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.d("Success", document.getId() + " => " + document.getData());
                                    List<String> data = (List<String>)document.get("Items");
                                    tv.setText("");
                                    for(int i = 0; i < data.size(); i++){
                                        tv.append(data.get(i));
                                        tv.append("\n");
                                    }
                                }
                            } else {
                                Log.d("Error", "Error getting documents: ", task.getException());
                            }
                        }
                    });
        }catch(Exception e)
        {
            Log.d("Error",e.getStackTrace().toString());
            e.printStackTrace();

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_expander);

        TextView tv = (TextView)findViewById(R.id.categoryItems);
        Bundle data = getIntent().getExtras();
        if(data == null){
            return ;
        }

        String category;

        int pos = Integer.parseInt(data.getString("Position"));


        if(pos == 0){
            category = "Beverages";
            setTextFromDb(category,tv);
        }
        else if(pos == 1){
            category = "Snacks";
            setTextFromDb(category,tv);
        }
        else if(pos == 2){
            category = "Chocolates";
            setTextFromDb(category,tv);
        }
        else if(pos == 3){
            category = "Biscuits";
            setTextFromDb(category,tv);
        }
        else if(pos == 4){
            category = "Meals";
            setTextFromDb(category,tv);
        }
        else if(pos == 5){
            category = "Rolls";
            setTextFromDb(category,tv);
        }
        else{
            category = "Not selected";
        }

        Log.d("Success",category);
    }
}