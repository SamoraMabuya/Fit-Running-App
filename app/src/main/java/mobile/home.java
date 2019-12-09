package mobile;

import android.Manifest;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.File;

import mobile.apps.R;

import static android.provider.MediaStore.INTENT_ACTION_MEDIA_PLAY_FROM_SEARCH;
import static android.provider.MediaStore.INTENT_ACTION_MEDIA_SEARCH;

public class home extends AppCompatActivity implements LocationListener, com.google.android.gms.location.LocationListener {

    private static final int STORAGE_PERMISSION_CODE = 58;


    Button start_btn;
    Button music_btn;
    Button settings_btn;
    LocationManager locationManager;
    Location location;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        start_btn = (Button) findViewById(R.id.start_btn);
        music_btn = (Button) findViewById(R.id.music_btn);
        settings_btn = (Button) findViewById(R.id.settings_btn);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);


        start_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(home.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(home.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(home.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, STORAGE_PERMISSION_CODE);

                } else {

                    if (ActivityCompat.checkSelfPermission(home.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(home.this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        startRun_interface();

                    }

                }
            }
        });
        music_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(INTENT_ACTION_MEDIA_PLAY_FROM_SEARCH);
                startActivity(intent);
            }
        });

        settings_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(home.this, settings.class);
                startActivity(intent);


            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                requestStoragePermission();

            } else {

                if (grantResults.length == 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(home.this, "Allow permission & enable GPS. If you've allowed permission, just enable GPS", Toast.LENGTH_LONG).show();

                }
            }
        }
    }


    private void requestStoragePermission() {
        androidx.appcompat.app.AlertDialog.Builder alertDialogBuilder = new androidx.appcompat.app.AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Location");
        alertDialogBuilder.setMessage("Please enable GPS to continue?")
                .setPositiveButton("Turn on location", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent callGPSSettingIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(callGPSSettingIntent);
                    }
                });
        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }


    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {
        if (!provider.equals(LocationManager.NETWORK_PROVIDER))
            backtohome();
        if (!provider.equals(LocationManager.GPS_PROVIDER))
            backtohome();

    }


    public void backtohome() {
        Intent intent = new Intent(home.this, home.class);
        startActivity(intent);

    }


    private void startRun_interface() {
        Intent intent = new Intent(home.this, run_interface.class);
        startActivity(intent);

    }


    @Override
    public void onProviderDisabled(final String provider) {
        if (!provider.equals(LocationManager.NETWORK_PROVIDER)) {
            backtohome();
            if (!provider.equals(LocationManager.GPS_PROVIDER)) {
                backtohome();
            }
        }
    }
}

