package shaastra.com.android_app_2017;

import android.os.AsyncTask;
import android.os.Message;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Hi on 06-Jun-16.
 */
public class GetRequest extends AsyncTask<Object, Void, JSONObject>
{
    /* This class sends GET Request and returns the response*/
    private static final String LOG_TAG = "GetRequest";

    @Override
    protected JSONObject doInBackground(Object... params) {

        //Creating dummy response
        String responseBody;
        JSONObject jsonResponse = new JSONObject();
        URL url= null;

        File f = (File) params[1];

        try {
            url = new URL(String.valueOf(params[0]));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try
        {
            // URL get Request
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            Log.d(LOG_TAG, url.toString());
            conn.setRequestMethod("GET");

            // Include responsecode form server in josnResponse
           // jsonResponse.put("status", conn.getResponseCode());

            // Terminate the process if response code is not 2xx or OK
            if (conn.getResponseCode()/100 != 2)
            {
                conn.disconnect();
                return jsonResponse;
            }

            // Processing the response
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String output;
            StringBuilder sb = new StringBuilder();
            responseBody = "";
            while ((output = br.readLine()) != null)
            {
                sb.append(output);
                responseBody = sb.toString();
                Log.d(LOG_TAG, responseBody);
                try
                {
                    jsonResponse.put("data", new JSONObject(responseBody));
                } catch (JSONException e)
                {
                    jsonResponse.put("data", new JSONObject("{ \"response\":" + responseBody + "}"));
                }
            }
            InputStream iStream = conn.getInputStream();
            byte[] bd;
            bd = String.valueOf(jsonResponse).getBytes();
            int count = iStream.read(bd, 0, 4096);
            OutputStream oStream = new FileOutputStream(f);
            oStream.write(bd);
            Log.d("RESPONSE", new String(bd, 0, 4096));
            oStream.close();
            conn.disconnect();
        }
        catch (JSONException | IOException e)
        {
            e.printStackTrace();
        }
        return jsonResponse;
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        super.onPostExecute(jsonObject);
        final Message msg= new Message();
        msg.obj = jsonObject;
    }
}