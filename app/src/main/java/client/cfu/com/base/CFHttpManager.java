package client.cfu.com.base;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Base64;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.*;

import client.cfu.com.constants.CFConstants;

/**
 *
 */
public class CFHttpManager {


    protected CFHttpManager() {
    }

    public static Boolean checkServerAvailability() {

        try {

            //Network is available but check if we can get access from the network.
            URL url = new URL(CFConstants.SERVICE_ROOT +"CFUDBService");
            HttpURLConnection urlc = (HttpURLConnection) url.openConnection();
            urlc.setRequestProperty("Connection", "close");
            urlc.setConnectTimeout(2000); // Timeout 2 seconds.
            urlc.connect();

            //Successful response.
            return urlc.getResponseCode() == 200;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;

    }

    protected static String authenticate(String email, String password) {

        BufferedReader reader = null;
        String status = "";
//
//
//        email = "risina.prime@gmail.com";
//        password = "123456";

        byte[] loginBytes = (email + ":" + password).getBytes();
        StringBuilder loginBuilder = new StringBuilder()
                .append("Basic ")
                .append(Base64.encodeToString(loginBytes, Base64.DEFAULT));

        try {

            URL url = new URL(CFConstants.SERVICE_ROOT + "CFUDBService/webresources/auth/basic");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.addRequestProperty("Authorization", loginBuilder.toString());

            StringBuilder sb = new StringBuilder();
            reader = new BufferedReader(new InputStreamReader(con.getInputStream()));

            String line = reader.readLine();
            if (line.equals(email)) {
                status = CFConstants.STATUS_OK;
            } else {
                status = CFConstants.STATUS_ERROR;
            }

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return status;
    }

    protected static String getData(String uri, HashMap<String, String> filterParams) {
        InputStream inputStream = null;
        String result = "";
        try {

            // create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // make GET request to the given URL
            HttpResponse httpResponse = httpclient.execute(new HttpGet(uri));

            // receive response as inputStream

            // convert inputstream to string
            int status = httpResponse.getStatusLine().getStatusCode();
            if (status == 200) {
                result = EntityUtils.toString(httpResponse.getEntity());
            } else {
                result = "Did not work!";
            }

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        return result;
    }

    protected static String getDataWithAuthCredentials(String uri, HashMap<String, String> filterParams, String email, String password) {
        // TODO implement here
        return "";
    }

    protected static String addData(String Uri, String jsonString, Bitmap image) {
//        // Create a new HttpClient and Post Header
//        HttpClient httpclient = new DefaultHttpClient();
//        HttpPost httppost = new HttpPost(Uri);
//
//        try {
//            // Add your data
//
//            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
//
//            // Execute HTTP Post Request
//            HttpResponse response = httpclient.execute(httppost);
//
//        } catch (ClientProtocolException e) {
//            // TODO Auto-generated catch block
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//        }

        int TIMEOUT_MILLISEC = 10000;  // = 10 seconds
        HttpParams httpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParams, TIMEOUT_MILLISEC);
        HttpConnectionParams.setSoTimeout(httpParams, TIMEOUT_MILLISEC);
        HttpClient client = new DefaultHttpClient(httpParams);

        HttpPost request = new HttpPost(Uri);
        HttpResponse response = null;
        try {
            request.setEntity(new ByteArrayEntity(
                    jsonString.toString().getBytes("UTF8")));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        try {
            response = client.execute(request);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


    protected static String uploadImage(Bitmap image, String name, BigInteger advertisementId) {
        // TODO implement here
        return "";
    }

    protected static Bitmap downloadImage(BigInteger advertisementId) {
        // TODO implement here
        return null;
    }


    protected static String removeAdvertisement(BigInteger advertisementId) {
        // TODO implement here
        return "";
    }

}