package com.example.sql_r2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ContactDB {

    public static final String KEY_ROWID = "_id";
    public static final String KEY_ROWNAME = "person_name";
    public static final String KEY_ROWCELL = "_cell";

    private final String DATABASE_NAME = "ContactsDB";
    private final String DATABASE_TABLE = "ContactsTable";
    private final int DATABASE_VERSION = 1;


    public DBHelper ourHelper;
    public SQLiteDatabase ourDatabase;

    public Context ourContext;

    public ContactDB(Context context)
    {
        ourContext = context;
    }

    public ContactDB open() throws SQLException
    {
        ourHelper = new DBHelper(ourContext);
        ourDatabase = ourHelper.getWritableDatabase();
        return this;
    }

    public void close()
    {
        ourHelper.close();
    }

    public long insert(String name, String cell)
    {
        ContentValues cv = new ContentValues();
        cv.put(KEY_ROWNAME, name);
        cv.put(KEY_ROWCELL, cell);

       return ourDatabase.insert(DATABASE_TABLE, null, cv);
    }

    public String readAllContacts()
    {
        String result = "";

        String []columns = new String[]{KEY_ROWID, KEY_ROWNAME, KEY_ROWCELL};

        Cursor c = ourDatabase.query(DATABASE_TABLE, columns, null, null, null, null, null);

        int indexID = c.getColumnIndex(KEY_ROWID);
        int indexName = c.getColumnIndex(KEY_ROWNAME);
        int indexCell = c.getColumnIndex(KEY_ROWCELL);

        for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
        {
            result = result + c.getString(indexID)+" : "+c.getString(indexName)+" "
                    +c.getString(indexCell) + "\n";
        }

        c.close();

        return result;

    }

    public int deleteContact(String rowID)
    {
        return ourDatabase.delete(DATABASE_TABLE, KEY_ROWID+"=?", new String[]{rowID});
    }

    public int updateContact(String rowID, String newName, String newCell)
    {
        ContentValues cv = new ContentValues();
        cv.put(KEY_ROWNAME, newName);
        cv.put(KEY_ROWCELL, newCell);

        return ourDatabase.update(DATABASE_TABLE, cv, KEY_ROWID+"=?", new String[]{rowID});
    }



    private class DBHelper extends SQLiteOpenHelper
    {

        public DBHelper(Context context)
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION, null);
        }
        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            /*
            CREATE TABLE ContactsTable(_id INTEGER PRIMARY KEY AUTOINCREMENT,
            person_name TEXT NOT NULL,
            _cell TEXT NOT NULL);
             */

            String query = "CREATE TABLE "+DATABASE_TABLE+"("+KEY_ROWID+
                    " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    KEY_ROWNAME+" TEXT NOT NULL, " +
                    KEY_ROWCELL+" TEXT NOT NULL);";

            sqLiteDatabase.execSQL(query);

        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

            // backup of data should be here

            String query = "DROP TABLE IF EXISTS "+DATABASE_TABLE;
            sqLiteDatabase.execSQL(query);
            onCreate(sqLiteDatabase);

        }
    }


}
