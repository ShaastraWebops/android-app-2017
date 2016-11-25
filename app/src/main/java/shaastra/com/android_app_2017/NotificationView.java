package shaastra.com.android_app_2017;

import android.app.Activity;
import android.os.Bundle;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
/**
 * Created by Shyam Shankar on 01-11-2016.
 */
public class NotificationView extends Activity {

    Button btnShow, btnClear;
    NotificationManager manager;
    Notification myNotication;


    private void initialise() {
        btnShow = (Button) findViewById(R.id.btnShowNotification);
        btnClear = (Button) findViewById(R.id.btnClearNotification);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification);
        initialise();
        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        btnShow.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent("com.rj.notitfications.SECACTIVITY");

                PendingIntent pendingIntent = PendingIntent.getActivity(NotificationView.this, 1, intent, 0);

                Notification.Builder builder = new Notification.Builder(NotificationView.this);

                builder.setAutoCancel(false);
                builder.setTicker("this is ticker text");
                builder.setContentTitle("Shaastra Notification");
                builder.setContentText("You have a new message");
                builder.setSmallIcon(R.drawable.notif_logo);
                builder.setContentIntent(pendingIntent);
                builder.setOngoing(true);
                builder.setSubText("Hello guys. Shaastra is coming!");
                builder.setNumber(100);
                builder.build();

                myNotication = builder.getNotification();
                manager.notify(11, myNotication);
            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                manager.cancel(11);
            }
        });

    }
}