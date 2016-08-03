package com.example.dimaj.gsmexample;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;


public class MainActivity extends Activity {
    private BroadcastReceiver mRegisttrationBroadcaseReceiver;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);
        mRegisttrationBroadcaseReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().endsWith(GSMRegistrationIntentService.REG_SUCCESS)) {
                    String token = intent.getStringExtra("token");
                    Toast.makeText(getApplicationContext(), "GCM token: " + token, Toast.LENGTH_LONG).show();
                } else if (intent.getAction().endsWith(GSMRegistrationIntentService.REG_ERROR)) {
                    Toast.makeText(getApplicationContext(), "GCM reg error", Toast.LENGTH_LONG).show();
                } else {

                }
            }
        };
        //check status google play service in device
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext());
        if (ConnectionResult.SUCCESS != resultCode) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                Toast.makeText(getApplicationContext(), "Google play Service is not install/enabled", Toast.LENGTH_LONG).show();
                GooglePlayServicesUtil.showErrorNotification(resultCode, getApplicationContext());
            } else {
                Toast.makeText(getApplicationContext(), "This device not support", Toast.LENGTH_LONG).show();
            }
        } else {
            //start service
            Intent intent = new Intent(this, GSMRegistrationIntentService.class);
            startService(intent);

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.v(TAG, "OnResume");
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegisttrationBroadcaseReceiver,
                new IntentFilter(GSMRegistrationIntentService.REG_SUCCESS));

        LocalBroadcastManager.getInstance(this).registerReceiver(mRegisttrationBroadcaseReceiver,
                new IntentFilter(GSMRegistrationIntentService.REG_ERROR));
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.v(TAG, "onPause");
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegisttrationBroadcaseReceiver);
    }
}
