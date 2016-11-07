package ba.biggy.androidbis.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

public class PhoneStateReceiver extends BroadcastReceiver {
    public PhoneStateReceiver() {
    }

    @Override
    public void onReceive(final Context context, Intent intent) {
       /* TelephonyManager mtelephony = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        mtelephony.listen(new PhoneStateListener(){
            @Override
            public void onCallStateChanged(int state, String incomingNumber) {
                super.onCallStateChanged(state, incomingNumber);
                switch (state) {
                    case TelephonyManager.CALL_STATE_RINGING:
                        // CALL_STATE_RINGING
                        Log.d("MyLittleDebugger", "I'm in " + state + " and the number is " + incomingNumber);
                        Toast.makeText(context, incomingNumber,
                                Toast.LENGTH_LONG).show();
                        Toast.makeText(context, "CALL_STATE_RINGING",
                                Toast.LENGTH_LONG).show();
                        break;
                    default:
                        break;
                }
            }
        },PhoneStateListener.LISTEN_CALL_STATE);*/


        Bundle extras = intent.getExtras();
        if (extras != null) {
            String state = extras.getString(TelephonyManager.EXTRA_STATE);
            Log.w("MY_DEBUG_TAG", state);
            if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
                String phoneNumber = extras
                        .getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
                Log.w("MY_DEBUG_TAG", phoneNumber);
                Toast.makeText(context, phoneNumber,
                        Toast.LENGTH_LONG).show();
            }
        }
    }
}
