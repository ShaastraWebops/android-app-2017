package shaastra.com.android_app_2017;

/**
 * Created by vikranth on 27/11/2016.
 */
import android.content.Context;
import android.util.Log;

import java.io.File;


public class FileCache {
    private File cacheDir;

    public FileCache(Context context)
    {
        /*if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            cacheDir = new File(Environment.getExternalStorageDirectory(), "Temporary");
            Log.e("error","entered the first if of filecache");
        }
        else {*/

            cacheDir = context.getCacheDir();
            Log.e("error","entered the else of filecache");
        //}
        if(!cacheDir.exists())
        {
            cacheDir.mkdirs();
            Log.e("error","entered the second if of filecache");
        }
    }

    public File getFile(String url)
    {
        String s = String.valueOf(url.hashCode());
        File f = new File(cacheDir, s);
        Log.e("error","entered the getfile");
        return f;
    }

    public void clear()
    {
        File[] files = cacheDir.listFiles();
        if(files == null)
            return;
        for(File f:files)
            f.delete();
    }
}