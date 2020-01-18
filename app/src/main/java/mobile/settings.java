package mobile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import mobile.apps.DisplayHistory;
import mobile.apps.R;

import static android.provider.MediaStore.INTENT_ACTION_MEDIA_PLAY_FROM_SEARCH;

public class settings extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Button go_back_btn, music_btn, history_btn;

    RadioGroup distancebtn_group;
    RadioGroup CountDownBtnGroup;
    RadioButton miles_btn;
    RadioButton kilometer_btn;
    RadioButton OnButton;
    RadioButton OffButton;
    RadioGroup radioGroup;

    public static String GetInfo = "GetInfo";
    public static String MILESBTN = "miles_btn";
    public static String KMBTN = "kilometer_btn";
    public static String ON = "on";
    public static String OFF = "off";
    public static String DBG = "distancebtn_group";

    SharedPreferences sharedPreferences;
    public static final String CATEGORY_APP_MUSIC = "android.intent.action.MUSIC_PLAYER";

    Spinner themeSpinner;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);


        sharedPreferences = getApplicationContext().getSharedPreferences(GetInfo, MODE_PRIVATE);

        go_back_btn = (Button) findViewById(R.id.go_back_btn);
        music_btn = (Button) findViewById(R.id.music_btn);
        distancebtn_group = findViewById(R.id.distance_btn_group);
        miles_btn = (RadioButton) findViewById(R.id.miles_btn);
        kilometer_btn = (RadioButton) findViewById(R.id.kilometer_btn);
        OnButton = (RadioButton) findViewById(R.id.OnButton);
        OffButton = (RadioButton) findViewById(R.id.OffButton);
        CountDownBtnGroup = (RadioGroup) findViewById(R.id.CountDownBtnGroup);
        history_btn = (Button) findViewById(R.id.history_btn);
        themeSpinner = findViewById(R.id.themeSpinner);




        history_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(settings.this, DisplayHistory.class);
                startActivity(intent);
            }
        });


        go_back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedPreferences = getApplicationContext().getSharedPreferences(GetInfo, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(MILESBTN, miles_btn.isChecked());
                editor.putBoolean(KMBTN, kilometer_btn.isChecked());
                editor.putBoolean(ON, OnButton.isChecked());
                editor.putBoolean(OFF, OffButton.isChecked());
                editor.apply();
                Intent intent = new Intent(getApplicationContext(), home.class);
                startActivity(intent);
            }
        });
        music_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CATEGORY_APP_MUSIC);
                startActivity(intent);

            }
        });
        kilometer_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedPreferences = getApplicationContext().getSharedPreferences(GetInfo, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(KMBTN, kilometer_btn.isChecked());
                editor.apply();

            }
        });
        miles_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences = getApplicationContext().getSharedPreferences(GetInfo, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(MILESBTN, miles_btn.isChecked());
                editor.apply();
            }
        });

        sharedPreferences = getApplicationContext().getSharedPreferences(GetInfo, MODE_PRIVATE);
        kilometer_btn.setChecked(sharedPreferences.getBoolean("kilometer_btn", true));
        miles_btn.setChecked(sharedPreferences.getBoolean("miles_btn", false));


        OnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences = getApplicationContext().getSharedPreferences(GetInfo, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(ON, OnButton.isChecked());
                editor.apply();
            }
        });

        OffButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences = getApplicationContext().getSharedPreferences(GetInfo, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(OFF, OffButton.isChecked());
                editor.apply();
            }
        });

        sharedPreferences = getApplicationContext().getSharedPreferences(GetInfo, MODE_PRIVATE);
        OnButton.setChecked(sharedPreferences.getBoolean("on", false));
        OffButton.setChecked(sharedPreferences.getBoolean("off", true));



        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(settings. this, R.array.themeOptions, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        themeSpinner.setOnItemSelectedListener(this);


    }



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}