package ba.biggy.androidbis.global;


import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.widget.Toast;

public class PhoneMethods {

    private Context context;
    String phoneNumber = "";

    public PhoneMethods(Context context){
        this.context=context;
    }

    public String getPhonenumber(){

        TelephonyManager mtelephony = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        mtelephony.listen(new PhoneStateListener(){
            @Override
            public void onCallStateChanged(int state, String incomingNumber) {
                super.onCallStateChanged(state, incomingNumber);
                switch (state) {
                    case TelephonyManager.CALL_STATE_RINGING:
                        // CALL_STATE_RINGING
                        phoneNumber = incomingNumber;
                        Toast.makeText(context, incomingNumber, Toast.LENGTH_LONG).show();
                        break;
                    default:
                        break;
                }
            }

        },PhoneStateListener.LISTEN_CALL_STATE);
        return phoneNumber;

    }


}
