package mobile;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

    private Context context;
    private Cursor cursor;


    public RecycleAdapter(ArrayList<RetrieveRunnerActivity> arrayList, Context context, Cursor cursor, OnItemClickListener deleteListener) {
        this.arrayList = arrayList;
        this.context = context;
        this.cursor = cursor;
        this.deleteListener = deleteListener;
    }


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


    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.history, parent, false);
        RecyclerViewHolder RVH = new RecyclerViewHolder(view, deleteListener, context, arrayList);

        return RVH;
    }


    @Override
    public void onBindViewHolder(RecycleAdapter.RecyclerViewHolder holder, int position) {

        RetrieveRunnerActivity retrieveRunnerActivity = arrayList.get(position);
        holder.Duration_column.setText(String.valueOf(retrieveRunnerActivity.getElapsed_time()));
        holder.Distance_column.setText(String.valueOf(retrieveRunnerActivity.getTotal_distance()));
        holder.date_heading.setText(String.valueOf(retrieveRunnerActivity.getDate()));
        holder.Entry_column.setText(String.valueOf(retrieveRunnerActivity.getId()));
        holder.deleteButton.getContext();

    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        ImageView deleteButton;
        TextView Duration_column, Distance_column, date_heading, Entry_column;
        RunoraDatabaseHelper Runora_database;


        RecyclerViewHolder(final View view, final OnItemClickListener listener, final Context context, final ArrayList arrayList) {

            super(view);
            Duration_column = view.findViewById(R.id.Duration_column);
            Distance_column = view.findViewById(R.id.Distance_column);
            date_heading = view.findViewById(R.id.date_heading);
            Entry_column = view.findViewById(R.id.Entry_column);
            deleteButton = view.findViewById(R.id.deleteButton);
            deleteButton.setTag(deleteListener);
            final RecycleAdapter adapter = new RecycleAdapter(arrayList);
            Runora_database = new RunoraDatabaseHelper(context);


            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        AlertDialog.Builder dialogbuilder = new AlertDialog.Builder(context);
                        dialogbuilder.setMessage("Delete activity permanently")
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(final DialogInterface dialog, int
                                            which) {
                                        Runora_database.DeleteData(Entry_column.getText().toString());
                                        adapter.notifyDataSetChanged();
                                        listener.onDeleteClick(getAdapterPosition());

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
            });
        }
    }
}