package shaastra.com.android_app_2017;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.R.id.list;

public class Verticals extends AppCompatActivity {

    public RecyclerView recyclerView;
    public VerticalAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verticals);

        Intent intent = getIntent();
        String url = "http://shaastra.org:8001/api/eventLists/events/"+intent.getStringExtra("verticalID");

        GetActivity g = new GetActivity(getApplicationContext());
        JSONArray verticals = g.getJSONobject(url, getApplicationContext());

        String imageid = null, imagename = null;
        try {
            JSONObject obj = (JSONObject) verticals.get(0);
            imageid = obj.getString("imageid");
            imagename = obj.getString("imagename");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ImageView eventimage = (ImageView) findViewById(R.id.eventimg);
        
        int loader = R.mipmap.ic_launcher;
        String imageurl = "http://shaastra.org:8001/api/uploads/"+imageid+"/"+imagename;

        ImageLoader imageLoader = new ImageLoader(getApplicationContext());
        imageLoader.DisplayImage(imageurl, loader, eventimage);

        JSONArray events = null;
        try {
            JSONObject obj = (JSONObject) verticals.get(0);
            events = (JSONArray) obj.get("events");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        int size = events.length();
        List<Item> list = new ArrayList<>();

        int i=0;
        while(i<size)
        {
            try {
                JSONObject obj =(JSONObject) events.get(i);
                Item item = new Item((String) obj.get("name"));
                list.add(item);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            i++;
        }

        recyclerView = (RecyclerView) findViewById(R.id.verticals_view);
        adapter = new VerticalAdapter(list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);


    }
}
