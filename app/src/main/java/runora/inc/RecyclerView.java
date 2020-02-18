package runora.inc;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import runora.RecycleAdapter;

public class RecyclerView extends AppCompatActivity {

    private SQLiteDatabase sqLiteDatabase;
    private RecycleAdapter recycleAdapter;
    private TextView EmptyTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_view_layout);



    }
}
