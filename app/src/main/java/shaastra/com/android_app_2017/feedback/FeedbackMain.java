package shaastra.com.android_app_2017.feedback;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import shaastra.com.android_app_2017.R;
import shaastra.com.android_app_2017.feedback.reused.EventDatabase;


import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressLint("HandlerLeak")


public class FeedbackMain extends ListActivity {

    int pos, count = 0;
    Thread networkThread;
    Boolean httpRes = false;
    String result="", resPage, user, pass;

    EventDatabase db;
    FeedbackDatabase info;
    Context context;
    JSONParser jsonParser=new JSONParser();
    Singleton app;

    String savelocation,backupsavelocation;


    String[] extra_depts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        user = getIntent().getStringExtra("user");
        pass = getIntent().getStringExtra("pass");
        Resources res=getResources();
        extra_depts=res.getStringArray(R.array.ExtraDept);
        savelocation=res.getString(R.string.feedback_csv);
        backupsavelocation=res.getString(R.string.feedback_backup_csv);

        Log.i("chooseUser", user);
        Log.i("choosePass", pass);



        String[] choices = { "Get Feedback", //"Get list from server",
                "Refresh Event List", "Show Feedback data", "Export to csv and send"//,"Send exported csv"
                ,"Delete Feedback database"};
        setListAdapter(new ArrayAdapter<String>(FeedbackMain.this,
                android.R.layout.simple_list_item_1, choices));
        setContentView(R.layout.choose_re);
    }

    public ProgressDialog mDialog;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0)
                mDialog.dismiss();
            else if (msg.what == 1)
                mDialog = ProgressDialog.show(FeedbackMain.this, "Fetching Data",
                        "Loading Please Wait", true);
            else if (msg.what == 2)
                mDialog = ProgressDialog.show(FeedbackMain.this, "Connection Error",
                        "Cannot connect to server", true);
            else if (msg.what == 3)
                mDialog = ProgressDialog.show(FeedbackMain.this,
                        "Refreshing Event List", "Loading Please Wait", true);
        }
    };

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        pos = position + 1;
        db = new EventDatabase(this);
        info = new FeedbackDatabase(this);
        context = getApplicationContext();
		/*networkThread = new Thread(new Runnable() {

			@Override
			public void run() {*/
        switch (pos+1) {
            case 2:
                Intent reg = new Intent(FeedbackMain.this, FeedbackForm.class);
                reg.putExtra("position", (long) (pos));
                reg.putExtra("user", user);
                reg.putExtra("pass", pass);
                startActivity(reg);
                break;
//					case 2:
//						Log.v("in choose","case 2");
//						// TODO fix app crash on no internet connection
//						// Intent list = new Intent(Choose.this, GetList.class);
//						// list.putExtra("user", user);
//						// list.putExtra("pass", pass);
//						Toast.makeText(context,
//								"Disabled,  wait for it!", Toast.LENGTH_SHORT)
//								.show();
//						// startActivity(list);
//						break;
            case 3:
                //handler.sendEmptyMessage(3);
                ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext()
                        .getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetworkInfo = connectivityManager
                        .getActiveNetworkInfo();
                final boolean a = activeNetworkInfo != null && activeNetworkInfo.isConnected();
                if(a){
                    //new getEvents().execute();
                } else {
                    Toast.makeText(
                            getApplicationContext(),
                            "No internet connection. Check your connection and "
                                    + "try again later", Toast.LENGTH_SHORT).show();
                }

                break;
            case 4:
                Intent table = new Intent(FeedbackMain.this, DisplayFeedback.class);
                table.putExtra("user", user);
                table.putExtra("pass", pass);
                startActivity(table);
                break;
            case 5:
                info.open();
                try {
                    info.dbToCsv(false);
                    sendcsv();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                info.close();
                break;
            case 6:

                TextView msg = new TextView(this);
                msg.setText(Html.fromHtml("Are you sure you want to delete the existing feedback database?"));
                msg.setTextSize(16);
                msg.setTextColor(Color.GRAY);
                msg.setMovementMethod(LinkMovementMethod.getInstance());
                AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
                builder1.setView(msg);
                builder1.setCancelable(true);
                builder1.setPositiveButton("OK.",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                info.open();
                                try {
                                    info.dbToCsv(true);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                Toast.makeText(
                                        getApplicationContext(),
                                        "Backup made at "+backupsavelocation, Toast.LENGTH_LONG).show();
                                info.delete();
                                info.close();
                                dialog.cancel();
                            }
                        });
                builder1.setNegativeButton("No!",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();



        }
			/*}
		});
		networkThread.start();*/
    }

    public void sendcsv(){

        File file = new File(savelocation);
        if(file.exists() && file.length()>24){
            String filelocation = "/mnt/sdcard/data.csv";
            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            //sharingIntent.setType("vnd.android.cursor.dir/email");
            sharingIntent.setType("text/html");
            String[] to = {"webops@shaastra.org"};
            sharingIntent.putExtra(Intent.EXTRA_EMAIL, to);
            sharingIntent.putExtra(Intent.EXTRA_STREAM,
                    Uri.fromFile(file));
            sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Data from barcode scanner app");
            startActivity(Intent.createChooser(
                    sharingIntent, "Send email"));
            info.open();
            info.makeSent();
            info.close();
        }
        else
            Toast.makeText(getApplicationContext(), "ERR: No Data to send", Toast.LENGTH_SHORT).show();
    }





}

