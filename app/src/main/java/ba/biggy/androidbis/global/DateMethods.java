package ba.biggy.androidbis.global;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateMethods {


    public String getTodayDateForMysql(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = sdf.format(new Date());
        return currentDate;
    }


    /*
     * this method get a date as input with format dd.MM.yyyy which is shown to the user
     * it returns a date with format yyyy-MM-dd which is used by mysql
     */
    public String formatDateFromViewToMysql(String date){
        String formatedDate = "";
        SimpleDateFormat viewFormat = new SimpleDateFormat("dd.MM.yyyy");
        SimpleDateFormat mysqlFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            formatedDate = mysqlFormat.format(viewFormat.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return formatedDate;
    }


}
