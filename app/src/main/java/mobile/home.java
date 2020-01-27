package mobile;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.text.DateFormat;
import java.util.Calendar;

import mobile.apps.About;
import mobile.apps.DisplayHistory;
import mobile.apps.R;


public class home extends AppCompatActivity implements LocationListener, com.google.android.gms.location.LocationListener {

    private static final int PermissionCode = 58;


    ImageView music_IV_btn, settings_IV_btn, history_IV_btn, about_image_view;
    LocationManager locationManager;
    TextView HomePageDate;
    Button history_btn, music_btn, settings_btn, start_btn, about_btn;


    public static final String CATEGORY_APP_MUSIC = "android.intent.action.MUSIC_PLAYER";

    String show_date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        start_btn = (Button) findViewById(R.id.start_btn);
        history_btn = (Button) findViewById(R.id.history_btn);
        music_btn = (Button) findViewById(R.id.music_btn);
        settings_btn = (Button) findViewById(R.id.settings_btn);
        about_btn = (Button) findViewById(R.id.about_btn);


        music_IV_btn = (ImageView) findViewById(R.id.music_imageview_btn);
        settings_IV_btn = (ImageView) findViewById(R.id.settings_imageview_btn);
        history_IV_btn = (ImageView) findViewById(R.id.history_imageview_btn);
        about_image_view = (ImageView) findViewById(R.id.about_imageView);


        HomePageDate = (TextView) findViewById(R.id.HomePageDate);

        CalenderDate();


        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        history_IV_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(home.this, DisplayHistory.class));

            }
        });

        history_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(home.this, DisplayHistory.class));

            }
        });


        settings_IV_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(home.this, settings.class);
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

        music_IV_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CATEGORY_APP_MUSIC);
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

        about_image_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(home.this, About.class);
                startActivity(intent);
            }
        });

        about_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(home.this, About.class);
                startActivity(intent);

            }
        });


        start_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(home.this,
                        Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(home.this,
                        Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                        && ContextCompat.checkSelfPermission(home.this,
                        Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED && locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    startRun_interface();
                } else {
                    LocationPermissionRequest();
                }
            }
        });
    }

    private void LocationPermissionRequest() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(home.this, Manifest.permission.ACCESS_FINE_LOCATION)) {

            new AlertDialog.Builder(this)
                    .setTitle("Location Permission Required")
                    .setMessage("Please allow permission access to proceed.")
                    .setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            ActivityCompat.requestPermissions(home.this,
                                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                                            Manifest.permission.ACCESS_COARSE_LOCATION,
                                            Manifest.permission.ACCESS_BACKGROUND_LOCATION,
                                            Manifest.permission.READ_PHONE_STATE}, PermissionCode);
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
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_BACKGROUND_LOCATION,
                            Manifest.permission.READ_PHONE_STATE}, PermissionCode);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PermissionCode) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                LocationAlert();
            } else {
                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();

            }
        }

    }

    public void LocationAlert() {
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

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public void onProviderEnabled(String provider) {

    }


    private void startRun_interface() {
        Intent intent = new Intent(home.this, run_interface.class);
        startActivity(intent);

    }


    @Override
    public void onProviderDisabled(final String provider) {

    }

    public void CalenderDate() {
        Calendar calendar = Calendar.getInstance();
        HomePageDate.setText(DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime()));
    }
}






