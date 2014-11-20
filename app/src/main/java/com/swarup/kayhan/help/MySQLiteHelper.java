package com.swarup.kayhan.help;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

/**
 * Created by Kayhan on 11/20/2014.
 */
public class MySQLiteHelper extends SQLiteOpenHelper {
    public final static String name = "HELP_DB";

    private static final int DATABASE_VERSION = 1;

    public final static String TABLE_NAME = "contacts";
    public final static String [] COLUMNS = {"id","phone_number"};

    public static String QUERY_CREATE_TABLE =
            "CREATE TABLE "+TABLE_NAME+" (ID INTEGER PRIMARY KEY, PHONE_NUMBER TEXT)";

    public static final ArrayList<String> list = new ArrayList<String>();
    public MySQLiteHelper(Context context) {
        super(context, name, null, DATABASE_VERSION);


    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(QUERY_CREATE_TABLE);
    }

    ContentValues content;
    public boolean isEmpty(){
        boolean empty = true;
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT COUNT("+COLUMNS[1]+") FROM "+TABLE_NAME,null);

        if(cursor!=null)
            cursor.moveToFirst();
        if(Integer.parseInt(cursor.getString(0))>0)
            empty = false;
        db.close();

        if(empty){
            db= getWritableDatabase();
            for(int i=0;i<3;i++){
                content = new ContentValues();
                content.put(COLUMNS[0],i);
                content.put(COLUMNS[1],"");
                db.insert(TABLE_NAME,null,content);

            }
            empty = true;
            db.close();

        }



        return empty;
    }

    public void updateContacts(List<String> list1){
        list.clear();
        SQLiteDatabase db  = getWritableDatabase();


        for(int i=0;!list1.isEmpty();i++){
            String number = list1.remove(0);
            ContentValues values = new ContentValues();
            values.put(COLUMNS[1],number);
            db.update(TABLE_NAME, values, COLUMNS[0] +" = ?",new String[]{String.valueOf(i)});
            list.add(number);
        }
        db.close();




    }


    public ArrayList<String> getAllContacts(){

        list.clear();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT "+COLUMNS[1]+" FROM "+TABLE_NAME,null);

        if(cursor.moveToFirst()){
            do{
                list.add(cursor.getString(0));
            }while (cursor.moveToNext());
        }

        return list;
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        this.onCreate(db);
    }



}
