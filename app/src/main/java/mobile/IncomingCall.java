package mobile;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.view.Gravity;
import android.widget.Toast;

public class IncomingCall extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getStringExtra(TelephonyManager.EXTRA_STATE).equals(TelephonyManager.EXTRA_STATE_OFFHOOK)) {
            showMessage(context, "Active call");

        } else if (intent.getStringExtra(TelephonyManager.EXTRA_STATE).equals(TelephonyManager.EXTRA_STATE_RINGING)) {
            showMessage(context, "Incoming call");

        } else if (intent.getStringExtra(TelephonyManager.EXTRA_STATE).equals(TelephonyManager.EXTRA_STATE_IDLE)) {
            showMessage(context, "Call Ended");



        }
    }

    void showMessage(Context context, String message) {
        Toast toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}

