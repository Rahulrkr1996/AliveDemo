package com.android.rahulrkr.www.alivedemoapp.BroadcastReceivers;

/**
 * Created by Rahul Kumar on 12/9/2016.
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.android.rahulrkr.www.alivedemoapp.BluetoothLeService;

public class BluetoothLeRestartBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        //Toast.makeText(context,"Service Stops!",Toast.LENGTH_SHORT).show();
        context.startService(new Intent(context, BluetoothLeService.class));
    }
}