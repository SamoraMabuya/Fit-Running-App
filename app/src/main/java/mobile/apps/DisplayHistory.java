package mobile.apps;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import mobile.RecycleAdapter;
import mobile.RetrieveRunnerActivity;
import mobile.RunoraDatabaseHelper;
import mobile.home;
import mobile.settings;

import static mobile.RunoraDatabaseHelper.COL_1;
import static mobile.RunoraDatabaseHelper.COL_2;
import static mobile.RunoraDatabaseHelper.TABLE_NAME;

public class DisplayHistory extends AppCompatActivity {

    RunoraDatabaseHelper Runora_database;


    RecyclerView recyclerView;
    TextView EmptyActivity;
    RecycleAdapter adapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<RetrieveRunnerActivity> arrayList = new ArrayList<>();

    Button returnButton, history_btn;
    ImageView deleteButton;

    RetrieveRunnerActivity retrieveRunnerActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_view_layout);

        recyclerView = (RecyclerView) findViewById(R.id.Recycle_layout);
        EmptyActivity = (TextView) findViewById(R.id.EmptyActivity);
        Runora_database = new RunoraDatabaseHelper(this);


        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        final RunoraDatabaseHelper runoraDatabaseHelper = new RunoraDatabaseHelper(this);
        final SQLiteDatabase sqLiteDatabase = runoraDatabaseHelper.getWritableDatabase();
        final Cursor cursor = runoraDatabaseHelper.RetrieveDataFromDatabase(sqLiteDatabase);


        returnButton = (Button) findViewById(R.id.returnButton);
        history_btn = (Button) findViewById(R.id.history_btn);
        deleteButton = (ImageView) findViewById(R.id.deleteButton);


        if (cursor.moveToFirst()) {
            do {
                RetrieveRunnerActivity retrieveRunnerActivity = new RetrieveRunnerActivity
                        (cursor.getString(1), cursor.getString(2),
                                (cursor.getString(3)), (cursor.getString(0)));
                arrayList.add(retrieveRunnerActivity);

                EmptyActivity.setVisibility(View.INVISIBLE);
            }
            while
            (cursor.moveToNext());

        } else
            EmptyActivity.setVisibility(View.VISIBLE);

        runoraDatabaseHelper.close();

        adapter = new RecycleAdapter(arrayList);
        recyclerView.setAdapter(adapter);


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

        adapter.setItemListener(new RecycleAdapter.OnItemClickListener() {
            @Override
            public void onDeleteClick(int position) {
                deleteItems(position);


            }

            private void deleteItems(int position) {
                arrayList.remove(position);
                adapter.notifyItemRemoved(position);

            }
        });
    }
}