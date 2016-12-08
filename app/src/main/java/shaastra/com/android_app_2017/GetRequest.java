package shaastra.com.android_app_2017;

import android.content.Context;
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


    public static JSONObject execute(String mUrl, Context context, String token)  {

        File file = new File(context.getFilesDir(), "data.dat");
        HashMap<String, Object> map = new HashMap<>();
        FileInputStream f = null;
        try {
            f = new FileInputStream(file);
            ObjectInputStream s = new ObjectInputStream(f);
            map = (HashMap<String, Object>) s.readObject();
            Log.d("Map size", String.valueOf(map.size()));
            s.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Creating dummy response
        String responseBody;
        JSONObject jsonResponse = new JSONObject();

        try {
            jsonResponse.put("status", 980);
            String results = null;
            if(map.containsKey(mUrl)) {
                results = StringEscapeUtils.unescapeJava(map.get(mUrl).toString());
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
                if (results!= null)
                    jsonResponse.put("data", map.get(mUrl));
                return jsonResponse;
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
                    map.put(url.toString(), responseBody);
                    file = new File(context.getFilesDir(), "data.dat");
//                    file = new File("data");
                    ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file));
                    outputStream.writeObject(map);
                    outputStream.flush();
                    outputStream.close();
                    Log.d("Success", "written 1");
                } catch (JSONException e){
                    Log.d("Success", "2");
                    jsonResponse.put("data", new JSONObject("{ \"response\":" + responseBody + "}"));
                    map.put(url.toString(), "{ \"response\":" + responseBody + "}");
//                    file = new File("data");
                    file = new File(context.getFilesDir(), "data.dat");
                    ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file));
                    outputStream.writeObject(map);
                    outputStream.flush();
                    outputStream.close();
                    Log.d("Success", "written 2");                }
            }
            conn.disconnect();
        } catch (JSONException e){
            e.printStackTrace();
        } catch (MalformedURLException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }


        return jsonResponse;
    }
}
