package Mobile.apps;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private Button Login_button;
    private Button create_account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Login_button = (Button) findViewById(R.id.Login_button);
        Login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openlog_in();
            }
        });

        create_account = (Button) findViewById(R.id.create_account);
        create_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                opencreate_account_section();
            }
        });

    }


    public void openlog_in() {
        Intent intent = new Intent(this, log_in.class);
        startActivity(intent);

    }

    public void opencreate_account_section() {
        Intent intent = new Intent(this, create_account_section.class);
        startActivity(intent);
    }

}
