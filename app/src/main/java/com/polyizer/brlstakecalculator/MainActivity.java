package com.polyizer.brlstakecalculator;

import static java.text.NumberFormat.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.EditText;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Locale;

import io.api.etherscan.core.impl.EtherScanApi;
import io.api.etherscan.model.Balance;

import java.math.BigInteger;
import android.view.MenuItem;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.material.navigation.NavigationView;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private double VolTax;
    private double VolTaxStakeAmount;
    private double stakedBRLConvert;
    private double result;
    private static double tax = .02;
    private static double totalSupply = 1000000;
    private static int base = 70;
    private double math;
    private double mathmath;
    EditText Volume;
    EditText stakedBRL;
    EditText Boosters;
    Button submit;
    Button refresh;
    TextView output;
    Thread thread;
    Double d;
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    TextView realBRLTotalSupply;
    TextView BRLTotalSupplyOutput;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //getSupportActionBar().hide();
        getWindow().setStatusBarColor(Color.BLACK);
        setContentView(R.layout.activity_main);
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
                    /*
                    BigInteger brlSupply = api.stats().supply("0x6291d951c5d68f47eD346042E2f86A94c253Bec4");
                    BigDecimal a = new BigDecimal(brlSupply);
                    BigDecimal b = new BigDecimal("0.000000000000000001");
                    BigDecimal c = a.multiply(b);
                    d = c.doubleValue();
                     */
                    Balance balance = api.account().balance("0x0e94f95913A66BBD4A32aF5f65f32F19e2859cE4", "0x6291d951c5d68f47eD346042E2f86A94c253Bec4");
                    BigDecimal a = new BigDecimal(balance.getGwei());
                    BigDecimal b = new BigDecimal("0.000000001");
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
        refresh = (Button) findViewById(R.id.refresh);
        Volume = (EditText) findViewById(R.id.Volume);
        stakedBRL = (EditText) findViewById(R.id.stakedBRL);
        Boosters = (EditText) findViewById(R.id.Boosters);
        output = (TextView) findViewById(R.id.output);
        submit = (Button) findViewById(R.id.submit);
        Volume.addTextChangedListener(onTextChangedListener());
        stakedBRL.addTextChangedListener(onTextChangedListener());
        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (internetIsConnected() && thread.getState() == Thread.State.NEW) {
                    thread.start();
                    BRLTotalSupplyOutput.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            realBRLTotalSupply.setText("BRL Total Supply: ");
                            BRLTotalSupplyOutput.setText(String.valueOf(d));
                        }
                    }, 8000);
                } else {
                    if (internetIsConnected()) {
                        calculateInternet();
                    } else {
                        calculateNoInternet();
                    }
                }
            }
        });

        refresh.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                refresh();
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
        if (Volume.getText().toString().equals("") || stakedBRL.getText().toString().equals("") || Boosters.getText().toString().equals("")) {
            output.setText("Please Enter all values");
        } else {
            stakedBRLConvert = Double.parseDouble(stakedBRL.getText().toString().replaceAll(",", "")) / d;
            System.out.println("stakedBRLConvert = " + stakedBRLConvert);
            VolTax = Double.parseDouble(Volume.getText().toString().replaceAll(",", "")) * tax;
            //System.out.println("VolTax = " + VolTax);
            VolTaxStakeAmount = VolTax * stakedBRLConvert;
            //System.out.println("VolTaxStakeAmount = " + VolTaxStakeAmount);
            result = VolTaxStakeAmount;
            //System.out.println("result = " + result);
            if (Integer.parseInt(Boosters.getText().toString()) > 0) {
                math = base + 6 * Integer.parseInt(Boosters.getText().toString());
                mathmath = math / 100.0;
                result = result * mathmath;
            } else {
                result = result * base / 100;
            }
            output.setText(String.format("$" + "%,.5f", result));
        }
    }

    public void calculateNoInternet() {
        if (Volume.getText().toString().equals("") || stakedBRL.getText().toString().equals("") || Boosters.getText().toString().equals("")) {
            output.setText("Please Enter all values");
        } else {
            stakedBRLConvert = Double.parseDouble(stakedBRL.getText().toString().replaceAll(",", "")) / totalSupply;
            //System.out.println("stakedBRLConvert = " + stakedBRLConvert);
            VolTax = Double.parseDouble(Volume.getText().toString().replaceAll(",", "")) * tax;
            //System.out.println("VolTax = " + VolTax);
            VolTaxStakeAmount = VolTax * stakedBRLConvert;
            //System.out.println("VolTaxStakeAmount = " + VolTaxStakeAmount);
            result = VolTaxStakeAmount;
            //System.out.println("result = " + result);
            if (Integer.parseInt(Boosters.getText().toString()) > 0) {
                math = base + 6 * Integer.parseInt(Boosters.getText().toString());
                mathmath = math / 100.0;
                result = result * mathmath;
            } else {
                result = result * base / 100;
            }
            output.setText(String.format("$" + "%,.5f", result));
        }
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
                } else if (stakedBRL.getText().hashCode() == s.hashCode()) {
                    stakedBRL.removeTextChangedListener(this);
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
                        stakedBRL.setText(formattedString);
                        stakedBRL.setSelection(stakedBRL.getText().length());
                    } catch (NumberFormatException nfe) {
                        nfe.printStackTrace();
                    }
                    stakedBRL.addTextChangedListener(this);
                }
            }
        };
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Intent intent;

        switch(id){
            case R.id.nav_calculator:
                intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
                return true;
            case R.id.nav_burn:
                intent = new Intent(MainActivity.this, Burn.class);
                startActivity(intent);
                return true;
            case R.id.nav_about:
                intent = new Intent(MainActivity.this, About.class);
                startActivity(intent);
                return true;
            case R.id.nav_socials:
                intent = new Intent(MainActivity.this, Socials.class);
                startActivity(intent);
                return true;
            case R.id.nav_donate:
                intent = new Intent(MainActivity.this, Donate.class);
                startActivity(intent);
                return true;
            default:
                return true;
        }
    }
}


