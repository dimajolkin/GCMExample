package com.example.dimaj.gsmexample;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

/**
 * Created by dimaj on 03.08.2016.
 */
public class GSMRegistrationIntentService extends IntentService {
    public static final String REG_SUCCESS = "RegOK";
    public static final String REG_ERROR = "RegError";
    public static final String TAG = "reg";


    public GSMRegistrationIntentService() {
        super("");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        regGSM();
    }

    protected void regGSM() {
        Intent registrationComplite = null;
        String token = null;
        try {
            InstanceID instanceID = InstanceID.getInstance(getApplicationContext());
            token = instanceID.getToken(getString(R.string.gcm_defaultSenderId), GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
            Log.v(TAG,"tokken: " + token);

            registrationComplite = new Intent(REG_SUCCESS);
            registrationComplite.putExtra("token", token);

        }catch (Exception ex) {
            Log.w(TAG, "reg error");
            registrationComplite = new Intent(REG_ERROR);
        }

        LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplite);
    }
}
