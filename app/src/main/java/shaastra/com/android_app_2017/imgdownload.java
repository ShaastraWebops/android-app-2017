package shaastra.com.android_app_2017;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuView;
import android.widget.ImageView;

public class imgdownload extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imgdownload);

        ImageView img = (ImageView) findViewById(R.id.imgbox);

        int loader = R.mipmap.ic_launcher;
        String image_url = "http://api.androidhive.info/images/sample.jpg";
        ImageLoader imgLoader = new ImageLoader(getApplicationContext());

        imgLoader.DisplayImage(image_url, loader, img);
    }
}
