package com.example.msifoodz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Sign_up extends AppCompatActivity {
    private static final String TAG = "TAG";
    Button already_user,signup_btn;
    EditText name_s,email_s,phone_s,password_s;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Hooks
        already_user=findViewById(R.id.already_user_signup);
        signup_btn=findViewById(R.id.signup_btn_signup);
        name_s=findViewById(R.id.name_signup);
        email_s=findViewById(R.id.email_signup);
        phone_s=findViewById(R.id.phone_number_signup);
        password_s=findViewById(R.id.password_signup);
        fAuth=FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();

        if(fAuth.getCurrentUser() !=null){
            startActivity(new Intent(getApplicationContext(),Dashboard.class));
            finish();
        }

        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressDialog progressDialog =new ProgressDialog(Sign_up.this);
                progressDialog.show();
                progressDialog.setContentView(R.layout.progress_dialog);
                progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                final String email=email_s.getText().toString().trim();
                final String name=name_s.getText().toString();
                final String phone=phone_s.getText().toString();
                String password=password_s.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    email_s.setError("Email is Required");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    password_s.setError("Password is Required");
                    return;
                }
                if(password.length()<8){
                    password_s.setError("Password must greater than or equal to 8 characters");
                    return;
                }
                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    //send verification link
                                    FirebaseUser fuser=fAuth.getCurrentUser();
                                    fuser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(Sign_up.this,"Verification mail has been send",Toast.LENGTH_SHORT).show();

                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.d(TAG, "onFailure: Email not sent"+e.getMessage());
                                        }
                                    });
                                    Toast.makeText(Sign_up.this,"User Created...",Toast.LENGTH_SHORT).show();
                                    String userID= Objects.requireNonNull(fAuth.getCurrentUser()).getUid();
                                    DocumentReference documentReference=fStore.collection("users").document(userID);
                                    Map<String,Object> user= new HashMap<>();
                                    user.put("Name",name_s);
                                    user.put("Email",email_s);
                                    user.put("Phone",phone_s);
                                    documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Log.d(TAG,"OnSuccess: User created with userID:"+userID);
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.d(TAG,"on failuer :"+e.toString());
                                        }
                                    });
                                    progressDialog.dismiss();
                                    startActivity(new Intent(getApplicationContext(),Login.class));
                                }
                                else{
                                    Toast.makeText(Sign_up.this,"Error : "+task.getException(),Toast.LENGTH_LONG).show();
                                    progressDialog.dismiss();
                                }
                            }
                        }
                );
            }
        });
        already_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Sign_up.this,Login.class);
                startActivity(intent);
            }
        });
    }
}