package mobile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import mobile.apps.R;

import static android.provider.MediaStore.INTENT_ACTION_MEDIA_PLAY_FROM_SEARCH;
import static android.provider.MediaStore.INTENT_ACTION_MEDIA_SEARCH;

public class settings extends AppCompatActivity {

    Button go_back_btn;
    Button music_btn;
    Button settings_btn;

    RadioGroup distancebtn_group;
    RadioButton miles_btn;
    RadioButton kilometer_btn;
    RadioButton distance_button_clicked;
    RadioButton radioButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        go_back_btn = (Button) findViewById(R.id.go_back_btn);
        music_btn = (Button) findViewById(R.id.music_btn);
        settings_btn = (Button) findViewById(R.id.settings_btn);
        distancebtn_group = findViewById(R.id.distance_btn_group);


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
    }

    public void buttonCheck(View view) {

        int radioId = distancebtn_group.getCheckedRadioButtonId();

        radioButton = findViewById(radioId);


    }
}




