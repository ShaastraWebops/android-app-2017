package shaastra.com.android_app_2017.feedback;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import shaastra.com.android_app_2017.R;
import shaastra.com.android_app_2017.feedback.reused.Choose;

public class FeedbackActivity extends AppCompatActivity {

    Button pbut,ebut,fbut;
    String user,pass;

    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        user = null;//getIntent().getStringExtra("user");
        pass = null;//getIntent().getStringExtra("pass");
        user = "test";//getIntent().getStringExtra("user");
        pass = "test";
        Log.i("chooseUser", user);
        Log.i("choosePass", pass);
        setContentView(R.layout.activity_feedback);

        pbut = (Button) findViewById(R.id.button1);
        ebut= (Button) findViewById(R.id.button2);
        fbut= (Button) findViewById(R.id.button3);
        pbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(FeedbackActivity.this, Passport.class);
                intent.putExtra("user", user);
                intent.putExtra("pass", pass);
                // i.putExtra("myStrings",myStrings);
                startActivity(intent);
            }
        });
        ebut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(FeedbackActivity.this, Choose.class);
                intent.putExtra("user", user);
                intent.putExtra("pass", pass);
                startActivity(intent);
            }
        });


        fbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(FeedbackActivity.this, FeedbackMain.class);
                intent.putExtra("user", user);
                intent.putExtra("pass", pass);
                startActivity(intent);
            }
        });

    }

}
