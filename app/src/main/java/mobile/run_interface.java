package mobile;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

import java.text.DecimalFormat;
import java.util.Locale;

import mobile.apps.R;

import static mobile.settings.GetInfo;


public class run_interface extends AppCompatActivity implements LocationListener, android.location.LocationListener, RadioGroup.OnCheckedChangeListener {

    FusedLocationProviderClient fusionprovider;
    LocationManager locationManager;
    LocationRequest locationRequest;
    Location start_location, end_location, curr_location;
    private static final int Permission_Request_Code = 100;
    boolean NetworkEnabled = false;
    boolean GPSEnabled = false;


    boolean active;
    long update;
    TextView distance_counter, SpdInmph, SpdInkmh, CountDownTimerView;
    Button play_button, pause_button, stop_btn, homebutton;

    Chronometer timer;
    CountDownTimer countDownTimer;
    private static final long StartTime = 11000;

    private long TimeLeft = StartTime;
    boolean StartCountDown = false;
    boolean CountDownProcess = false;

    double distance = 0;
    double current_speed;


    RadioGroup distancebtn_group;
    RadioButton miles_btn;
    RadioButton kilometer_btn;


    public static final String MILESBTN = "miles_btn";
    public static final String KMBTN = "kilometer_btn";
    public static final String Shared_info = "info";
    public static String DBG = "distancebtn_group";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.run_interface);

        distance_counter = (TextView) findViewById(R.id.distance_counter);
        play_button = (Button) findViewById(R.id.play_button);
        pause_button = (Button) findViewById(R.id.pause_button);
        stop_btn = (Button) findViewById(R.id.stop_btn);
        SpdInmph = (TextView) findViewById(R.id.spdInmph);
        SpdInkmh = (TextView) findViewById(R.id.speedInkmh);
        timer = (Chronometer) findViewById(R.id.timer);
        distancebtn_group = (RadioGroup) findViewById(R.id.distance_btn_group);
        miles_btn = (RadioButton) findViewById(R.id.miles_btn);
        kilometer_btn = (RadioButton) findViewById(R.id.kilometer_btn);
        distancebtn_group = findViewById(R.id.distance_btn_group);
        homebutton = (Button) findViewById(R.id.homebutton);
        CountDownTimerView = (TextView) findViewById(R.id.CountDownTimerView);

        CountDownSwitch();


        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        NetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        GPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        timer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                if ((SystemClock.elapsedRealtime() - timer.getBase()) >= 86400000) {
                    chronometer.setBase(SystemClock.elapsedRealtime());
                }
            }
        });

        homebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(run_interface.this, home.class);
                startActivity(intent);


        final int LocationFinePermission = ContextCompat.checkSelfPermission(run_interface.this, Manifest.permission.ACCESS_FINE_LOCATION);

        play_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
                fusionprovider = LocationServices.getFusedLocationProviderClient(run_interface.this);
                timer.setBase(SystemClock.elapsedRealtime());
                if (LocationFinePermission != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(run_interface.this, new String[]
                                    {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                            Permission_Request_Code);
                    LocationSwitch();
                } else {
                    if (!active) {
                        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 0, run_interface.this);
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 7000, 0, run_interface.this);
                        createLocationRequest();
                        timer.setBase(SystemClock.elapsedRealtime() - update);
                        timer.start();
                        play_button.setVisibility(View.GONE);
                        active = true;
                    } else {
                        play_button.setVisibility(View.VISIBLE);
                        pause_button.setVisibility(View.GONE);
                        active = false;


                    }

                    pause_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (active) {
                                timer.stop();
                                active = false;
                                play_button.setVisibility(View.VISIBLE);
                                update = SystemClock.elapsedRealtime() - timer.getBase();
                                locationManager.removeUpdates(run_interface.this);

                            }
                        }
                    });

                    stop_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            timer.stop();
                            active = false;
                            update = 0;
                            timer.setBase(SystemClock.elapsedRealtime());
                            play_button.setVisibility(View.VISIBLE);
                            locationManager.removeUpdates(run_interface.this);
                            start_location = null;
                            end_location = null;
                            distance = 0;
                            current_speed = 0;

                        }
                    });
                }



                    }
                });
            }
        });
    }
    private void createLocationRequest() {
        locationRequest = LocationRequest.create();
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        RadioButton activeButton = (RadioButton) findViewById(checkedId);
        if (activeButton.getId() == R.id.miles_btn) {
            distanceInMiles();
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


    private void distanceInMeters() {
        distance = distance + (start_location.distanceTo(end_location));
        start_location = end_location;

    }

    private void distanceInMiles() {
        distance = distance + (start_location.distanceTo(end_location) * 0.00062137);
        distance_counter.setText(new DecimalFormat("0.00").format(distance) + " Miles");


    }

    private void distanceInkilometers() {
        distance = distance + (start_location.distanceTo(end_location) / 1000);
        start_location = end_location;
        distance_counter.setText(new DecimalFormat("0.00").format(distance) + " km");


    }


    private void LocationSwitch() {
        if (!NetworkEnabled && !GPSEnabled)
            EnableGPSAlertBox();

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == Permission_Request_Code) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            } else {
                Toast.makeText(this, "Allow permission & enable GPS. If you've allowed permission, just enable GPS", Toast.LENGTH_LONG).show();

            }
        }
    }


    public void EnableGPSAlertBox() {
        AlertDialog.Builder dialogbuilder = new AlertDialog.Builder(run_interface.this);
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

    private void ChooseMetricUnits() {
        final SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(GetInfo, MODE_PRIVATE);
        final Boolean MetricUnit = sharedPreferences.getBoolean(settings.KMBTN, true);
        if (MetricUnit) {
            distanceInkilometers();
        } else
            distanceInMiles();
    }

    private void StartCountDown() {
        countDownTimer = new CountDownTimer(TimeLeft, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                TimeLeft = millisUntilFinished;
                CountDownViewUpdate();

            }


            @Override
            public void onFinish() {
                active = false;
                AutoStartActivity();
                CountDownTimerView.setVisibility(View.GONE);


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
        final boolean TimerSwitch = sharedPreferences.getBoolean(settings.ON, true);
        if (TimerSwitch) {
            StartCountDown();
        } else
            CountDownTimerView.setVisibility(View.GONE);

    }

    private void AutoStartActivity() {
        if (Build.VERSION.SDK_INT >= 23) {
            play_button.performContextClick();
            View playButton = findViewById(R.id.play_button);
            playButton.performClick();

        }
    }
}





































