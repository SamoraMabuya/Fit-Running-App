package runora;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.provider.Settings;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Locale;

import runora.inc.R;

import static runora.settings.GetInfo;


public class run_interface extends AppCompatActivity implements LocationListener, android.location.LocationListener {


    RunoraDatabaseHelper Runora_database;
    TelephonyManager telephonyManager;


    FusedLocationProviderClient fusionprovider;
    LocationManager locationManager;
    LocationRequest locationRequest;
    Location start_location, end_location, curr_location;


    boolean active;
    long update;
    TextView distance_counter, SpdInmph, SpdInkmh, CountDownTimerView;
    Button play_button, pause_button, stop_btn, homebutton, musicButton;
    ImageView overlayscreen;

    Chronometer timer;
    CountDownTimer countDownTimer;
    private static final long StartTime = 11000;

    private long TimeLeft = StartTime;


    double distance = 0;
    double current_speed;

    RadioGroup distancebtn_group;
    RadioButton miles_btn;
    RadioButton kilometer_btn;


    private static final int AccessCode = 48;
    String CATEGORY_APP_MUSIC = "android.intent.action.MUSIC_PLAYER";


    String current_date;

    Spinner themeSpinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.run_interface);


        Runora_database = new RunoraDatabaseHelper(this);

        distance_counter = findViewById(R.id.distance_counter);
        play_button = findViewById(R.id.play_button);
        pause_button = findViewById(R.id.pause_button);
        stop_btn = findViewById(R.id.stop_btn);
        SpdInmph = findViewById(R.id.spdInmph);
        SpdInkmh = findViewById(R.id.speedInkmh);
        CountDownTimerView = findViewById(R.id.CountDownTimerView);
        timer = findViewById(R.id.timer);
        distancebtn_group = findViewById(R.id.distance_btn_group);
        miles_btn = findViewById(R.id.miles_btn);
        kilometer_btn = findViewById(R.id.kilometer_btn);
        distancebtn_group = findViewById(R.id.distance_btn_group);
        homebutton = findViewById(R.id.homebutton);
        musicButton = findViewById(R.id.musicButton);
        themeSpinner = findViewById(R.id.themeSpinner);
        overlayscreen = findViewById(R.id.overlayScreen);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        fusionprovider = LocationServices.getFusedLocationProviderClient(run_interface.this);
        telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        telephonyManager.listen(phoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);


        CountDownSwitch();
        ToggleTheme();

//SystemClock.elapsedRealtime returns elapsed count from 0,
// By adding getBase, the timer returns a start time from the current pause/stop point
// To ensure this timer doesn't exceed 24hrs, I've added 86400000 which is 24hrs in milliseconds
        timer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                if ((SystemClock.elapsedRealtime() - timer.getBase()) >= 86400000) {
                    chronometer.setBase(SystemClock.elapsedRealtime());
                }
            }
        });


        musicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MusicRunnable musicRunnable = new MusicRunnable();
                new Thread(musicRunnable).start();

            }

            class MusicRunnable implements Runnable {
                @Override
                public void run() {
                    musicButton.post(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(CATEGORY_APP_MUSIC);
                            startActivity(intent);
                        }

                    });
                }
            }
        });

        homebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeRunnnable homeRunnnable = new HomeRunnnable();
                new Thread(homeRunnnable).start();

            }

            class HomeRunnnable implements Runnable {
                @Override
                public void run() {
                    homebutton.post(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(run_interface.this, home.class);
                            startActivity(intent);
                        }
                    });
                }
            }
        });

//At first I used GPS and Network provide together. Seeing the huge inaccuracy of using both, I
//opted to just GPS provider for more accurate readings.

//Make sure to add permissions in manifest folder to use the following services.
//Read phone state read the devices current state(incoming call, active call, no call) This helps
//to specify actions when the phone rings during running activity.
        play_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ResumeRunnable resumeRunnable = new ResumeRunnable();
                new Thread(resumeRunnable).start();
            }


            class ResumeRunnable implements Runnable {
                @Override
                public void run() {
                    play_button.post(new Runnable() {
                        @Override
                        public void run() {
                            if (!active) {
                                if (ContextCompat.checkSelfPermission(run_interface.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                                        && ContextCompat.checkSelfPermission(run_interface.this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                                        && locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                                        && ContextCompat.checkSelfPermission(run_interface.this,
                                        Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
                                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0, run_interface.this);
                                    createLocationRequest();
                                    timer.setBase(SystemClock.elapsedRealtime() - update);
                                    timer.start();
                                    play_button.setVisibility(View.GONE);
                                    pause_button.setVisibility(View.VISIBLE);
                                    active = true;
                                } else {
                                    RequestPermissions();
                                    {
                                    }
                                    play_button.setVisibility(View.VISIBLE);
                                    active = false;
                                }
                            }
                        }
                    });
                    pause_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            PauseRunnable pauseRunnable = new PauseRunnable();
                            new Thread(pauseRunnable).start();
                        }

                        class PauseRunnable implements Runnable {
                            @Override
                            public void run() {
                                pause_button.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (active) {
                                            timer.stop();
                                            active = false;
                                            play_button.setVisibility(View.VISIBLE);
                                            update = SystemClock.elapsedRealtime() - timer.getBase();
                                            locationManager.removeUpdates(run_interface.this);

                                        }
                                    }
                                });
                            }
                        }
                    });


                    stop_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            StopRunnable stopRunnable = new StopRunnable();
                            new Thread(stopRunnable).start();


                        }

                        class StopRunnable implements Runnable {
                            @Override
                            public void run() {
                                stop_btn.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        SaveData();
                                    }
                                });
                            }
                        }
                    });
                }
            }
        });
    }

    private void RequestPermissions() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(run_interface.this, Manifest.permission.ACCESS_FINE_LOCATION)) {

            new AlertDialog.Builder(this)
                    .setTitle("Location Permission Required")
                    .setMessage("Please allow permission access to proceed.")
                    .setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                                ActivityCompat.requestPermissions(run_interface.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                                                Manifest.permission.ACCESS_COARSE_LOCATION,
                                                Manifest.permission.ACCESS_BACKGROUND_LOCATION,
                                                Manifest.permission.READ_PHONE_STATE}, AccessCode);
                            }
                            ActivityCompat.requestPermissions(run_interface.this,
                                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                                            Manifest.permission.ACCESS_COARSE_LOCATION,
                                            Manifest.permission.READ_PHONE_STATE}, AccessCode);
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();

        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.ACCESS_BACKGROUND_LOCATION,
                                Manifest.permission.READ_PHONE_STATE}, AccessCode);
            }
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.READ_PHONE_STATE}, AccessCode);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == AccessCode) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                LocationCall();

            } else {
                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();

            }
        }
    }

// to call this method, fuisonLocationprovider must be declared and assigned
// This methods enhances gps accuracy
    private void createLocationRequest() {
        locationRequest = LocationRequest.create();
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

//Specify Settings.Action_Location_Source_Settings allows intent to take us to location source
//settings
    public void LocationCall() {
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            AlertDialog.Builder dialogbuilder = new AlertDialog.Builder(this);
            dialogbuilder.setMessage(" Enable GPS To Continue")
                    .setPositiveButton("Turn location on", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent call_gps_settings = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivity(call_gps_settings);


                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
            AlertDialog alertDialog = dialogbuilder.create();
            alertDialog.show();
        }
    }


    @Override
    public void onLocationChanged(Location location) {
        curr_location = location;
        if (start_location == null) {
            start_location = curr_location;
            end_location = curr_location;
        } else
            end_location = curr_location;

        ChooseMetricUnits();

        current_speed = location.getSpeed() * 2.236936;
        SpdInmph.setText(new DecimalFormat("0.00").format(current_speed));

        current_speed = location.getSpeed() * 3.6;
        SpdInkmh.setText(new DecimalFormat("0.00").format(current_speed));

    }


    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }


    @Override
    public void onProviderDisabled(String provider) {

    }


    private void distanceInMiles() {
        String miles = "mi";
        SpannableStringBuilder builder = new SpannableStringBuilder();
        SpannableString spannableString = new SpannableString(miles);
        spannableString.setSpan(new RelativeSizeSpan(0.50f), 0, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        distance = distance + (start_location.distanceTo(end_location) * 0.00062137);
        start_location = end_location;
        builder.append(new DecimalFormat("0.00 ").format(distance));
        builder.append(spannableString);
        distance_counter.setText(builder);


    }

    private void distanceInkilometers() {
        String kilometers = "km";
        SpannableStringBuilder builder = new SpannableStringBuilder();
        SpannableString spannableString = new SpannableString(kilometers);
        spannableString.setSpan(new RelativeSizeSpan(0.50f), 0, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        distance = distance + (start_location.distanceTo(end_location) / 1000);
        start_location = end_location;
        builder.append(new DecimalFormat("0.00 ").format(distance));
        builder.append(spannableString);
        distance_counter.setText(builder);

    }


    private void ChooseMetricUnits() {
        final SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(GetInfo, MODE_PRIVATE);
        final boolean MetricUnit = sharedPreferences.getBoolean(settings.KMBTN, true);
        if (MetricUnit) {
            distanceInkilometers();
        } else
            distanceInMiles();
    }

    private void ToggleTheme() {
        final SharedPreferences LastSelectedItem = getApplicationContext().getSharedPreferences("PriorSelected", Context.MODE_PRIVATE);
        int LastSelection = LastSelectedItem.getInt("LastSelection", 0);
        if (LastSelection == 1) {
            LightTheme();

        }
    }

    private void LightTheme() {
        overlayscreen.setBackgroundResource(R.drawable.light_theme);
        homebutton.setBackgroundResource(R.drawable.light_theme_buttons);
        play_button.setBackgroundResource(R.drawable.light_theme_buttons);
        pause_button.setBackgroundResource(R.drawable.light_theme_buttons);
        stop_btn.setBackgroundResource(R.drawable.light_theme_buttons);
        musicButton.setBackgroundResource(R.drawable.light_theme_buttons);
    }

    public void VoiceCountdown() {
        final SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(GetInfo, MODE_PRIVATE);
        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.count_down_voice);
        boolean VoiceSwitch = sharedPreferences.getBoolean(settings.VoiceON, true);
        if (VoiceSwitch) {
            mediaPlayer.start();
        }
    }


    private void StartCountDown() {
        countDownTimer = new CountDownTimer(TimeLeft, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                homebutton.setEnabled(false);
                homebutton.setBackgroundResource(R.drawable.disabled_button_resource);

                play_button.setEnabled(false);
                play_button.setBackgroundResource(R.drawable.disabled_button_resource);

                pause_button.setEnabled(false);
                pause_button.setBackgroundResource(R.drawable.disabled_button_resource);

                stop_btn.setEnabled(false);
                stop_btn.setBackgroundResource(R.drawable.disabled_button_resource);

                musicButton.setEnabled(false);
                musicButton.setBackgroundResource(R.drawable.disabled_button_resource);

                TimeLeft = millisUntilFinished;
                CountDownViewUpdate();

            }


            @Override
            public void onFinish() {
                active = false;
                AutoStartActivity();
                CountDownTimerView.setVisibility(View.GONE);
                ToggleTheme();

                homebutton.setEnabled(true);
                homebutton.setBackgroundResource(R.drawable.resume_button);

                play_button.setEnabled(true);
                play_button.setBackgroundResource(R.drawable.resume_button);

                pause_button.setEnabled(true);
                pause_button.setBackgroundResource(R.drawable.resume_button);

                stop_btn.setEnabled(true);
                stop_btn.setBackgroundResource(R.drawable.resume_button);

                musicButton.setEnabled(true);
                musicButton.setBackgroundResource(R.drawable.resume_button);


            }
        }.start();
    }


    private void CountDownViewUpdate() {
        int min = (int) (TimeLeft / 1000) / 60;
        int sec = (int) (TimeLeft / 1000) % 60;

        String TimeLeftFormat = String.format(Locale.getDefault(), "%02d:%02d", min, sec);

        CountDownTimerView.setText(TimeLeftFormat);

    }

    private void CountDownSwitch() {
        final SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(GetInfo, MODE_PRIVATE);
        final boolean TimerSwitch = sharedPreferences.getBoolean(settings.ON, false);
        if (TimerSwitch) {
            StartCountDown();
            VoiceCountdown();
        } else
            CountDownTimerView.setVisibility(View.GONE);

    }


    private void AutoStartActivity() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            play_button.performContextClick();
        }
        View playButton = findViewById(R.id.play_button);
        playButton.performClick();

    }


    private void SaveData() {
        new AlertDialog.Builder(run_interface.this)
                .setTitle("Save Activity To History")
                .setMessage("Would you like to save your activity?.")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        CalenderDate();
                        boolean dataSaved = Runora_database.insertData(timer.getText().toString(), distance_counter.getText().toString(), current_date);
                        boolean dataUpdate = Runora_database.updateData(timer.getText().toString(), distance_counter.getText().toString(), current_date);
                        if (dataSaved && dataUpdate)
                            Toast.makeText(run_interface.this, "Activity Saved To History", Toast.LENGTH_LONG).show();
                        StopActivity();


                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        StopActivity();

                    }
                })
                .create().show();

    }


    public void CalenderDate() {
        Calendar calendar = Calendar.getInstance();
        current_date = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());

    }

    public void StopActivity() {
        start_location = null;
        end_location = null;
        distance = 0;
        current_speed = 0;
        distance_counter.setText(new DecimalFormat("0.00").format(distance));
        timer.setBase(SystemClock.elapsedRealtime());
        locationManager.removeUpdates(run_interface.this);
        timer.stop();
        active = false;
        update = 0;
        play_button.setVisibility(View.VISIBLE);
        SpdInmph.setText(new DecimalFormat("0.00").format(current_speed));
        SpdInkmh.setText(new DecimalFormat("0.00").format(current_speed));


    }

    PhoneStateListener phoneStateListener = new PhoneStateListener() {
        @Override
        public void onCallStateChanged(int state, String phoneNumber) {

            if (state == TelephonyManager.CALL_STATE_RINGING || state == TelephonyManager.CALL_STATE_OFFHOOK) {
                timer.stop();
                active = false;
                play_button.setVisibility(View.VISIBLE);
                update = SystemClock.elapsedRealtime() - timer.getBase();
                locationManager.removeUpdates(run_interface.this);
            } else if (state == TelephonyManager.CALL_STATE_IDLE) {
                timer.stop();
                active = false;
                play_button.setVisibility(View.VISIBLE);
                update = SystemClock.elapsedRealtime() - timer.getBase();
                locationManager.removeUpdates(run_interface.this);

            }
        }
    };


    public void ActiveApp() {
        ComponentName componentName = new ComponentName(this, IncomingCall.class);
        getPackageManager().setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);

    }

    public void ClosedApp() {
        ComponentName componentName = new ComponentName(this, IncomingCall.class);
        getPackageManager().setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);


    }

    @Override
    protected void onPause() {
        super.onPause();
        telephonyManager.listen(phoneStateListener, PhoneStateListener.LISTEN_NONE);
        ClosedApp();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        ActiveApp();
    }

    @Override
    protected void onStop() {
        super.onStop();
        telephonyManager.listen(phoneStateListener, PhoneStateListener.LISTEN_NONE);
        ClosedApp();
    }
}































