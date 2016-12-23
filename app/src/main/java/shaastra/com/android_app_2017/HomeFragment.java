package shaastra.com.android_app_2017;


import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    TextView tv;
    long diff;
    long milliseconds;
    long endTime;

    ArrayList<String> name;

    ArrayList<String> schedule;

    List<Strings> list = new ArrayList<>() ;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        tv = (TextView) view.findViewById(R.id.countDown);
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy, HH:mm");
        formatter.setLenient(false);

        String oldTime = "31.12.2016, 08:00";
        Date oldDate;
        try {
            oldDate = formatter.parse(oldTime);
            milliseconds = oldDate.getTime();

            //long startTime = System.currentTimeMillis();
            // do your work...
            long endTime = System.currentTimeMillis();
            diff = milliseconds - endTime;

//            Log.e("day", "miliday"+diff);
//            long seconds = (long) (diff / 1000) % 60 ;
//            Log.e("secnd", "miliday"+seconds);
//            long minutes = (long) ((diff / (1000*60)) % 60);
//            Log.e("minute", "miliday"+minutes);
//            long hours   = (long) ((diff / (1000*60*60)) % 24);
//            Log.e("hour", "miliday"+hours);
//            long days = (int)((diff / (1000*60*60*24)) % 365);
//            Log.e("days", "miliday"+days);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        new CountDownTimer(diff, 1000) {

            public void onTick(long millisUntilFinished) {
                tv.setText((millisUntilFinished / 1000) / 86400 + " days, " + ((millisUntilFinished / 1000) % 86400) / 3600 + " hours remaining!");
            }

            public void onFinish() {
                tv.setText("Welcome to Shaastra 2017!");
            }
        }.start();
//        MyCount counter = new MyCount(milliseconds,1000);
//        counter.start();

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)

    {


        File f = new File(getContext().getCacheDir() + File.separator + "Bookmarks.txt");


        try {
            BufferedReader reader = new BufferedReader(new FileReader(f));





            String line;

            try {
                while( (line = reader.readLine()) != null)

                    Log.e("error_1","something is ther");

                {
                    String[] array = line.split("\t");

                    Strings object = new Strings(array);

                    list.add(object);

                }


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    RecyclerView recycler1 = (RecyclerView) getView().findViewById(R.id.RecylerView_1);
    Log.e("error_1", "passed recyclerview");

        if (list!=null)
        {
            Log.e("error_1","Number is " + String.valueOf(list.size()));
        }
    recycler1.setLayoutManager(new LinearLayoutManager(getContext()));
    recycler1.setAdapter(new CustomAdapter());



    }

    class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {










        public CustomAdapter() {

        }
        public class ViewHolder extends RecyclerView.ViewHolder implements RecyclerView.OnClickListener {

            TextView title1, subtitle;
            ImageView imageview;




            public ViewHolder(View itemView) {

                super(itemView);

                itemView.setOnClickListener(this);




                title1 = (TextView) itemView.findViewById(R.id.text2);
                subtitle = (TextView) itemView.findViewById(R.id.text1);
                imageview = (ImageView)itemView.findViewById(R.id.imageView);



            }

            @Override
            public void onClick(View view) {



                //Intent i = new Intent(getActivity().getApplicationContext(),NEXT_ACTIVITY.class);

                //i.putExtra("VERTICAL_ID",s);

                //startActivity(i);

            }
        }


        @Override
        public CustomAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_view, parent, false);



            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(CustomAdapter.ViewHolder holder, int position) {
            Log.e("error_1","position is " + String.valueOf(position));




            Strings item = list.get(position);

            String[] string_array = item.getarray();



            holder.title1.setText(string_array[2]);

            holder.imageview.setImageResource(Integer.valueOf(string_array[3]));










        }

        @Override
        public int getItemCount() {
            return list.size();
        }



    }


}

