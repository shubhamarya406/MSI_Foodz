package com.example.msifoodz;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import soup.neumorphism.NeumorphButton;

public class Profile extends AppCompatActivity {

    TextView phone_p,email_p,name_p;
    NeumorphButton edit_btn;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        phone_p=findViewById(R.id.phone_num_id_profile);
        email_p=findViewById(R.id.email_id_profile);
        name_p=findViewById(R.id.name_id_profile);
        fAuth=FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();
        userID=fAuth.getCurrentUser().getUid();
        edit_btn=findViewById(R.id.edit_btn_id_profile);
        DocumentReference documentReference=fStore.collection("users").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                phone_p.setText(value.getString("Phone"));
                email_p.setText(value.getString("Email"));
                name_p.setText(value.getString("Name"));
            }
        });

        edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),edit_profile.class));
            }
        });
    }
}