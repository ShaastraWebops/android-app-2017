package shaastra.com.android_app_2017;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
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
    private static ArrayList<String> jsonArr = new ArrayList<>(8);

    //Array of json key in the order of the tabs
    public ArrayList <String> keyArray = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
        jsonArr.clear();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);

        //Assigning keyArray
        keyArray.add("info");
        keyArray.add("info");
        keyArray.add("info");
        keyArray.add("info");
        keyArray.add("info");
        keyArray.add("info");
        keyArray.add("info");
        keyArray.add("info");

        //Dynamically add fragments to the adapter
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mSectionsPagerAdapter.addFragment(PlaceholderFragment.newInstance(0), "Home");
        mSectionsPagerAdapter.addFragment(PlaceholderFragment.newInstance(1), "Event Format");
        mSectionsPagerAdapter.addFragment(PlaceholderFragment.newInstance(2), "Problem Statement");
        mSectionsPagerAdapter.addFragment(PlaceholderFragment.newInstance(3), "Registration");
        mSectionsPagerAdapter.addFragment(PlaceholderFragment.newInstance(4), "Prize Money");
        mSectionsPagerAdapter.addFragment(PlaceholderFragment.newInstance(5), "Resources");
        mSectionsPagerAdapter.addFragment(PlaceholderFragment.newInstance(6), "FAQs");
        mSectionsPagerAdapter.addFragment(PlaceholderFragment.newInstance(7), "Contact Us");

        //Setup adapter to viewPager
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        //Setup Tablayout with viewPager
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        new FetchEventTask().execute("http://shaastra.org:8001/api/eventLists/events/57ceccc4a65edf661ac430e4");

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

    public void saveResponse(JSONObject response){
        int ind = 0;
        while(ind < 8) {
            try {
                jsonArr.add(response.getString(keyArray.get(ind)));
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
        public TextView textView;

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
            textView = (TextView) rootView.findViewById(R.id.section_label);
            if (!jsonArr.isEmpty()){
                textView.setText(jsonArr.get(getArguments().getInt(ARG_SECTION_ID)));
            }else {
                textView.setText("Loading...");
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
                JSONObject object =  (JSONObject) response.getJSONObject("data").getJSONArray("events").get(0);
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

        public void addResponse(JSONObject response){
            int ind = 0;
            Iterator<PlaceholderFragment> iterator = mFragmentList.iterator();
            while(iterator.hasNext()) {
                PlaceholderFragment fragment = iterator.next();
                try {
                    if (fragment.textView != null) {
                        fragment.textView.setText(response.getString(keyArray.get(ind)));
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
