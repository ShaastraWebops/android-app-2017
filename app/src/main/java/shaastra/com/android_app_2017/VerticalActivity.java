package shaastra.com.android_app_2017;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class VerticalActivity extends AppCompatActivity {
    private JSONObject response;
    public RecyclerView recyclerView;
    public VerticalAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vertical);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        String url = "http://shaastra.org:8001/api/eventLists/events/57ceccc4a65edf661ac430e4";
        new FetchVerticalTask().execute(url);

//        GetActivity g = new GetActivity(getApplicationContext());
//        JSONArray verticals = g.getJSONobject(url, getApplicationContext());

//        String imageid = null, imagename = null;
//        try {
//            JSONObject obj = (JSONObject) verticals.get(0);
//            imageid = obj.getString("imageid");
//            imagename = obj.getString("imagename");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }


//        ImageView eventimage = (ImageView) findViewById(R.id.eventimg);
//
//        int loader = R.mipmap.ic_launcher;
//        String imageurl = "http://shaastra.org:8001/api/uploads/"+imageid+"/"+imagename;
//
//        ImageLoader imageLoader = new ImageLoader(getApplicationContext());
//        imageLoader.DisplayImage(imageurl, loader, eventimage);



    }

    //Class to asynchronously fetch event details from API
    class FetchVerticalTask extends AsyncTask<String, Void, JSONObject> {

        protected JSONObject doInBackground(String... urls){
            response = GetRequest.execute(urls[0], VerticalActivity.this, null);
            Log.i("URL", urls[0]);
            Log.i("Response", String.valueOf(response));
            return response;
        }

        protected void onPostExecute(JSONObject response){
            try {
                JSONArray object = response.getJSONObject("data").getJSONArray("events");
                List<VerticalItem> list = new ArrayList<>();

                int i=0;
                while(i<object.length())
                {
                    try {
                        JSONObject obj =(JSONObject) object.get(i);
                        VerticalItem item = new VerticalItem((String) obj.get("name"));
                        list.add(item);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    i++;
                }
//                TextView textView =  (TextView) findViewById(R.id.eventTitle);
//                textView.setText(response.getJSONObject("data").getString("name"));
                recyclerView = (RecyclerView) findViewById(R.id.verticals_view);
                adapter = new VerticalAdapter(list);
                recyclerView.setLayoutManager(new LinearLayoutManager(VerticalActivity.this));
                recyclerView.setAdapter(adapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}