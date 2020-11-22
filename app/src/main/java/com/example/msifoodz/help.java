package com.example.msifoodz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import soup.neumorphism.NeumorphCardView;

public class help extends AppCompatActivity {

    private NeumorphCardView call,email;
    private TextView num,mail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        call=findViewById(R.id.call_id_help);
        email=findViewById(R.id.email_id_help);
        num=findViewById(R.id.number_id_help);
        mail=findViewById(R.id.mail_id_help);
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String uri="tel : "+num.getText().toString().trim();
                startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel",num.getText().toString().trim(),null)));
            }
        });
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_SENDTO,Uri.fromParts("mailto",mail.getText().toString().trim(),null)));
            }
        });
    }
}