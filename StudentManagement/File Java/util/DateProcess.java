package util;

import java.util.Calendar;
import java.util.Date;

public class DateProcess {
    
    public static String getDayOfWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        switch (calendar.get(Calendar.DAY_OF_WEEK)) {
            case Calendar.SUNDAY:
                return "CN";
            case Calendar.MONDAY:
                return "Thứ 2";
            case Calendar.TUESDAY:
                return "Thứ 3";
            case Calendar.WEDNESDAY:
                return "Thứ 4";
            case Calendar.THURSDAY:
                return "Thứ 5";
            case Calendar.FRIDAY:
                return "Thứ 6";
            default:
                return "Thứ 7";
        }
    }
    
    public static Date addDay(Date date, int nDays) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        
        calendar.add(Calendar.DATE, nDays);
        
        return calendar.getTime();
    }
    
    public static Date addMinute(Date date, int nMinutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        
        calendar.add(Calendar.MINUTE, nMinutes);
        
        return calendar.getTime();
    }
    
    public static int compare(Date date1, Date date2) {
        return date1.compareTo(date2);
    }
    
    public static Date timeToDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);
        int sec = calendar.get(Calendar.SECOND);
        int millisec = calendar.get(Calendar.MILLISECOND);
        
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, min);
        calendar.set(Calendar.SECOND, sec);
        calendar.set(Calendar.MILLISECOND, millisec);
        
        return calendar.getTime();
    }
    
    public static Date combineDateAndTime(Date date, Date time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);
        int sec = calendar.get(Calendar.SECOND);
        int millisec = calendar.get(Calendar.MILLISECOND);
        
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, min);
        calendar.set(Calendar.SECOND, sec);
        calendar.set(Calendar.MILLISECOND, millisec);
        
        return calendar.getTime();
    }
    
    public static Date getStartOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }
    
    public static int distanceOf(Date startDate, Date endDate) {
        long distance = endDate.getTime() - startDate.getTime(); // milliseconds
        int numberDays =  (int) (distance / (24* 1000 * 60 * 60));
        numberDays = (numberDays / 7) + 1;
        
        return numberDays;
    }
    
}
