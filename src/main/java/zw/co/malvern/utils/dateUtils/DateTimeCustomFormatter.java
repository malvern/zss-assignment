package zw.co.malvern.utils.dateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeCustomFormatter {
    public static Date formatStringToDate(String date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        try {
            return formatter.parse(date.replaceAll("Z$", "+0200"));
        } catch (ParseException e) {
            throw new RuntimeException(e.getMessage());
        }

    }
}
