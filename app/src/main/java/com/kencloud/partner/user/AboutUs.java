package com.kencloud.partner.user;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.ProgressBar;

public class AboutUs extends AppCompatActivity {
WebView webView;
    ProgressBar progressbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        progressbar=(ProgressBar) findViewById(R.id.progress);

        webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("http://www.swashconvergence.com/kencloud-partner/partner-overview.html");


//        swash about us and faq links

//        http://www.swashconvergence.com/kencloud-partner/faqs.html
//        http://www.swashconvergence.com/kencloud-partner/partner-overview.html
    }
}





