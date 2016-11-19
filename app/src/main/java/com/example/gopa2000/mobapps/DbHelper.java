package com.example.gopa2000.mobapps;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;

/**
 * Created by gopa2000 on 11/10/16.
 */

public class DbHelper extends SQLiteOpenHelper {

    private final String TAG = "SQLiteOpenHelper";

    // went through this much trouble cos had to map to json return objects from the backend - not modifying 20 million
    // queries for a typo

    // db config
    private static final int DB_VERSION = 16;
    public static final String DB_NAME              = "AppDb";
    public static final String TABLE_LISTINGS       = "Listings";
    public static final String TABLE_EMPLOYERS      = "Employers";
    public static final String TABLE_SEEKERS        = "Seekers";
    public static final String TABLE_LIKES          = "LikeTable";
    public static final String TABLE_LASTMODIFIED   = "LastModified";
    public static final String TABLE_MATCHED        = "MatchedTable";


    // common
    public static final String KEY_EMAIL        = "email";
    public static final String KEY_TYPE         = "type";
    public static final String KEY_IMG          = "img";
    public static final String KEY_MOBNUM       = "mobnum";
    public static final String KEY_LIKES        = "likes";
    public static final String KEY_TAGS         = "tags";
    public static final String KEY_DISLIKES     = "dislikes";

    // seeker specific
    public static final String KEY_FNAME        = "fname";
    public static final String KEY_LNAME        = "lname";
    public static final String KEY_SALUT        = "salut";
    public static final String KEY_WORKEXP      = "workexp";
    public static final String KEY_EDUCATION    = "education";
    public static final String KEY_SKILLS       = "skills";

    // employer specific
    public static final String KEY_NAME         = "name";
    public static final String KEY_INFO         = "info";

    // listing specific
    public static final String KEY_DESC         = "jobdesc";
    public static final String KEY_SKILLSREQ    = "skillsReq";
    public static final String KEY_OWNER        = "owner";
    public static final String KEY_JOBTITLE     = "jobTitle";
    public static final String KEY_EXPREQ       = "expRequired";

    // like table specific
    public static final String KEY_LIKEE        = "likee";
    public static final String KEY_LIKER        = "liker";

    // matched table specific
    public static final String KEY_SEEKER       = "seeker";
    public static final String KEY_EMPLOYER     = "employer";


    public DbHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    // creating sql tables
    @Override
    public void onCreate(SQLiteDatabase db){

        Log.d(TAG, "onCreate: " + "Create TABLE " + TABLE_SEEKERS + ".");
        String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_SEEKERS + "(" +
                KEY_SALUT + " TEXT, " + KEY_FNAME + " TEXT, " +
                KEY_LNAME + " TEXT, " + KEY_EMAIL + " VARCHAR(320) PRIMARY KEY, " +
                KEY_EDUCATION + " TEXT, " + KEY_IMG + " TEXT, " +
                KEY_WORKEXP + " TEXT, " + KEY_MOBNUM + " TEXT, " +
                KEY_TAGS + " TEXT, " + KEY_LIKES + " TEXT, " +
                KEY_DISLIKES + " TEXT," + KEY_SKILLS + " TEXT)";

        db.execSQL(CREATE_TABLE);

        Log.d(TAG, "onCreate: " + "Create TABLE " + TABLE_EMPLOYERS + ".");
        CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_EMPLOYERS + "(" +
                KEY_NAME + " TEXT, " + KEY_EMAIL + " TEXT PRIMARY KEY, " +
                KEY_IMG + " TEXT, " + KEY_MOBNUM + " TEXT, " +
                KEY_INFO + " TEXT, " + KEY_LIKES + " TEXT, " +
                KEY_DISLIKES + " TEXT" +")";

        db.execSQL(CREATE_TABLE);

        Log.d(TAG, "onCreate: " + "Create TABLE " + TABLE_LISTINGS + ".");
        CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_LISTINGS + "(" +
                KEY_DESC + " TEXT, " + KEY_TAGS + " TEXT," +
                KEY_OWNER + " TEXT, " + KEY_SKILLSREQ +" TEXT, " +
                KEY_JOBTITLE + " TEXT, " + KEY_EXPREQ + " TEXT)";

        db.execSQL(CREATE_TABLE);

        Log.d(TAG, "onCreate: " + "Create TABLE " + TABLE_LIKES);
        CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_LIKES + "(" +
                "TYPE TEXT, LIKER TEXT, LIKEE TEXT" + ")";

        db.execSQL(CREATE_TABLE);

        Log.d(TAG, "onCreate: " + "Create TABLE" + TABLE_MATCHED);
        CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_MATCHED + "(" +
                "SEEKER TEXT, EMPLOYER TEXT);";

        db.execSQL(CREATE_TABLE);

        Log.d(TAG, "onCreate: " + "Create TABLE " + TABLE_LASTMODIFIED + ".");
        CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_LASTMODIFIED + "(" +
                "TABLE_NAME TEXT, LAST_MODIFIED TEXT)";

        db.execSQL(CREATE_TABLE);

        ArrayList<String> TableNames = new ArrayList<>(Arrays.asList(TABLE_SEEKERS,TABLE_EMPLOYERS,TABLE_LISTINGS,TABLE_LIKES,TABLE_MATCHED));

        for(String name:TableNames) {
            Log.d(TAG, "onCreate: Adding " + name + " init value to TABLE_LASTMODIFIED");
            String ADD_TABLENAMES = "INSERT INTO " + TABLE_LASTMODIFIED + " VALUES (\"" + name + "\", \"1\")";
            db.execSQL(ADD_TABLENAMES);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        //db = this.getWritableDatabase();

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LASTMODIFIED);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LIKES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LISTINGS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EMPLOYERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SEEKERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MATCHED);

        onCreate(db);
    }

    public Hashtable<String, String> getLastDownloaded(){
        Hashtable<String, String> res =  new Hashtable<>();

        String selectQuery  = "SELECT * FROM " + TABLE_LASTMODIFIED + ";";
        SQLiteDatabase db   = this.getReadableDatabase();

        try (Cursor cursor =  db.rawQuery(selectQuery, null)) {
            while (cursor.moveToNext()) {
                res.put(cursor.getString(0), cursor.getString(1));
            }
            cursor.close();
        }
        return res;
    }

    public static boolean doesDatabaseExist(Context context, String dbName) {
        File dbFile = context.getDatabasePath(dbName);
        return dbFile.exists();
    }

    public void storeData(JSONArray response, String TABLE_NAME){

        // define SQL queries here
        String INSERT_QUERY = "";
        SQLiteDatabase db = this.getWritableDatabase();

        try{
            for(int i=0; i<response.length(); i++){
                JSONObject jsonObject = response.getJSONObject(i);

                ContentValues values = new ContentValues();

                if(TABLE_NAME.equals(TABLE_SEEKERS)) {

                    ArrayList<String> ColumnNames = new ArrayList<>(
                            Arrays.asList(KEY_SALUT, KEY_FNAME, KEY_LNAME, KEY_EMAIL, KEY_EDUCATION ,KEY_IMG, KEY_WORKEXP,KEY_MOBNUM, KEY_TAGS, KEY_LIKES, KEY_DISLIKES, KEY_SKILLS));

                    for(String column:ColumnNames){
                        if(column.equals(KEY_LIKES) || column.equals(KEY_DISLIKES))
                            values.put(column, jsonObject.getJSONArray(column).toString());
                        else
                            values.put(column, jsonObject.getString(column));
                    }
                }

                else if(TABLE_NAME.equals(TABLE_EMPLOYERS)) {
                    ArrayList<String> ColumnNames = new ArrayList<>(
                            Arrays.asList(KEY_NAME, KEY_EMAIL, KEY_IMG, KEY_MOBNUM, KEY_INFO, KEY_LIKES, KEY_DISLIKES));

                    for(String column:ColumnNames){
                        if(column.equals(KEY_LIKES) || column.equals(KEY_DISLIKES))
                            values.put(column, jsonObject.getJSONArray(column).toString());
                        else
                            values.put(column, jsonObject.getString(column));
                    }
                }

                else if(TABLE_NAME.equals(TABLE_LIKES)) {
                    ArrayList<String> ColumnNames = new ArrayList<>(
                            Arrays.asList(KEY_TYPE, KEY_LIKER, KEY_LIKEE));

                    for(String column:ColumnNames){
                        if(column.equals(KEY_LIKES) || column.equals(KEY_DISLIKES))
                            values.put(column, jsonObject.getJSONArray(column).toString());
                        else
                            values.put(column, jsonObject.getString(column));
                    }
                }


                else if(TABLE_NAME.equals(TABLE_MATCHED)) {
                    ArrayList<String> ColumnNames = new ArrayList<>(
                            Arrays.asList(KEY_SEEKER, KEY_EMPLOYER));

                    for(String column:ColumnNames){
                        if(column.equals(KEY_LIKES) || column.equals(KEY_DISLIKES))
                            values.put(column, jsonObject.getJSONArray(column).toString());
                        else
                            values.put(column, jsonObject.getString(column));
                    }
                }

                else if(TABLE_NAME.equals(TABLE_LISTINGS)) {
                    ArrayList<String> ColumnNames = new ArrayList<>(
                            Arrays.asList(KEY_DESC, KEY_TAGS, KEY_OWNER, KEY_SKILLSREQ, KEY_JOBTITLE, KEY_EXPREQ));

                    for(String column:ColumnNames){
                        if(column.equals(KEY_LIKES) || column.equals(KEY_DISLIKES))
                            values.put(column, jsonObject.getJSONArray(column).toString());
                        else
                            values.put(column, jsonObject.getString(column));
                    }
                }

                db.insert(TABLE_NAME, null, values);
                updateLastModified(TABLE_NAME);
            }

        } catch(JSONException e){
            Log.e(TAG, "storeEmployers: ", e);
        }
    }

    public void updateLastModified(String TABLE_NAME){
        long now = System.currentTimeMillis();
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("LAST_MODIFIED", String.valueOf(now));
        String where = "TABLE_NAME = ?";
        String[] whereArgs = new String[] {TABLE_NAME};

        db.update(TABLE_LASTMODIFIED, values, where, whereArgs);
    }

    public ArrayList<CustomCard> getSeekers(){
        ArrayList<CustomCard> result = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        String SELECT_QUERY = "SELECT * FROM Seekers;";

        try (Cursor cursor = db.rawQuery(SELECT_QUERY, null)){
            while(cursor.moveToNext()){

                String salut        = cursor.getString(0);
                String fname        = cursor.getString(1);
                String lname        = cursor.getString(2);
                String email        = cursor.getString(3);
                String education    = cursor.getString(4);
                String img          = cursor.getString(5);
                String workexp      = cursor.getString(6);
                String mobnum       = cursor.getString(7);
                String tags         = cursor.getString(8);
                String likes        = cursor.getString(9);
                String dislikes     = cursor.getString(10);
                String skills       = cursor.getString(11);

                result.add(new SeekerClass(salut, fname, lname, img, education, workexp , skills, mobnum, email, tags, likes, dislikes));
            }
        };

        return result;
    }

    public ArrayList<CustomCard> getListings(){
        ArrayList<CustomCard> result = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        String SELECT_QUERY = "SELECT * FROM Listings;";

        try(Cursor cursor = db.rawQuery(SELECT_QUERY, null)){

            while(cursor.moveToNext()) {
                String desc = cursor.getString(0);
                String tags = cursor.getString(1);
                String owner = cursor.getString(2);
                String skillsreq = cursor.getString(3);
                String jobtitle = cursor.getString(4);
                String expreq = cursor.getString(5);

                result.add(new JobListingClass(owner, desc, skillsreq, tags, jobtitle, expreq));
            }
        }

        return result;
    }

    public ArrayList<EmployerClass> getEmployers(){
        ArrayList<EmployerClass> result = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        String SELECT_QUERY = "SELECT * FROM Employers";

        try(Cursor cursor = db.rawQuery(SELECT_QUERY, null)){

            while(cursor.moveToNext()) {
                String name = cursor.getString(0);
                String email = cursor.getString(1);
                String img = cursor.getString(2);
                String mobnum = cursor.getString(3);
                String info = cursor.getString(4);
                String likes = cursor.getString(5);
                String dislikes = cursor.getString(6);

                result.add(new EmployerClass(name, img, info, mobnum, email, likes, dislikes));
            }
        }

        return result;
    }

    public HashMap<String, String> getLiketable(){
        HashMap<String, String> result = new HashMap<>();

        SQLiteDatabase db = this.getReadableDatabase();
        String SELECT_QUERY = "SELECT * FROM LikeTable WHERE type=like";

        try (Cursor cursor = db.rawQuery(SELECT_QUERY, null)){

            while(cursor.moveToNext()) {
                result.put(cursor.getString(1), cursor.getString(2));
            }
        }

        return result;
    }

    public String getCompanyEmail(String compname){
        SQLiteDatabase db = this.getReadableDatabase();
        String SELECT_QUERY = "SELECT " + KEY_EMAIL + " FROM Employers WHERE " + KEY_NAME + " like \'" + compname + "\';";

        String result = "null";
        try(Cursor cursor = db.rawQuery(SELECT_QUERY, null)){
            while(cursor.moveToNext()) {
                result = cursor.getString(0);
            }
        }
        return result;
    }
}
