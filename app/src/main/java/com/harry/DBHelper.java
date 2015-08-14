package com.harry;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Harry on 7/31/15.
 */
public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "test.db";
    private static final int VERSION = 1;

    private static final String TABLE_NAME = "students";
    private static final String ID = "_id";
    private static final String FIRST_NAME = "firstName";
    private static final String LAST_NAME = "lastName";
    private static final String BRANCH = "branch";

    //    private static final String CREATE_STATEMENT = "CREATE TABLE "+ TABLE_NAME+" ( "+ID+" INTEGER PRIMARY KEY AUTOINCREMENT "+NAME+" TEXT "
//            +AGE+" INTEGER);";
    private static final String CREATE_TABLE = "create table " + TABLE_NAME + "(" + ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + FIRST_NAME + " TEXT , " + LAST_NAME + " TEXT, "+ BRANCH + " TEXT);";

    Context context;
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addContact(Student student) {
        SQLiteDatabase sq = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(FIRST_NAME, student.getFirstName());
        contentValues.put(LAST_NAME, student.getLastName());
        contentValues.put(BRANCH, student.getBranch());
        sq.insert(TABLE_NAME, null, contentValues);
        sq.close();
    }
    public void deleteContact(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, "_id=?", new String[]{String.valueOf(id)});
        db.close();
    }

    public void updateContact(Student student) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(FIRST_NAME, student.getFirstName());
        contentValues.put(LAST_NAME, student.getLastName());
        contentValues.put(BRANCH, "computer");
        db.update(TABLE_NAME, contentValues, "_id=?", new String[]{String.valueOf(student.getId())});
        db.close();
    }
    public ArrayList<Student> readParticularContact(String s) {
        String[] arr = {ID,FIRST_NAME, LAST_NAME, BRANCH};
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Student> list = new ArrayList<Student>();
        Cursor cursor;
        if (s.length() > 0) {
//             cursor = db.query(TABLE_NAME, // a. table
//                    arr, "branch = ?", new String[]{s}, null, null, null, null);
            //raw query
            cursor = db.rawQuery("select * from "+TABLE_NAME+" where branch = ?", new String[]{s});
        } else {
            cursor = db.query(TABLE_NAME, arr, null, null, null, null, null);
        }


        if (cursor != null)
            cursor.moveToFirst();
        do {
            try {
                int id = cursor.getInt(cursor.getColumnIndex(ID));
                String firstName = cursor.getString(cursor.getColumnIndex(FIRST_NAME));
                String lastName = cursor.getString(cursor.getColumnIndex(LAST_NAME));
                String branch = cursor.getString(cursor.getColumnIndex(BRANCH));
                Log.d("training", id + " " + firstName + " " + lastName + " " + branch);
                Student student = new Student(firstName, lastName, branch);
                student.setId(id);
                list.add(student);
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(context, "not valid", Toast.LENGTH_SHORT).show();
            }


        }while(cursor.moveToNext());

        return list;

    }

}
