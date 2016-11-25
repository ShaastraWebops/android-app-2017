package shaastra.com.android_app_2017;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
    private Map<String, JSONObject> textViews = Collections.synchronizedMap(new WeakHashMap<String, JSONObject>());
    ExecutorService executorService;

    public GetActivity(Context context)
    {
        fCache = new FileCache(context);
        executorService = Executors.newFixedThreadPool(5);
    }

    public JSONObject getJSONobject(String url, Context context)
    {
        File f = fCache.getFile(url);
        try
        {
            JSONObject obj = new GetRequest().execute(url, f).get();
            textViews.put(url, obj);
            return obj;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        JSONObject object = getObject(f, context);
        return object;
    }

    private JSONObject getObject(File f, Context context)
    {
        try
        {
            /*AssetManager manager = context.getAssets();
            InputStream iStream = manager.open(String.valueOf(f));
            byte[] formArray = new byte[iStream.available()];
            iStream.read(formArray);
            Log.d("READ_CACHE", String.valueOf(formArray));
            iStream.close();
            return new JSONObject(String.valueOf(formArray));*/
            BufferedReader br = new BufferedReader(new FileReader("JSONResponse.txt"));
            StringBuilder response = new StringBuilder();
            String line = br.readLine();
            while( line != null)
            {
                response.append(line);
                line = br.readLine();
            }

            String r = new String(response);
            return new JSONObject(r);
        }
        catch (FileNotFoundException e){}
        catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void clearCache()
    {
        mCache.clear();
        fCache.clear();
    }
}
