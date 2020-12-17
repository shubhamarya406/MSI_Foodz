package com.example.msifoodz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import soup.neumorphism.NeumorphCardView;

public class paymentpage extends AppCompatActivity {
    public static String payerName, UpiId, msgNote, sendAmount, status;
    NeumorphCardView gpayCard;
    public static final String GPAY_PACKAGE_NAME = "com.google.android.apps.nbu.paisa.user";
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paymentpage);
        Bundle extra = getIntent().getExtras();
       gpayCard = findViewById(R.id.googlepay_id_payment);
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

            }

        });


    }

    private Uri getUpiPaymentUri(String payerName, String upiId, String msgNote, String sendAmount) {
        return  new Uri.Builder()
                .scheme("upi")
                .authority("pay")
                .appendQueryParameter("pa",upiId)
                .appendQueryParameter("pn",payerName)
                .appendQueryParameter("tn",msgNote)
                .appendQueryParameter("am",sendAmount)
                .appendQueryParameter("cu","INR")
                .build();
    }
    private void payWithGpay(String packageName){

        if(isAppInstalled(this,packageName)){

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(uri);
            intent.setPackage(packageName);
            startActivityForResult(intent,0);

        }
        else{
            Toast.makeText(paymentpage.this,"Google pay is not installed. Please install and try again.", Toast.LENGTH_SHORT).show();
        }

    }
    public static boolean isAppInstalled(Context context, String packageName){
        try{
            context.getPackageManager().getApplicationInfo(packageName,0);
            return true;
        }catch (PackageManager.NameNotFoundException e){
            return false;
        }
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null) {
            status = data.getStringExtra("Status").toLowerCase();

        }
        if ((RESULT_OK == resultCode) && status.equals("success")) {
            Toast.makeText(paymentpage.this, "Transaction successful. ", Toast.LENGTH_SHORT).show();
//            msg.setText("Transaction successful of ₹" + sendAmount);
//            msg.setTextColor(Color.GREEN);

        }

        else{
            Toast.makeText(paymentpage.this, "Transaction cancelled or failed please try again.", Toast.LENGTH_SHORT).show();
        //    msg.setText("Transaction Failed of ₹" + sendAmount);
         //  msg.setTextColor(Color.RED);
        }

    }

}