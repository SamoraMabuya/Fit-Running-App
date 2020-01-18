package mobile.apps;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import mobile.RecycleAdapter;
import mobile.RetrieveRunnerActivity;
import mobile.RunoraDatabaseHelper;
import mobile.home;
import mobile.settings;

public class DisplayHistory extends AppCompatActivity {

    RecyclerView recyclerView;
    TextView EmptyActivity;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<RetrieveRunnerActivity> arrayList = new ArrayList<>();

    Button returnButton;
    Button history_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_view_layout);
        recyclerView = (RecyclerView) findViewById(R.id.Recycle_layout);
        EmptyActivity = (TextView) findViewById(R.id.EmptyActivity);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        RunoraDatabaseHelper runoraDatabaseHelper = new RunoraDatabaseHelper(this);
        SQLiteDatabase sqLiteDatabase = runoraDatabaseHelper.getReadableDatabase();

        Cursor cursor = runoraDatabaseHelper.RetrieveDataFromDatabase(sqLiteDatabase);

        while (cursor.moveToFirst()) {
            RetrieveRunnerActivity retrieveRunnerActivity = new RetrieveRunnerActivity(cursor.getString(1), cursor.getString(2), (cursor.getString(3)));
            arrayList.add(retrieveRunnerActivity);
            adapter = new RecycleAdapter(arrayList);
            recyclerView.setAdapter(adapter);
            EmptyActivity.setVisibility(View.INVISIBLE);
            cursor.moveToNext();
        }
        if (cursor.getCount() <= 0) {
            EmptyActivity.setVisibility(View.VISIBLE);






            returnButton = (Button) findViewById(R.id.returnButton);
            history_btn = (Button) findViewById(R.id.history_btn);


            returnButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(DisplayHistory.this, home.class);
                    startActivity(intent);
                }
            });

            history_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(DisplayHistory.this, settings.class);
                    startActivity(intent);
                }
            });
        }
    }
}


