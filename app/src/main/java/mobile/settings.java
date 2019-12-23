package mobile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import mobile.apps.R;

import static android.provider.MediaStore.INTENT_ACTION_MEDIA_PLAY_FROM_SEARCH;

public class settings extends AppCompatActivity {

    Button go_back_btn;
    Button music_btn;
    Button settings_btn;

    RadioGroup distancebtn_group;
    RadioButton miles_btn;;
    RadioButton kilometer_btn;

    public static final String Shared_info = "info";
    public static String MILESBTN = "miles_btn";
    public static String KMBTN = "kilometer_btn";
    public static String DBG = "distancebtn_group";

    Context context;

    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);


        sharedPreferences = getSharedPreferences(Shared_info, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(Shared_info,  false);
        editor.apply();



        go_back_btn = (Button) findViewById(R.id.go_back_btn);
        music_btn = (Button) findViewById(R.id.music_btn);
        settings_btn = (Button) findViewById(R.id.settings_btn);
        distancebtn_group = findViewById(R.id.distance_btn_group);
        miles_btn = (RadioButton) findViewById(R.id.miles_btn);
        kilometer_btn = (RadioButton) findViewById(R.id.kilometer_btn);


        go_back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(settings.this, home.class);
                startActivity(intent);
            }
        });
        music_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(INTENT_ACTION_MEDIA_PLAY_FROM_SEARCH);
                startActivity(intent);

            }
        });
        kilometer_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                boolean kmChecked = kilometer_btn.isChecked();
                editor.putBoolean(KMBTN, kmChecked);
                editor.apply();


            }
        });
        miles_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                boolean milesChecked = miles_btn.isChecked();
                editor.putBoolean(MILESBTN, milesChecked);
                editor.apply();
            }
        });


        sharedPreferences = getSharedPreferences(Shared_info, MODE_PRIVATE);
        kilometer_btn.setChecked(sharedPreferences.getBoolean("kilometer_btn", true));
        miles_btn.setChecked(sharedPreferences.getBoolean("miles_btn", false));

    }
}

