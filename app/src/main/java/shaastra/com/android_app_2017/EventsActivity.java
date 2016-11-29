package shaastra.com.android_app_2017;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;
import android.widget.Toast;

import com.mukesh.MarkdownView;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabSelectedListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EventsActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private JSONObject response;
    private TabLayout tabLayout;
    private String dialPhone;
    private static ArrayList<String> jsonArr = new ArrayList<>(5);

    //Array of json key in the order of the tabs
    public ArrayList <Integer> keyArray = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
        jsonArr.clear();
        dialPhone = null;

        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        //TODO Pass the event vertical as an extra to this activity
        toolbar.setTitle("Event Vertical");
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        CollapsingToolbarLayout toolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        if (toolbarLayout != null){
        }

        //Initialising the Footer with Bootom Navigation bar
        BottomBar bottomBar = (BottomBar) findViewById(R.id.bottombar);
        bottomBar.setItemsFromMenu(R.menu.event_footer, new OnMenuTabSelectedListener() {
            @Override
            public void onMenuItemSelected(int menuItemId) {
                switch (menuItemId) {
                    case R.id.bookmark:
                        Toast.makeText(getApplicationContext(), "Bookmark", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.locate:
                        Toast.makeText(getApplicationContext(), "Bookmark", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.call:
                        if (dialPhone != null) {
                            Intent intent = new Intent(Intent.ACTION_DIAL);
                            intent.setData(Uri.parse("tel:" + dialPhone));
                            startActivity(intent);
                        }else{
                            Toast.makeText(getApplicationContext(), "Wait for Coordinator contact to Load", Toast.LENGTH_LONG).show();
                        }
                        break;
                    case R.id.share:
                        Toast.makeText(getApplicationContext(), "Bookmark", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });

        //Assigning keyArray
        keyArray.add(0);
        keyArray.add(1);
        keyArray.add(2);
        keyArray.add(4);
        keyArray.add(6);

        //Dynamically add fragments to the adapter
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mSectionsPagerAdapter.addFragment(PlaceholderFragment.newInstance(0), "Home");
        mSectionsPagerAdapter.addFragment(PlaceholderFragment.newInstance(1), "Format");
        mSectionsPagerAdapter.addFragment(PlaceholderFragment.newInstance(2), "PS");
        mSectionsPagerAdapter.addFragment(PlaceholderFragment.newInstance(3), "Prizes");
        mSectionsPagerAdapter.addFragment(PlaceholderFragment.newInstance(4), "FAQ");

        //Setup adapter to viewPager
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        //Setup Tablayout with viewPager
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        new FetchEventTask().execute("http://shaastra.org:8001/api/events/showWeb/57d43892a65edf661ac43909");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_events, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void saveResponse(JSONArray response){
        int ind = 0;
        while(ind < 5) {
            try {
                jsonArr.add(((JSONObject) response.get(keyArray.get(ind))).getString("info"));
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                ind++;
            }
        }
    }

    //A placeholder fragment containing a TextView.
    public static class PlaceholderFragment extends Fragment {

        //Section Id to identify the fragment
        private static final String ARG_SECTION_ID = "section_id";
        //Textview in the fragment
        public MarkdownView textView;

        public PlaceholderFragment() {
        }

        //Constructor with section id
        public static PlaceholderFragment newInstance(int id ) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_ID, id);
            fragment.setArguments(args);
            return fragment;
        }

        //On inflating the fragment
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_events, container, false);
            textView = (MarkdownView) rootView.findViewById(R.id.section_label);

            if (!jsonArr.isEmpty()){
                textView.setMarkDownText(jsonArr.get(getArguments().getInt(ARG_SECTION_ID)));
            }else {
                textView.setMarkDownText("Loading...");
            }
            return rootView;
        }
        /*
        @Override
        public void onActivityCreated(@Nullable Bundle savedInstanceState){
            super.onActivityCreated((savedInstanceState));
            if (savedInstanceState != null) {
                textView.setText(savedInstanceState.getString("content"));
            }
           }
        */

    }

    //Class to asynchronously fetch event details from API
    class FetchEventTask extends AsyncTask<String, Void, JSONObject>{

        protected JSONObject doInBackground(String... urls){
            response = GetRequest.execute(urls[0], null);
            Log.i("URL", urls[0]);
            Log.i("Response", String.valueOf(response));
            return response;
        }

        protected void onPostExecute(JSONObject response){
            try {
                JSONArray object = response.getJSONObject("data").getJSONArray("eventTabs");
                TextView textView =  (TextView) findViewById(R.id.eventTitle);
                textView.setText(response.getJSONObject("data").getString("name"));
                textView = (TextView) findViewById(R.id.venue);
                textView.setText(response.getJSONObject("data").getString("venue"));
                dialPhone = ((JSONObject) response.getJSONObject("data").getJSONArray("assignees").get(0)).getString("phoneNumber");
                saveResponse(object);
                mSectionsPagerAdapter.addResponse(object);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    //Implementation of FragmentPagerAdapter which has FragmentList and FragmentTitleList
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private final List<PlaceholderFragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public void addResponse(JSONArray response){
            int ind = 0;
            Iterator<PlaceholderFragment> iterator = mFragmentList.iterator();
            while(iterator.hasNext()) {
                PlaceholderFragment fragment = iterator.next();
                try {
                    if (fragment.textView != null) {
                        fragment.textView.setMarkDownText(((JSONObject) response.get(keyArray.get(ind))).getString("info"));
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                ind++;
            }
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return  mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }

        public void addFragment(PlaceholderFragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }
    }
}
