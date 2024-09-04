package com.polyizer.elonmuskrat;

import static java.text.NumberFormat.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.Locale;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;


public class ReflectionsCalculator extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private double result;
    EditText Volume;
    EditText muskOwned;
    EditText circSupply;
    Button submit;
    Button refresh;
    TextView output;
    Thread thread;
    Double d;
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //getSupportActionBar().hide();
        getWindow().setStatusBarColor(Color.BLACK);
        setContentView(R.layout.reflectionscalculator);
        // Define ColorDrawable object and parse color
        // using parseColor method
        // with color hash code as its parameter

        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("black"));
        getSupportActionBar().setTitle("$MUSK Reflections Calculator");
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

        Volume = (EditText) findViewById(R.id.Volume);
        muskOwned = (EditText) findViewById(R.id.muskOwned);
        circSupply = (EditText) findViewById(R.id.circSupply);
        output = (TextView) findViewById(R.id.output);
        submit = (Button) findViewById(R.id.submit);
        Volume.addTextChangedListener(onTextChangedListener());
        muskOwned.addTextChangedListener(onTextChangedListener());
        circSupply.addTextChangedListener(onTextChangedListener());
        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                calculateInternet();
                /*
                if (internetIsConnected()) {
                    calculateInternet();
                } else {
                    calculateNoInternet();
                }
                */
            }
        });
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

    public void calculateInternet() {
        if (Volume.getText().toString().equals("") || muskOwned.getText().toString().equals("") || circSupply.getText().toString().equals("")){
            output.setText("Please Enter all values");
        } else {
            result = (Double.parseDouble(Volume.getText().toString().replaceAll(",", "")) * 0.01) * (Double.parseDouble(muskOwned.getText().toString().replaceAll(",", "")) / Double.parseDouble(circSupply.getText().toString().replaceAll(",", "")));
            output.setText(String.format("$" + "%,.5f", result));
        }
    }

    public void calculateNoInternet() {
        if (Volume.getText().toString().equals("") || muskOwned.getText().toString().equals("") || circSupply.getText().toString().equals("")){
            output.setText("Please Enter all values");
        } else {
            result = Double.parseDouble(Volume.getText().toString().replaceAll(",", "")) * Double.parseDouble(muskOwned.getText().toString().replaceAll(",", ""));
            output.setText(String.format("$" + "%,.5f", result));
        }
    }

    private TextWatcher onTextChangedListener() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (Volume.getText().hashCode() == s.hashCode()) {
                    Volume.removeTextChangedListener(this);
                    try {
                        String originalString = s.toString();

                        Long longval;
                        if (originalString.contains(",")) {
                            originalString = originalString.replaceAll(",", "");
                        }
                        longval = Long.parseLong(originalString);

                        DecimalFormat formatter = (DecimalFormat) getInstance(Locale.US);
                        formatter.applyPattern("#,###,###,###");
                        String formattedString = formatter.format(longval);

                        //setting text after format to EditText
                        Volume.setText(formattedString);
                        Volume.setSelection(Volume.getText().length());
                    } catch (NumberFormatException nfe) {
                        nfe.printStackTrace();
                    }

                    Volume.addTextChangedListener(this);
                } else if (muskOwned.getText().hashCode() == s.hashCode()) {
                    muskOwned.removeTextChangedListener(this);
                    try {
                        String originalString = s.toString();

                        Long longval;
                        if (originalString.contains(",")) {
                            originalString = originalString.replaceAll(",", "");
                        }
                        longval = Long.parseLong(originalString);

                        DecimalFormat formatter = (DecimalFormat) getInstance(Locale.US);
                        formatter.applyPattern("#,###,###,###");
                        String formattedString = formatter.format(longval);

                        //setting text after format to EditText
                        muskOwned.setText(formattedString);
                        muskOwned.setSelection(muskOwned.getText().length());
                    } catch (NumberFormatException nfe) {
                        nfe.printStackTrace();
                    }
                    muskOwned.addTextChangedListener(this);
                } else if (circSupply.getText().hashCode() == s.hashCode()) {
                    circSupply.removeTextChangedListener(this);
                    try {
                        String originalString = s.toString();

                        Long longval;
                        if (originalString.contains(",")) {
                            originalString = originalString.replaceAll(",", "");
                        }
                        longval = Long.parseLong(originalString);

                        DecimalFormat formatter = (DecimalFormat) getInstance(Locale.US);
                        formatter.applyPattern("#,###,###,###");
                        String formattedString = formatter.format(longval);

                        //setting text after format to EditText
                        circSupply.setText(formattedString);
                        circSupply.setSelection(circSupply.getText().length());
                    } catch (NumberFormatException nfe) {
                        nfe.printStackTrace();
                    }
                    circSupply.addTextChangedListener(this);
                }
            }
        };
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Intent intent;

        switch (id) {
            case R.id.nav_home:
                intent = new Intent(ReflectionsCalculator.this, MainActivity.class);
                startActivity(intent);
                return true;
            case R.id.nav_voltichange:
                intent = new Intent(ReflectionsCalculator.this, Voltichange.class);
                startActivity(intent);
                return true;
            case R.id.nav_dextoolschart:
                intent = new Intent(ReflectionsCalculator.this, DextoolsChart.class);
                startActivity(intent);
                return true;
            case R.id.nav_calculator:
                intent = new Intent(ReflectionsCalculator.this, ReflectionsCalculator.class);
                startActivity(intent);
                return true;
            case R.id.nav_burn:
                intent = new Intent(ReflectionsCalculator.this, Burn.class);
                startActivity(intent);
                return true;
            case R.id.nav_about:
                intent = new Intent(ReflectionsCalculator.this, About.class);
                startActivity(intent);
                return true;
            case R.id.nav_socials:
                intent = new Intent(ReflectionsCalculator.this, Socials.class);
                startActivity(intent);
                return true;
            case R.id.nav_donate:
                intent = new Intent(ReflectionsCalculator.this, Donate.class);
                startActivity(intent);
                return true;
            default:
                return true;
        }
    }
}


