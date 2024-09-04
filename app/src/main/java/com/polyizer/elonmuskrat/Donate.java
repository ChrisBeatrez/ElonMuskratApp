package com.polyizer.elonmuskrat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;


public class Donate extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    TextView EthereumAddress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.donate);

        //getSupportActionBar().hide();
        getWindow().setStatusBarColor(Color.BLACK);
        setContentView(R.layout.donate);
        // Define ColorDrawable object and parse color
        // using parseColor method
        // with color hash code as its parameter

        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("black"));
        getSupportActionBar().setTitle("$MUSK Donate");
        // Set BackgroundDrawable
        getSupportActionBar().setBackgroundDrawable(colorDrawable);
        // drawer layout instance to toggle the menu icon to open
        // drawer and back button to close drawer
        drawerLayout = findViewById(R.id.my_drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);

        // pass the Open and Close toggle for the drawer layout listener
        // to toggle the button
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        // to make the Navigation drawer icon always appear on the action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        EthereumAddress = (TextView) findViewById(R.id.EthereumAddress);
        EthereumAddress.setTextIsSelectable(true);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean internetIsConnected() {
        try {
            String command = "ping -c 1 google.com";
            return (Runtime.getRuntime().exec(command).waitFor() == 0);
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Intent intent;

        switch (id) {
            case R.id.nav_home:
                intent = new Intent(Donate.this, MainActivity.class);
                startActivity(intent);
                return true;
            case R.id.nav_voltichange:
                intent = new Intent(Donate.this, Voltichange.class);
                startActivity(intent);
                return true;
            case R.id.nav_dextoolschart:
                intent = new Intent(Donate.this, DextoolsChart.class);
                startActivity(intent);
                return true;
            case R.id.nav_calculator:
                intent = new Intent(Donate.this, ReflectionsCalculator.class);
                startActivity(intent);
                return true;
            case R.id.nav_burn:
                intent = new Intent(Donate.this, Burn.class);
                startActivity(intent);
                return true;
            case R.id.nav_about:
                intent = new Intent(Donate.this, About.class);
                startActivity(intent);
                return true;
            case R.id.nav_socials:
                intent = new Intent(Donate.this, Socials.class);
                startActivity(intent);
                return true;
            case R.id.nav_donate:
                intent = new Intent(Donate.this, Donate.class);
                startActivity(intent);
                return true;
            default:
                return true;
        }
    }
}