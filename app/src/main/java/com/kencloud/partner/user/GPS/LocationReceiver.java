package com.kencloud.partner.user.GPS;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;

import com.google.android.gms.location.FusedLocationProviderApi;

public class LocationReceiver extends BroadcastReceiver 
{
	Location location;
	Intent intent;
    @Override
    public void onReceive(Context context, Intent intent) 
    {
    	location = intent.getParcelableExtra(FusedLocationProviderApi.KEY_LOCATION_CHANGED);
    	intent = new Intent(context, LocationHandlerService.class);    	
        Bundle mBundle = new Bundle(); 
        mBundle.putParcelable("location", location);
        intent.putExtras(mBundle);
    	context.startService(intent);
    }
}
