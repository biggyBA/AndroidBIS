package ba.biggy.androidbis.global;


import java.text.SimpleDateFormat;
import java.util.Date;

public class DateMethods {


    public String getDateForMysql(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = sdf.format(new Date());
        return currentDate;
    }


}
