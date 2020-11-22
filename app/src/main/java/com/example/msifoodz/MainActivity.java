package com.example.msifoodz;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    //Splash Screen
    private static int SPLASH_TIME_OUT=5000;
    View first,second,third,fourth,fifth,sixth,logo;
    TextView slogan,name;
    Animation top,bottom,middle,middle_right;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        top= AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottom= AnimationUtils.loadAnimation(this,R.anim.bottom_animation);
        middle= AnimationUtils.loadAnimation(this,R.anim.middle_animation);
        middle_right= AnimationUtils.loadAnimation(this,R.anim.middle_right_animation);

        //hooks
        first=findViewById(R.id.firstLine);
        second=findViewById(R.id.secondLine);
        third=findViewById(R.id.thirdLine);
        fourth=findViewById(R.id.fourthLine);
        fifth=findViewById(R.id.fifthLine);
        sixth=findViewById(R.id.sixthLine);
        logo=findViewById(R.id.logo);
        slogan=findViewById(R.id.slogan);
        name=findViewById(R.id.name);
        first.setAnimation(top);
        second.setAnimation(top);
        third.setAnimation(top);
        fourth.setAnimation(top);
        fifth.setAnimation(top);
        sixth.setAnimation(top);
        logo.setAnimation(middle);
        slogan.setAnimation(bottom);
        name.setAnimation(middle_right);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(MainActivity.this,Sign_up.class);
                    startActivity(intent);
                    finish();
                }
        },SPLASH_TIME_OUT);
    }
}