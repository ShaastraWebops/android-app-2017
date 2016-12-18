package shaastra.com.android_app_2017;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;


/**
 * Created by Shyam Shankar on 31-10-2016.
 */
public class Splashscreen extends Activity {
    private ImageView imageView, copter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        imageView = (ImageView) findViewById(R.id.marqueeimage);
        copter = (ImageView) findViewById(R.id.copter);

        Animation animation = new TranslateAnimation(0,-1000,0, 0);
        animation.setDuration(3000);
        animation.setFillAfter(true);
        animation.setInterpolator(new LinearInterpolator());
        imageView.startAnimation(animation);

        Animation animation2 = new TranslateAnimation(0,0,0,50);
        animation2.setDuration(3000);
        animation2.setFillAfter(true);
        animation2.setInterpolator(new CycleInterpolator(2));
        copter.startAnimation(animation2);
//        imageView.setVisibility(View.GONE);

        Thread timerThread = new Thread(){
            public void run(){
                try{
                    sleep(3000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }finally{
                    Intent intent = new Intent(Splashscreen.this,MainActivity.class);
                    startActivity(intent);
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
