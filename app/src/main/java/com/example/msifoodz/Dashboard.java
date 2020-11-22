package com.example.msifoodz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class Dashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    List<Dashboard_food_category_details> food;
    private DrawerLayout drawer;
    NavigationView navigationView;
//    FirebaseAuth firebaseAuth;
//    FirebaseUser firebaseUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Toolbar toolbar=findViewById(R.id.toolbar_id);
        setSupportActionBar(toolbar);
        drawer=findViewById(R.id.drawer_layout_id);
        navigationView=findViewById(R.id.nav_view_id);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        food = new ArrayList<>();
        food.add(new Dashboard_food_category_details("BEVERAGES",R.drawable.beverages));
        food.add(new Dashboard_food_category_details("SNACKS",R.drawable.snacks));
        food.add(new Dashboard_food_category_details("CHOCOLATES",R.drawable.chocolates));
        food.add(new Dashboard_food_category_details("BISCUITS",R.drawable.biscuits));
        food.add(new Dashboard_food_category_details("MEALS",R.drawable.meals));
        food.add(new Dashboard_food_category_details("ROLLS",R.drawable.rolls));

        RecyclerView rv= findViewById(R.id.recycler_view);
        Dashboard_rv_Adapter myAdapter=new Dashboard_rv_Adapter(this,food);
        rv.setLayoutManager(new GridLayoutManager(this,2));
        rv.setAdapter(myAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.top_right_menu,menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.wallet_id_menu:
                startActivity(new Intent(getApplicationContext(),wallet.class));
                break;
            case R.id.cart_id_menu:
                startActivity(new Intent(getApplicationContext(),cart.class));
                break;
            default:
                super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }
        super.onBackPressed();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        switch (id){
            case R.id.profile_id:
                    startActivity(new Intent(getApplicationContext(),Profile.class));
                break;
            case R.id.order_id:
                break;
            case R.id.faq_id:
                startActivity(new Intent(getApplicationContext(),faq.class));

                break;
            case R.id.help_id:
                startActivity(new Intent(getApplicationContext(),help.class));
                break;
            case R.id.share_id:
                break;
            case R.id.logout_id:
                    FirebaseAuth.getInstance().signOut();
                    Intent intent=new Intent(getApplicationContext(),Login.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                break;


        }
        return true;
    }

}