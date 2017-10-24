package lab1.com.sqli3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eokinyi on 10/25/2017.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    //First name
    private static final String DATABASE_NAME = "contactsManager";

    private static final String TABLE_CONTACTS = "contacts";

    private static final String TABLE_COLOR = "color";

    //first table
    private static final String KEY_ID = "id";
    private static final String KEY_FNAME = "fname";
    private static final String KEY_LNAME = "lname";

    //Second Table

    private static final String KEY_CID = "cid";
    private static final String KEY_CNAME = "cname";

    public DatabaseHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Creating Tables
    @Override
    //First table
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = " CREATE TABLE " + TABLE_CONTACTS +
                "(" + KEY_ID + " INTEGER PRIMARY KEY, " + KEY_FNAME + " TEXT, " + KEY_LNAME + " TEXT" + ") ";
        String CREATE_COLOR_TABLE = " CREATE TABLE " + TABLE_COLOR +
                "("+ KEY_CID + " INTEGER PRIMARY KEY, " + KEY_CNAME + " TEXT" + ") ";
        db.execSQL(CREATE_COLOR_TABLE);
        db.execSQL(CREATE_CONTACTS_TABLE);
    }
    //UPDATING DATABASE
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_CONTACTS);
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_COLOR);
        //CREATE TABLE AGAIN
        onCreate(db);
    }
    //Adding new contact
    public void addContact(Contacts contacts){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_FNAME, contacts.getFname());
        values.put(KEY_LNAME, contacts.getLname());

        //Inserting rows
        db.insert(TABLE_CONTACTS, null, values);
        db.close();
    }

    //Adding new courses
    public void addColor(Color color){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_CNAME, color.getCName());

        //Inserting rows
        db.insert(TABLE_COLOR, null, values);
        db.close();
    }

    //Getting single contact
    public Contacts getContact(int id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CONTACTS, new String[]{KEY_ID,
                        KEY_FNAME, KEY_LNAME }, KEY_ID + "=?",
                new String[] { String.valueOf(id) },null, null, null,null );
        if (cursor != null)
            cursor.moveToFirst();

        Contacts contacts = new Contacts(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2));
        //return contact
        return contacts;
    }

    //Getting single course
    public Color getColor(int cid){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_COLOR, new String[]{KEY_CID,
                        KEY_CNAME }, KEY_CID + "=?",
                new String[] { String.valueOf(cid) },null, null, null,null );
        if (cursor != null)
            cursor.moveToFirst();

        Color color = new Color(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1));
        //return contact
        return color;
    }

    //Getting all contacts
    public List<Contacts> getAllContacts(){
        List<Contacts> contactList = new ArrayList<Contacts>();
        //Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_CONTACTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()){
            do {
                Contacts contact = new Contacts();
                contact.setID(Integer.parseInt(cursor.getString(0)));
                contact.setFname(cursor.getString(1));
                contact.setLname(cursor.getString(2));
                //Adding contact to list
                contactList.add(contact);
            }while (cursor.moveToNext());
        }
        //return contact list
        return contactList;
    }

    //Getting all courses
    public List<Color> getAllColor(){
        List<Color> courseList = new ArrayList<Color>();
        //Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_COLOR;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()){
            do {
                Color color = new Color();
                color.setCID(Integer.parseInt(cursor.getString(0)));
                color.setCName(cursor.getString(1));
                //Adding contact to list
                courseList.add(color);
            }while (cursor.moveToNext());
        }
        //return contact list
        return courseList;
    }

    //Getting contacts count
    public int getContactsCount(){
        String countQuery = "SELECT * FROM " + TABLE_CONTACTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        //return count
        return cursor.getCount();
    }

    //Getting course count
    public int getCoursesCount(){
        String countQuery = "SELECT * FROM " + TABLE_COLOR;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        //return count
        return cursor.getCount();
    }

    //Updating single contact
    public  int updateContact(Contacts contact){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_FNAME, contact.getFname());
        values.put(KEY_LNAME, contact.getLname());

        //updating row
        return db.update(TABLE_CONTACTS, values, KEY_ID + "= ?",
                new String[] { String.valueOf(contact.getID())});
    }

    //Updating single course
    public  int updateCourse(Color color){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_CNAME, color.getCName());

        //updating row
        return db.update(TABLE_COLOR, values, KEY_CID + "= ?",
                new String[] { String.valueOf(color.getCID())});
    }

    //Deleting single contact
    public void deleteContact(Contacts contact){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, KEY_ID + "= ?",
                new String[] { String.valueOf(contact.getID())});
        db.close();
    }

    //Deleting single course
    public void deleteColor(Color color){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_COLOR, KEY_CID + "= ?",
                new String[] { String.valueOf(color.getCID())});
        db.close();
    }
}

