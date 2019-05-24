package miage.nanterre.m1app.realtimekeynote.helpers;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Helper {
    public static java.sql.Date getDateFromString(String dateString) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        java.sql.Date date = new java.sql.Date(format.parse(dateString).getTime());
        return date;
    }
    public static java.sql.Time getTimeFromString(String timeString){
        String[] partsTime = timeString.split(":");
        return new java.sql.Time((Integer.parseInt(partsTime[0])*3600 +
                Integer.parseInt(partsTime[1])*60)*1000);
    }
}
