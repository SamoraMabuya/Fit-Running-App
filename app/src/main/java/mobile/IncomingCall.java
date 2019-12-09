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

        try {

            String calling_state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
            if (calling_state.equals(TelephonyManager.CALL_STATE_RINGING)) {
                Toast.makeText(context, "Incoming Call", Toast.LENGTH_SHORT).show();
            }

            if (calling_state.equals(TelephonyManager.CALL_STATE_OFFHOOK)) {
                Toast.makeText(context, "Incoming Call", Toast.LENGTH_SHORT).show();
            }

            if (calling_state.equals(TelephonyManager.CALL_STATE_IDLE)) {
                Toast.makeText(context, "Incoming Call", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}