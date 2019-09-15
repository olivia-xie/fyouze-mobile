package com.example.socialmediaconsolidator.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.socialmediaconsolidator.Model.Contact;

import java.util.ArrayList;

public class DatabaseHandler extends SQLiteOpenHelper {

    private final ArrayList<Contact> contactList = new ArrayList<>();
    private Context context;

    public DatabaseHandler(Context context) {
        super(context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + Constants.TABLE_NAME + "(" +
                Constants.KEY_ID + " INTEGER PRIMARY KEY, " + Constants.CONTACT_USERNAME +
                " TEXT, " + Constants.CONTACT_FB + " TEXT, " + Constants.CONTACT_TW +
                " TEXT, " + Constants.CONTACT_IG + " TEXT);";

        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // Drop existing table
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_NAME);

        // Create new table
        onCreate(db);
    }

    // Delete Entry
    public void deleteEntry(int id) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Constants.TABLE_NAME, Constants.KEY_ID + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
    }

    // Add an entry
    public void addContact(Contact contact) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Constants.CONTACT_USERNAME, contact.getUsername());
        values.put(Constants.CONTACT_FB, String.valueOf(contact.isFacebook()));
        values.put(Constants.CONTACT_TW, String.valueOf(contact.isTwitter()));
        values.put(Constants.CONTACT_IG, String.valueOf(contact.isInstagram()));

        db.insert(Constants.TABLE_NAME, null, values);

        db.close();
    }

    // Get all entries
    public ArrayList<Contact> getContacts() {

        contactList.clear();

        SQLiteDatabase dba = this.getReadableDatabase();

        Cursor cursor = dba.query(Constants.TABLE_NAME,
                new String[]{Constants.KEY_ID, Constants.CONTACT_USERNAME, Constants.CONTACT_FB,
                        Constants.CONTACT_TW, Constants.CONTACT_IG}, null, null, null, null, Constants.CONTACT_USERNAME + " DESC ");

        //loop through...
        if (cursor.moveToFirst()) {
            do {

                Contact contact = new Contact();
                contact.setUsername(cursor.getString(cursor.getColumnIndex(Constants.CONTACT_USERNAME)));
                contact.setFacebook(Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex(Constants.CONTACT_FB))));
                contact.setInstagram(Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex(Constants.CONTACT_IG))));
                contact.setTwitter(Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex(Constants.CONTACT_TW))));
                contact.setContactID(cursor.getInt(cursor.getColumnIndex(Constants.KEY_ID)));

                contactList.add(contact);

            } while (cursor.moveToNext());


        }

        cursor.close();
        dba.close();

        return contactList;

    }
}
