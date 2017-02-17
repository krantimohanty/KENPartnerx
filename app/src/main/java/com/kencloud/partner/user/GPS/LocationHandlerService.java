package com.kencloud.partner.user.GPS;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.location.Location;
import android.widget.Toast;

import com.kencloud.partner.user.app_util.Constants;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class LocationHandlerService  extends IntentService
{    
    SharedPreferences pref;
    Editor editor;
    
    public LocationHandlerService() 
    {
        super("LocationHandlerService");
    }
    public static final String TAG = "LocationHandlerService";
 
    @Override
    protected void onHandleIntent(Intent intent) 
    {
    	pref= getSharedPreferences("Location", Context.MODE_PRIVATE);
    	Location location = (Location)intent.getParcelableExtra("location");
		Constants.flagLocation = true;
    	if(location!=null)
    	{	    	
    		editor = pref.edit();
    		editor.putString("Lat", location.getLatitude()+"");
    		editor.putString("Long", location.getLongitude() + "");
    		editor.putString("Time", getDate(location.getTime(), "yyyy-MM-dd HH:mm:ss"));
    		editor.commit();
			/*SAllDriverLocationActivity.lat1 = SAllDriverLocationActivity.lat2;
			SAllDriverLocationActivity.long1 = SAllDriverLocationActivity.long2;
			SAllDriverLocationActivity.lat2 = location.getLatitude();
			SAllDriverLocationActivity.long2 = location.getLongitude();*/
			Constants.strCurLat = String.valueOf(location.getLatitude());
			Constants.strCurLong = String.valueOf(location.getLongitude());
			/*if (Constants.flagMapPage) {
				Dashboard3Activity.setLocationOnMap(location.getLatitude(), location.getLongitude());
			}*/
			//Toast.makeText(getApplicationContext(),Constants.strCurLat+","+Constants.strCurLong, Toast.LENGTH_LONG).show();
    	}
		else {
			//Toast.makeText(getApplicationContext(), "location not found", Toast.LENGTH_LONG).show();
		}
    }
    
	public String getDate(long milliSeconds, String dateFormat)
	{	  
	    SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);	 
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTimeInMillis(milliSeconds);
	    return formatter.format(calendar.getTime());
	}
}
