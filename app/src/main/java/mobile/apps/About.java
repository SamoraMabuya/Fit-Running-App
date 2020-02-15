package mobile.apps;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import java.io.File;

import mobile.home;
import mobile.settings;

public class About extends AppCompatActivity {

    Button return_home, settings_button, history_btn, music_btn;

    String CATEGORY_APP_MUSIC = "android.intent.action.MUSIC_PLAYER";

    ImageView runner_bc;

    String image_link;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);

        return_home = findViewById(R.id.return_home);
        settings_button = findViewById(R.id.settings_button);
        history_btn = findViewById(R.id.history_btn);
        music_btn = findViewById(R.id.music_btn);
        runner_bc = new ImageView(this);
        ImageView runnerBC = new ImageView(this);
        runnerBC.setImageDrawable(getResources().getDrawable(R.drawable.runner_image_intro_page_compressed));




        return_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReturnHomeRunnable returnHomeRunnable = new ReturnHomeRunnable();
                new Thread(returnHomeRunnable).start();
            }


            class ReturnHomeRunnable implements Runnable {
                @Override
                public void run() {
                    return_home.post(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(About.this, home.class);
                            startActivity(intent);
                        }
                    });
                }
            }
        });

        settings_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingsButtonRunnable settingsButtonRunnable = new SettingsButtonRunnable();
                new Thread(settingsButtonRunnable).start();

            }

            class SettingsButtonRunnable implements Runnable {
                @Override
                public void run() {
                    settings_button.post(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(About.this, settings.class);
                            startActivity(intent);
                        }
                    });
                }
            }
        });
        history_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HistoryBtnRunnable historyBtnRunnable = new HistoryBtnRunnable();
                new Thread(historyBtnRunnable).start();

            }

            class HistoryBtnRunnable implements Runnable {
                @Override
                public void run() {
                    history_btn.post(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(About.this, DisplayHistory.class);
                            startActivity(intent);
                        }
                    });
                }
            }
        });

        music_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
    }
}


