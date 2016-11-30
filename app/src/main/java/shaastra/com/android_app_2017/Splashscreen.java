package shaastra.com.android_app_2017;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;


/**
 * Created by Shyam Shankar on 31-10-2016.
 */
public class Splashscreen extends Activity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);


        Thread timerThread = new Thread(){
            public void run(){
                try{
                    sleep(3000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }finally
                {
                    final SharedPreferences sharedPreferences = getSharedPreferences("ShaPreferences", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    final boolean  firstTime=sharedPreferences.getBoolean("first", true);
                    if(firstTime) {
                        editor.putBoolean("first",false);
                        editor.apply();
                        Intent intent = new Intent(Splashscreen.this, BeforeMain.class);
                        startActivity(intent);
                    }
                    else
                    {
                        Intent intent = new Intent(Splashscreen.this, MainActivity.class);
                        startActivity(intent);
                    }
//                    Intent intent = new Intent(Splashscreen.this,MainActivity.class);
//                    startActivity(intent);
                }
            }
        };
        timerThread.start();
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        finish();
    }
}
