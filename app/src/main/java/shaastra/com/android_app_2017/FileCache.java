package shaastra.com.android_app_2017;

import android.content.ContentValues;
import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * Created by gokulan on 31/10/16.
 */
public class FileCache {
    private File cacheDir;

    public FileCache(Context context)
    {
        if(android.os.Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
            cacheDir = new File(android.os.Environment.getExternalStorageDirectory(), "Temproary");
        else
            cacheDir = context.getCacheDir();
        if(!cacheDir.exists())
            cacheDir.mkdirs();
    }

    public File getFile(String url)
    {
        String file = String.valueOf(url.hashCode());
        File f = new File(cacheDir, file);
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
