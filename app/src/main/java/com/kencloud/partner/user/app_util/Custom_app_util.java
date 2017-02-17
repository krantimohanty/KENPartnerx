package com.kencloud.partner.user.app_util;

import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.SpannedString;
import android.view.View;

public class Custom_app_util {

    public static void customSnackbar(String message, Context context, boolean setAction, String actionString) {
        if (setAction == true) {
            Snackbar.make(((AppCompatActivity) context).findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
                    .setAction(actionString, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    })
                    .setActionTextColor(Color.RED)
                    .show();
        } else {
            Snackbar.make(((AppCompatActivity) context).findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
                    .show();
        }

    }

    public static SpannedString linkedText(SpannedString text) {
        String s = "<font color='#FF669900'>" + text + "</font>";
        SpannedString str = new SpannedString(Html.fromHtml(s));
        return str;
    }


}
