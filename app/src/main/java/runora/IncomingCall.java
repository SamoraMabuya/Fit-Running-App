package runora;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.view.Gravity;
import android.widget.Toast;

import java.util.Objects;


public class IncomingCall extends BroadcastReceiver {
    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    @Override
    public void onReceive(Context context, Intent intent) {

        if (Objects.requireNonNull(intent.getStringExtra(TelephonyManager.EXTRA_STATE)).equals(TelephonyManager.EXTRA_STATE_RINGING)) {
            showMessage(context, "Activity Paused");

        } else if (Objects.equals(intent.getStringExtra(TelephonyManager.EXTRA_STATE), TelephonyManager.EXTRA_STATE_OFFHOOK)) {
            showMessage(context, "Active Call");

        } else if (Objects.equals(intent.getStringExtra(TelephonyManager.EXTRA_STATE), TelephonyManager.EXTRA_STATE_IDLE)) {
            showMessage(context, "Resume Activity");

        }

    }

    void showMessage(Context context, String message) {
        Toast toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

}


