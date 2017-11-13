package com.development.android.expensemanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.PorterDuff;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Created by ragult on 11/6/2017.
 */

public class StoreData { // extends SQLiteOpenHelper {

    /* private SQLiteDatabase myDB;
    private Cursor myCursor; */
    private String file = "edata1";
    private String[] history_data;

    /* public StoreData(Context context){
        // super(context, "expense.db", null ,1);
        super(context, Environment.getExternalStorageDirectory()
                + File.separator + "/expense_manager/db/"
                + File.separator
                + "expenses.db", null, 1);
        // this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("DB: ", "Create called" );
        // db.openOrCreateDatabase("expenses",null);
        db.execSQL("CREATE TABLE IF NOT EXISTS user_exp (amt REAL, category CHAR(14), description VARCHAR(200), exp_date TEXT)");
        Log.i("DB::", "Create closed" );
    } */

    public boolean insertExpData(float amt, String category, String desc, String date_exp, Context ctx){
        // Log.i("Entered: ", "YYYY");
        String amt_string;
        try {
            amt_string = String.valueOf(amt) + " ";
            category += " ";
            date_exp += " ";
            FileOutputStream fOut = ctx.openFileOutput(file, Context.MODE_PRIVATE);
            fOut.write(amt_string.getBytes());
            fOut.write(category.getBytes());
            fOut.write(date_exp.getBytes());
            fOut.close();
            // Log.i("Entered: ", "AAA");

            /* SQLiteDatabase db = this.getWritableDatabase();
            Log.i("Entered: ", "BBB");
            ContentValues row = new ContentValues();
            Log.i("Entered: ", "CCC");
            row.put("amt", amt);
            row.put("category", category);
            row.put("desc", desc);
            row.put("exp_date", date_exp);
            db.insert("user_exp", null, row);
            myCursor = db.rawQuery("select * from user_exp", null);
            String cate = myCursor.getString(1);
            Log.i("XXX:", cate);
            // closeDB();
            // db.close(); */
            return true;
        }
        catch( Exception ex ){
            // Log.i("Catch_Y: ", ex.getMessage());
            return false;
        }
    }

    public String[] retrieveData(Context ctx){
        // Log.i("Entered: ", "XXXX");
        try{
            FileInputStream fileInputStream =  ctx.openFileInput(file);
            int read = -1;
            String buffer = "";
            while((read = fileInputStream.read())!= -1){
                buffer += Character.toString((char)read);
            }
            history_data = buffer.split(" ");

            /* myCursor = myDB.rawQuery("select * from user_exp", null);
            String category = myCursor.getString(1);
            Log.i("XXX:", category);
            // closeDB();
            myCursor.close(); */
            fileInputStream.close();
            return history_data;
        }
        catch (Exception ex){
            // Log.i("Catch: ", ex.getMessage());
            return null;
        }
    }

    /* public void closeDB(){
        myCursor.close();
        myDB.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    } */
}
