package miage.nanterre.m1app.realtimekeynote.helpers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;

public class Helper {
    public static Date getDateFromString(String dateString) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(format.parse(dateString).getTime());
        return date;
    }
    public static Time getTimeFromString(String timeString){
        String[] partsTime = timeString.split(":");
        int[] time = Arrays.stream(partsTime).mapToInt(Integer::parseInt).toArray();

        if (time.length==2) {
            time[2] = 00;
        }

        return new Time(time[0], time[1], time[2]);
    }

}
