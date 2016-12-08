package shaastra.com.android_app_2017;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import org.apache.commons.lang3.StringEscapeUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

/**
 * Created by keerthana on 25/11/16.
 */
public class GetRequest {
    private static final String LOG_TAG = "GetRequest";
    private static String MyPREFERENCES = "MyPREFERENCES";


    public static JSONObject execute(String mUrl, Context context, String token)  {

        SharedPreferences sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE).edit();

        //Creating dummy response
        String responseBody;
        JSONObject jsonResponse = new JSONObject();

        try {
            jsonResponse.put("status", 980);
            String results = null;
            if(sharedpreferences.contains(mUrl)) {
                results = sharedpreferences.getString(mUrl, null);
            }
            if (results!= null)
                jsonResponse.put("data", new JSONObject(results));


            // URL get Request
            URL url = new URL(mUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            Log.d(LOG_TAG, url.toString());
            conn.setRequestMethod("GET");

            // Set header if token is given
            if (token != null){
                conn.setRequestProperty ("Authorization", "Bearer " + token);
            }

            // Include responsecode form server in josnResponse
            jsonResponse.put("status", conn.getResponseCode());

            // Terminate the process if response code is not 2xx or OK
            if (conn.getResponseCode()/100 != 2) {
                conn.disconnect();
            }

            // Processing the response
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));
            String output;
            StringBuilder sb = new StringBuilder();
            responseBody = "";
            while ((output = br.readLine()) != null) {
                sb.append(output);
                responseBody = sb.toString();
                Log.d(LOG_TAG, responseBody);
                try {
                    Log.d("Success", "1");
                    jsonResponse.put("data", new JSONObject(responseBody));
                    editor.putString(mUrl, responseBody);
                    editor.commit();
                    Log.d("Success", "written 1");
                } catch (JSONException e){
                    Log.d("Success", "2");
                    jsonResponse.put("data", new JSONObject("{ \"response\":" + responseBody + "}"));
                    editor.putString(mUrl, "{ \"response\":" + responseBody + "}");
                    editor.commit();
                    Log.d("Success", "written 2");                }
            }
            conn.disconnect();
        } catch (JSONException e){
            Log.d("1","1");
            e.printStackTrace();
        } catch (MalformedURLException e){
            Log.d("2","2");
            e.printStackTrace();
        } catch (IOException e){
            Log.d("3","3");
            e.printStackTrace();
        }


        return jsonResponse;
    }
}
