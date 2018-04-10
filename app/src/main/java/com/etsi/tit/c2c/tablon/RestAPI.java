package com.etsi.tit.c2c.tablon;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by josel on 19/03/2018.
 */

public class RestAPI {

    /*Variables*/
    private String DB_NAME = "socialdb";
    private String COLLECTION_NAME = "anuncios";
    private String API_KEY = "ysYX7aNAtztYmZzuJjUAYmSaaXAPPs-s";
    private String stream = null;

    /*Funciones*/
    public String getAddressCollection() {
        String baseURL = String.format("https://api.mlab.com/api/1/databases/%s/collections/%s",
                DB_NAME, COLLECTION_NAME);
        StringBuilder stringBuilder = new StringBuilder(baseURL);
        stringBuilder.append("?apiKey="+API_KEY);
        return stringBuilder.toString();
    }
    public String GetHTTPData (String urlString) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();

            if(urlConnection.getResponseCode() == 200) {
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader r = new BufferedReader(new InputStreamReader(in));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line=r.readLine()) != null) {
                    sb.append(line);
                }
                stream = sb.toString();
                urlConnection.disconnect();
            }
            else {
                //
            }
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stream;
    }
}
