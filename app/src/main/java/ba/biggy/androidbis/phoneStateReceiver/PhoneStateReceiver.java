package ba.biggy.androidbis.phoneStateReceiver;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import ba.biggy.androidbis.MainActivity;

public class PhoneStateReceiver extends BroadcastReceiver {

    TelephonyManager telephony;


    @Override
    public void onReceive(Context context, Intent intent) {

        MyPhoneStateListener phoneListener = new MyPhoneStateListener();
        telephony = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        telephony.listen(phoneListener, PhoneStateListener.LISTEN_CALL_STATE);

    }



}

