package shaastra.com.android_app_2017;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by gokulan on 31/10/16.
 */
public class GetActivity
{
    MemoryCache mCache = new MemoryCache();
    FileCache fCache;
    private Map<String, JSONArray> textViews = Collections.synchronizedMap(new WeakHashMap<String, JSONArray>());
    ExecutorService executorService;

    public GetActivity(Context context)
    {
        fCache = new FileCache(context);
        executorService = Executors.newFixedThreadPool(5);
    }

    public JSONArray getJSONobject(String url, Context context) {
        File f = fCache.getFile(url);
        Log.d("CACHE_DIR", String.valueOf(context.getCacheDir()));
        try
        {
            JSONArray obj = null;
            obj = new GetRequest().execute(url, f, context).get();
            Log.d("Milestone 1", String.valueOf(obj));
            if(obj != null) {
                textViews.put(url, obj);
                return obj;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        String line=null;
        try
        {
            BufferedReader reader = new BufferedReader(new FileReader(f));
            line = reader.readLine().trim();
            Log.d("FILE_READ", line);
            reader.close();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        JSONArray array = null;
        JSONObject obj = null;

        try {
            if (line.startsWith("{"))
                line = "[" + line + "]";

            array = new JSONArray(line);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        if( array != null)
            return array;
        Toast.makeText(context, "No Internet Connection. Connect to a Network", Toast.LENGTH_SHORT).show();
        return null;
    }

    /*private JSONArray getObject(File f, Context context, String url)
    {
        try
        {
            *//*AssetManager manager = context.getAssets();
            InputStream iStream = manager.open(String.valueOf(f));
            byte[] formArray = new byte[iStream.available()];
            iStream.read(formArray);
            Log.d("READ_CACHE", String.valueOf(formArray));
            iStream.close();
            return new JSONObject(String.valueOf(formArray));*//*
            //BufferedReader br = new BufferedReader(new FileReader("JSONResponse.txt"));
            *//*File file = new File(context.getCacheDir().getPath()+"/"+"JSONResponse.json");*//*

           *//* StringBuilder response = new StringBuilder();
            char array[]=null;
            FileReader reader = null;
            reader = new FileReader(f);
            reader.read(array, 0, 4096);
            reader.close();
            response.append(String.valueOf(array));*//*
            *//*String r = new String(response);*//*

            *//*String fileName = String.valueOf(Uri.parse(url).getLastPathSegment());
            FileReader reader = new FileReader(fileName);
            StringBuilder response=null;
            char line[]=null;
            while( reader.read(line, 0, 4096) != 0)
                response.append(String.valueOf(line));
            Log.d("READ_FROM_CACHE", String.valueOf(response));
            return new JSONObject(String.valueOf(response));*//*



            *//*if(line == null)
                return null;
            return new JSONArray(line);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return null;*//*
    }
*/
    public void clearCache()
    {
        mCache.clear();
        fCache.clear();
    }
}
