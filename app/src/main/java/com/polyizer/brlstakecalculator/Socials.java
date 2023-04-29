package com.polyizer.brlstakecalculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.VideoView;

import com.google.android.material.navigation.NavigationView;

import java.math.BigDecimal;
import java.math.BigInteger;

import io.api.etherscan.core.impl.EtherScanApi;

public class Socials extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    TextView realBRLTotalSupply;
    TextView BRLTotalSupplyOutput;
    Button refresh3;
    Thread thread;
    Double d;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.socials);

        //getSupportActionBar().hide();
        getWindow().setStatusBarColor(Color.BLACK);
        setContentView(R.layout.socials);
        // Define ColorDrawable object and parse color
        // using parseColor method
        // with color hash code as its parameter

        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("black"));
        getSupportActionBar().setTitle("$BRL Staking Calculator");
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

        BRLTotalSupplyOutput = (TextView) findViewById(R.id.BRLTotalSupplyOutput);
        realBRLTotalSupply = (TextView) findViewById(R.id.realBRLTotalSupply);
        VideoView vv = findViewById(R.id.video_view);

        vv.setOnCompletionListener ( new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                vv.start();
            }
        });

        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/"
                + R.raw.spinning_logo);

        vv.setVideoURI(uri);
        vv.start();
        vv.requestFocus();
        vv.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
            }
        });
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    EtherScanApi api = new EtherScanApi();
                    BigInteger brlSupply = api.stats().supply("0x6291d951c5d68f47eD346042E2f86A94c253Bec4");
                    BigDecimal a = new BigDecimal(brlSupply);
                    BigDecimal b = new BigDecimal("0.000000000000000001");
                    BigDecimal c = a.multiply(b);
                    d = c.doubleValue();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        if (internetIsConnected()) {
            BRLTotalSupplyOutput.setText("loading...");
            thread.start();
            BRLTotalSupplyOutput.postDelayed(new Runnable() {
                @Override
                public void run() {
                    BRLTotalSupplyOutput.setText(String.valueOf(d));
                }
            }, 8000);
        } else {
            realBRLTotalSupply.setText("No Internet");
        }
        TextView textView1;
        TextView textView2;
        TextView textView3;
        TextView textView4;
        TextView textView5;
        if(internetIsConnected()){
            textView1 = findViewById(R.id.telegram);

            textView1.setMovementMethod(LinkMovementMethod.getInstance());
        }
        if(internetIsConnected()){
            textView2 = findViewById(R.id.dextools);

            textView2.setMovementMethod(LinkMovementMethod.getInstance());
        }
        if(internetIsConnected()){
            textView3 = findViewById(R.id.website);

            textView3.setMovementMethod(LinkMovementMethod.getInstance());
        }
        if(internetIsConnected()){
            textView4 = findViewById(R.id.youtube);

            textView4.setMovementMethod(LinkMovementMethod.getInstance());
        }
        if(internetIsConnected()){
            textView5 = findViewById(R.id.twitter);

            textView5.setMovementMethod(LinkMovementMethod.getInstance());
        }

        refresh3 = (Button) findViewById(R.id.refresh3);
        refresh3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                refresh();
            }
        });
    }

    public void refresh(){
        if (internetIsConnected() && thread.getState() == Thread.State.NEW) {
            thread.start();
            realBRLTotalSupply.setText("BRL Total Supply: ");
            BRLTotalSupplyOutput.setText("loading...");
            BRLTotalSupplyOutput.postDelayed(new Runnable() {
                @Override
                public void run() {
                    BRLTotalSupplyOutput.setText(String.valueOf(d));
                }
            }, 8000);
        } else if (internetIsConnected()){
            BRLTotalSupplyOutput.setText("loading...");
            BRLTotalSupplyOutput.postDelayed(new Runnable() {
                @Override
                public void run() {
                    BRLTotalSupplyOutput.setText(String.valueOf(d));
                }
            }, 8000);
        } else {
            realBRLTotalSupply.setText("No Internet");
        }
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

        switch(id){
            case R.id.nav_calculator:
                intent = new Intent(Socials.this, MainActivity.class);
                startActivity(intent);
                return true;
            case R.id.nav_burn:
                intent = new Intent(Socials.this, Burn.class);
                startActivity(intent);
                return true;
            case R.id.nav_about:
                intent = new Intent(Socials.this, About.class);
                startActivity(intent);
                return true;
            case R.id.nav_socials:
                intent = new Intent(Socials.this, Socials.class);
                startActivity(intent);
                return true;
            case R.id.nav_donate:
                intent = new Intent(Socials.this, Donate.class);
                startActivity(intent);
                return true;
            default:
                return true;
        }
    }
}