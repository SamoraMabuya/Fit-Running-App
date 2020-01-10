package mobile;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class RunoraDatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "RUNORA.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "runora_user_table";
    public static final String COL_1 = "Id";
    public static final String COL_2 = "Elapsed_Time";
    public static final String COL_3 = "Total_Distance";


    public RunoraDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " (Id INTEGER PRIMARY KEY AUTOINCREMENT, Elapsed_Time INTEGER,  Total_Distance INTEGER )");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }

    public boolean insertData(String elapsed_time, String total_distance) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, elapsed_time);
        contentValues.put(COL_3, total_distance);
        long result = db.insert(TABLE_NAME, null, contentValues);
        if(result == -1)
            return  false;
        else
            return  true;
    }
}

