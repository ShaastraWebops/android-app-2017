package shaastra.com.android_app_2017;

/**
 * Created by vikranth on 27/11/2016.
 */
import android.graphics.Bitmap;

import java.lang.ref.SoftReference;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by gokulan on 31/10/16.
 */
public class MemoryCache {
    private Map<String, SoftReference<Bitmap>> cache = Collections.synchronizedMap(new HashMap<String, SoftReference<Bitmap>>());

    public Bitmap get(String id)
    {
        if(!cache.containsKey(id))
            return null;
        SoftReference<Bitmap> ref = cache.get(id);
        return ref.get();
    }

    public void put(String id, Bitmap bmp)
    {
        cache.put(id, new SoftReference<Bitmap>(bmp));
    }
    public void clear()
    {
        cache.clear();
    }


}