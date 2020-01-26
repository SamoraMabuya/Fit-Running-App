package mobile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

import mobile.apps.DisplayHistory;
import mobile.apps.R;

public class settings extends AppCompatActivity {

    Button go_back_btn, music_btn, history_btn;

    RadioGroup distancebtn_group;
    RadioGroup CountDownBtnGroup;
    RadioGroup StartAlarmButtons;
    RadioButton miles_btn;
    RadioButton kilometer_btn;
    RadioButton OnButton;
    RadioButton OffButton;
    RadioButton StartAlarmOn;
    RadioButton StartAlarmOff;

    public static String GetInfo = "GetInfo";
    public static String MILESBTN = "miles_btn";
    public static String KMBTN = "kilometer_btn";
    public static String ON = "on";
    public static String OFF = "off";
    public static String START_ALARM_ON = "start_alarm_on";
    public static String START_ALARM_OFF = "start_alarm_off";

    Spinner themeSpinner, LanguageSpinner;

    SharedPreferences sharedPreferences;
    SharedPreferences LastSelectedItem;
    SharedPreferences.Editor editor;


    public static final String CATEGORY_APP_MUSIC = "android.intent.action.MUSIC_PLAYER";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        sharedPreferences = getApplicationContext().getSharedPreferences(GetInfo, MODE_PRIVATE);
        LastSelectedItem = getSharedPreferences("PriorSelected", Context.MODE_PRIVATE);


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
        LanguageSpinner = (Spinner) findViewById(R.id.LanguageSpinner);
        StartAlarmButtons = (RadioGroup) findViewById(R.id.StartAlarmButtons);
        StartAlarmOn = (RadioButton) findViewById(R.id.StartAlarmOn);
        StartAlarmOff = (RadioButton) findViewById(R.id.StartAlarmOff);


        final int Myposition = themeSpinner.getSelectedItemPosition();
        final int lingoPosition = LanguageSpinner.getSelectedItemPosition();

        final int LastSelection = LastSelectedItem.getInt("LastSelection", 0);
        editor = LastSelectedItem.edit();


        go_back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(MILESBTN, miles_btn.isChecked());
                editor.putBoolean(KMBTN, kilometer_btn.isChecked());
                editor.putBoolean(ON, OnButton.isChecked());
                editor.putBoolean(OFF, OffButton.isChecked());
                editor.putBoolean(START_ALARM_ON, StartAlarmOn.isChecked());
                editor.putBoolean(START_ALARM_OFF, StartAlarmOff.isChecked());
                editor.putInt("LastSelection", Myposition);
                editor.putInt("LastSelection", lingoPosition);
                editor.apply();
                Intent intent = new Intent(getApplicationContext(), home.class);
                startActivity(intent);

            }
        });
        history_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences = getApplicationContext().getSharedPreferences(GetInfo, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(MILESBTN, miles_btn.isChecked());
                editor.putBoolean(KMBTN, kilometer_btn.isChecked());
                editor.putBoolean(ON, OnButton.isChecked());
                editor.putBoolean(OFF, OffButton.isChecked());
                editor.putBoolean(START_ALARM_ON, StartAlarmOn.isChecked());
                editor.putBoolean(START_ALARM_OFF, StartAlarmOff.isChecked());
                editor.putInt("LastSelection", Myposition);
                editor.putInt("LastSelection", lingoPosition);
                editor.apply();
                Intent intent = new Intent(settings.this, DisplayHistory.class);
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
                editor.putBoolean(START_ALARM_ON, StartAlarmOn.isChecked());
                editor.putBoolean(START_ALARM_OFF, StartAlarmOff.isChecked());
                editor.putInt("LastSelection", Myposition);
                editor.putInt("LastSelection", lingoPosition);
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


        StartAlarmOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences = getApplicationContext().getSharedPreferences(GetInfo, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(START_ALARM_ON, StartAlarmOn.isChecked());
                editor.apply();
            }
        });

        StartAlarmOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences = getApplicationContext().getSharedPreferences(GetInfo, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(START_ALARM_OFF, StartAlarmOff.isChecked());
                editor.apply();
            }
        });

        sharedPreferences = getApplicationContext().getSharedPreferences(GetInfo, MODE_PRIVATE);
        StartAlarmOn.setChecked(sharedPreferences.getBoolean("start_alarm_on", true));
        StartAlarmOff.setChecked(sharedPreferences.getBoolean("start_alarm_off", false));


        ArrayAdapter<CharSequence> themeAdapter = ArrayAdapter.createFromResource
                (this, R.array.theme_array, R.layout.spinner_selected_text);
        themeAdapter.setDropDownViewResource(R.layout.spinner_drop_down);

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

        ArrayAdapter<CharSequence> lingoAdapter = ArrayAdapter.createFromResource
                (this, R.array.Language_Options, R.layout.spinner_selected_text);
        lingoAdapter.setDropDownViewResource(R.layout.spinner_drop_down);

        LanguageSpinner.setAdapter(lingoAdapter);
        LanguageSpinner.setSelection(LastSelection);

        LanguageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                editor.putInt("LastSelection", position);
                editor.apply();

                if (position == 0) {
                    setLang("en");
                } else if(position == 1) {
                    setLang("de");
                }

            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });
    }

    private void setLang(String lang) {
        Locale locale = new Locale(lang);
        Resources resources = getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        Configuration configuration = resources.getConfiguration();
        configuration.locale = locale;
        resources.updateConfiguration(configuration, displayMetrics);

    }
}
