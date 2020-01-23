package mobile;

import android.content.Context;
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

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import mobile.apps.DisplayHistory;
import mobile.apps.R;

public class settings extends AppCompatActivity {

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

    public static String LIGHT = "Ligth1";
    public static String MARBLE_BLUE = "marble_blue";

    Spinner themeSpinner;


    SharedPreferences sharedPreferences;
    SharedPreferences LastSelectedItem;
    SharedPreferences.Editor editor;


    public static final String CATEGORY_APP_MUSIC = "android.intent.action.MUSIC_PLAYER";


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
        themeSpinner = (Spinner) findViewById(R.id.themeSpinner);

        LastSelectedItem = getSharedPreferences("PriorSelected", Context.MODE_PRIVATE);


        history_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences = getApplicationContext().getSharedPreferences(GetInfo, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(MILESBTN, miles_btn.isChecked());
                editor.putBoolean(KMBTN, kilometer_btn.isChecked());
                editor.putBoolean(ON, OnButton.isChecked());
                editor.putBoolean(OFF, OffButton.isChecked());
                editor.apply();
                Intent intent = new Intent(settings.this, DisplayHistory.class);
                startActivity(intent);
            }
        });

        go_back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int Myposition = themeSpinner.getSelectedItemPosition();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("LastSelection", Myposition);
                editor.apply();
                Intent intent = new Intent(getApplicationContext(), home.class);
                startActivity(intent);
            }
        });
        music_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedPreferences = getApplicationContext().getSharedPreferences(GetInfo, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(MILESBTN, miles_btn.isChecked());
                editor.putBoolean(KMBTN, kilometer_btn.isChecked());
                editor.putBoolean(ON, OnButton.isChecked());
                editor.putBoolean(OFF, OffButton.isChecked());
                editor.apply();
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



        int LastSelection = LastSelectedItem.getInt("LastSelection", 0);
        editor = LastSelectedItem.edit();


        ArrayAdapter<CharSequence> themeAdapter = ArrayAdapter.createFromResource(settings. this, R.array.theme_array, android.R.layout.simple_spinner_item);
        themeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);

        themeSpinner.setAdapter(themeAdapter);
        themeSpinner.setSelection(LastSelection);



        themeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                editor.putInt("LastSelection", position);
                editor.apply();



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
}


