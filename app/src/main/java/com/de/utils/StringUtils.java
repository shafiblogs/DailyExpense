package com.de.utils;

import android.content.Context;

import com.de.activity.R;
import com.de.constant.AppConstants;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Shafi on 5/20/2015.
 */
public class StringUtils {
    public static boolean isNotBlank(String string) {
        return null != string && !string.isEmpty() && !string.trim().isEmpty();
    }

    public static String getNetworkErrorString(Context context) {
        JSONObject jObj = new JSONObject();
        try {
            jObj.put("Key", AppConstants.NETWORK_ERROR);
            jObj.put("Value", context.getString(R.string.network_err));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jObj.toString();
    }

    public static String getStringFromInputStream(InputStream is) {
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();
        String line;
        try {
            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }
}
