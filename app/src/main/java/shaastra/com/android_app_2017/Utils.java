package shaastra.com.android_app_2017;

import android.util.Log;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by gokulan on 31/10/16.
 */
public class Utils {

    public static void CopyStream(InputStream is, OutputStream os)
    {
        final int buffer_size = 4096;
        try
        {
            byte[] bytes = new byte[buffer_size];
            for(;;)
            {
                int count = is.read(bytes, 0, buffer_size);
                if (count == -1)
                    break;;
                os.write(bytes, 0, count);
            }
            Log.d("CACHE_WRITE", String.valueOf(bytes));
        }
        catch (Exception e){}
    }
}
