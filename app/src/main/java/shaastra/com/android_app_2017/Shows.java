package shaastra.com.android_app_2017;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Shows extends AppCompatActivity implements ShowsFragment.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shows);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
