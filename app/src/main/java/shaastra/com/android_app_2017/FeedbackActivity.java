package shaastra.com.android_app_2017;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FeedbackActivity extends AppCompatActivity {
    private Spinner spinner;
    private Button submit;
    private String eventName;
    private RatingBar rating1, rating2, rating3, rating4, rating5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        addItemsOnSpinner();
        addListenerOnSpinnerItemSelection();

        rating1 = (RatingBar) findViewById(R.id.rating1);
        rating2 = (RatingBar) findViewById(R.id.rating2);
        rating3 = (RatingBar) findViewById(R.id.rating3);
        rating4 = (RatingBar) findViewById(R.id.rating4);
        rating5 = (RatingBar) findViewById(R.id.rating5);

        submit = (Button) findViewById(R.id.button_sub);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String r1 = String.valueOf(rating1.getRating());
                new SubmitFeedback(String.valueOf(rating1.getRating()), String.valueOf(rating2.getRating()),String.valueOf(rating3.getRating()), String.valueOf(rating4.getRating()),String.valueOf(rating5.getRating())).execute();
            }
        });
    }

    // add items into spinner dynamically
    public void addItemsOnSpinner() {

        spinner = (Spinner) findViewById(R.id.spinner);
    }

    public void addListenerOnSpinnerItemSelection() {
        spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                // Showing selected spinner item
                eventName = item;
                Toast.makeText(adapterView.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }



    public class SubmitFeedback extends AsyncTask<Void, Void, Boolean> {

        JSONObject ResponseJSON;
        String q1, q2, q3, q4, q5;

        SubmitFeedback(String r1, String r2, String r3, String r4, String r5) {
            q1 = r1;
            q2 = r2;
            q3 = r3;
            q4 = r4;
            q5 = r5;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
                    ArrayList<PostParam> iPostParams = new ArrayList<PostParam>();
                    PostParam postemail = new PostParam("event_name", eventName);
                    iPostParams.add(postemail);
                    PostParam postcomment = new PostParam("comment", "Hi");
                    iPostParams.add(postcomment);
                    PostParam postrating1 = new PostParam("q1", q1);
                    iPostParams.add(postrating1);
                    PostParam postrating2 = new PostParam("q2", q2);
                    iPostParams.add(postrating2);
                    PostParam postrating3 = new PostParam("q3", q3);
                    iPostParams.add(postrating3);
                    PostParam postrating4 = new PostParam("q4", q4);
                    iPostParams.add(postrating4);
                    PostParam postrating5 = new PostParam("q5", q5);
                    iPostParams.add(postrating5);
                    ResponseJSON = PostRequest.execute("http://shaastra.org:8001/api/feedbacks", iPostParams);
                    Log.d("RESPONSE", ResponseJSON.toString());

                    // TODO: register the new account here.
//                }
//            });
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            Toast.makeText(getApplicationContext(), "Thank you for the feedback", Toast.LENGTH_LONG).show();
            finish();
        }

        @Override
        protected void onCancelled() {
//            mAuthTask = null;
        }
    }
}
