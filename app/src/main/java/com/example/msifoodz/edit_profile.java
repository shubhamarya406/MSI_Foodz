package com.example.msifoodz;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import soup.neumorphism.NeumorphButton;

public class edit_profile extends AppCompatActivity {

    EditText name_et,email_et,phone_et,department_et,year_of_adm_et;
    FirebaseFirestore fstore;
    FirebaseAuth fAuth;
    String userID;
    NeumorphButton update_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__profile);

        Spinner s = findViewById(R.id.Gender_spinner_edit_profile);
        Spinner s1 = findViewById(R.id.Shift_spinner_edit_profile);
        Spinner s2 = findViewById(R.id.Course_spinner_edit_profile);


        ArrayAdapter<CharSequence> adapter =ArrayAdapter.createFromResource(this,R.array.GENDER, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        s.setAdapter(adapter);


        ArrayAdapter<CharSequence> adapter1 =ArrayAdapter.createFromResource(this,R.array.SHIFT, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        s1.setAdapter(adapter1);

        ArrayAdapter<CharSequence> adapter2 =ArrayAdapter.createFromResource(this,R.array.COURSE, android.R.layout.simple_spinner_dropdown_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        s2.setAdapter(adapter2);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        name_et=findViewById(R.id.name_id_edit_profile);
        email_et=findViewById(R.id.email_id_edit_profile);
        phone_et=findViewById(R.id.phone_num_id_edit_profile);
        department_et=findViewById(R.id.Department_edit_profile);
        phone_et=findViewById(R.id.Year_of_adm_edit_profile);

        fstore=FirebaseFirestore.getInstance();
        fAuth=FirebaseAuth.getInstance();
        userID=fAuth.getCurrentUser().getUid();
        update_btn=findViewById(R.id.update_btn_edit_profile);
        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ProgressDialog progressDialog = new ProgressDialog(edit_profile.this);
                progressDialog.show();
                progressDialog.setContentView(R.layout.progress_dialog);
                progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                DocumentReference documentReference=fstore.collection("users").document(userID);
                if(!name_et.getText().toString().equals("")){
                    documentReference.update("Name",name_et.getText().toString());
                }
                if (!email_et.getText().toString().equals("")){
                    documentReference.update("Email",email_et.getText().toString());
                }
                if (!year_of_adm_et.getText().toString().equals("")){
                    documentReference.update("Year_of_adm",year_of_adm_et.getText().toString());
                }
                if (!phone_et.getText().toString().equals("")){
                    documentReference.update("Phone",phone_et.getText().toString());
                }
                if (!year_of_adm_et.getText().toString().equals("")){
                    documentReference.update("Phone",phone_et.getText().toString());
                }
                if (!s.getSelectedItem().toString().equals("")){
                    documentReference.update("Gender",s.getSelectedItem().toString());
                }
                if (!s1.getSelectedItem().toString().equals("")){
                    documentReference.update("Shift",s1.getSelectedItem().toString());
                }
                if (!s2.getSelectedItem().toString().equals("")){
                    documentReference.update("Course",s2.getSelectedItem().toString());
                }
                Toast.makeText(edit_profile.this,"Profile Updated Successfully",Toast.LENGTH_SHORT).show();

                progressDialog.dismiss();
                startActivity(new Intent(getApplicationContext(),Dashboard.class));
            }
        });

    }
}