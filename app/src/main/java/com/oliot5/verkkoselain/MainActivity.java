package com.oliot5.verkkoselain;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    WebView web;
    EditText webPage;
    Button rButton;
    String pageUrl;
    int checkLoad = 0;
    ArrayList<String> urlList;
    int listCounter = -1;
    String latestUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        urlList = new ArrayList<>();
        rButton = (Button) findViewById(R.id.refreshButton);
        web = (WebView) findViewById(R.id.webView);
        webPage = (EditText) findViewById(R.id.editTextWebPage);
        web.setWebViewClient(new WebViewClient());
        web.getSettings().setJavaScriptEnabled(true);

        webPage.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if((keyEvent.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    web.loadUrl("http://"+webPage.getText());
                    urlList.add("http://"+webPage.getText());
                    listCounter++;
                    checkLoad = 0;
                }
                return false;
            }
        });
    }
    public void refreshButton (View v) {
        pageUrl = web.getUrl();
        System.out.println(pageUrl);
        web.loadUrl(pageUrl);
    }

    public void shoutOutButton (View v) {
        if(checkLoad == 0) {
            web.loadUrl("file:///android_asset/index.html");
            urlList.add("file:///android_asset/index.html");
            listCounter++;
            checkLoad = 1;
        }
        web.evaluateJavascript("javascript:shoutOut()",null);
    }

    public void initializeButton (View v) {
        if(checkLoad == 0) {
            web.loadUrl("file:///android_asset/index.html");
            urlList.add("file:///android_asset/index.html");
            listCounter++;
            checkLoad = 1;
        }
        web.evaluateJavascript("javascript:initialize()",null);
    }

    public void backButton (View v) {
        if(listCounter > 0) {
            web.loadUrl((urlList.get(listCounter - 1)));
            listCounter--;
        }
    }

    public void nextButton (View v) {
        if(listCounter < urlList.size()) {
            web.loadUrl((urlList.get(listCounter + 1)));
            listCounter++;
        }
    }
}