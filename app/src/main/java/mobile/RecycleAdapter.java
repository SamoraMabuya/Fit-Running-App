package mobile;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import mobile.apps.R;

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.RecyclerViewHolder> {

    public ArrayList<RetrieveRunnerActivity> arrayList;

    public RecycleAdapter(ArrayList<RetrieveRunnerActivity> arrayList, Context context, Cursor cursor, OnItemClickListener deleteListener, Context context1) {
        this.arrayList = arrayList;
        this.context = context;
        this.cursor = cursor;
        this.deleteListener = deleteListener;
        this.context = context1;
    }

    private Context context;
    private Cursor cursor;


    private OnItemClickListener deleteListener;


    public interface OnItemClickListener {
        void onDeleteClick(int position);


    }


    public void setItemListener(OnItemClickListener listener) {
        deleteListener = listener;


    }

    public RecycleAdapter(ArrayList<RetrieveRunnerActivity> arrayList) {
        this.arrayList = arrayList;


    }


    private RecycleAdapter(Context mycontext) {
        this.context = mycontext;
    }


    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context mycontext = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(mycontext);
        View view = inflater.inflate(R.layout.history, parent, false);
        RecyclerViewHolder RVH = new RecyclerViewHolder(view, deleteListener, mycontext, arrayList);

        return RVH;
    }


    @Override
    public void onBindViewHolder(RecycleAdapter.RecyclerViewHolder holder, int position) {

        RetrieveRunnerActivity retrieveRunnerActivity = arrayList.get(position);
        holder.Duration_column.setText(String.valueOf(retrieveRunnerActivity.getElapsed_time()));
        holder.Distance_column.setText(String.valueOf(retrieveRunnerActivity.getTotal_distance()));
        holder.date_heading.setText(String.valueOf(retrieveRunnerActivity.getDate()));
        holder.Entry_column.setText(String.valueOf(retrieveRunnerActivity.getId()));
        holder.avg_spd_column.setText(String.valueOf(retrieveRunnerActivity.getAverage_speed()));
        holder.deleteButton.getContext();

    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        ImageView deleteButton;
        TextView Duration_column, Distance_column, date_heading, Entry_column, avg_spd_column;
        RecyclerViewHolder RVH;
        RunoraDatabaseHelper Runora_database;
        RetrieveRunnerActivity retrieveRunnerActivity;


        RecyclerViewHolder(final View view, final OnItemClickListener listener, final Context mycontext, final ArrayList arrayList) {

            super(view);
            Duration_column = (TextView) view.findViewById(R.id.Duration_column);
            Distance_column = (TextView) view.findViewById(R.id.Distance_column);
            date_heading = (TextView) view.findViewById(R.id.date_heading);
            Entry_column = (TextView) view.findViewById(R.id.Entry_column);
            deleteButton = (ImageView) view.findViewById(R.id.deleteButton);
            avg_spd_column = (TextView) view.findViewById(R.id.avg_spd_column);
            deleteButton.setTag(deleteListener);
            final RecycleAdapter this_adapter = new RecycleAdapter(mycontext);
            final RecycleAdapter adapter = new RecycleAdapter(arrayList);
            Runora_database = new RunoraDatabaseHelper(mycontext);



            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            Runora_database.DeleteData(Entry_column.getText().toString());
                            listener.onDeleteClick(position);
                            adapter.notifyDataSetChanged();


                        }
                    }
                }

            });
        }
    }
}