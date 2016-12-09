package com.android.rahulrkr.www.alivedemoapp;

/**
 * Created by Rahul Kumar on 12/9/2016.
 */
import com.android.rahulrkr.www.alivedemoapp.Constants.GattAttributesConstants;

import java.util.HashMap;

/**
 * This class includes a small subset of standard GATT attributes for demonstration purposes.
 */

public class SampleGattAttributes {
    private static HashMap<String, String> attributes = new HashMap();

    static {
        // Sample Services.
        attributes.put("0000180d-0000-1000-8000-00805f9b34fb", "Heart Rate Service");
        attributes.put("0000180a-0000-1000-8000-00805f9b34fb", "Device Information Service");

        // blechat - HM-10 serial service
        attributes.put("0000ffe0-0000-1000-8000-00805f9b34fb", "HM 10 Serial");


        // Sample Characteristics.
        attributes.put(GattAttributesConstants.HEART_RATE_MEASUREMENT, "Heart Rate Measurement");
        attributes.put("00002a29-0000-1000-8000-00805f9b34fb", "Manufacturer Name String");

        // blechat - HM-10 serial characteristic
        attributes.put(GattAttributesConstants.HM_RX_TX,"RX/TX data");

    }

    public static String lookup(String uuid, String defaultName) {
        String name = attributes.get(uuid);
        return name == null ? defaultName : name;
    }
}

