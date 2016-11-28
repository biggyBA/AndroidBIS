package ba.biggy.androidbis.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.os.Bundle;
import android.provider.CallLog;
import android.support.design.widget.Snackbar;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import ba.biggy.androidbis.Constants;
import ba.biggy.androidbis.MainActivity;
import ba.biggy.androidbis.POJO.Phonecall;
import ba.biggy.androidbis.SQLite.PhonecallsTableController;
import ba.biggy.androidbis.fragments.FragmentAddFault;

public class PhoneStateReceiver extends BroadcastReceiver {

    private SharedPreferences pref;
    private PhonecallsTableController phonecallsTableController;
    private Phonecall phonecall;

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

                        pref = context.getSharedPreferences(Constants.PREF, 0);
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString(Constants.SP_PHONE, incomingNumber);
                        editor.apply();
                        //Toast.makeText(context, incomingNumber, Toast.LENGTH_LONG).show();

                        break;

                    case TelephonyManager.CALL_STATE_IDLE:

                        phonecallsTableController = new PhonecallsTableController();
                        phonecall = new Phonecall();
                        String phNumber = "";
                        String callDuration = "";
                        String reportDate = "";
                        /*Cursor cur = context.getContentResolver().query( CallLog.Calls.CONTENT_URI,null, null,null, android.provider.CallLog.Calls.DATE + " DESC limit 1;");

                        int number = cur.getColumnIndex( CallLog.Calls.NUMBER );
                        int duration = cur.getColumnIndex( CallLog.Calls.DURATION);
                        int countryISO = cur.getColumnIndex(CallLog.Calls.COUNTRY_ISO);
                        int dat = cur.getColumnIndex(CallLog.Calls.DATE);

                        while ( cur.moveToFirst() ) {
                            phNumber = cur.getString( number );
                            callDuration = cur.getString( duration );
                            String countryIS = cur.getString(countryISO);
                            String da = cur.getString(dat);

                            long date1 = Long.valueOf(da).longValue();
                            Date date = new Date(date1);
                            DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
                            reportDate = df.format(date);







                            break;
                        }

                        *//*phonecall.setPhoneNumber(phNumber);
                        phonecall.setDuration(callDuration);
                        phonecall.setDate(reportDate);
                        phonecallsTableController.insertPhonecall(phonecall);*//*
                        String c = DatabaseUtils.dumpCursorToString(cur);
                        cur.close();
                        Log.d("LOOOOOOOOOOOOOOOOOOOOG", c);
                        //Toast.makeText(context, c, Toast.LENGTH_LONG).show();*/

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
