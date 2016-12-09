package shaastra.com.android_app_2017;


import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
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
public class WorkshopsFragment extends Fragment {
    View view;
    JSONObject response;
    private RecyclerView recyclerView;
    private WorkshopListAdapter adapter;
    private ProgressDialog pdia;


    public WorkshopsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_workshops, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

//        Title = new ArrayList<>();
//        Subtitle = new ArrayList<>();
        List<EventListitem> list = new ArrayList<>();
        EventListitem item;
        item = new EventListitem("Robotics", "Robotics", null, null);
        list.add(item);
        item = new EventListitem("Coding", "Coding", null, null);
        list.add(item);
        item = new EventListitem("Design", "Design", null, null);
        list.add(item);
        item = new EventListitem("Mechanical", "Mechanical", null, null);
        list.add(item);
        item = new EventListitem("Miscellaneous", "Miscellaneous", null, null);
        list.add(item);

        int[] imageresources = new int[]{R.drawable.robotics, R.drawable.coding, R.drawable.design, R.drawable.mechanical,
                R.drawable.miscellaneous};
        recyclerView = (RecyclerView) view.findViewById(R.id.recylerView);
        adapter = new WorkshopListAdapter(list, getContext(), imageresources);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setAdapter(adapter);

    }

}
