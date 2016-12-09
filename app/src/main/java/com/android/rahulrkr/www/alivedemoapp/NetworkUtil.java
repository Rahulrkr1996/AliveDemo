package com.android.rahulrkr.www.alivedemoapp;

/**
 * Created by Rahul Kumar on 12/9/2016.
 */

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.android.rahulrkr.www.alivedemoapp.Constants.NetworkUtilConstants;

public class NetworkUtil {

    public static int getConnectivityStatus(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (null != activeNetwork) {
            if(activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
                return NetworkUtilConstants.TYPE_WIFI;

            if(activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                return NetworkUtilConstants.TYPE_MOBILE;
        }
        return NetworkUtilConstants.TYPE_NOT_CONNECTED;
    }

    public static String getConnectivityStatusString(Context context) {
        int conn = NetworkUtil.getConnectivityStatus(context);
        String status = null;
        if (conn == NetworkUtilConstants.TYPE_WIFI) {
            status = "Wifi enabled";
        } else if (conn == NetworkUtilConstants.TYPE_MOBILE) {
            status = "Mobile data enabled";
        } else if (conn == NetworkUtilConstants.TYPE_NOT_CONNECTED) {
            status = "Not connected to Internet";
        }
        return status;
    }
}
