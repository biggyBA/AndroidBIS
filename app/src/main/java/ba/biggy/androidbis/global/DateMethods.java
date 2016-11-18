package ba.biggy.androidbis.global;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import ba.biggy.androidbis.Constants;

public class DateMethods {


    public String getTodayDateForMysql(){
        SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_MYSQL_FORMAT);
        String currentDate = sdf.format(new Date());
        return currentDate;
    }


    /*
     * this method get a date as input with format dd.MM.yyyy which is shown to the user
     * it returns a date with format yyyy-MM-dd which is used by mysql
     */
    public String formatDateFromViewToMysql(String date){
        String formatedDate = "";
        SimpleDateFormat viewFormat = new SimpleDateFormat(Constants.DATE_SHOW_FORMAT);
        SimpleDateFormat mysqlFormat = new SimpleDateFormat(Constants.DATE_MYSQL_FORMAT);

        try {
            formatedDate = mysqlFormat.format(viewFormat.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return formatedDate;
    }


    /*
     * this method get a date as input with format yyyy-MM-dd which is used in mysql
     * it returns a date with format dd.MM.yyyy which is showed to the user
     */
    public String formatDateFromMysqlToView(String date){
        String formatedDate = "";
        SimpleDateFormat viewFormat = new SimpleDateFormat(Constants.DATE_SHOW_FORMAT);
        SimpleDateFormat mysqlFormat = new SimpleDateFormat(Constants.DATE_MYSQL_FORMAT);

        try {
            formatedDate = viewFormat.format(mysqlFormat.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return formatedDate;
    }


}
