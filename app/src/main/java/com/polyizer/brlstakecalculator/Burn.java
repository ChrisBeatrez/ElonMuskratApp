package com.polyizer.brlstakecalculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import org.xml.sax.ErrorHandler;

import java.math.BigDecimal;
import java.math.BigInteger;

import io.api.etherscan.core.impl.EtherScanApi;

public class Burn extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    TextView realBRLTotalSupply;
    TextView BRLTotalSupplyOutput;
    TextView burntsofaroutput;
    TextView burntpercentoutput;
    Button refresh2;
    Thread thread;
    Double d;
    Double mil = 1000000.0;
    TextView EthereumAddress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.burn);

        //getSupportActionBar().hide();
        getWindow().setStatusBarColor(Color.BLACK);
        setContentView(R.layout.burn);
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

        burntsofaroutput = (TextView) findViewById(R.id.burnsofaroutput);
        burntpercentoutput = (TextView) findViewById(R.id.burntpercentoutput);

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
            burntsofaroutput.setText("loading...");
            burntpercentoutput.setText("loading...");
            thread.start();
            BRLTotalSupplyOutput.postDelayed(new Runnable() {
                @Override
                public void run() {BRLTotalSupplyOutput.setText(String.valueOf(d)); burntsofaroutput.setText(String.valueOf(mil - d)); burntpercentoutput.setText(String.valueOf(((mil - d) / mil) * 100));}
            }, 8000);
        } else {
            BRLTotalSupplyOutput.setText("No Internet");
            burntsofaroutput.setText("No Internet");
            burntpercentoutput.setText("No Internet");
        }

        refresh2 = (Button) findViewById(R.id.refresh2);
        refresh2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                refresh();
            }
        });
    }

    public void refresh(){
        if (internetIsConnected() && thread.getState() == Thread.State.NEW) {
            thread.start();
            BRLTotalSupplyOutput.setText("loading...");
            burntsofaroutput.setText("loading...");
            burntpercentoutput.setText("loading...");
            BRLTotalSupplyOutput.postDelayed(new Runnable() {
                @Override
                public void run() {
                    BRLTotalSupplyOutput.setText(String.valueOf(d)); burntsofaroutput.setText(String.valueOf(mil - d)); burntpercentoutput.setText(String.valueOf(((mil - d) / mil) * 100));
                }
            }, 8000);
        } else if (internetIsConnected()){
            BRLTotalSupplyOutput.setText("loading...");
            burntsofaroutput.setText("loading...");
            burntpercentoutput.setText("loading...");
            BRLTotalSupplyOutput.postDelayed(new Runnable() {
                @Override
                public void run() {
                    BRLTotalSupplyOutput.setText(String.valueOf(d));
                }
            }, 8000);
        } else {
            BRLTotalSupplyOutput.setText("No Internet");
            burntsofaroutput.setText("No Internet");
            burntpercentoutput.setText("No Internet");
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
                intent = new Intent(Burn.this, MainActivity.class);
                startActivity(intent);
                return true;
            case R.id.nav_burn:
                intent = new Intent(Burn.this, Burn.class);
                startActivity(intent);
                return true;
            case R.id.nav_about:
                intent = new Intent(Burn.this, About.class);
                startActivity(intent);
                return true;
            case R.id.nav_socials:
                intent = new Intent(Burn.this, Socials.class);
                startActivity(intent);
                return true;
            case R.id.nav_donate:
                intent = new Intent(Burn.this, Donate.class);
                startActivity(intent);
                return true;
            default:
                return true;
        }
    }
}