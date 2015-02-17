package tech.jm.myfirstandroidapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.SQLException;
import java.util.HashMap;

/**
 * Created by jimmy on 1/18/2015.
 */
public class MySqliteDBTest extends SQLiteOpenHelper
{
    private static String DATABASE_NAME = "TestJimboDB.db";
    private static int DATABASE_VERSION = 4;
    private static String TABLE_NAME_1 = "Test_Table_1";
    private final String TAG = this.getClass().getSimpleName();

    public MySqliteDBTest(Context context, String name, SQLiteDatabase.CursorFactory factory)
    {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        createTable(sqLiteDatabase);
        Log.d(TAG,"JM called to create the DB now.");
    }

    private void createTable(SQLiteDatabase sqLiteDatabase)
    {
        String tt = "create table "+TABLE_NAME_1+
     " (_id integer primary key autoincrement, pid integer, fname text, lname text, age integer);";

        Log.d(TAG,tt);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_1);
        sqLiteDatabase.execSQL(tt);
        Log.d(TAG,"exe sql to create the table");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldv, int newv)
    {
        if(oldv < newv)
        {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_1 + ";");
            createTable(sqLiteDatabase);
            Log.d(TAG,"doing an upgrade to the DB.");
        }
        else
        {
            Log.d(TAG,"No chg to the current DB version..dont do anything");
        }
    }

    //CRUD operations for the UI with the input data
    //add a person with a value map and the name of the table.
    public void addPerson(int pid, String fname, String lname, int age)
    {
        ContentValues map = new ContentValues();
        map.put("pid", pid);
        map.put("fname", fname);
        map.put("lname", lname);
        map.put("age", age);

        long rows = getWritableDatabase().insert(TABLE_NAME_1, null, map);

        Log.d(TAG,"added person with content value map + val = "+rows);
    }

    //local fnc to do raw sql insert but as a prepared statement like in JDBC..
    //much safer. this is a sql bind approach.
    public void addPersonSql(int pid, String fname, String lname, int age)
    {
        String sql =
                " INSERT INTO " +TABLE_NAME_1+" "+
                " (_id, pid, fname, lname, age) " +
                " VALUES " +
                " (NULL,?, ?, ?, ?) ";

        Object[] bindArgs = new Object[]{pid, fname, lname,age};
        getWritableDatabase().execSQL(sql, bindArgs);

        Log.d(TAG,"added person with sql bind values..");
    }

    public void updatePerson(int pid, String fname, String lname, int age)
    {
        ContentValues map = new ContentValues();
        map.put("fname", fname);
        map.put("lname", lname);
        map.put("age", age);

        //add the where args for the update sql.
        String[] whereArgs = new String[]{Integer.toString(pid)};

        long rows = getWritableDatabase().update(TABLE_NAME_1, map, "pid=?", whereArgs);

        Log.d(TAG,"updated person with content value map with pid = "+pid+" status = "+rows);
    }

    public void deletePerson(int pid)
    {
        String[] whereArgs = new String[]{Integer.toString(pid)};
        //del the person at that id..
        long rows = getWritableDatabase().delete(TABLE_NAME_1, "pid=?", whereArgs);

        Log.d(TAG,"deleted person with pid = "+pid+" status = "+rows);
    }

    public HashMap<String,String> getPerson(int pid)
    {
        Cursor cursor = null;
        HashMap<String,String> rv = new HashMap<String,String>();

        try
        {
            String[] whereArgs = new String[]{Integer.toString(pid)};
            String query = "SELECT * FROM "+TABLE_NAME_1+" where pid = ?";
            //make a sql bind type of query..here for the get params.
            cursor = getReadableDatabase().rawQuery(query,whereArgs);

            if (cursor.moveToFirst())
            {
                do
                {
                    String fname = cursor.getString(cursor.getColumnIndex("fname"));
                    rv.put("fname",fname);

                    String lname = cursor.getString(cursor.getColumnIndex("lname"));
                    rv.put("lname",lname);

                    String age = cursor.getString(cursor.getColumnIndex("age"));
                    rv.put("age",age);

                    int my_age = Integer.parseInt(age);

                    Log.d(TAG,"fname = "+fname+" lname = "+lname+" age = "+age);
                }
                while (cursor.moveToNext());
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            //to avoid mem leak just like jdbc result set and other resource items.
            if (cursor != null)
            {
                try
                {
                    cursor.close();
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
        }

        return rv;
    }
}
