package mobile;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import mobile.apps.R;

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.RecyclerViewHolder> {
    ArrayList<RetrieveRunnerActivity> arrayList = new ArrayList<>();
    public RecycleAdapter(ArrayList<RetrieveRunnerActivity> arrayList)
    {
        this.arrayList = arrayList;
    }
    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history,parent,false);
        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(view);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        RetrieveRunnerActivity retrieveRunnerActivity = arrayList.get(position);
        holder.Duration_column.setText(String.valueOf(retrieveRunnerActivity.getElapsed_time()));
        holder.Distance_column.setText(String.valueOf(retrieveRunnerActivity.getTotal_distance()));
        holder.date_heading.setText(String.valueOf(retrieveRunnerActivity.getDate()));


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder

    {
        TextView Duration_column, Distance_column, date_heading;
         RecyclerViewHolder( View view)
         {
            super(view);
            Duration_column = (TextView)view.findViewById(R.id.Duration_column);
            Distance_column = (TextView)view.findViewById(R.id.Distance_column);
            date_heading = (TextView)view.findViewById(R.id.date_heading);





         }
    }
}
