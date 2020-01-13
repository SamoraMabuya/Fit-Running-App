package mobile.apps;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import mobile.RunoraDatabaseHelper;

import static mobile.RunoraDatabaseHelper.TABLE_NAME;

public class history extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history);


    }
}
