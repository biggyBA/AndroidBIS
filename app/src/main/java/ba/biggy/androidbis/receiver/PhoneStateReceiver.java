package ba.biggy.androidbis.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import ba.biggy.androidbis.Constants;
import ba.biggy.androidbis.MainActivity;
import ba.biggy.androidbis.POJO.Phonecall;
import ba.biggy.androidbis.fragments.FragmentAddFault;

public class PhoneStateReceiver extends BroadcastReceiver {

    private SharedPreferences pref;


    public PhoneStateReceiver() {
    }

    @Override
    public void onReceive(final Context context, final Intent intent) {

        TelephonyManager mtelephony = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        mtelephony.listen(new PhoneStateListener(){
            @Override
            public void onCallStateChanged(int state, String incomingNumber) {
                super.onCallStateChanged(state, incomingNumber);
                switch (state) {
                    case TelephonyManager.CALL_STATE_RINGING:
                        // CALL_STATE_RINGING
                        //Log.d("MyLittleDebugger", "I'm in " + state + " and the number is " + incomingNumber);
                        //Toast.makeText(context, incomingNumber, Toast.LENGTH_LONG).show();
                        pref = context.getSharedPreferences(Constants.PREF, 0);
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString("phone", incomingNumber);
                        editor.apply();


                        break;


                    case TelephonyManager.CALL_STATE_OFFHOOK:



                        //Toast.makeText(context, "Talking to " + incomingNumber, Toast.LENGTH_LONG).show();


                        break;
                    default:
                        break;
                }
            }
        },PhoneStateListener.LISTEN_CALL_STATE);


       /* Bundle extras = intent.getExtras();
        if (extras != null) {
            String state = extras.getString(TelephonyManager.EXTRA_STATE);
            Log.w("MY_DEBUG_TAG", state);
            if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
                String phoneNumber = extras.getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
                Log.w("MY_DEBUG_TAG", phoneNumber);
                Toast.makeText(context, phoneNumber, Toast.LENGTH_LONG).show();


            }
        }*/
    }
}
