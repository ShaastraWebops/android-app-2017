package shaastra.com.android_app_2017;

import android.*;
import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    


    private String[] activityTitles;
    private static final String TAG_HOME = "home";
    private static final String TAG_EVENTS = "events";
    private static final String TAG_WORKSHOPS = "workshops";
    private static final String TAG_SHOWS = "shows";
    private static final String TAG_MAP = "summit";
    private static final String TAG_SUMMIT = "map";
    private static final String TAG_SPONSORS = "sponsors";
    private static final String TAG_ABOUTUS = "about us";
    public static String CURRENT_TAG = TAG_HOME;
//    public int idx;
    private DrawerLayout drawer;
    private Handler mHandler;
    public static int navItemIndex = 0;
    private Toolbar toolbar;
    private FloatingActionButton fab;
    private NavigationView navigationView;

    private boolean shouldLoadHomeFragOnBackPress = true;
    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        idx = R.id.home;
        toolbar = (Toolbar) findViewById(R.id.toolbar);


//        IMPORTANT CODE
//        Button b1 = (Button)findViewById(R.id.notif_page_button);
//        b1.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, NotificationView.class);
//                startActivity(intent);
//            }
//        });

        setSupportActionBar(toolbar);

        mHandler = new Handler();
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        activityTitles = getResources().getStringArray(R.array.nav_item_activity_titles);
        // Drawer object Assigned to the view

        // load nav menu header data
        // loadNavHeader();

        // initializing navigation menu
        setUpNavigationView();

        if (savedInstanceState == null) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_HOME;
            loadHomeFragment();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        navigationView.getMenu().getItem(navItemIndex).setChecked(true);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Log.d("MainActivity", "Cancelled scan");
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                Log.d("MainActivity", "Scanned");
                Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
            }
        } else {
            // This is important, otherwise the result will not be passed to the fragment
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    /***
     * Returns respected fragment that user
     * selected from navigation menu
     */
    private void loadHomeFragment() {
        // selecting appropriate nav menu item
        selectNavMenu();

        // set toolbar title
//        if(navItemIndex != 5) {
            setToolbarTitle();
//        }

        // if user select the current navigation menu again, don't do anything
        // just close the navigation drawer
        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
            drawer.closeDrawers();


            return;
        }

        // Sometimes, when fragment has huge data, screen seems hanging
        // when switching between navigation menus
        // So using runnable, the fragment is loaded with cross fade effect
        // This effect can be seen in GMail app
        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                // update the main content by replacing fragments
                if(navItemIndex == 5) {

                }
                else if(navItemIndex == 6) {

                }
                else {
                    Fragment fragment = getHomeFragment();
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                            android.R.anim.fade_out);
                    fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
                    fragmentTransaction.commitAllowingStateLoss();
                }
            }
        };

        // If mPendingRunnable is not null, then add to the message queue
        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }



        //Closing drawer on item click
        drawer.closeDrawers();

        // refresh toolbar menu
        invalidateOptionsMenu();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startActivity(new Intent(MainActivity.this, MapsActivity.class));

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }


    }

    private Fragment getHomeFragment() {
        switch (navItemIndex) {
            case 0:
                // home
                HomeFragment homeFragment = new HomeFragment();
                return homeFragment;
            case 1:
                // photos
                EventsFragment eventsFragment = new EventsFragment();
                return eventsFragment;
            case 2:
                // movies fragment
                WorkshopsFragment workshopsFragment = new WorkshopsFragment();
                return workshopsFragment;
            case 3:
                // notifications fragment
                ShowsFragment showsFragment = new ShowsFragment();
                return showsFragment;
            case 4:
                // notifications fragment
                SummitFragment summitFragment = new SummitFragment();
                return summitFragment;

//            case 5:
//                startActivity(new Intent(MainActivity.this, MapsActivity.class));

//            case 6:
                // settings fragment
//                FeedbackFragment feedbackFragment = new FeedbackFragment();
//                startActivity(new Intent(MainActivity.this, VerticalActivity.class));
//                return feedbackFragment;
            default:
                return new HomeFragment();
        }
    }

    private void setToolbarTitle() {
        getSupportActionBar().setTitle(activityTitles[navItemIndex]);
    }

    private void selectNavMenu() {
//        navigationView.getMenu().getItem(navItemIndex).setChecked(true);
    }

    private void setUpNavigationView() {
        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
//                idx = menuItem.getItemId();
                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {
                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.home:
//                        idx = R.id.home;
                        navItemIndex = 0;
                        CURRENT_TAG = TAG_HOME;
                        break;
                    case R.id.events:
//                        idx = R.id.events;
                        navItemIndex = 1;
                        CURRENT_TAG = TAG_EVENTS;
                        break;
                    case R.id.work:
//                        idx = R.id.work;
                        navItemIndex = 2;
                        CURRENT_TAG = TAG_WORKSHOPS;
                        break;
                    case R.id.shows:
//                        idx = R.id.shows;
                        navItemIndex = 3;
                        CURRENT_TAG = TAG_SHOWS;
                        break;
                    case R.id.summit:
//                        idx = R.id.summit;
                        navItemIndex = 4;
                        CURRENT_TAG = TAG_SUMMIT;
                        break;
                    case R.id.map:
//                        idx = R.id.map;
                         navItemIndex = 5;
                        CURRENT_TAG = TAG_MAP;
                        drawer.closeDrawers();
                        if (ContextCompat.checkSelfPermission(MainActivity.this,
                                Manifest.permission.ACCESS_FINE_LOCATION)
                                != PackageManager.PERMISSION_GRANTED)
                        {
//                        Toast.makeText(getApplicationContext(), "No perm", Toast.LENGTH_LONG).show();
                            ActivityCompat.requestPermissions(MainActivity.this,
                                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                    MY_PERMISSIONS_REQUEST_READ_CONTACTS);

                            // Should we show an explanation?
                        }
                        else {
                            startActivity(new Intent(MainActivity.this, MapsActivity.class));
                        }
                        return true;

                    case R.id.qrscanner:
//                        navItemIndex = 7;






                    new IntentIntegrator(MainActivity.this).initiateScan();

                        drawer.closeDrawers();
                        return true;
//                        break;
                        // launch new intent instead of loading fragment
//                        startActivity(new Intent(MainActivity.this, AboutUsActivity.class));
//                        drawer.closeDrawers();
//                        return true;
//                    case R.id.nav_privacy_policy:
//                        // launch new intent instead of loading fragment
//                        startActivity(new Intent(MainActivity.this, PrivacyPolicyActivity.class));
//                        drawer.closeDrawers();
//                        return true;
                    default:
                        navItemIndex = 0;
                }

                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) {
                    menuItem.setChecked(false);
                } else {
                    menuItem.setChecked(true);
                }
                menuItem.setChecked(true);

                loadHomeFragment();

                return true;
            }
        });


        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawer.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessary or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawers();
            return;
        }

        // This code loads home fragment when back key is pressed
        // when user is in other fragment than home
        if (shouldLoadHomeFragOnBackPress) {
            // checking if user is on other navigation menu
            // rather than home
            if (navItemIndex != 0) {
                navItemIndex = 0;
                CURRENT_TAG = TAG_HOME;
//                idx = R.id.home;
//                navigationView.getMenu().findItem(navItemIndex).setChecked(true);
                loadHomeFragment();
                return;
            }
        }

        super.onBackPressed();
    }




}
