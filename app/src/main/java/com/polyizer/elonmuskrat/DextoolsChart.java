package com.polyizer.elonmuskrat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.android.material.navigation.NavigationView;

import java.io.InputStream;
import java.math.BigDecimal;

import io.goodforgod.api.etherscan.EtherScanAPI;
import io.goodforgod.api.etherscan.model.Balance;


public class DextoolsChart extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    private WebView wv;
    String html = "<iframe class=\"youtube-player\" style=\"border: 0; width: 100%; height: 95%; padding:0px; margin:0px\" id=\"ytplayer\" type=\"text/html\" src=\"http://www.youtube.com/embed/WBYnk3zR0os"
            + "?fs=0\" frameborder=\"0\">\n"
            + "</iframe>";
    String voltichange = "<iframe src=\"https://voltichange.net/api/widget/?chain=1&\" width=\"500\" height=\"600\" darktheme=false& tokenin=Native& tokenout=\"0x578f382244c53ad5658bc8cebd465c26b708c56d& slippage=1\" frameborder=\"0\" class=\"voltichange-widget\"></iframe>";
    String dextoolsChart = "<iframe id=\"dextools-widget\" title=\"DEXTools Trading Chart\" width=\"500\" height=\"100%\"src=\"https://www.dextools.io/widget-chart/en/ether/pe-light/0xb1cd770bba3c847cdaa3e51a61364f36ee0c6d79?theme=light&chartType=2&chartResolution=30&drawingToolbars=false\"></iframe>";
    WebSettings webSettings;
    DisplayMetrics displayMetrics;


/*
<!-- 1. Place this link tag on the <head> of your page: -->

<link rel="stylesheet" href="https://voltichange.net/css/widget.css" />


<!-- 2. Place this iframe tag at the location where the Voltichange Widget will be:  -->

<iframe
    src="https://voltichange.net/api/widget/?chain=1&darktheme=false&tokenin=Native&tokenout=0x7f792db54B0e580Cdc755178443f0430Cf799aCa&slippage=1"
    frameborder="0"
    class="voltichange-widget"></iframe>

<!-- END OF VOLTICHANGE WIDGET CODE -->
*/




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dextoolschart);

        //getSupportActionBar().hide();
        getWindow().setStatusBarColor(Color.BLACK);
        setContentView(R.layout.dextoolschart);
        // Define ColorDrawable object and parse color
        // using parseColor method
        // with color hash code as its parameter

        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("black"));
        getSupportActionBar().setTitle("Dextools Chart");
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
        wv = findViewById(R.id.widget);

        webSettings = wv.getSettings();
        wv.setInitialScale(getScale());
        wv.getSettings().setLoadsImagesAutomatically(true);
        wv.getSettings().setJavaScriptEnabled(true);
        wv.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        wv.getSettings().setDomStorageEnabled(true);
        //wv.getSettings().setLoadWithOverviewMode(true);
        //wv.getSettings().setUseWideViewPort(true);
        wv.setBackgroundColor(0);
        wv.loadData(dextoolsChart, "text/html", null);
    }
    private int getScale(){
        displayMetrics = new DisplayMetrics();
        // on below line we are getting metrics for display using window manager.
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        Double val = new Double(width)/new Double(515);
        val = val * 100d;
        return val.intValue();
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
            case R.id.nav_home:
                intent = new Intent(DextoolsChart.this, MainActivity.class);
                startActivity(intent);
                return true;
            case R.id.nav_voltichange:
                intent = new Intent(DextoolsChart.this, Voltichange.class);
                startActivity(intent);
                return true;
            case R.id.nav_dextoolschart:
                intent = new Intent(DextoolsChart.this, DextoolsChart.class);
                startActivity(intent);
                return true;
            case R.id.nav_calculator:
                intent = new Intent(DextoolsChart.this, ReflectionsCalculator.class);
                startActivity(intent);
                return true;
            case R.id.nav_burn:
                intent = new Intent(DextoolsChart.this, Burn.class);
                startActivity(intent);
                return true;
            case R.id.nav_about:
                intent = new Intent(DextoolsChart.this, About.class);
                startActivity(intent);
                return true;
            case R.id.nav_socials:
                intent = new Intent(DextoolsChart.this, Socials.class);
                startActivity(intent);
                return true;
            case R.id.nav_donate:
                intent = new Intent(DextoolsChart.this, Donate.class);
                startActivity(intent);
                return true;
            default:
                return true;
        }
    }
}