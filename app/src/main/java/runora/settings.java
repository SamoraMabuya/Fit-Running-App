package runora;

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

import runora.inc.About;
import runora.inc.DisplayHistory;
import runora.inc.R;

public class settings extends AppCompatActivity {

    public static final String CATEGORY_APP_MUSIC = "android.intent.action.MUSIC_PLAYER";
    public static String GetInfo = "GetInfo";
    public static String MILESBTN = "miles_btn";
    public static String KMBTN = "kilometer_btn";
    public static String ON = "on";
    public static String OFF = "off";
    public static String VoiceON = "voiceOn";
    public static String VoiceOff = "VoiceOff";
    private static final String StoreLang = "StoreLang";
    private static final String InsertLang = "InsertLang";
    Button go_back_btn, music_btn, history_btn, AboutButton;
    RadioGroup distancebtn_group;
    RadioGroup CountDownBtnGroup;
    RadioGroup StartAlarmButtons;
    RadioButton miles_btn;
    RadioButton kilometer_btn;
    RadioButton OnButton;
    RadioButton OffButton;
    RadioButton VoiceCountOn;
    RadioButton VoiceCountOff;
    Spinner themeSpinner, LanguageSpinner;
    SharedPreferences sharedPreferences;
    SharedPreferences LastSelectedItem;
    SharedPreferences LastChosen;
    SharedPreferences.Editor editor;
    boolean onetime_restart = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);


        sharedPreferences = getApplicationContext().getSharedPreferences(GetInfo, MODE_PRIVATE);
        LastSelectedItem = getSharedPreferences("PriorSelected", Context.MODE_PRIVATE);
        LastChosen = getSharedPreferences("PriorSelected", Context.MODE_PRIVATE);


        go_back_btn = findViewById(R.id.go_back_btn);
        music_btn = findViewById(R.id.music_btn);
        AboutButton = findViewById(R.id.AboutButton);
        distancebtn_group = findViewById(R.id.distance_btn_group);
        miles_btn = findViewById(R.id.miles_btn);
        kilometer_btn = findViewById(R.id.kilometer_btn);
        OnButton = findViewById(R.id.OnButton);
        OffButton = findViewById(R.id.OffButton);
        CountDownBtnGroup = findViewById(R.id.CountDownBtnGroup);
        history_btn = findViewById(R.id.history_btn);
        themeSpinner = findViewById(R.id.themeSpinner);
        LanguageSpinner = findViewById(R.id.LanguageSpinner);
        StartAlarmButtons = findViewById(R.id.StartAlarmButtons);
        VoiceCountOn = findViewById(R.id.VoiceCountOn);
        VoiceCountOff = findViewById(R.id.VoiceCountOff);



        final int Myposition = themeSpinner.getSelectedItemPosition();
        final int lingoPosition = LanguageSpinner.getSelectedItemPosition();

        final int LastSelection = LastSelectedItem.getInt("LastSelection", 0);
        editor = LastSelectedItem.edit();

        final int DefaultItem = LastChosen.getInt("LastChosen", 0);
        editor = LastChosen.edit();


        go_back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GoBackBtnRunnable goBackBtnRunnable = new GoBackBtnRunnable();
                new Thread(goBackBtnRunnable).start();
            }

            class GoBackBtnRunnable implements Runnable {
                @Override
                public void run() {
                    go_back_btn.post(new Runnable() {
                        @Override
                        public void run() {
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putBoolean(MILESBTN, miles_btn.isChecked());
                            editor.putBoolean(KMBTN, kilometer_btn.isChecked());
                            editor.putBoolean(ON, OnButton.isChecked());
                            editor.putBoolean(OFF, OffButton.isChecked());
                            editor.putBoolean(VoiceON, VoiceCountOn.isChecked());
                            editor.putBoolean(VoiceOff, VoiceCountOff.isChecked());
                            editor.putInt("LastSelection", Myposition);
                            editor.putInt("LastChosen", lingoPosition);
                            editor.apply();
                            Intent intent = new Intent(getApplicationContext(), home.class);
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
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putBoolean(MILESBTN, miles_btn.isChecked());
                            editor.putBoolean(KMBTN, kilometer_btn.isChecked());
                            editor.putBoolean(ON, OnButton.isChecked());
                            editor.putBoolean(OFF, OffButton.isChecked());
                            editor.putBoolean(VoiceON, VoiceCountOn.isChecked());
                            editor.putBoolean(VoiceOff, VoiceCountOff.isChecked());
                            editor.putInt("LastSelection", Myposition);
                            editor.putInt("LastChosen", lingoPosition);
                            editor.apply();
                            Intent intent = new Intent(getApplicationContext(), DisplayHistory.class);
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
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putBoolean(MILESBTN, miles_btn.isChecked());
                            editor.putBoolean(KMBTN, kilometer_btn.isChecked());
                            editor.putBoolean(ON, OnButton.isChecked());
                            editor.putBoolean(OFF, OffButton.isChecked());
                            editor.putBoolean(VoiceON, VoiceCountOn.isChecked());
                            editor.putBoolean(VoiceOff, VoiceCountOff.isChecked());
                            editor.putInt("LastSelection", Myposition);
                            editor.putInt("LastChosen", lingoPosition);
                            editor.apply();
                            Intent intent = new Intent(CATEGORY_APP_MUSIC);
                            startActivity(intent);
                        }
                    });

                }
            }
        });

        AboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AboutButtonRunnable aboutButtonRunnable = new AboutButtonRunnable();
                new Thread(aboutButtonRunnable).start();

            }


            class AboutButtonRunnable implements Runnable {
                @Override
                public void run() {
                    AboutButton.post(new Runnable() {
                        @Override
                        public void run() {
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putBoolean(MILESBTN, miles_btn.isChecked());
                            editor.putBoolean(KMBTN, kilometer_btn.isChecked());
                            editor.putBoolean(ON, OnButton.isChecked());
                            editor.putBoolean(OFF, OffButton.isChecked());
                            editor.putBoolean(VoiceON, VoiceCountOn.isChecked());
                            editor.putBoolean(VoiceOff, VoiceCountOff.isChecked());
                            editor.putInt("LastSelection", Myposition);
                            editor.putInt("LastChosen", lingoPosition);
                            editor.apply();
                            Intent intent = new Intent(settings.this, About.class);
                            startActivity(intent);
                        }
                    });
                }
            }
        });


        kilometer_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                KilometerBtnRunnable kilometerBtnRunnable = new KilometerBtnRunnable();
                new Thread(kilometerBtnRunnable).start();

            }

            class KilometerBtnRunnable implements Runnable {
                @Override
                public void run() {
                    kilometer_btn.post(new Runnable() {
                        @Override
                        public void run() {
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putBoolean(KMBTN, kilometer_btn.isChecked());
                            editor.apply();
                        }
                    });
                }
            }
        });
        miles_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MilesBtnRunnable milesBtnRunnable = new MilesBtnRunnable();
                new Thread(milesBtnRunnable).start();
            }

            class MilesBtnRunnable implements Runnable {
                @Override
                public void run() {
                    miles_btn.post(new Runnable() {
                        @Override
                        public void run() {
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putBoolean(MILESBTN, miles_btn.isChecked());
                            editor.apply();

                        }
                    });
                }
            }
        });

        sharedPreferences = getApplicationContext().getSharedPreferences(GetInfo, MODE_PRIVATE);
        kilometer_btn.setChecked(sharedPreferences.getBoolean(KMBTN, true));
        miles_btn.setChecked(sharedPreferences.getBoolean(MILESBTN, false));


        OnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnButtonRunnable onButtonRunnable = new OnButtonRunnable();
                new Thread(onButtonRunnable).start();
            }

            class OnButtonRunnable implements Runnable {
                @Override
                public void run() {
                    OnButton.post(new Runnable() {
                        @Override
                        public void run() {
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putBoolean(ON, OnButton.isChecked());
                            editor.apply();
                        }
                    });
                }
            }
        });

        OffButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OffButtonRunnable offButtonRunnable = new OffButtonRunnable();
                new Thread(offButtonRunnable).start();
            }

            class OffButtonRunnable implements Runnable {
                @Override
                public void run() {
                    OffButton.post(new Runnable() {
                        @Override
                        public void run() {
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putBoolean(OFF, OffButton.isChecked());
                            editor.apply();
                        }
                    });
                }
            }
        });


        sharedPreferences = getApplicationContext().getSharedPreferences(GetInfo, MODE_PRIVATE);
        OnButton.setChecked(sharedPreferences.getBoolean(ON, false));
        OffButton.setChecked(sharedPreferences.getBoolean(OFF, true));


        VoiceCountOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VoiceCountOnRunnable voiceCountOnRunnable = new VoiceCountOnRunnable();
                new Thread(voiceCountOnRunnable).start();
            }

            class VoiceCountOnRunnable implements Runnable {
                @Override
                public void run() {
                    VoiceCountOn.post(new Runnable() {
                        @Override
                        public void run() {
                            sharedPreferences = getApplicationContext().getSharedPreferences(GetInfo, MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putBoolean(VoiceON, VoiceCountOn.isChecked());
                            editor.apply();
                        }
                    });
                }
            }
        });


        VoiceCountOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VoiceCountOffRunnable voiceCountOffRunnable = new VoiceCountOffRunnable();
                new Thread(voiceCountOffRunnable).start();
            }

            class VoiceCountOffRunnable implements Runnable {
                @Override
                public void run() {
                    VoiceCountOff.post(new Runnable() {
                        @Override
                        public void run() {
                            sharedPreferences = getApplicationContext().getSharedPreferences(GetInfo, MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putBoolean(VoiceOff, VoiceCountOff.isChecked());
                            editor.apply();
                        }
                    });
                }
            }
        });

        sharedPreferences = getApplicationContext().getSharedPreferences(GetInfo, MODE_PRIVATE);
        VoiceCountOn.setChecked(sharedPreferences.getBoolean(VoiceON, true));
        VoiceCountOff.setChecked(sharedPreferences.getBoolean(VoiceOff, false));


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
        LanguageSpinner.setSelection(DefaultItem);

        LanguageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                editor.putInt("LastChosen", position);
                editor.apply();
                if (onetime_restart) {
                    onetime_restart = false;
                } else {
                    if (position == 0) {
                        setLang("en");
                        Intent intent = new Intent(getApplicationContext(), settings.class);
                        finish();
                        startActivity(intent);
                    } else if (position == 1) {
                        setLang("de");
                        Intent intent = new Intent(getApplicationContext(), settings.class);
                        finish();
                        startActivity(intent);

                    }


                }

            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }

        });
    }

    public void setLang(String language) {
        Locale locale = new Locale(language);
        Resources resources = getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        Configuration configuration = resources.getConfiguration();
        configuration.locale = locale;
        resources.updateConfiguration(configuration, displayMetrics);
    }
}
