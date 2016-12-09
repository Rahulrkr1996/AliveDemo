package com.android.rahulrkr.www.alivedemoapp.BroadcastReceivers;

/**
 * Created by Rahul Kumar on 12/9/2016.
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.android.rahulrkr.www.alivedemoapp.BluetoothLeService;

public class RestartBLEServiceReceiver extends BroadcastReceiver {

    private static final String TAG = "RestartServiceReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        context.startService(new Intent(context, BluetoothLeService.class));

    }

}
