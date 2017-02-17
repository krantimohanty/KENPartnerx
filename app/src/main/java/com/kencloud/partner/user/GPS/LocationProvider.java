package com.kencloud.partner.user.GPS;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;


public class LocationProvider implements
								        GoogleApiClient.ConnectionCallbacks,
								        GoogleApiClient.OnConnectionFailedListener,
        LocationListener
{
    public abstract interface LocationCallback
    {
        public void handleNewLocation(Location location);
    }
    public static final String TAG = LocationProvider.class.getSimpleName();
     
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;

    private LocationCallback mLocationCallback;
    private Context mContext;
    private static GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;

    public LocationProvider(Context context, LocationCallback callback)
    {
        mLocationCallback = callback;
        mContext = context;
        
        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(1 * 100)        // 1 seconds, in milliseconds
                .setFastestInterval(1 * 100); // 1 second, in milliseconds
    }

    public void connect() 
    {
    	try
    	{
		   if (!mGoogleApiClient.isConnected()) 
	        {
			   mGoogleApiClient.connect();
	        }
    	}
    	catch(Exception e)
    	{
    		Log.i(TAG, "Error location services connect."+e.toString());
    	}
    }

    public void disconnect() 
    {
        if (mGoogleApiClient.isConnected()) 
        {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onConnected(Bundle bundle) 
    {
      /*Log.i(TAG, "Location services connected.");        
        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (location == null) 
        {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
        else 
        {
            mLocationCallback.handleNewLocation(location);
        }*/
    	
    	 Intent intent = new Intent(mContext, LocationReceiver.class);
    	 PendingIntent locationIntent = PendingIntent.getBroadcast(mContext.getApplicationContext(), 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
    	 LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, locationIntent);

    	 //LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
    }

    @Override
    public void onConnectionSuspended(int i) 
    {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult)
    {
        if (connectionResult.hasResolution() && mContext instanceof Activity)
        {
            try 
            {
               Activity activity = (Activity)mContext;          
               connectionResult.startResolutionForResult(activity, CONNECTION_FAILURE_RESOLUTION_REQUEST);             
            }
            catch (IntentSender.SendIntentException e) 
            {
                e.printStackTrace();
            }
        } 
        else 
        {             
            Log.i(TAG, "Location services connection failed with code " + connectionResult.getErrorCode());
        }
    }

    @Override
    public void onLocationChanged(Location location)
    {

        mLocationCallback.handleNewLocation(location);
    }
}

