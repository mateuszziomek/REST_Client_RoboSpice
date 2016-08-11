package com.mateuszziomek.restclientrobospice;

import com.octo.android.robospice.request.SpiceRequest;

import org.apache.commons.io.IOUtils;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Used API: http://ip-api.com/docs/api:json
 */
public class GeoRequest extends SpiceRequest<String> {

    final String GEOLOC_BASE_URL = "http://ip-api.com/json/?fields=city,countryCode,lat,lon";


    public GeoRequest() {
        super(String.class);
    }

    @Override
    public String loadDataFromNetwork() throws Exception {

        URL url = new URL(GEOLOC_BASE_URL);

        // Create the request to API and open the connection
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("GET");
        urlConnection.connect();

        // Read the input and close the connection
        InputStream inputStream = urlConnection.getInputStream();
        String result = IOUtils.toString(inputStream, "UTF-8");
        urlConnection.disconnect();

        return result;
    }
}
