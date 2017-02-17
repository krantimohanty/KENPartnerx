package com.kencloud.partner.user;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class FAQs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faqs);
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Saving your data.. Please wait..");
        progressDialog.show();
        WebView webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("http://www.swashconvergence.com/kencloud-partner/faqs.html");
        progressDialog.hide();
    }
}
