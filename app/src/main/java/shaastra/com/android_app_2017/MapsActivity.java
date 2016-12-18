package shaastra.com.android_app_2017;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private List<LatLng> coordinates;
    private List<String> labels;
    private Marker locationWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        coordinates = new ArrayList<LatLng>() {{
                    add(new LatLng(12.989528, 80.237902));
                            add(new LatLng(12.990187, 80.230358));
                            add(new LatLng(12.990636, 80.230789));
                            add(new LatLng(12.990155, 80.232012));
                            add(new LatLng(12.992058, 80.232162));
                            add(new LatLng(12.989026, 80.233621));
                            add(new LatLng(12.989473, 80.231896));
                            add(new LatLng(12.988813, 80.230721));
                            add(new LatLng(12.988923, 80.229530));
        }};

        labels = new ArrayList<String>() {{
            add("SAC");
            add("CRC");
            add("MSB");
            add("HSB");
            add("ICSR");
            add("OAT");
            add("CLT");
            add("ESB");
            add("CS");
        }};
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if(id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(12.989528, 80.237902);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(12.989026, 80.233621)));
        for(int i=0; i<coordinates.size(); i++) {
            MarkerOptions mo = new MarkerOptions().position(coordinates.get(i)).title(labels.get(i));
            locationWindow = mMap.addMarker(mo);
//            locationWindow.showInfoWindow();
            if(getIntent().getStringExtra("location")!= null && getIntent().getStringExtra("location").equals(labels.get(i))) {
                locationWindow.showInfoWindow();
                mMap.moveCamera(CameraUpdateFactory.newLatLng(coordinates.get(i)));
        }
        mMap.animateCamera(CameraUpdateFactory.zoomIn());
        // Zoom out to zoom level 10, animating with a duration of 2 seconds.
        mMap.animateCamera(CameraUpdateFactory.zoomTo(16), 2000, null);

        }
    }
}
