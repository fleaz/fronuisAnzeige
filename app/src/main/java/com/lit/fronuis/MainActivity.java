package com.lit.fronuis;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity
{
    WebView webView;
    WebView webWeather;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //webView = new WebView(MainActivity.this);
        setContentView(R.layout.activity_main);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Radio.class);
                startActivity(intent);
            }
        });
        webView = findViewById(R.id.web);
        webWeather = findViewById(R.id.webv);
        webWeather.getSettings().setJavaScriptEnabled(true);
        webWeather.setWebViewClient(new WebViewClient());
        webWeather.loadUrl("file:///android_asset/weather.html");
        //setContentView(R.layout.activity_main);
        //hideSystemUI();
        //webv = findViewById(R.id.webv);
        WebViewClient client = new WebViewClient();
        webView.setWebViewClient(client);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        String newUA= "Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.9.0.4) Gecko/20100101 Firefox/4.0";
        //webView.getSettings().setUserAgentString(newUA);
        webView.loadUrl("http://192.168.178.58/#/dashboard");
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                webView.evaluateJavascript("document.getElementsByTagName('site-header')[0].remove()", null);
                webView.evaluateJavascript("document.getElementsByTagName('main-menu')[0].remove()", null);
                webView.evaluateJavascript("document.getElementsByTagName('mat-tab-header')[0].remove()", null);
                webView.evaluateJavascript("document.getElementsByClassName('ng-tns-c329-9 card-inner cardwrapper-col-sm-5 cardwrapper-col-md-6 cardwrapper-col-md-offset-0 ng-trigger ng-trigger-triggerFade ng-star-inserted')[0].remove()", null);
                webView.evaluateJavascript("document.getElementsByClassName('ng-tns-c329-9 card-inner cardwrapper-col-sm-12 cardwrapper-col-md-6 cardwrapper-col-md-offset-0 ng-trigger ng-trigger-triggerFade ng-star-inserted')[0].remove()", null);
                //webView.evaluateJavascript("document.getElementsByClassName('container-fluid app-content ng-tns-c48-0')[0].style.marginLeft = 0", null);
                //webView.evaluateJavascript("document.getElementsByClassName('beneath-header ng-tns-c48-0')[0].style.top = 0", null);
                //webView.evaluateJavascript("document.getElementsByClassName('container-fluid app-content ng-tns-c48-0')[0].style.paddingLeft = 0", null);
                webView.evaluateJavascript("document.getElementsByClassName('mat-card-title ng-tns-c329-9 ng-star-inserted')[0].remove()", null);
                webView.evaluateJavascript("document.getElementsByClassName('container-fluid app-content ng-tns-c48-0')[0].style.margin = 0", null);
                webView.evaluateJavascript("document.getElementsByClassName('container-fluid app-content ng-tns-c48-0')[0].style.backgroundColor = \"#000000\"", null);
                webView.evaluateJavascript("document.getElementsByClassName('mat-card mat-focus-indicator ng-tns-c331-9')[0].style.backgroundColor = \"#000000\"", null);
                webView.scrollBy(0, 187);
            }
        }, 20000);

    }

    @Override
    protected void onResume()
    {
        super.onResume();
        //setContentView(webView);
        //hideSystemUI();
    }

    private void hideSystemUI()
    {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        // Set the content to appear under the system bars so that the
                        // content doesn't resize when the system bars hide and show.
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }
}