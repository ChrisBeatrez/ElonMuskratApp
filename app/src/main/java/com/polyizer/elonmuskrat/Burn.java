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
import android.widget.Button;
import android.widget.TextView;
import android.view.View;
import com.google.android.material.navigation.NavigationView;
import io.goodforgod.api.etherscan.model.Balance;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import io.goodforgod.api.etherscan.EtherScanAPI;

public class Burn extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    TextView realMUSKTotalSupply;
    TextView MUSKTotalSupplyOutput;
    TextView burntsofaroutput;
    TextView burntpercentoutput;
    Button refresh2;
    Thread thread;
    Double d;
    double totalMusk = 1337000000;
    DecimalFormat df;
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
        getSupportActionBar().setTitle("$MUSK Burn");
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
        MUSKTotalSupplyOutput = (TextView) findViewById(R.id.MUSKTotalSupplyOutput);
        realMUSKTotalSupply = (TextView) findViewById(R.id.realMUSKTotalSupply);

        burntsofaroutput = (TextView) findViewById(R.id.burnsofaroutput);
        burntpercentoutput = (TextView) findViewById(R.id.burntpercentoutput);
        df = new DecimalFormat("#,###");
        df.setMaximumFractionDigits(8);
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    EtherScanAPI api = EtherScanAPI.builder().build();
                    Balance balance = api.account().balance("0x000000000000000000000000000000000000dEaD", "0x578F382244c53ad5658Bc8cEBD465c26b708c56d");
                    BigDecimal a = balance.getBalanceInWei().asGwei();
                    BigDecimal b = new BigDecimal("0.000000001");
                    BigDecimal c = a.multiply(b);
                    d = c.doubleValue();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        if (internetIsConnected()) {
            MUSKTotalSupplyOutput.setText("loading...");
            burntsofaroutput.setText("loading...");
            burntpercentoutput.setText("loading...");
            thread.start();
            MUSKTotalSupplyOutput.postDelayed(new Runnable() {
                @Override
                public void run() {MUSKTotalSupplyOutput.setText(df.format(totalMusk)); burntsofaroutput.setText(df.format(d)); burntpercentoutput.setText(String.valueOf((100 - ((totalMusk - d) / totalMusk * 100))));}
            }, 8000);
        } else {
            MUSKTotalSupplyOutput.setText("No Internet");
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
            MUSKTotalSupplyOutput.setText("loading...");
            burntsofaroutput.setText("loading...");
            burntpercentoutput.setText("loading...");
            MUSKTotalSupplyOutput.postDelayed(new Runnable() {
                @Override
                public void run() {
                    MUSKTotalSupplyOutput.setText(df.format(totalMusk)); burntsofaroutput.setText(String.valueOf(d)); burntpercentoutput.setText(String.valueOf((100 - ((totalMusk - d) / totalMusk * 100))));
                }
            }, 8000);
        } else if (internetIsConnected()){
            MUSKTotalSupplyOutput.setText("loading...");
            burntsofaroutput.setText("loading...");
            burntpercentoutput.setText("loading...");
            MUSKTotalSupplyOutput.postDelayed(new Runnable() {
                @Override
                public void run() {
                    MUSKTotalSupplyOutput.setText(df.format(totalMusk)); burntsofaroutput.setText(String.valueOf(d)); burntpercentoutput.setText(String.valueOf((100 - ((totalMusk - d) / totalMusk * 100))));
                }
            }, 8000);
        } else {
            MUSKTotalSupplyOutput.setText(df.format(totalMusk));
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

        switch (id) {
            case R.id.nav_home:
                intent = new Intent(Burn.this, MainActivity.class);
                startActivity(intent);
                return true;
            case R.id.nav_voltichange:
                intent = new Intent(Burn.this, Voltichange.class);
                startActivity(intent);
                return true;
            case R.id.nav_dextoolschart:
                intent = new Intent(Burn.this, DextoolsChart.class);
                startActivity(intent);
                return true;
            case R.id.nav_calculator:
                intent = new Intent(Burn.this, ReflectionsCalculator.class);
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