package mobile.apps;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import mobile.home;
import mobile.settings;

public class About extends AppCompatActivity {

    Button return_home, settings_button, history_btn, music_btn;

    public static final String CATEGORY_APP_MUSIC = "android.intent.action.MUSIC_PLAYER";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);

        return_home = (Button) findViewById(R.id.return_home);
        settings_button = (Button) findViewById(R.id.settings_button);
        history_btn = (Button) findViewById(R.id.history_btn);
        music_btn = (Button) findViewById(R.id.music_btn);

        return_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(About.this, home.class);
                startActivity(intent);
            }
        });

        settings_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(About.this, settings.class);
                startActivity(intent);
            }
        });
        history_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(About.this, DisplayHistory.class);
                startActivity(intent);

            }
        });
        music_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CATEGORY_APP_MUSIC);
                startActivity(intent);
            }
        });
    }
}