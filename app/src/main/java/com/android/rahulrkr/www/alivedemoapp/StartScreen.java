package com.android.rahulrkr.www.alivedemoapp;

import android.bluetooth.BluetoothGattCharacteristic;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class StartScreen extends AppCompatActivity {

    private RelativeLayout start_layout;
    private ServiceConnection mServiceConnection;
    private BluetoothLeService mBluetoothLeService;
    private String mDeviceName;
    private String mDeviceAddress;
    private GPSTracker gps;
    private boolean mConnected = false;
    public int track = 0;
    public int status_request = 0;
    int count = 0;
    int layer = 0;
    int layer_back_trig = 0;
    private SmsManager mConnection;
    private final static String TAG = "StartScreen";

    // blechat - characteristics for HM-10 serial
    private BluetoothGattCharacteristic characteristicTX;
    private BluetoothGattCharacteristic characteristicRX;

    public static final String EXTRAS_DEVICE_NAME = "DEVICE_NAME";
    public static final String EXTRAS_DEVICE_ADDRESS = "DEVICE_ADDRESS";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);

        ///////////////////////////////////////////////////////

        // Code to manage Service lifecycle.
        mServiceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName,
                                           IBinder service) {
                mBluetoothLeService = ((BluetoothLeService.LocalBinder) service)
                        .getService();
                if (!mBluetoothLeService.initialize()) {
                    Toast.makeText(getApplicationContext(), "Cannot start Bluetooth Service", Toast.LENGTH_SHORT).show();
                    finish();
                }
                // Automatically connects to the device upon successful start-up
                // initialization.
                // Toast.makeText(getApplicationContext(), "service connected", Toast.LENGTH_SHORT).show();
                mBluetoothLeService.connect(mDeviceAddress);

            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
                //Toast.makeText(getApplicationContext(), "service disconnected", Toast.LENGTH_SHORT).show();
                mBluetoothLeService = null;
            }
        };

////////////////////////////////////////////////////////////////////
        start_layout = (RelativeLayout) findViewById(R.id.activity_start_screen);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainIntent = new Intent(StartScreen.this, LoginActivity.class);
                startActivity(mainIntent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
            }
        }, 1000);
    }
/////////////////////////////////////////////////////////////////////
    private void callGPS() {
        // create class object
        gps = new GPSTracker(StartScreen.this);

        // check if GPS enabled
        if (gps.canGetLocation()) {

            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();
            String latitudeS = String.valueOf(latitude);
            String longitudeS = String.valueOf(longitude);
       /**     if (track == 1) {
                mConnection.sendTextMessage(encryption("LOCATION-" + username_init + "-" + latitudeS + "-" + longitudeS + "-" + transfer_session));//mConnection.sendTextMessage(encryption("sessionRequest-" + username_init));
           }
            Start it when encrytion function is enabled
        To-Do
        */
            //Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
        } else {
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
        }
    }

    private void sendSerialBLE(String message) {
        Log.d(TAG, "Sending: " + message);
        final byte[] tx = message.getBytes();
        if (mConnected == true) {
            characteristicTX.setValue(tx);
            mBluetoothLeService.writeCharacteristic(characteristicTX);
        }
    }

    public void initializeBLE() {
        final Intent intent = getIntent();

        mDeviceName = intent.getStringExtra(EXTRAS_DEVICE_NAME);

        SharedPreferences ble_mac_add = getSharedPreferences("BLEMACAdd", Context.MODE_PRIVATE);
        String ble_mac = ble_mac_add.getString("blemacadd", "");

        mDeviceAddress = ble_mac;

        Intent gattServiceIntent = new Intent(this, BluetoothLeService.class);
        bindService(gattServiceIntent, mServiceConnection, BIND_AUTO_CREATE);
        Intent broadcastIntent = new Intent("Startbleservice");
        sendBroadcast(broadcastIntent);

    }

}

