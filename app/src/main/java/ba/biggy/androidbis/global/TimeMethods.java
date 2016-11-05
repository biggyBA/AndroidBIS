package ba.biggy.androidbis.global;


import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeMethods {

    public String getCurrentTime(){
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String currentTime = sdf.format(new Date());
        return currentTime;
    }
}
