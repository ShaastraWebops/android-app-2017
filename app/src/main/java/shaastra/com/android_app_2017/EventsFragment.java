package shaastra.com.android_app_2017;


import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class EventsFragment extends Fragment {
    View view;
    JSONObject response;
    private RecyclerView recyclerView;
    private EventListAdapter adapter;
    private ProgressDialog pdia;

    public EventsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_events, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

//        Title = new ArrayList<>();
//        Subtitle = new ArrayList<>();
        Log.e("error", "entered onviewcreated");
        String url = "http://shaastra.org:8001/api/eventLists/";
        new FetchListTask().execute(url);

    }

    class FetchListTask extends AsyncTask<String, Void, JSONObject> {

        protected void onPreExecute() {
//            super.onPreExecute();
            pdia = new ProgressDialog(getContext());
            pdia.setMessage("Loading...");
            pdia.setCancelable(false);
            pdia.show();
        }

        protected JSONObject doInBackground(String... urls){
            response = GetRequest.execute(urls[0], getActivity(), null);
            Log.i("URL", urls[0]);
            Log.i("Response", String.valueOf(response));
            return response;
        }

        protected void onPostExecute(JSONObject response){
            try {
                JSONArray object = response.getJSONObject("data").getJSONArray("response");
                List<EventListitem> list = new ArrayList<>();

                int i=0;
                while(i<object.length())
                {
                    try {
                        JSONObject obj =(JSONObject) object.get(i);
                        EventListitem item = new EventListitem(obj.getString("_id"), obj.getString("title"), obj.getString("imageid"), (obj.getString("imagename")));
                        if(!obj.getString("title").equals("WORKSHOP"))
                            list.add(item);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    i++;
                }

                recyclerView = (RecyclerView) view.findViewById(R.id.recylerView);
                adapter = new EventListAdapter(list, getContext());
                recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
                recyclerView.setAdapter(adapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            pdia.dismiss();
        }
    }

}
