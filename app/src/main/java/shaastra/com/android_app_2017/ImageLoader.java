package shaastra.com.android_app_2017;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by gokulan on 31/10/16.
 */
public class ImageLoader {
    MemoryCache mCache = new MemoryCache();
    FileCache fCache;
    private Map<ImageView, String> imgviews = Collections.synchronizedMap(new WeakHashMap<ImageView, String>());
    ExecutorService executorService;

    public ImageLoader(Context context)
    {
        fCache = new FileCache(context);
        executorService = Executors.newFixedThreadPool(5);
    }

    int stub_id = R.mipmap.ic_launcher;

    public void DisplayImage(String url, int loader, ImageView imageView)
    {
        stub_id = loader;
        imgviews.put(imageView, url);
        Bitmap bmp = mCache.get(url);
        if(bmp != null)
            imageView.setImageResource(loader);
        else {
            queuePhoto(url, imageView);
            imageView.setImageResource(loader);
        }
    }

    private void queuePhoto(String url, ImageView imageView)
    {
        PhotoToLoad p = new PhotoToLoad(url, imageView);
        executorService.submit(new PhotosLoader(p));
    }

    private Bitmap getBitmap(String url, int max_height)
    {
        File f = fCache.getFile(url);
        Bitmap b = decodeFile(f);

        if(b!=null)
            return b;

        try
        {
            Bitmap bitmap = null;
            URL imageUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
            conn.setConnectTimeout(30000);
            conn.setReadTimeout(30000);
            conn.setInstanceFollowRedirects(true);
            InputStream iStream = conn.getInputStream();
            OutputStream oStream = new FileOutputStream(f);
            Utils.CopyStream(iStream, oStream);
            oStream.close();
            bitmap = decodeFile(f);
            return bitmap;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    private Bitmap decodeFile(File F)
    {
        try
        {
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(F), null, o);
            int REQUIRED_SIZE = 70;
            int width_temp = o.outWidth, height_temp = o.outHeight;
            int scale = 1;
            while(true)
            {
                if( width_temp/2 < REQUIRED_SIZE || height_temp/2 < REQUIRED_SIZE)
                    break;
                width_temp/=2;
                height_temp/=2;
                scale *= 2;
            }

            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            return BitmapFactory.decodeStream(new FileInputStream(F), null, o2);
        }
        catch (FileNotFoundException e){}
        return null;
    }

    private class PhotoToLoad
    {
        public String url;
        public ImageView imageView;
        public PhotoToLoad(String u, ImageView i)
        {
            url = u;
            imageView = i;
        }
    }

    class PhotosLoader implements Runnable
    {
        PhotoToLoad photoToLoad;
        PhotosLoader(PhotoToLoad p)
        {
            this.photoToLoad = p;
        }

        @Override
        public void run()
        {
            if(imageViewReused(photoToLoad))
                return;
            photoToLoad.imageView.measure(0,0);
            Bitmap bmp = getBitmap(photoToLoad.url, photoToLoad.imageView.getMeasuredHeight());
            if(imageViewReused(photoToLoad))
                return;
            BitmapDisplayer bd = new BitmapDisplayer(bmp, photoToLoad);
            new Handler(Looper.getMainLooper()).post(bd);
        }
    }

    boolean imageViewReused(PhotoToLoad photoToLoad)
    {
        String tag = imgviews.get(photoToLoad.imageView);
        if(tag == null || !tag.equals(photoToLoad.url))
            return true;
        return false;
    }

    class BitmapDisplayer implements Runnable
    {
        Bitmap bitmap;
        PhotoToLoad photoToLoad;

        public BitmapDisplayer(Bitmap b, PhotoToLoad p)
        {
            bitmap = b;
            photoToLoad = p;
        }

        public void run()
        {
            if(imageViewReused(photoToLoad))
                return;
            if(bitmap != null)
                photoToLoad.imageView.setImageBitmap(bitmap);
            else
                photoToLoad.imageView.setImageResource(stub_id);
        }
    }

    public void clearCache()
    {
        mCache.clear();
        fCache.clear();
    }
}
