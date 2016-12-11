package shaastra.com.android_app_2017;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class SummitActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summit);
    }

    public void summ_events_but(View v)
    {
        Intent intent = new Intent(SummitActivity.this, SummitEvents.class);
        startActivity(intent);
    }
}
