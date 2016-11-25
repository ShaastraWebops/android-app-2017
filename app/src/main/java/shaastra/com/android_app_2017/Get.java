package shaastra.com.android_app_2017;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONObject;
import org.w3c.dom.Text;

public class Get extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get);

        TextView tv = (TextView) findViewById(R.id.textView);

        String request_url = "http://shaastra.org:8001/api/events";
        GetActivity getActivity = new GetActivity(getApplicationContext());

        JSONObject object = getActivity.getJSONobject(request_url, getApplicationContext());

        tv.setText(String.valueOf(object));
    }
}
