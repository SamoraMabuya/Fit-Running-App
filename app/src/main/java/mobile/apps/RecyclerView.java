package mobile.apps;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class RecyclerView extends androidx.recyclerview.widget.RecyclerView {

    private AdapterDataObserver adapterDataObserver = new AdapterDataObserver() {
        @Override
        public void onChanged() {
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, @Nullable Object payload) {
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
        }
    };


    public RecyclerView(@NonNull Context context) {
        super(context);
    }

    public RecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public static void ShowAvailable(androidx.recyclerview.widget.RecyclerView recyclerView) {
    }
    public static void NoActivity(View showEmptyActivity) {

}


    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);
        if (adapter != null) {
            adapter.registerAdapterDataObserver(adapterDataObserver);
            
        }
        
        adapterDataObserver.onChanged();
    }


    }


