package shaastra.com.android_app_2017.feedback.reused;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Shyam Shankar on 27-11-2016.
 */

public class EventDatabase {
    public static final String KEY_ROWID = "_id";
    public static final String KEY_EVENTID = "event_id";
    public static final String KEY_EVENT = "event_name";
    public static final String KEY_TEAM = "is_team";

    private static final String DATABASE_NAME = "ListOfEvents";
    private static final String DATABASE_TABLE1 = "eventTable";
    private static final String DATABASE_TABLE2 = "eventTableOld";
    private static final int DATABASE_VERSION = 3;

    private Data myData;
    private final Context myContext;
    private static SQLiteDatabase myDatabase;

    private static class Data extends SQLiteOpenHelper{

        public Data(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            // TODO Auto-generated constructor stub
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            // TODO Auto-generated method stub
            db.execSQL("CREATE TABLE " + DATABASE_TABLE1 + " (" + KEY_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    KEY_EVENTID+" TEXT NOT NULL, "+
                    KEY_EVENT + " TEXT NOT NULL, "+
                    KEY_TEAM + " TEXT NOT NULL);"
            );
            db.execSQL("CREATE TABLE " + DATABASE_TABLE2 + " (" + KEY_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    KEY_EVENTID+" TEXT NOT NULL, "+
                    KEY_EVENT + " TEXT NOT NULL, "+
                    KEY_TEAM + " TEXT NOT NULL);"
            );
            ContentValues cv1 = new ContentValues();
            cv1.put(KEY_ROWID,0);
            cv1.put(KEY_EVENTID, "1");
            cv1.put(KEY_EVENT, "SURC");
            cv1.put(KEY_TEAM, "1");
            db.insert(DATABASE_TABLE1, null, cv1);

            ContentValues cv2 = new ContentValues();
            cv2.put(KEY_ROWID,1);
            cv2.put(KEY_EVENTID, "2");
            cv2.put(KEY_EVENT, "BlackBox");
            cv2.put(KEY_TEAM, "1");
            db.insert(DATABASE_TABLE1, null, cv2);

            ContentValues cv3 = new ContentValues();
            cv3.put(KEY_ROWID, 2);
            cv3.put(KEY_EVENTID, "3");
            cv3.put(KEY_EVENT, "Elekart");
            cv3.put(KEY_TEAM, "1");
            db.insert(DATABASE_TABLE1, null, cv3);

            ContentValues cv4 = new ContentValues();
            cv4.put(KEY_ROWID, 3);
            cv4.put(KEY_EVENTID, "4");
            cv4.put(KEY_EVENT, "Android App Development Workshop");
            cv4.put(KEY_TEAM, "0");
            db.insert(DATABASE_TABLE1, null, cv4);

            ContentValues cv5 = new ContentValues();
            cv5.put(KEY_ROWID, 4);
            cv5.put(KEY_EVENTID, "5");
            cv5.put(KEY_EVENT, "Game Development Workshop");
            cv5.put(KEY_TEAM, "0");
            db.insert(DATABASE_TABLE1, null, cv5);

            ContentValues cv6 = new ContentValues();
            cv6.put(KEY_ROWID, 5);
            cv6.put(KEY_EVENTID, "6");
            cv6.put(KEY_EVENT, "PCB Design Workshop");
            cv6.put(KEY_TEAM, "0");
            db.insert(DATABASE_TABLE1, null, cv6);

            ContentValues cv7 = new ContentValues();
            cv7.put(KEY_ROWID, 6);
            cv7.put(KEY_EVENTID, "7");
            cv7.put(KEY_EVENT, "Python Workshop");
            cv7.put(KEY_TEAM, "0");
            db.insert(DATABASE_TABLE1, null, cv7);

            ContentValues cv8 = new ContentValues();
            cv8.put(KEY_ROWID, 7);
            cv8.put(KEY_EVENTID, "8");
            cv8.put(KEY_EVENT, "Web Development Workshop");
            cv8.put(KEY_TEAM, "0");
            db.insert(DATABASE_TABLE1, null, cv8);

            ContentValues cv9 = new ContentValues();
            cv9.put(KEY_ROWID, 8);
            cv9.put(KEY_EVENTID, "9");
            cv9.put(KEY_EVENT, "Junkyard Wars");
            cv9.put(KEY_TEAM, "1");
            db.insert(DATABASE_TABLE1, null, cv9);

            ContentValues cv10 = new ContentValues();
            cv10.put(KEY_ROWID, 9);
            cv10.put(KEY_EVENTID, "10");
            cv10.put(KEY_EVENT, "How Things Work");
            cv10.put(KEY_TEAM, "0");
            db.insert(DATABASE_TABLE1, null, cv10);

            ContentValues cv11 = new ContentValues();
            cv11.put(KEY_ROWID, 10);
            cv11.put(KEY_EVENTID, "11");
            cv11.put(KEY_EVENT, "Puzzle Champ");
            cv11.put(KEY_TEAM, "0");
            db.insert(DATABASE_TABLE1, null, cv11);

            ContentValues cv12 = new ContentValues();
            cv12.put(KEY_ROWID, 11);
            cv12.put(KEY_EVENTID, "12");
            cv12.put(KEY_EVENT, "Paper Plane");
            cv12.put(KEY_TEAM, "0");
            db.insert(DATABASE_TABLE1, null, cv12);

            ContentValues cv13 = new ContentValues();
            cv13.put(KEY_ROWID, 12);
            cv13.put(KEY_EVENTID, "13");
            cv13.put(KEY_EVENT, "Reverse Coding");
            cv13.put(KEY_TEAM, "0");
            db.insert(DATABASE_TABLE1, null, cv13);

            ContentValues cv14 = new ContentValues();
            cv14.put(KEY_ROWID, 13);
            cv14.put(KEY_EVENTID, "14");
            cv14.put(KEY_EVENT, "Mixel");
            cv14.put(KEY_TEAM, "0");
            db.insert(DATABASE_TABLE1, null, cv14);

            ContentValues cv15 = new ContentValues();
            cv15.put(KEY_ROWID, 14);
            cv15.put(KEY_EVENTID, "15");
            cv15.put(KEY_EVENT, "BugCrush");
            cv15.put(KEY_TEAM, "0");
            db.insert(DATABASE_TABLE1, null, cv15);

            ContentValues cv16 = new ContentValues();
            cv16.put(KEY_ROWID, 15);
            cv16.put(KEY_EVENTID, "16");
            cv16.put(KEY_EVENT, "Research in 2 Min");
            cv16.put(KEY_TEAM, "0");
            db.insert(DATABASE_TABLE1, null, cv16);
        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // TODO Auto-generated method stub
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE1);
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE2);
            onCreate(db);
        }
    }
    public long update(){
        myDatabase.delete(DATABASE_TABLE2, null, null);
        myDatabase.execSQL("INSERT INTO "+DATABASE_TABLE2+" SELECT * FROM "+DATABASE_TABLE1);
        return myDatabase.delete(DATABASE_TABLE1, null, null);
    }
    public EventDatabase(Context c){
        myContext = c;
    }
    public EventDatabase open(){
        myData = new Data(myContext);
        myDatabase = myData.getWritableDatabase();
        return this;
    }
    public long createEntry(String event, String eventid, String isTeam){
        ContentValues cv = new ContentValues();
        cv.put(KEY_EVENT, event);
        cv.put(KEY_EVENTID, eventid);
        cv.put(KEY_TEAM, isTeam);
        return myDatabase.insert(DATABASE_TABLE1, null, cv);
    }
    public String[][] getData(){
        int count=0;
        int i=0;
        String[] columns = new String[]{KEY_ROWID,KEY_EVENTID,KEY_EVENT,KEY_TEAM};
        Cursor c = myDatabase.query(DATABASE_TABLE1, columns, null, null, null, null, null);
        int eventid = c.getColumnIndex(KEY_EVENTID);
        int  event= c.getColumnIndex(KEY_EVENT);
        int team = c.getColumnIndex(KEY_TEAM);

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
            count++;
        }
        String[][] data = new String[3][count];
        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
            data[0][i] = c.getString(eventid);
            data[1][i] = c.getString(event);
            data[2][i] = c.getString(team);
            i++;
        }
        return data;
    }
    public void close(){
        myData.close();
    }
}
