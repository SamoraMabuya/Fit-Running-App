package runora.inc;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import runora.RecycleAdapter;
import runora.RetrieveRunnerActivity;
import runora.RunoraDatabaseHelper;
import runora.home;
import runora.settings;

public class DisplayHistory extends AppCompatActivity {

    RunoraDatabaseHelper Runora_database;


    RecyclerView recyclerView;
    TextView EmptyActivity;
    RecycleAdapter adapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<RetrieveRunnerActivity> arrayList = new ArrayList<>();

    Button returnButton, music_btn, About_btn, settings_btn;
    ImageView deleteButton;

    String CATEGORY_APP_MUSIC = "android.intent.action.MUSIC_PLAYER";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_view_layout);

        recyclerView = findViewById(R.id.Recycle_layout);
        EmptyActivity = findViewById(R.id.EmptyActivity);
        Runora_database = new RunoraDatabaseHelper(this);


        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        final RunoraDatabaseHelper runoraDatabaseHelper = new RunoraDatabaseHelper(this);
        final SQLiteDatabase sqLiteDatabase = runoraDatabaseHelper.getWritableDatabase();
        final Cursor cursor = runoraDatabaseHelper.RetrieveDataFromDatabase(sqLiteDatabase);


        returnButton = findViewById(R.id.returnButton);
        settings_btn = findViewById(R.id.settings_btn);
        music_btn = findViewById(R.id.music_btn);
        About_btn = findViewById(R.id.About_btn);
        deleteButton = findViewById(R.id.deleteButton);


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
                ReturnButtonRunnable returnButtonRunnable = new ReturnButtonRunnable();
                new Thread(returnButtonRunnable).start();
            }

            class ReturnButtonRunnable implements Runnable {
                @Override
                public void run() {
                    returnButton.post(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(DisplayHistory.this, home.class);
                            startActivity(intent);
                        }
                    });
                }
            }
        });

        settings_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingsBtnRunnable settingsBtnRunnable = new SettingsBtnRunnable();
                new Thread(settingsBtnRunnable).start();

            }

            class SettingsBtnRunnable implements Runnable {
                @Override
                public void run() {
                    settings_btn.post(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(DisplayHistory.this, settings.class);
                            startActivity(intent);
                        }
                    });
                }
            }
        });


        music_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MusicBtnRunnable musicBtnRunnable = new MusicBtnRunnable();
                new Thread(musicBtnRunnable).start();

            }

            class MusicBtnRunnable implements Runnable {
                @Override
                public void run() {
                    music_btn.post(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(CATEGORY_APP_MUSIC);
                            startActivity(intent);
                        }
                    });
                }
            }
        });

        About_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AboutBtnRunnable aboutBtnRunnable = new AboutBtnRunnable();
                new Thread(aboutBtnRunnable).start();
            }

            class AboutBtnRunnable implements Runnable {
                @Override
                public void run() {
                    About_btn.post(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(DisplayHistory.this, About.class);
                            startActivity(intent);
                        }
                    });
                }
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

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), home.class);
        startActivity(intent);
        finish();
    }
}

