package com.rhymestore.android.authenticator;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/**
 * Service to handle Account authentication. It instantiates the authenticator and returns its
 * IBinder.
 */
public class AuthenticatorService extends Service
{
    private static final String TAG = "AuthenticationService";

    private Authenticator authenticator;

    @Override
    public void onCreate()
    {
        if (Log.isLoggable(TAG, Log.VERBOSE))
        {
            Log.v(TAG, "SampleSyncAdapter Authentication Service started.");
        }
        authenticator = new Authenticator(this);
    }

    @Override
    public void onDestroy()
    {
        if (Log.isLoggable(TAG, Log.VERBOSE))
        {
            Log.v(TAG, "SampleSyncAdapter Authentication Service stopped.");
        }
    }

    @Override
    public IBinder onBind(final Intent intent)
    {
        if (Log.isLoggable(TAG, Log.VERBOSE))
        {
            Log.v(TAG, "getBinder()...  returning the AccountAuthenticator binder for intent "
                + intent);
        }

        return authenticator.getIBinder();
    }
}
