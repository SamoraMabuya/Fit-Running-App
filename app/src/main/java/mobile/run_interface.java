package mobile;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.text.DecimalFormat;

import mobile.apps.R;

import static com.google.android.gms.location.LocationServices.FusedLocationApi;


public class run_interface extends AppCompatActivity implements LocationListener, android.location.LocationListener {

    FusedLocationProviderClient fusionprovider;
    LocationManager locationManager;
    LocationCallback locationCallback;
    LocationServices locationServices;
    LocationRequest locationRequest;
    LocationListener locationListener;
    Location start_location, end_location, curr_location, location;
    private static final int Permission_Request_Code = 103;

    boolean active;
    long update;
    TextView distance_counter, curr_speed, avg_speed;
    Button play_button, pause_button, stop_btn;
    Chronometer timer;
    double distance = 0;
    double current_speed = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.run_interface);


        distance_counter = (TextView) findViewById(R.id.distance_counter);
        play_button = (Button) findViewById(R.id.play_button);
        pause_button = (Button) findViewById(R.id.pause_button);
        stop_btn = (Button) findViewById(R.id.stop_btn);
        curr_speed = (TextView) findViewById(R.id.curr_speed);
        avg_speed = (TextView) findViewById(R.id.avg_speed);
        timer = (Chronometer) findViewById(R.id.timer);

        final int LocationFinePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);


        timer.setBase(SystemClock.elapsedRealtime());


        timer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                if ((SystemClock.elapsedRealtime() - timer.getBase()) >= 86400000) {
                    chronometer.setBase(SystemClock.elapsedRealtime());
                }
            }
        });

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        fusionprovider = LocationServices.getFusedLocationProviderClient(run_interface.this);


        play_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if (LocationFinePermission != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(run_interface.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, Permission_Request_Code);
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
                                start_location = null;
                                end_location = null;
                                distance = 0;
                            }
                        });

                    }
            }

            private void createLocationRequest() {
                locationRequest = LocationRequest.create();
                locationRequest.setInterval(5000);
                locationRequest.setFastestInterval(5000);
                locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            }
        });
    }

    @Override
    public void onLocationChanged(Location location) {
        curr_location = location;
        if (start_location == null) {
            start_location = curr_location;
            end_location = curr_location;
        } else
            end_location = curr_location;

        distanceUI();
        distance_counter.setText(new DecimalFormat("#.##").format(distance));

        speedUI();
        curr_speed.setText(new DecimalFormat("#.##").format(current_speed));

        averagespeed_UI();
        avg_speed.setText(new DecimalFormat("#.##").format(current_speed));

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {
            if (provider.equals(LocationManager.GPS_PROVIDER)) {
            BackToActivity();
            }
        }


    @Override
    public void onProviderDisabled(String provider) {
        if (!provider.equals(LocationManager.NETWORK_PROVIDER)) {
            onStop();
            if (!provider.equals(LocationManager.GPS_PROVIDER)) {
                onStop();

            }
        }
    }


    private void distanceUI() {
        distance = distance + (start_location.distanceTo(end_location) / 1000.00);
        start_location = end_location;

    }

    private void speedUI() {


    }

    private void averagespeed_UI() {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == Permission_Request_Code) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                EnableGPSAlertBox();
            } else {
                Toast.makeText(run_interface.this, "Allow permission & enable GPS. If you've allowed permission, just enable GPS", Toast.LENGTH_LONG).show();

            }
        }
    }

    private void EnableGPSAlertBox() {
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

    private void BackToActivity() {
        Intent intent = new Intent(run_interface.this, run_interface.class);
        startActivity(intent);
    }

    @Override
    protected void onStop() {
        super.onStop();
        timer.stop();
        active = false;
        update = 0;
        timer.setBase(SystemClock.elapsedRealtime());
        play_button.setVisibility(View.VISIBLE);
        start_location = null;
        end_location = null;
        distance = 0;
    }
}






























