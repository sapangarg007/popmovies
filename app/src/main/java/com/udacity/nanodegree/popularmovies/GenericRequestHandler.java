/**
 * Created by sgarg on 1/5/2016.
 */
package com.udacity.nanodegree.popularmovies;

import android.util.Base64;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Map;

public class GenericRequestHandler {
    private final static String TAG = "GenericRequestHandler";
    public final static int GET = 1;
    public final static int POST = 2;
    private String response = null;

    /**
     * Making generic HTTP request
     *
     * @url - url to make request
     * @method - http request method
     * @params - http request params
     */
    public String makeGenericRequest(String url, int method, Map<String,String> params) {
        try{
            String charset = "UTF-8";
            URLConnection connection;
            InputStream is;
            // Checking http request method type
            if(method == GET)
            {
                String parameterUrl = "";
                if(params != null) {
                    for (Map.Entry<String, String> entry : params.entrySet()) {
                        parameterUrl = parameterUrl + entry.getKey() + "=" + URLEncoder.encode(entry.getValue(), charset) + "&";
                    }
                    if(parameterUrl != "")
                        parameterUrl = parameterUrl.substring(0,parameterUrl.length()-1);
                }
                if(parameterUrl != "") {
                    connection = new URL(url + "?" + parameterUrl).openConnection();
                    connection.setRequestProperty("Accept-Charset", charset);
                }
                else
                    connection = new URL(url).openConnection();
                is = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                StringBuilder out = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    out.append(line);
                }
                reader.close();
                is.close();
                response = out.toString();
            }
        } catch (Exception e){
            Log.e(TAG, "Exception : ", e);
        }
        return response;
    }
}
