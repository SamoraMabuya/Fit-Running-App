package mobile.apps;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import mobile.RecycleAdapter;
import mobile.RetrieveRunnerActivity;
import mobile.RunoraDatabaseHelper;

import static mobile.RunoraDatabaseHelper.TABLE_NAME;

public class history extends AppCompatActivity {


    ImageView deleteButton;
    TextView Duration_column, Distance_column, date_heading;
    RecycleAdapter.RecyclerViewHolder RVH;
    RunoraDatabaseHelper Runora_database;
    RetrieveRunnerActivity retrieveRunnerActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history);


        Duration_column = (TextView) findViewById(R.id.Duration_column);
        Distance_column = (TextView) findViewById(R.id.Distance_column);
        date_heading = (TextView) findViewById(R.id.date_heading);
        deleteButton = (ImageView) findViewById(R.id.deleteButton);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Runora_database.DeleteData(Duration_column.getText().toString());
                Runora_database.DeleteData(TABLE_NAME);

            }
        });
    }
}