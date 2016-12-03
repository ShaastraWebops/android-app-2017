package shaastra.com.android_app_2017;


import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    TextView tv;
    long diff;
    long milliseconds;
    long endTime;

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
            long endTime=System.currentTimeMillis();
            diff = milliseconds-endTime;

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
                tv.setText((millisUntilFinished / 1000)/86400 + " days, " + ((millisUntilFinished / 1000)%86400)/3600 + " hours remaining!");
            }

            public void onFinish() {
                tv.setText("Welcome to Shaastra 2017!");
            }
        }.start();
//        MyCount counter = new MyCount(milliseconds,1000);
//        counter.start();

        return view;
    }

}
