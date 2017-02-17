package com.kencloud.partner.user;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class TrainingActivity extends AppCompatActivity {
WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training);
        webView = (WebView) findViewById(R.id.web_training);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("http://www.swashconvergence.com/channel-partners.html");

    }
}
