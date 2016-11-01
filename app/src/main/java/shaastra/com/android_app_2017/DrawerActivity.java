package shaastra.com.android_app_2017;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class DrawerActivity extends AppCompatActivity {

    protected DrawerLayout fullLayout;
    protected FrameLayout frameLayout;
    protected NavigationView navigationView;
    protected DrawerLayout Drawer;

    @Override
    public void setContentView(int layoutResID) {


        fullLayout = (android.support.v4.widget.DrawerLayout) getLayoutInflater().inflate(R.layout.activity_drawer, null);
        frameLayout = (FrameLayout) fullLayout.findViewById(R.id.drawer_frame);

        getLayoutInflater().inflate(layoutResID, frameLayout, true);

        super.setContentView(fullLayout);


        Drawer = (DrawerLayout) findViewById(R.id.DrawerLayout);        // Drawer object Assigned to the view


        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                if (item.isChecked()) item.setChecked(false);
                else item.setChecked(true);

                Drawer.closeDrawers();

                Intent intent;

                switch (item.getItemId()) {
                    case R.id.home:
                        intent = new Intent(getApplicationContext(), Main2Activity.class);
                        intent.putExtra(getString(R.string.intentExtra), getString(R.string.home_string));
                        startActivity(intent);
                        return true;

                    case R.id.events:
                        intent = new Intent(getApplicationContext(), EventsActivity.class);
                        intent.putExtra(getString(R.string.intentExtra), getString(R.string.events_string));
                        startActivity(intent);
                        return true;

                    case R.id.work:
                        intent = new Intent(getApplicationContext(), Main2Activity.class);
                        intent.putExtra(getString(R.string.intentExtra), getString(R.string.workshops_string));
                        startActivity(intent);
                        return true;

                    case R.id.summit:
                        intent = new Intent(getApplicationContext(), Main2Activity.class);
                        intent.putExtra(getString(R.string.intentExtra), getString(R.string.summit_string));
                        startActivity(intent);
                        return true;

                    case R.id.spons:
                        intent = new Intent(getApplicationContext(), Main2Activity.class);
                        intent.putExtra(getString(R.string.intentExtra), getString(R.string.sponsors_string));
                        startActivity(intent);
                        return true;

                    case R.id.about:
                        intent = new Intent(getApplicationContext(), Main2Activity.class);
                        intent.putExtra(getString(R.string.intentExtra), getString(R.string.aboutus_string));
                        startActivity(intent);
                        return true;

                    default:
                        return true;

                }
            }

        });

    }

}