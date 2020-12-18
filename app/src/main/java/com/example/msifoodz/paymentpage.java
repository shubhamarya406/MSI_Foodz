package com.example.msifoodz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import soup.neumorphism.NeumorphCardView;

public class paymentpage extends AppCompatActivity {

    NeumorphCardView gpayCard;
    public static final String GPAY_PACKAGE_NAME = "com.google.android.apps.nbu.paisa.user";
    EditText name, upiId, amount, note;
    TextView msg;
    Button pay;
    Uri uri;
    String approvalRefNo;

    public static String payerName, UpiId, msgNote, sendAmount, status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gpay);
        Bundle extra = getIntent().getExtras();
        name = findViewById(R.id.name);
        upiId = findViewById(R.id.upi_id);
        amount = findViewById(R.id.amount);
        note = findViewById(R.id.transaction_note);

        msg = findViewById(R.id.status);
        pay = findViewById(R.id.pay);
       /*gpayCard = findViewById(R.id.googlepay_id_payment);
        gpayCard.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v ) {

                payerName = "MSI FOODZ";
                UpiId = "shubhamarya406@oksbi";
                msgNote = "Food Payment";
                sendAmount = extra.getString("totalamount");

                if(!payerName.equals("") && !UpiId.equals("") && !msgNote.equals("")) //&& !sendAmount.equals("")){
                {
                    uri = getUpiPaymentUri(payerName, UpiId, msgNote, sendAmount);
                    payWithGpay(GPAY_PACKAGE_NAME);

                }
                else {
                    Toast.makeText(paymentpage.this,"Fill all above details and try again.", Toast.LENGTH_SHORT).show();


                }

            }*/
        //initialising default value
        name.setText("");
        upiId.setText("soumyaawasthi05@okicici");
        note.setText("payment");
        amount.setText("1");

        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                payerName = name.getText().toString();
                UpiId = upiId.getText().toString();
                msgNote = note.getText().toString();
                sendAmount = amount.getText().toString();

                try {
                    if (!payerName.equals("") && !upiId.equals("") && !msgNote.equals("") && !sendAmount.equals("")) {


                        uri = new Uri.Builder()
                                .scheme("upi")
                                .authority("pay")
                                .appendQueryParameter("pa", UpiId)
                                .appendQueryParameter("pn", payerName)
                                .appendQueryParameter("tn", msgNote)
                                .appendQueryParameter("am", sendAmount)
                                .appendQueryParameter("cu", "INR")
                                .build();
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(uri);
                        intent.setPackage(GPAY_PACKAGE_NAME);
                        startActivityForResult(intent, 123);

                    } else {
                        Toast.makeText(paymentpage.this, "Fill all above details and try again.", Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    Toast.makeText(paymentpage.this, "Google Pay is not setup on this device! Please set it up and try again!", Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null) {
            status = data.getStringExtra("Status").toLowerCase();
            approvalRefNo = data.getStringExtra("txnRef");
        }
        if ((RESULT_OK == resultCode) && status.equals("success")) {
            Toast.makeText(paymentpage.this, "Transaction successful. ", Toast.LENGTH_SHORT).show();
               msg.setText("Transaction successful of ₹" + sendAmount);
            msg.setTextColor(Color.GREEN);

        }

        else{
            Toast.makeText(paymentpage.this, "Transaction cancelled or failed please try again.", Toast.LENGTH_SHORT).show();
        //    msg.setText("Transaction Failed of ₹" + sendAmount);
         //  msg.setTextColor(Color.RED);
        }

    }

}